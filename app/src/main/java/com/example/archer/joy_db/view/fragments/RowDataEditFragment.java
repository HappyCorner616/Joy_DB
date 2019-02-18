package com.example.archer.joy_db.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.archer.joy_db.MainActivity;
import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.MyColor;
import com.example.archer.joy_db.view.recViewAdapters.RowDataEditAdapter;

import static com.example.archer.joy_db.App.MY_TAG;

public class RowDataEditFragment extends Fragment implements View.OnClickListener, RowDataEditAdapter.RowDataEditAdapterListener, RefSelectFragment.RefSelectFragmentListener {

    private Row row;
    private boolean isNew;
    private MyColor bgColor, itemColor;
    private String schemaName, tableName;
    private View rowDateEdit;
    private RecyclerView cellsList;
    private ImageView doneBtn;

    public static RowDataEditFragment getNewInstance(Row row, boolean isNew, String schemaName, String tableName){
        RowDataEditFragment fragment = new RowDataEditFragment();
        fragment.row = row;
        fragment.isNew = isNew;
        fragment.bgColor = MyColor.whiteColor();
        fragment.bgColor = MyColor.whiteColor();
        fragment.schemaName = schemaName;
        fragment.tableName = tableName;
        return fragment;
    }

    public void setColors(MyColor bgColor, MyColor itemColor){
        this.bgColor = bgColor;
        this.itemColor = itemColor;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_data_edit, container, false);

        rowDateEdit = view.findViewById(R.id.row_data_edit);
        cellsList = view.findViewById(R.id.cells_list);
        doneBtn = view.findViewById(R.id.done_btn);

        rowDateEdit.setBackgroundColor(bgColor.asInt());
        doneBtn.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cellsList.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        cellsList.addItemDecoration(divider);

        RowDataEditAdapter adapter = new RowDataEditAdapter(row.getCells(), isNew, itemColor);
        adapter.setListener(this);
        cellsList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.done_btn){
            ((RowDataEditAdapter)cellsList.getAdapter()).updateLastRow();
            if(isNew){
                new AddRowTask((MainActivity)getActivity(), this, schemaName, tableName, row).execute();
            }else{
                new UpdateRowTask((MainActivity)getActivity(), this, schemaName, tableName, row).execute();
            }
        }
    }

    public void updateRow(Row row){
        this.row = row;
        isNew = false;
        RowDataEditAdapter adapter = new RowDataEditAdapter(row.getCells(), isNew, itemColor);
        adapter.setListener(this);
        cellsList.setAdapter(adapter);
    }

    @Override
    public void searchRef(String refSchema, String refTable) {
        RefSelectFragment fragment = RefSelectFragment.getNewInstance(refSchema, refTable);
        fragment.setColors(bgColor, itemColor);
        fragment.setListener(this);
        getFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("REF_SELECT")
                .commit();
    }

    @Override
    public void getSelectedRow(Row row) {
        ((RowDataEditAdapter)cellsList.getAdapter()).loadRefValue(row);
    }

    class AddRowTask extends AsyncTask<Void, Void, String>{

        private MainActivity activity;
        private RowDataEditFragment callback;
        private String schemaName, tableName;
        private Row row;
        private boolean isSuccessful;

        public AddRowTask(MainActivity activity, RowDataEditFragment callback, String schemaName, String tableName, Row row) {
            this.activity = activity;
            this.callback = callback;
            this.schemaName = schemaName;
            this.tableName = tableName;
            this.row = row;
            this.isSuccessful = true;
        }

        @Override
        protected void onPreExecute() {
            activity.setWaitingMode();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                row = HttpProvider.getInstance().addRow(row, schemaName, tableName);
                return "Done";
            } catch (Exception e) {
                isSuccessful = false;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            activity.desetWaitingMode();
            if(isSuccessful){
                callback.updateRow(row);
                activity.showToast(s);
            }else{
                activity.showError(s);
            }
        }
    }

    class UpdateRowTask extends AsyncTask<Void, Void, String>{

        private MainActivity activity;
        private RowDataEditFragment callback;
        private String schemaName, tableName;
        private Row row;
        private boolean isSuccessful;

        public UpdateRowTask(MainActivity activity, RowDataEditFragment callback, String schemaName, String tableName, Row row) {
            this.activity = activity;
            this.callback = callback;
            this.schemaName = schemaName;
            this.tableName = tableName;
            this.row = row;
            this.isSuccessful = true;
        }

        @Override
        protected void onPreExecute() {
            activity.setWaitingMode();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                row = HttpProvider.getInstance().updateRow(row, schemaName, tableName);
                return "Done";
            } catch (Exception e) {
                isSuccessful = false;
                e.printStackTrace();
                Log.d(MY_TAG, "UpdateRowTask error: " + e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            activity.desetWaitingMode();
            if(isSuccessful){
                callback.updateRow(row);
                activity.showToast(s);
            }else{
                activity.showError(s);
            }
        }
    }
}
