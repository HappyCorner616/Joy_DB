package com.example.archer.joy_db;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.archer.joy_db.model.Schema;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.SchemasListFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout waitingFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waitingFrame = findViewById(R.id.waiting_frame);

        new getSchemasTask().execute();

    }

    private void setWaitingMode(){
        waitingFrame.setVisibility(View.VISIBLE);
    }

    private void desetWaitingMode(){
        waitingFrame.setVisibility(View.GONE);
    }

    public void showError(String error){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setPositiveButton("Ok", null)
                .create().show();
    }

    void startSchemasListFragment(List<Schema> list){
        SchemasListFragment schemasListFragment = SchemasListFragment.getNewInstance(list);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, schemasListFragment)
                .commit();
    }

    class getSchemasTask extends AsyncTask<Void, Void, String>{

        private List<Schema> list;
        private boolean isSuccessful;

        getSchemasTask(){
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
            } catch (IOException e) {
                e.printStackTrace();
                isSuccessful = false;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            desetWaitingMode();
            if(isSuccessful){
                startSchemasListFragment(list);
            }else{
                showError(s);
            }
        }
    }

}
