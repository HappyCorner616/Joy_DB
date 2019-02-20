package com.example.archer.joy_db.view.schemas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.archer.joy_db.MainActivity;
import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.view.MyColor;
import com.example.archer.joy_db.view.table.data.TableDataFragment;
import com.example.archer.joy_db.view.table.information.TableInformationFragment;

import java.util.List;

public class SchemasListFragment extends MvpAppCompatFragment implements SchemasListAdapter.SchemaListAdapterListener, ISchemasListFragment {

    private RecyclerView recyclerView;

    @InjectPresenter
    SchemasListPresenter presenter;

    public static SchemasListFragment getNewInstance(){
        SchemasListFragment schemasListFragment = new SchemasListFragment();
        return schemasListFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAllSchemas();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schemas_list, container, false);

        recyclerView = view.findViewById(R.id.schemas_list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    @Override
    public void updateSchemasList(List<Schema> list) {
        SchemasListAdapter adapter = new SchemasListAdapter(list);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {
        ((MainActivity)getActivity()).showError(error);
    }

    @Override
    public void setWaitingMode() {
        ((MainActivity)getActivity()).setWaitingMode();
    }

    @Override
    public void desetWaitingMode() {
        ((MainActivity)getActivity()).desetWaitingMode();
    }


}
