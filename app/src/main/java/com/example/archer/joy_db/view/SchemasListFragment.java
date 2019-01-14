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

import java.util.ArrayList;
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
        recyclerView.setItemViewCacheSize(adapter.getItemCount());
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
        private List<View> buffer;

        public SchemasAdapter(List<Schema> list) {
            this.list = list;
            buffer = new ArrayList<>();
        }

        @NonNull
        @Override
        public SchemaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.row_container, viewGroup, false);
            Log.d(MY_TAG, "id: " + (i + ID_SHIFT));
            SchemaViewHolder schemaViewHolder = new SchemaViewHolder(itemView);
            View container = schemaViewHolder.setContainerId(i + ID_SHIFT);
            buffer.add(container);
            Log.e(MY_TAG, "buffer size: " + buffer.size());
            return schemaViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SchemaViewHolder schemaViewHolder, int i) {
            Schema schema = schemasList.get(i);
            int id = i + ID_SHIFT;
            Log.e(MY_TAG, "before setContainerId: " + schemaViewHolder.getId());
            View container = schemaViewHolder.setContainerId(id);
            Log.e(MY_TAG, "after setContainerId: " + id);
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
        //private View itemView;

        public SchemaViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.itemView = itemView;
            rowContainer = null;
        }

        public View setContainerId(int id){
            if(rowContainer == null){
                rowContainer = LayoutInflater.from(getContext()).inflate(R.layout.row_container, (ViewGroup) itemView, false);
                ViewGroup.LayoutParams params = rowContainer.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rowContainer.setLayoutParams(params);
                ((ViewGroup)itemView).addView(rowContainer);
            }
            rowContainer.setId(id);
            return rowContainer;
        }

        public int getId(){
            return rowContainer == null ? -1 : rowContainer.getId();
        }

    }


}
