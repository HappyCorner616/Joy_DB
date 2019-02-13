package com.example.archer.joy_db.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archer.joy_db.MainActivity;
import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.MyColor;
import com.example.archer.joy_db.view.recViewAdapters.TableDataListAdapter;

import static com.example.archer.joy_db.App.MY_TAG;

public class TableDataFragment extends Fragment implements TableDataListAdapter.TableDataListAdapterListener, View.OnClickListener {

    private Table table;
    private View tableData;
    private TextView tableTitle;
    private RecyclerView dataList;
    private ImageView addBtn;
    private MyColor bgColor, itemColor;

    public static TableDataFragment getNewInstance(Table table){
        TableDataFragment tableDataFragment = new TableDataFragment();
        tableDataFragment.table = table;
        tableDataFragment.bgColor = MyColor.whiteColor();
        tableDataFragment.itemColor = tableDataFragment.bgColor.copy(20, MyColor.DARKER);
        return tableDataFragment;
    }

    public void setColors(MyColor schemaColor, MyColor tableColor){
        this.bgColor = schemaColor;
        this.itemColor = tableColor;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.table_data, container, false);

        tableData = view.findViewById(R.id.table_data);
        tableTitle = view.findViewById(R.id.table_title);
        dataList = view.findViewById(R.id.table_data_list);
        addBtn = view.findViewById(R.id.add_btn);

        tableTitle.setText(table.getName());
        tableData.setBackgroundColor(bgColor.asInt());
        addBtn.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        dataList.addItemDecoration(divider);

        if(table != null){
            new FillTableTask(table, this).execute();
        }

        return view;
    }

    public void fillTableRow(Table filledTable){
        table = filledTable;
        TableDataListAdapter adapter = new TableDataListAdapter(table.getRows(), table.getColumns(), bgColor, itemColor);
        adapter.setListener(this);
        dataList.setAdapter(adapter);
        //Log.d(MY_TAG, "fillTableRow: " + table.getRows());
    }

    @Override
    public void openRowData(Row row, MyColor bgColor, MyColor itemColor) {
        RowDataFragment fragment = RowDataFragment.getNewInstance(row, table.getSchemaName(), table.getName());
        fragment.setColors(bgColor, itemColor);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("ROW_DATA")
                .commit();
    }

    private void openRowDataEditFragment(Row row){
        RowDataEditFragment fragment = RowDataEditFragment.getNewInstance(row, true, table.getSchemaName(), table.getName());
        fragment.setColors(bgColor, itemColor);
        getFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("ROW_DATA_EDIT")
                .commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_btn){
            Row row = table.emptyRow();
            openRowDataEditFragment(row);
        }
    }

    class FillTableTask extends AsyncTask<Void, Void, String> {

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
            ((MainActivity)getActivity()).setWaitingMode();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                table = HttpProvider.getInstance().getTable(table.getSchemaName(), table.getName(), true);
                return "Done";
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            ((MainActivity)getActivity()).desetWaitingMode();
            if(isSuccessful){
                tableDataFragment.fillTableRow(table);
            }else{
                ((MainActivity)getActivity()).showError(s);
            }
        }
    }

}
