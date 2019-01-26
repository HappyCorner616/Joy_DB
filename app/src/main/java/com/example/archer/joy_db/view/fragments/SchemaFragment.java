package com.example.archer.joy_db.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.view.recViewAdapters.NameableListAdapter;

import static com.example.archer.joy_db.App.MY_TAG;

public class SchemaFragment extends Fragment implements NameableListAdapter.NameableListAdapterListener {

    private TextView label;
    private RecyclerView recyclerView;

    private Schema schema;

    public static SchemaFragment getNewInstance(Schema schema){
        SchemaFragment schemaFragment = new SchemaFragment();
        schemaFragment.schema = schema;
        return schemaFragment;
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

        label = view.findViewById(R.id.title_txt);
        recyclerView = view.findViewById(R.id.list);

        label.setText(schema.getName());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        Log.d(MY_TAG, "Schema: " + schema);

        NameableListAdapter<Table> adapter = new NameableListAdapter<>(schema.getTables());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public String toString() {
        return "SchemaFragment{" +
                "schema=" + schema.getName() +
                '}';
    }

    @Override
    public void onRowClick(int position) {
        Table table = schema.getTables().get(position);
        TableDataFragment tableDataFragment = TableDataFragment.getNewInstance(table);
        getFragmentManager().beginTransaction()
                .add(R.id.list_container, tableDataFragment)
                .addToBackStack("FILLED_TABLE_" + table.getName())
                .commit();
    }

    @Override
    public void onRowLongClick(int position) {
        Table table = schema.getTables().get(position);
        TableFragment tableFragment = TableFragment.getNewInstance(table);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, tableFragment)
                .addToBackStack("TABLE_" + table.getName())
                .commit();
    }

}
