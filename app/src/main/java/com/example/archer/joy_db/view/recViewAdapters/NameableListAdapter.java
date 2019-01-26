package com.example.archer.joy_db.view.recViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.interfaces.Nameable;

import java.util.List;

public class NameableListAdapter<T extends Nameable> extends RecyclerView.Adapter<NameableListAdapter.NameableViewHolder>{

    private List<T> list;
    private NameableListAdapterListener listener;

    public NameableListAdapter(List<T> list) {
        this.list = list;
    }

    public void setListener(NameableListAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NameableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new NameableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameableListAdapter.NameableViewHolder nameableViewHolder, int i) {
        T item = list.get(i);
        nameableViewHolder.rowTxt.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NameableViewHolder extends RecyclerView.ViewHolder{

        TextView rowTxt;
        boolean longClick;

        public NameableViewHolder(@NonNull View itemView) {
            super(itemView);
            rowTxt = itemView.findViewById(R.id.row_txt);
            longClick = false;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(longClick){
                        longClick = false;
                    }else if(listener != null){
                        listener.onRowClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(list != null){
                        longClick = true;
                        listener.onRowLongClick(getAdapterPosition());
                    }
                    return false;
                }
            });
        }
    }

    public interface NameableListAdapterListener{
        void onRowClick(int position);
        void onRowLongClick(int position);
    }

}
