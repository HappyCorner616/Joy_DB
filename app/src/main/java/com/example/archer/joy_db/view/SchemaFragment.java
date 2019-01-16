package com.example.archer.joy_db.view;

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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.Schema;
import com.example.archer.joy_db.model.Table;

import java.util.List;

import static com.example.archer.joy_db.App.MY_TAG;

public class SchemaFragment extends Fragment implements NameableListAdapter.NameableListAdapterListener {

    private TextView label;
    private RecyclerView recyclerView;
    private Fragment previousFragment;
    SchemaFragmentListener listener;

    private Schema schema;

    public static SchemaFragment getNewInstance(Schema schema){
        SchemaFragment schemaFragment = new SchemaFragment();
        schemaFragment.schema = schema;
        return schemaFragment;
    }

    public static SchemaFragment getNewInstance(Schema schema, Fragment previousFragment){
        SchemaFragment schemaFragment = new SchemaFragment();
        schemaFragment.schema = schema;
        schemaFragment.previousFragment = previousFragment;
        return schemaFragment;
    }

    public void setListener(SchemaFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public Fragment getPreviousFragment() {
        return previousFragment;
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
        if(listener != null){
            listener.openTableDataFragment(table, this);
        }
    }

    @Override
    public void onRowLongClick(int position) {
        Table table = schema.getTables().get(position);
        if(listener != null){
            listener.openTableFragment(table, this);
        }
    }

    @Override
    public void onDestroy() {
        if(listener != null){
            listener.restoreFragment(previousFragment);
        }
        super.onDestroy();
    }

    public interface SchemaFragmentListener extends FragmentRestorable{
        void openTableFragment(Table table, Fragment previousFragment);
        void openTableDataFragment(Table table, Fragment previousFragment);
    }
}
