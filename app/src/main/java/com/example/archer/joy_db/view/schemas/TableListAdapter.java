package com.example.archer.joy_db.view.schemas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Column;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.view.MyColor;

import java.util.List;

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.TableViewHolder> {

    private List<Table> list;
    private SchemasListAdapter.SchemaListAdapterListener listener;
    private MyColor bgColor, itemColor;

    public TableListAdapter(List<Table> list) {
        this.list = list;
    }

    public void setListener(SchemasListAdapter.SchemaListAdapterListener listener) {
        this.listener = listener;
    }

    public void setColors(MyColor bgColor, MyColor itemColor) {
        this.bgColor = bgColor;
        this.itemColor = itemColor;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_item, viewGroup, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder viewHolder, int i) {
        Table table = list.get(i);
        viewHolder.tableTitle.setText(table.getName());
        viewHolder.tableFrame.setBackgroundColor(itemColor.asInt());

        List<Column> columnList = table.getColumns();
        if(columnList.size() > 0) viewHolder.prop1.setText(columnList.get(0).getName());
        if(columnList.size() > 1) viewHolder.prop2.setText(columnList.get(1).getName());
        if(columnList.size() > 2) viewHolder.prop3.setText(columnList.get(2).getName());
        if(columnList.size() > 3) viewHolder.prop4.setText(columnList.get(3).getName());
        if(columnList.size() > 4) viewHolder.prop5.setText(columnList.get(4).getName());
        if(columnList.size() > 5) viewHolder.prop6.setText(columnList.get(5).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TableViewHolder extends RecyclerView.ViewHolder{

        TextView tableTitle, prop1, prop2, prop3, prop4, prop5, prop6;
        FrameLayout tableFrame;
        private boolean longClick;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            tableTitle = itemView.findViewById(R.id.table_data_list);
            tableFrame = itemView.findViewById(R.id.table_frame);
            prop1 = itemView.findViewById(R.id.prop_1);
            prop2 = itemView.findViewById(R.id.prop_2);
            prop3 = itemView.findViewById(R.id.prop_3);
            prop4 = itemView.findViewById(R.id.prop_4);
            prop5 = itemView.findViewById(R.id.prop_5);
            prop6 = itemView.findViewById(R.id.prop_6);

            tableFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null && !longClick){
                        Table table = list.get(getAdapterPosition());
                        listener.openTableData(table, bgColor, itemColor);
                    }
                    longClick = false;
                }
            });
            tableFrame.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClick = true;
                    if(listener != null){
                        Table table = list.get(getAdapterPosition());
                        listener.openTableInformation(table, bgColor, itemColor);
                    }
                    return false;
                }
            });
        }
    }
}
