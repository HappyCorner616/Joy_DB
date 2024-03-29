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
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.Propertyable;
import com.example.archer.joy_db.model.Table;

public class TableFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView label;
    private TableFragmentListener listener;
    private Fragment previousFragment;
    private Table table;

    public static TableFragment getNewInstance(Table table){
        TableFragment tableFragment = new TableFragment();
        tableFragment.table = table;
        return tableFragment;
    }
    public static TableFragment getNewInstance(Table table, Fragment previousFragment){
        TableFragment tableFragment = new TableFragment();
        tableFragment.table = table;
        tableFragment.previousFragment = previousFragment;
        return tableFragment;
    }

    public void setListener(TableFragmentListener listener) {
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
        label.setText(table.getName());

        recyclerView = view.findViewById(R.id.list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        PropertyableListAdapter adapter = new PropertyableListAdapter(table.getColumns());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        if(listener != null){
            listener.restoreFragment(previousFragment);
        }
        super.onDestroy();
    }

    public interface TableFragmentListener extends FragmentRestorable{
    }
}
