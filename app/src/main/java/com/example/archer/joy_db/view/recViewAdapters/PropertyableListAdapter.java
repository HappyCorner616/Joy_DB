package com.example.archer.joy_db.view.recViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.interfaces.Propertyable;

import java.util.List;

public class PropertyableListAdapter<T extends Propertyable> extends RecyclerView.Adapter<PropertyableListAdapter.PropertyableViewHolder> {

    private List<T> list;

    public PropertyableListAdapter(List<T> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PropertyableListAdapter.PropertyableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.propertyable_row, viewGroup, false);
        return new PropertyableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyableListAdapter.PropertyableViewHolder propertyableViewHolder, int i) {
        Propertyable item = list.get(i);
        propertyableViewHolder.property.setText(item.getProperty());
        propertyableViewHolder.val.setText(item.getPropertyVal().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PropertyableViewHolder extends RecyclerView.ViewHolder{

        TextView property;
        TextView val;

        public PropertyableViewHolder(@NonNull View itemView) {
            super(itemView);
            property = itemView.findViewById(R.id.row_property);
            val = itemView.findViewById(R.id.row_val);
        }
    }

}
