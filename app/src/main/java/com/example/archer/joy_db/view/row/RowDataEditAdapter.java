package com.example.archer.joy_db.view.row;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Cell;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.view.MyColor;

import java.util.ArrayList;
import java.util.List;

public class RowDataEditAdapter extends RecyclerView.Adapter<RowDataEditAdapter.RowDataEditViewHolder> {

    private List<Cell> list;
    private boolean isNewRow;
    private MyColor cellColor;
    private Row unchangedRow;
    private RowDataEditViewHolder lastViewHolder;
    private RowDataEditAdapterListener listener;

    public RowDataEditAdapter(List<Cell> list, boolean isNewRow, MyColor cellColor) {
        this.list = list;
        this.isNewRow = isNewRow;
        this.cellColor = cellColor;
        List<Cell> newList = new ArrayList<>(list.size());
        for(Cell c : list){
            newList.add(c.copy());
        }
        unchangedRow = new Row(newList);
        lastViewHolder = null;
    }

    public void setListener(RowDataEditAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RowDataEditViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_data_edit, viewGroup, false);
        return new RowDataEditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowDataEditViewHolder viewHolder, int i) {
        Cell cell = list.get(i);

        viewHolder.cell = cell;

        if(cell.getColumn().isPK() && !isNewRow){
            viewHolder.colVal.setEnabled(false);
        }

        if(cell.getColumn().isInt()) {
            if (cell.getColumn().unsigned()) {
                viewHolder.colVal.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                viewHolder.colVal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
        }else if(cell.getColumn().isDecimal()){
            if (cell.getColumn().unsigned()) {
                viewHolder.colVal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            } else {
                viewHolder.colVal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
        }else if(cell.getColumn().isLOB()){
            viewHolder.colVal.setEnabled(false);
        }else if(cell.getColumn().isDate()){
            viewHolder.colVal.setInputType(InputType.TYPE_CLASS_DATETIME);
        }else if(cell.getColumn().isString()){
            viewHolder.colVal.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        viewHolder.colName.setText(cell.getColumn().getName());
        viewHolder.colVal.setText(String.valueOf(cell.getVal()));
        viewHolder.colName.setBackgroundColor(cellColor.asInt());
        viewHolder.colVal.setBackgroundColor(cellColor.asInt());
        if(cell.getColumn().isRef()){
            viewHolder.searchBtn.setVisibility(View.VISIBLE);
        }else{
            viewHolder.searchBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateLastRow(){
        if(lastViewHolder != null){
            lastViewHolder.updateCellVal();
        }
    }

    public void loadRefValue(Row row){
        if(lastViewHolder != null){
            lastViewHolder.cell.setVal(row.getVal(lastViewHolder.cell.getColumn().getRefColumnName()));
            lastViewHolder.colVal.setText(lastViewHolder.cell.getVal().toString());
        }
    }

    class RowDataEditViewHolder extends RecyclerView.ViewHolder implements  View.OnFocusChangeListener, View.OnClickListener {

        Cell cell;
        TextView colName;
        EditText colVal;
        ImageButton searchBtn;
        ConstraintLayout cellData;

        public RowDataEditViewHolder(@NonNull View itemView) {
            super(itemView);
            colName = itemView.findViewById(R.id.col_name);
            colVal = itemView.findViewById(R.id.col_val);
            cellData = itemView.findViewById(R.id.cell_data);
            searchBtn = itemView.findViewById(R.id.search_btn);

            colVal.setOnFocusChangeListener(this);
            searchBtn.setOnClickListener(this);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v.getId() == R.id.col_val){
                if(hasFocus){
                    lastViewHolder = this;
                }else{
                    updateCellVal();
                }
            }
        }

        public void updateCellVal(){
            cell.setVal(colVal.getText().toString());
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.search_btn){
                if(listener != null){
                    listener.searchRef(cell.getColumn().getRefSchemaName(), cell.getColumn().getRefTableName());
                }
            }
        }
    }

    public interface RowDataEditAdapterListener{
        void searchRef(String refSchema, String refTable);
    }
}
