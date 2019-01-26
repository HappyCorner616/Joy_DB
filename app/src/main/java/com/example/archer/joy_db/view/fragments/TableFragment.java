package com.example.archer.joy_db.view.fragments;

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
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.view.recViewAdapters.ColumnListAdapter;
import com.example.archer.joy_db.view.recViewAdapters.PropertyableListAdapter;

public class TableFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView label;
    private Table table;

    public static TableFragment getNewInstance(Table table){
        TableFragment tableFragment = new TableFragment();
        tableFragment.table = table;
        return tableFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.column_list, container, false);

        label = view.findViewById(R.id.table_title);
        label.setText(table.getName());

        recyclerView = view.findViewById(R.id.col_list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        ColumnListAdapter adapter = new ColumnListAdapter(table.getColumns());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
