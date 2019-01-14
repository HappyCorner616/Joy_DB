package com.example.archer.joy_db.view;

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
import com.example.archer.joy_db.model.Schema;

import java.util.List;

public class SchemasListFragment extends Fragment implements NameableListAdapter.NameableListAdapterListener {

    private RecyclerView recyclerView;
    private Fragment previousFragment;
    private SchemasListFragmentListener listener;
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

    public void setListener(SchemasListFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schemas_list, container, false);

        recyclerView = view.findViewById(R.id.list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        NameableListAdapter<Schema> adapter = new NameableListAdapter(schemasList);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onRowClick(int position) {
        Schema schema = schemasList.get(position);
        if(listener != null){
            listener.openSchemaFragment(schema, this);
        }
    }

    public interface SchemasListFragmentListener{
        void openSchemaFragment(Schema schema, Fragment previousFragment);
    }
}
