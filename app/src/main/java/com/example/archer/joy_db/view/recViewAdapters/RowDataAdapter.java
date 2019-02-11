package com.example.archer.joy_db.view.recViewAdapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Cell;
import com.example.archer.joy_db.view.MyColor;

import java.util.List;

public class RowDataAdapter extends RecyclerView.Adapter<RowDataAdapter.RowDataViewHolder> {

    private List<Cell> list;
    private MyColor cellColor;

    public RowDataAdapter(List<Cell> list, MyColor cellColor) {
        this.list = list;
        this.cellColor = cellColor;
    }

    @NonNull
    @Override
    public RowDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_data, viewGroup, false);
        return new RowDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowDataViewHolder viewHolder, int i) {
        Cell cell = list.get(i);
        viewHolder.colName.setText(cell.getColumn().getName());
        viewHolder.colVal.setText(String.valueOf(cell.getVal()));
        viewHolder.colName.setBackgroundColor(cellColor.asInt());
        viewHolder.colVal.setBackgroundColor(cellColor.asInt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RowDataViewHolder extends RecyclerView.ViewHolder{

        TextView colName, colVal;
        ConstraintLayout cellData;

        public RowDataViewHolder(@NonNull View itemView) {
            super(itemView);
            colName = itemView.findViewById(R.id.col_name);
            colVal = itemView.findViewById(R.id.col_val);
            cellData = itemView.findViewById(R.id.cell_data);
        }
    }
}
