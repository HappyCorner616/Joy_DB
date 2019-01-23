package com.example.archer.joy_db.view;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
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
import com.example.archer.joy_db.model.Schema;

import java.util.List;
import java.util.Set;

import static com.example.archer.joy_db.App.MY_TAG;

public class SchemasListFragment extends Fragment implements NameableListAdapter.NameableListAdapterListener {

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

        View view = inflater.inflate(R.layout.list, container, false);

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d(MY_TAG, "SchemasList_Fragment onCreate(): " + savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MY_TAG, "SchemasList_Fragment onDestroy(): ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(MY_TAG, "SchemasList_Fragment onAttach(): ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MY_TAG, "SchemasList_Fragment onDetach(): ");
    }

    @Override
    public void onRowClick(int position) {
        Schema schema = schemasList.get(position);

        SchemaFragment schemaFragment = SchemaFragment.getNewInstance(schema);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.list_container, schemaFragment)
                .addToBackStack("SCHEMA_" + schema.getName())
                .commit();
    }

    @Override
    public void onRowLongClick(int position) {
        //Nothing to do
    }

}
