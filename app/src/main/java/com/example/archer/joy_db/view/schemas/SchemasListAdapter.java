package com.example.archer.joy_db.view.schemas;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.view.MyColor;

import java.util.ArrayList;
import java.util.List;

public class SchemasListAdapter extends RecyclerView.Adapter<SchemasListAdapter.SchemaViewHolder> {

    private static final int DEFAULT_SCHEMA_BG = 250;
    private static final int TABLE_BG_SHIFT = 50;

    private List<Schema> list;
    private List<MyColor> bgColors;
    private SchemaListAdapterListener listener;

    public SchemasListAdapter(List<Schema> list) {
        this.list = list;
        bgColors = getBgColors();
    }

    public void setListener(SchemaListAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SchemaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schema_item, viewGroup, false);
        return new SchemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemaViewHolder viewHolder, int i) {
        Schema schema = list.get(i);

        MyColor schemaBgColor = bgColors.get(i % bgColors.size());
        MyColor tablesBgColor = schemaBgColor.copy(TABLE_BG_SHIFT, MyColor.DARKER);

        viewHolder.schemaTitle.setText(schema.getName());
        viewHolder.schemaFrame.setBackgroundColor(schemaBgColor.asInt());

        TableListAdapter adapter = new TableListAdapter(schema.getTables());
        adapter.setListener(listener);
        adapter.setColors(schemaBgColor, tablesBgColor);
        viewHolder.tables.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SchemaViewHolder extends RecyclerView.ViewHolder{

        TextView schemaTitle;
        RecyclerView tables;
        ConstraintLayout schemaFrame;

        public SchemaViewHolder(@NonNull View itemView) {
            super(itemView);
            schemaTitle = itemView.findViewById(R.id.schema_title);
            schemaFrame = itemView.findViewById(R.id.schema_frame);
            tables = itemView.findViewById(R.id.tables_list);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            tables.setLayoutManager(manager);
        }
    }

    private static List<MyColor> getBgColors(){
        List<MyColor> list = new ArrayList<>();
        list.add(new MyColor(255, 204, 153));
        list.add(new MyColor(255, 255, 153));
        list.add(new MyColor(204, 255, 153));
        list.add(new MyColor(153, 255, 204));
        return list;
    }

    public interface SchemaListAdapterListener{
        void openTableData(Table table, MyColor schemaColor, MyColor tableColor);
        void openTableInformation(Table table, MyColor schemaColor, MyColor tableColor);
    }

}
