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
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.Row;
import com.example.archer.joy_db.model.Table;

import static com.example.archer.joy_db.App.MY_TAG;

public class TableDataFragment extends Fragment implements NameableListAdapter.NameableListAdapterListener {

    private Table table;
    private TextView label;
    private RecyclerView recyclerView;
    private TableDataFragmentListener listener;
    private Fragment previousFragment;

    public static TableDataFragment getNewInstance(Table table, Fragment previousFragment){
        TableDataFragment tableDataFragment = new TableDataFragment();
        tableDataFragment.table = table;
        tableDataFragment.previousFragment = previousFragment;
        return tableDataFragment;
    }

    public void setListener(TableDataFragmentListener listener) {
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

        if(listener != null){
            listener.fillTableRow(table, this);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        if(listener != null){
            listener.restoreFragment(previousFragment);
        }
        super.onDestroy();
    }

    public void fillTableRow(Table filledTable){
        table = filledTable;
        Log.d(MY_TAG, "fillTableRow: " + table.getRows());
        NameableListAdapter<Row> adapter = new NameableListAdapter<>(table.getRows());
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRowClick(int position) {
        Row row = table.getRows().get(position);
        if(listener != null){
            listener.openRowDataFragment(row, this);
        }
    }

    @Override
    public void onRowLongClick(int position) {

    }

    public interface TableDataFragmentListener extends FragmentRestorable{
        void fillTableRow(Table table, TableDataFragment tableDataFragment);
        void openRowDataFragment(Row row, Fragment previousFragment);
    }

}
