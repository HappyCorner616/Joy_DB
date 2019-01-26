package com.example.archer.joy_db.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.archer.joy_db.MainActivity;
import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Cell;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.providers.HttpProvider;
import com.example.archer.joy_db.view.recViewAdapters.EditablePropertyableListAdapter;

import java.io.IOException;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RowDataEditFragment extends Fragment implements View.OnClickListener {

    private Row row;
    private String schemaName;
    private String tableName;
    private boolean newRow;

    private RecyclerView recyclerView;
    private ImageView doneButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static RowDataEditFragment getNewInstance(Row row, String schemaName, String tableName, boolean newRow){
        RowDataEditFragment rowDataEditFragment = new RowDataEditFragment();
        rowDataEditFragment.row = row;
        rowDataEditFragment.schemaName = schemaName;
        rowDataEditFragment.tableName = tableName;
        rowDataEditFragment.newRow = newRow;
        return rowDataEditFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        params.verticalBias = 0.95F;
        params.horizontalBias = 0.9F;

        View doneButtonFrame = inflater.inflate(R.layout.done_button, container, false);
        ((ViewGroup)view).addView(doneButtonFrame, params);

        doneButton = doneButtonFrame.findViewById(R.id.done_circle);
        doneButton.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.list);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(divider);

        EditablePropertyableListAdapter<Cell> adapter = new EditablePropertyableListAdapter(row.getCells());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.done_circle){
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            new UpdateRowTask(row, schemaName, tableName, newRow, this).execute();
        }
    }

    private void saveChanges(){
        List<Cell> cells = ((EditablePropertyableListAdapter)recyclerView.getAdapter()).getList();
        for(Cell c : cells){
            row.setCellVal(c.getColumn().getName(), c.getPropertyVal());
        }
    }

    private class UpdateRowTask extends AsyncTask<Void, Void, String>{

        private Row row;
        private String schemaName;
        private String tableName;
        private Fragment fragment;
        private boolean newRow;
        private boolean isSuccessful;

        public UpdateRowTask(Row row, String schemaName, String tableName, boolean newRow, Fragment fragment) {
            this.row = row;
            this.schemaName = schemaName;
            this.tableName = tableName;
            this.newRow = newRow;
            this.fragment = fragment;
            isSuccessful = true;
        }

        @Override
        protected void onPreExecute() {
            ((MainActivity)getActivity()).setWaitingMode();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                if(newRow){
                    return HttpProvider.getInstance().addRow(row, schemaName, tableName);
                }else{
                    return HttpProvider.getInstance().updateRow(row, schemaName, tableName);
                }
            }catch (IOException e){
                isSuccessful = false;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            ((MainActivity)getActivity()).desetWaitingMode();
            if(isSuccessful){
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .commit();
            }else{
                ((MainActivity)getActivity()).showError(s);
            }
        }
    }

}
