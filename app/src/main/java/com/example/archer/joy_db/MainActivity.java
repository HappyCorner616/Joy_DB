package com.example.archer.joy_db;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.archer.joy_db.model.Row;
import com.example.archer.joy_db.model.Schema;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.Table;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.RowDataFragment;
import com.example.archer.joy_db.view.SchemaFragment;
import com.example.archer.joy_db.view.SchemasListFragment;
import com.example.archer.joy_db.view.TableDataFragment;
import com.example.archer.joy_db.view.TableFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.archer.joy_db.App.MY_TAG;

public class MainActivity extends AppCompatActivity implements SchemasListFragment.SchemasListFragmentListener, SchemaFragment.SchemaFragmentListener, TableFragment.TableFragmentListener, TableDataFragment.TableDataFragmentListener, RowDataFragment.RowDataFragmentListener {

    private FrameLayout waitingFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MY_TAG, "Main activity onCreate");

        waitingFrame = findViewById(R.id.waiting_frame);

        new GetSchemasTask().execute();

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
        schemasListFragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, schemasListFragment)
                //.addToBackStack("SCHEMAS_LIST")
                .commit();
    }

    @Override
    public void openSchemaFragment(Schema schema, Fragment previousFragment) {
        SchemaFragment schemaFragment = SchemaFragment.getNewInstance(schema, previousFragment);
        schemaFragment.setListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(previousFragment != null){
            transaction.detach(previousFragment);
        }
        transaction.add(R.id.container, schemaFragment)
            .addToBackStack("SCHEMA_" + schema.getName())
            .commit();
    }

    @Override
    public void openTableFragment(Table table, Fragment previousFragment) {
        TableFragment tableFragment = TableFragment.getNewInstance(table, previousFragment);
        tableFragment.setListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(previousFragment != null){
            transaction.detach(previousFragment);
        }
        transaction.add(R.id.container, tableFragment)
                .addToBackStack("TABLE_" + table.getName())
                .commit();
    }

    @Override
    public void openTableDataFragment(Table table, Fragment previousFragment) {
        TableDataFragment tableDataFragment = TableDataFragment.getNewInstance(table, previousFragment);
        tableDataFragment.setListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(previousFragment != null){
            transaction.detach(previousFragment);
        }
        transaction.add(R.id.container, tableDataFragment)
                .addToBackStack("FILLED_TABLE_" + table.getName())
                .commit();
    }

    @Override
    public void restoreFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    @Override
    public void fillTableRow(Table table, TableDataFragment tableDataFragment) {
        new FillTableTask(table, tableDataFragment).execute();
    }

    @Override
    public void openRowDataFragment(Row row, Fragment previousFragment) {
        RowDataFragment rowDataFragment = RowDataFragment.getNewInstance(row, previousFragment);
        rowDataFragment.setListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(previousFragment != null){
            transaction.detach(previousFragment);
        }
        transaction.add(R.id.container, rowDataFragment)
                .addToBackStack("ROW_DATA")
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

    class FillTableTask extends AsyncTask<Void, Void, String>{

        private Table table;
        private boolean isSuccessful;
        private TableDataFragment tableDataFragment;

        public FillTableTask(Table table, TableDataFragment tableDataFragment) {
            this.table = table;
            this.tableDataFragment = tableDataFragment;
            isSuccessful = true;
        }

        @Override
        protected void onPreExecute() {
            setWaitingMode();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                table = HttpProvider.getInstance().getTable(table.getSchemaName(), table.getName(), true);
                return "Done";
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            desetWaitingMode();
            if(isSuccessful){
                tableDataFragment.fillTableRow(table);
            }else{
                showError(s);
            }
        }
    }

}
