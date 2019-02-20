package com.example.archer.joy_db.view.table.data;


import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.view.MyColor;


public class RefSelectFragment extends TableDataFragment {

    private RefSelectFragmentListener listener;

    public static RefSelectFragment getNewInstance(String refSchema, String refTable){
            RefSelectFragment tableDataFragment = new RefSelectFragment();
        tableDataFragment.table = new Table(refSchema, refTable);
        tableDataFragment.bgColor = MyColor.whiteColor();
        tableDataFragment.itemColor = tableDataFragment.bgColor.copy(20, MyColor.DARKER);
        return tableDataFragment;
    }

    public void setListener(RefSelectFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void rowDataClick(Row row, MyColor bgColor, MyColor itemColor) {
        if(listener != null){
            listener.getSelectedRow(row);
        }
        getFragmentManager().beginTransaction()
                .detach(this)
                .commit();
    }

    public interface RefSelectFragmentListener{
        void getSelectedRow(Row row);
    }
}
