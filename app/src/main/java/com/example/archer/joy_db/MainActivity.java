package com.example.archer.joy_db;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.fragments.SchemasListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout waitingFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.d(MY_TAG, "Main activity onCreate");

        waitingFrame = findViewById(R.id.waiting_frame);

        if(savedInstanceState == null){
            new GetSchemasTask().execute();
        }
    }

    public void setWaitingMode(){
        waitingFrame.setVisibility(View.VISIBLE);
    }

    public void desetWaitingMode(){
        waitingFrame.setVisibility(View.GONE);
    }

    public void showError(String error){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setPositiveButton("Ok", null)
                .create().show();
    }

    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    void openSchemasListFragment(List<Schema> list){
        SchemasListFragment schemasListFragment = SchemasListFragment.getNewInstance(list);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, schemasListFragment)
                //.addToBackStack("SCHEMAS_LIST")
                .commit();
    }

    class GetSchemasTask extends AsyncTask<Void, Void, String>{

        private List<Schema> list;
        private boolean isSuccessful;

        GetSchemasTask(){
            list = new ArrayList<>();
            isSuccessful = true;
        }

        @Override
        protected void onPreExecute() {
            setWaitingMode();
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
            desetWaitingMode();
            if(isSuccessful){
                openSchemasListFragment(list);
            }else{
                showError(s);
            }
        }
    }



}
