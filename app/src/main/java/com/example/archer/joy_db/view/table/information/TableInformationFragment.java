package com.example.archer.joy_db.view.table.information;

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
import com.example.archer.joy_db.view.MyColor;

public class TableInformationFragment extends Fragment {

    private Table table;
    private MyColor bgColor, itemColor;
    private View tableInformation;
    private TextView tableTitle;
    private RecyclerView columnList;

    public static TableInformationFragment getNewInstance(Table table){
        TableInformationFragment fragment = new TableInformationFragment();
        fragment.table = table;
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
        View view = inflater.inflate(R.layout.table_information, container, false);

        tableInformation = view.findViewById(R.id.table_information);
        tableTitle = view.findViewById(R.id.table_title);
        columnList = view.findViewById(R.id.column_list);

        tableInformation.setBackgroundColor(bgColor.asInt());
        tableTitle.setText(table.getName());

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        columnList.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        columnList.addItemDecoration(divider);

        TableInformationAdapter adapter = new TableInformationAdapter(table.getColumns(), itemColor);
        columnList.setAdapter(adapter);

        return view;
    }
}
