package com.example.archer.joy_db.view.recViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Column;

import java.util.ArrayList;
import java.util.List;

import static com.example.archer.joy_db.App.MY_TAG;

public class ColumnListAdapter extends RecyclerView.Adapter<ColumnListAdapter.ColumnViewHolder> {

    private List<Column> list;
    private int nameWeight, typeWeight, keyWeight, aiWeight, totalWeight;

    public ColumnListAdapter() {
        list = new ArrayList<>();
        nameWeight = 4;
        typeWeight = 4;
        keyWeight = 3;
        aiWeight = 3;
        totalWeight = nameWeight + typeWeight + keyWeight + aiWeight;
    }

    public ColumnListAdapter(List<Column> list) {
        this.list = list;
        nameWeight = 4;
        typeWeight = 4;
        keyWeight = 3;
        aiWeight = 3;
        setWeights();
    }

    private void setWeights(){
        for(Column column : list){
            nameWeight = Math.max(nameWeight, column.getName().length());
            typeWeight = Math.max(typeWeight, String.valueOf(column.getType()).length());
            keyWeight = Math.max(keyWeight, String.valueOf(column.getKey()).length());
            aiWeight = Math.max(aiWeight, String.valueOf(column.isAutoIncrement()).length());
        }
        Log.d(MY_TAG, "setWeights: nameWeight: " + nameWeight + "; typeWeight: " + typeWeight + "; keyWeight: " + keyWeight + "; aiWeight: " + aiWeight);
        totalWeight = nameWeight + typeWeight + keyWeight + aiWeight;
    }

    @NonNull
    @Override
    public ColumnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout viewRow;
        if(i == 0){
           viewRow = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.column_title, viewGroup, false);
       }else{
           viewRow = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.column_row, viewGroup, false);
       }
       for(int j = 0; j < viewRow.getChildCount(); j++){
           View view = viewRow.getChildAt(j);
           LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
           switch (j){
               case 0:
                   params.weight = totalWeight - nameWeight;
                   break;
               case 1:
                   params.weight = totalWeight - typeWeight;
                   break;
               case 2:
                   params.weight = totalWeight - keyWeight;
                   break;
               case 3:
                   params.weight = totalWeight - aiWeight;
                   break;
           }
           view.setLayoutParams(params);
       }

       return new ColumnViewHolder(viewRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ColumnViewHolder viewHolder, int i) {
        if(i == 0){
            viewHolder.colName.setText("name");
            viewHolder.colType.setText("type");
            viewHolder.colKey.setText("key");
            viewHolder.colAI.setText("AI");
        }else {
            Column column = list.get(i-1);
            viewHolder.colName.setText(column.getName());
            viewHolder.colType.setText(column.getType().toString());
            viewHolder.colKey.setText(column.getKey().toString());
            viewHolder.colAI.setText(String.valueOf(column.isAutoIncrement()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    class ColumnViewHolder extends RecyclerView.ViewHolder{

        TextView colName;
        TextView colType;
        TextView colKey;
        TextView colAI;

        public ColumnViewHolder(@NonNull View itemView) {
            super(itemView);

            colName = itemView.findViewById(R.id.col_name);
            colType = itemView.findViewById(R.id.col_type);
            colKey = itemView.findViewById(R.id.col_key);
            colAI = itemView.findViewById(R.id.col_auto_increment);

        }
    }

}
