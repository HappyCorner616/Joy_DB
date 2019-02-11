package com.example.archer.joy_db.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.view.MyColor;
import com.example.archer.joy_db.view.recViewAdapters.RowDataAdapter;

public class RowDataFragment extends Fragment {

    private Row row;
    private MyColor bgColor, itemColor;
    private RecyclerView cellsList;
    private ConstraintLayout rowData;

    public static RowDataFragment getNewInstance(Row row){
        RowDataFragment fragment = new RowDataFragment();
        fragment.row = row;
        fragment.bgColor = MyColor.whiteColor();
        fragment.itemColor = MyColor.whiteColor();
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
        View view = inflater.inflate(R.layout.row_data, container, false);

        cellsList = view.findViewById(R.id.cells_list);
        rowData = view.findViewById(R.id.row_data);

        rowData.setBackgroundColor(bgColor.asInt());

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cellsList.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        cellsList.addItemDecoration(divider);

        RowDataAdapter adapter = new RowDataAdapter(row.getCells(), itemColor);
        cellsList.setAdapter(adapter);

        return view;
    }
}
