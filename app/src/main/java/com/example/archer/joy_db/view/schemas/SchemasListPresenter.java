package com.example.archer.joy_db.view.schemas;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.archer.joy_db.App;
import com.example.archer.joy_db.interfaces.information.IInformationRepository;
import com.example.archer.joy_db.interfaces.information.IInformationRepositoryCallback;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.providers.HttpProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class SchemasListPresenter extends MvpPresenter<ISchemasListFragment> implements IInformationRepositoryCallback {

    @Inject
    IInformationRepository informationRepository;

    public SchemasListPresenter() {
        App.get().informationComponent().inject(this);
    }

    void getAllSchemas(){
        //new GetSchemasTask().execute();
        getViewState().setWaitingMode();
        informationRepository.getAllSchemas(this);
    }

    @Override
    public void getSchemas(Schemas schemas) {

        getViewState().updateSchemasList(schemas.getSchemas());
        getViewState().desetWaitingMode();
    }

    @Override
    public void showError(String error) {
        getViewState().desetWaitingMode();
        getViewState().showError(error);
    }

    class GetSchemasTask extends AsyncTask<Void, Void, String> {

        private List<Schema> list;
        private boolean isSuccessful;

        GetSchemasTask(){
            list = new ArrayList<>();
            isSuccessful = true;
        }

        @Override
        protected void onPreExecute() {
            getViewState().setWaitingMode();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                list = HttpProvider.getInstance().getSchemas();
                return "Done";
            } catch (Exception e) {
                e.printStackTrace();
                isSuccessful = false;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            getViewState().desetWaitingMode();
            if(isSuccessful){
                getViewState().updateSchemasList(list);
            }else{
                getViewState().showError(s);
            }
        }
    }

}
