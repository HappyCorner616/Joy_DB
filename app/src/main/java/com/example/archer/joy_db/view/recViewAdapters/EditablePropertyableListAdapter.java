package com.example.archer.joy_db.view.recViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.interfaces.EditablePropertyable;

import java.util.List;

public class EditablePropertyableListAdapter<T extends EditablePropertyable> extends RecyclerView.Adapter<EditablePropertyableListAdapter.EditablePropertyableViewHolder> {

    private List<T> list;

    public EditablePropertyableListAdapter(List<T> list) {
        this.list = list;
    }

    public List<T> getList(){
        return list;
    }

    @NonNull
    @Override
    public EditablePropertyableListAdapter.EditablePropertyableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.editable_propertyable_row, viewGroup, false);

        return new EditablePropertyableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditablePropertyableListAdapter.EditablePropertyableViewHolder viewHolder, int i) {
        T item = list.get(i);
        viewHolder.property.setText(item.getProperty());
        viewHolder.val.setInputType(item.typeForEditField());
        viewHolder.val.setText(item.getPropertyVal().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class EditablePropertyableViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {

        TextView property;
        EditText val;

        public EditablePropertyableViewHolder(@NonNull View itemView) {
            super(itemView);
            property = itemView.findViewById(R.id.row_property);
            val = itemView.findViewById(R.id.row_val);
            val.setOnFocusChangeListener(this);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v.getId() == R.id.row_val && !hasFocus){
                EditablePropertyable item = list.get(getAdapterPosition());
                item.setVal(((EditText)v).getText().toString());
            }
        }
    }

}
