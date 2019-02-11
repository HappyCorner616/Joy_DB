package com.example.archer.joy_db.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class RowDataEditFragment extends Fragment implements View.OnClickListener {

    private Row row;
    private boolean isNew;
    private MyColor bgColor, itemColor;
    private View rowDateEdit;
    private RecyclerView cellsList;
    private ImageView doneBtn;

    public static RowDataEditFragment getNewInstance(Row row, boolean isNew){
        RowDataEditFragment fragment = new RowDataEditFragment();
        fragment.row = row;
        fragment.isNew = isNew;
        fragment.bgColor = MyColor.whiteColor();
        fragment.bgColor = MyColor.whiteColor();
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
        cellsList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.done_btn){

        }
    }

    public void updateRow(Row row){
        this.row = row;
        isNew = false;
        RowDataEditAdapter adapter = new RowDataEditAdapter(row.getCells(), isNew, itemColor);
        cellsList.setAdapter(adapter);
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
}
