package com.example.archer.joy_db.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.Schema;

public class SchemaFragment extends Fragment {

    private FrameLayout titleFrame;
    private TextView titleTxt;

    private Schema schema;

    public static SchemaFragment getNewInstance(Schema schema){
        SchemaFragment schemaFragment = new SchemaFragment();
        schemaFragment.schema = schema;
        return schemaFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.roll_list, container, false);

        titleFrame = view.findViewById(R.id.title_frame);
        titleTxt = view.findViewById(R.id.title_txt);

        titleTxt.setText(schema.getName());

        return view;
    }

    @Override
    public String toString() {
        return "SchemaFragment{" +
                "schema=" + schema.getName() +
                '}';
    }
}
