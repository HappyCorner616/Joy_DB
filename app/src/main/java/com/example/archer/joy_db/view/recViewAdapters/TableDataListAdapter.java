package com.example.archer.joy_db.view.recViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Cell;
import com.example.archer.joy_db.model.sql.Column;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.view.MyColor;

import java.util.List;

public class TableDataListAdapter extends RecyclerView.Adapter<TableDataListAdapter.TableDataViewHolder> {

    private List<Row> list;
    private List<Column> columnList;
    private MyColor bgColor, rowColor;
    private TableDataListAdapterListener listener;

    public TableDataListAdapter(List<Row> list, List<Column> columnList, MyColor bgColor, MyColor rowColor) {
        this.list = list;
        this.columnList = columnList;
        this.bgColor = bgColor;
        this.rowColor = rowColor;
    }

    public void setListener(TableDataListAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TableDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_data_prev, viewGroup, false);
        return new TableDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableDataViewHolder viewHolder, int i) {
        if(i == 0){
            MyColor titleColor = rowColor.copy(20, MyColor.DARKER);
            viewHolder.col1.setBackgroundColor(titleColor.asInt());
            viewHolder.col2.setBackgroundColor(titleColor.asInt());
            viewHolder.col3.setBackgroundColor(titleColor.asInt());
            if(columnList.size() > 0) viewHolder.col1.setText(columnList.get(0).getName());
            if(columnList.size() > 1) viewHolder.col2.setText(columnList.get(1).getName());
            if(columnList.size() > 2) viewHolder.col3.setText(columnList.get(2).getName());
        }else{
            viewHolder.col1.setBackgroundColor(rowColor.asInt());
            viewHolder.col2.setBackgroundColor(rowColor.asInt());
            viewHolder.col3.setBackgroundColor(rowColor.asInt());
            Row row = list.get(i - 1);
            List<Cell> cells = row.getCells();
            if(cells.size() > 0) viewHolder.col1.setText(cells.get(0).getVal().toString());
            if(cells.size() > 1) viewHolder.col2.setText(cells.get(1).getVal().toString());
            if(cells.size() > 2) viewHolder.col3.setText(cells.get(2).getVal().toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    class TableDataViewHolder extends RecyclerView.ViewHolder{

        TextView col1, col2, col3;
        View rowDataPrev;

        public TableDataViewHolder(@NonNull View itemView) {
            super(itemView);
            col1 = itemView.findViewById(R.id.col_1);
            col2 = itemView.findViewById(R.id.col_2);
            col3 = itemView.findViewById(R.id.col_3);
            rowDataPrev = itemView.findViewById(R.id.row_data_prev);

            rowDataPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        Row row = list.get(getAdapterPosition() - 1);
                        listener.openRowData(row, bgColor, rowColor);
                    }
                }
            });
        }
    }

    public interface TableDataListAdapterListener{
        void openRowData(Row row, MyColor bgColor, MyColor itemColor);
    }

}
