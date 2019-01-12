package com.example.archer.joy_db.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.archer.joy_db.R;
import com.example.archer.joy_db.model.Schema;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.archer.joy_db.App.MY_TAG;

public class SchemasListFragment extends Fragment {

    private static final int ID_SHIFT = 3000;

    private RecyclerView recyclerView;

    private List<Schema> schemasList;
    private Map<String, Integer> idMap;

    public static SchemasListFragment getNewInstance(List<Schema> schemasList){
        SchemasListFragment schemasListFragment = new SchemasListFragment();
        schemasListFragment.schemasList = schemasList;
        schemasListFragment.idMap = new TreeMap<>();
        return schemasListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schemas_list, container, false);

        recyclerView = view.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        SchemasAdapter adapter = new SchemasAdapter(schemasList);
        recyclerView.setAdapter(adapter);

        /*int previousElementId = -ID_SHIFT;

        for(Schema schema : schemasList){
            ConstraintLayout cl = new ConstraintLayout(getContext());
            int idVal = schemasList.indexOf(schema) + ID_SHIFT;
            String idKey = schema.getName() + "_" + idVal;
            idMap.put(idKey, idVal);
            cl.setId(idVal);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;

            if(previousElementId == -ID_SHIFT){
                params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            }else{
                params.topToBottom = previousElementId;
            }

            ((ViewGroup)view).addView(cl, params);

            previousElementId = idVal;

        }

        addSchemaFragments();*/

        return view;
    }
    private void addSchemaFragments() {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for(Schema schema : schemasList){
            int containerId = schemasList.indexOf(schema) + ID_SHIFT;
            SchemaFragment schemaFragment = SchemaFragment.getNewInstance(schema);
            transaction.add(containerId, schemaFragment);
        }
        transaction.commit();

    }

    class SchemasAdapter extends RecyclerView.Adapter<SchemaViewHolder>{

        private List<Schema> list;

        public SchemasAdapter(List<Schema> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public SchemaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.row_container, viewGroup, false);
            View rowContainer = LayoutInflater.from(getContext()).inflate(R.layout.row_container, (ViewGroup) itemView, false);

            Log.d(MY_TAG, "id: " + (i + ID_SHIFT));

            rowContainer.setId(i + ID_SHIFT);
            ViewGroup.LayoutParams params = rowContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            rowContainer.setLayoutParams(params);

            ((ViewGroup)itemView).addView(rowContainer);
            return new SchemaViewHolder(itemView, rowContainer);
        }

        @Override
        public void onBindViewHolder(@NonNull SchemaViewHolder schemaViewHolder, int i) {
            Schema schema = schemasList.get(i);
            //int id = i + ID_SHIFT;
            int id = schemaViewHolder.getId();
            Log.e(MY_TAG, "onBindViewHolder id: " + id);
            SchemaFragment schemaFragment = SchemaFragment.getNewInstance(schema);
            getChildFragmentManager().beginTransaction()
                    .replace(id, schemaFragment)
                    .commit();

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class SchemaViewHolder extends RecyclerView.ViewHolder{

        private View rowContainer;

        public SchemaViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public SchemaViewHolder(@NonNull View itemView, View rowContainer) {
            super(itemView);
            this.rowContainer = rowContainer;
        }

        public void setContainerId(int id){
            rowContainer.setId(id);
        }

        public int getId(){
            return rowContainer.getId();
        }

    }


}
