package com.example.archer.joy_db.view.table.data;

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
import android.widget.TextView;

import com.example.archer.joy_db.MainActivity;
import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.MyColor;
import com.example.archer.joy_db.view.row.RowDataEditFragment;
import com.example.archer.joy_db.view.row.RowDataFragment;
import com.example.archer.joy_db.view.table.interfaces.ITableDataFragment;

public class TableDataFragment extends Fragment implements ITableDataFragment, TableDataListAdapter.TableDataListAdapterListener, View.OnClickListener {

    protected Table table;
    private View tableData;
    private TextView tableTitle;
    private RecyclerView dataList;
    private ImageView addBtn;
    protected MyColor bgColor, itemColor;

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

        requestFilledTable(table);

        return view;
    }

    public void requestFilledTable(Table table) {
        if(table != null){
            new FillTableTask(table, this).execute();
        }
    }

    @Override
    public void fillTableRow(Table filledTable){
        table = filledTable;
        TableDataListAdapter adapter = new TableDataListAdapter(table.getRows(), table.getColumns(), bgColor, itemColor);
        adapter.setListener(this);
        dataList.setAdapter(adapter);
        //Log.d(MY_TAG, "fillTableRow: " + table.getRows());
    }

    @Override
    public void rowDataClick(Row row, MyColor bgColor, MyColor itemColor) {
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
        String schemaName, tableName;
        private boolean isSuccessful;
        private ITableDataFragment tableDataFragment;

        public FillTableTask(String schemaName, String tableName, ITableDataFragment tableDataFragment) {
            table = null;
            this.schemaName = schemaName;
            this.tableName = tableName;
            this.tableDataFragment = tableDataFragment;
            isSuccessful = true;
        }

        public FillTableTask(Table table, ITableDataFragment tableDataFragment) {
            this(table.getSchemaName(), table.getName(), tableDataFragment);
        }

        @Override
        protected void onPreExecute() {
            ((MainActivity)getActivity()).setWaitingMode();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                table = HttpProvider.getInstance().getTable(schemaName, tableName, true);
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
