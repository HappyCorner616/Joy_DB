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
import com.example.archer.joy_db.model.Row;

public class RowDataFragment extends Fragment {

    private Row row;
    private RecyclerView recyclerView;
    private Fragment prevoiusFragment;
    private RowDataFragmentListener listener;

    public static RowDataFragment getNewInstance(Row row, Fragment previousFragment){
        RowDataFragment rowDataFragment = new RowDataFragment();
        rowDataFragment.row = row;
        rowDataFragment.prevoiusFragment = previousFragment;
        return rowDataFragment;
    }

    public void setListener(RowDataFragmentListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        recyclerView = view.findViewById(R.id.list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        PropertyableListAdapter<Row.Cell> adapter = new PropertyableListAdapter<>(row.getCells());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        if(listener != null){
            listener.restoreFragment(prevoiusFragment);
        }
        super.onDestroy();
    }

    public interface RowDataFragmentListener extends FragmentRestorable{
    }

}
