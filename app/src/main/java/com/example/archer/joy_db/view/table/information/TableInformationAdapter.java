package com.example.archer.joy_db.view.table.information;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Column;
import com.example.archer.joy_db.view.MyColor;

import java.util.List;

import static com.example.archer.joy_db.App.MY_TAG;

public class TableInformationAdapter extends RecyclerView.Adapter<TableInformationAdapter.TableInformationViewHolder> {

    private List<Column> list;
    private MyColor itemColor;

    public TableInformationAdapter(List<Column> list, MyColor itemColor) {
        this.list = list;
        this.itemColor = itemColor;
        Log.d(MY_TAG, "TableInformationAdapter: list: " + list.size());
    }

    @NonNull
    @Override
    public TableInformationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.column_information, viewGroup, false);
        return new TableInformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableInformationViewHolder viewHolder, int i) {
        Column column = list.get(i);
        viewHolder.colName.setText(column.getName());
        viewHolder.colInformation.setText(column.information());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TableInformationViewHolder extends RecyclerView.ViewHolder{

        TextView colName, colInformation;

        public TableInformationViewHolder(@NonNull View itemView) {
            super(itemView);
            colName = itemView.findViewById(R.id.col_name);
            colInformation = itemView.findViewById(R.id.col_information);

            colName.setBackgroundColor(itemColor.asInt());
            colInformation.setBackgroundColor(itemColor.asInt());
        }
    }

}
