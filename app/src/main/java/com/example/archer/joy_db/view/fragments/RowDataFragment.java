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

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Cell;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.view.recViewAdapters.PropertyableListAdapter;

public class RowDataFragment extends Fragment {

    private Row row;
    private RecyclerView recyclerView;

    public static RowDataFragment getNewInstance(Row row){
        RowDataFragment rowDataFragment = new RowDataFragment();
        rowDataFragment.row = row;
        return rowDataFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        recyclerView = view.findViewById(R.id.list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        PropertyableListAdapter<Cell> adapter = new PropertyableListAdapter<>(row.getCells());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
