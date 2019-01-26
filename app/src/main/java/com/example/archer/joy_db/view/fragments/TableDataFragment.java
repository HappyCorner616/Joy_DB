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
import com.example.archer.joy_db.view.recViewAdapters.NameableListAdapter;

import static com.example.archer.joy_db.App.MY_TAG;

public class TableDataFragment extends Fragment implements NameableListAdapter.NameableListAdapterListener, View.OnClickListener {

    private Table table;
    private TextView label;
    private RecyclerView recyclerView;
    private ImageView addButton;

    public static TableDataFragment getNewInstance(Table table){
        TableDataFragment tableDataFragment = new TableDataFragment();
        tableDataFragment.table = table;
        return tableDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.labeled_list, container, false);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        params.verticalBias = 0.95F;
        params.horizontalBias = 0.9F;

        View addButtonFrame = inflater.inflate(R.layout.add_button, container, false);
        ((ViewGroup)view).addView(addButtonFrame, params);

        addButton = addButtonFrame.findViewById(R.id.add_circle);
        addButton.setOnClickListener(this);

        label = view.findViewById(R.id.title_txt);
        label.setText(table.getName());

        recyclerView = view.findViewById(R.id.list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        if(table != null){
            new FillTableTask(table, this).execute();
        }

        return view;
    }

    public void fillTableRow(Table filledTable){
        table = filledTable;
        Log.d(MY_TAG, "fillTableRow: " + table.getRows());
        NameableListAdapter<Row> adapter = new NameableListAdapter<>(table.getRows());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRowClick(int position) {
        Row row = table.getRows().get(position);
        Log.d(MY_TAG, "openRowDataFragment: ");
        RowDataFragment rowDataFragment = RowDataFragment.getNewInstance(row);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, rowDataFragment)
                .addToBackStack("ROW_DATA")
                .commit();
    }

    @Override
    public void onRowLongClick(int position) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_circle){
            //Toast.makeText(getContext(), "add circle clicked!", Toast.LENGTH_SHORT).show();
            RowDataEditFragment rowDataEditFragment = RowDataEditFragment.getNewInstance(table.emptyRow(), table.getSchemaName(), table.getName(), true);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, rowDataEditFragment)
                    .addToBackStack("ROW_DATA_EDIT")
                    .commit();
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