package com.example.archer.joy_db.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.view.MyColor;
import com.example.archer.joy_db.view.recViewAdapters.SchemasListAdapter;

import java.util.List;

import static com.example.archer.joy_db.App.MY_TAG;

public class SchemasListFragment extends Fragment implements SchemasListAdapter.SchemaListAdapterListener {

    private RecyclerView recyclerView;
    private Fragment previousFragment;
    private List<Schema> schemasList;

    public static SchemasListFragment getNewInstance(List<Schema> schemasList){
        SchemasListFragment schemasListFragment = new SchemasListFragment();
        schemasListFragment.schemasList = schemasList;
        return schemasListFragment;
    }

    public static SchemasListFragment getNewInstance(List<Schema> schemasList, Fragment previousFragment){
        SchemasListFragment schemasListFragment = new SchemasListFragment();
        schemasListFragment.schemasList = schemasList;
        schemasListFragment.previousFragment = previousFragment;
        return schemasListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(MY_TAG, "SchemasList_Fragment onCreateView(): ");

        View view = inflater.inflate(R.layout.schemas_list, container, false);

        recyclerView = view.findViewById(R.id.schemas_list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        SchemasListAdapter adapter = new SchemasListAdapter(schemasList);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d(MY_TAG, "SchemasList_Fragment onCreate(): " + savedInstanceState);
    }

    @Override
    public void openTableData(Table table, MyColor schemaColor, MyColor tableColor) {
        TableDataFragment tableDataFragment = TableDataFragment.getNewInstance(table);
        tableDataFragment.setColors(schemaColor, tableColor);
        getFragmentManager().beginTransaction()
                .add(R.id.list_container, tableDataFragment)
                .addToBackStack("FILLED_TABLE_" + table.getName())
                .commit();
    }

    @Override
    public void openTableInformation(Table table, MyColor bgColor, MyColor itemColor) {
        TableInformationFragment fragment = TableInformationFragment.getNewInstance(table);
        fragment.setColors(bgColor, itemColor);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("TABLE_INFORMATION")
                .commit();
    }
}
