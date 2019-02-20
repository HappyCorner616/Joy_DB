package com.example.archer.joy_db;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.schemas.SchemasListFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.archer.joy_db.App.MY_TAG;

public class MainActivity extends AppCompatActivity {

    private FrameLayout waitingFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waitingFrame = findViewById(R.id.waiting_frame);

        if(savedInstanceState == null){
            openSchemasListFragment();
        }
    }

    public void setWaitingMode(){
        waitingFrame.setVisibility(View.VISIBLE);
    }

    public void desetWaitingMode(){
        waitingFrame.setVisibility(View.GONE);
    }

    public void showError(String error){
        Log.d(MY_TAG, "showError: " + error);
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(error)
                .setPositiveButton("Ok", null)
                .create().show();
    }

    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    void openSchemasListFragment(){
        SchemasListFragment schemasListFragment = SchemasListFragment.getNewInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, schemasListFragment)
                //.addToBackStack("SCHEMAS_LIST")
                .commit();
    }





}
