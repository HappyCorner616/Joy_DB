
package com.example.archer.joy_db.model.sql;

import android.util.Log;

import com.example.archer.joy_db.model.interfaces.Nameable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.archer.joy_db.App.MY_TAG;

public class Table implements Nameable {

    private String schemaName;
    private String name;
    Map<String, Column> columns;
    List<Row> rows;

    public Table(){
        this.name = "_";
        this.columns = new TreeMap<>();
        this.rows = new ArrayList<>();
    }

    public Table(String name){
        this.name = name;
        this.columns = new TreeMap<>();
        this.rows = new ArrayList<>();
    }

    public Table(String schemaName, String name){
        this.schemaName = schemaName;
        this.name = name;
        this.columns = new TreeMap<>();
        this.rows = new ArrayList<>();
    }

    @Override
    public String getName(){
        return name;
    }

    public List<Row> getRows(){
        Log.d(MY_TAG, "returned: " + rows.size() + " rows");
        return rows;
    }

    public List<Column> getColumns(){
        List<Column> list = new ArrayList<>(columns.values());
        Collections.sort(list, new Column.PositionComparator());
        return list;
    }

    public boolean empty(){
        return rows.size() == 0;
    }

    public boolean emptyColumns(){
        return columns.size() == 0;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void clearRows(){
        rows.clear();
    }

    public void addRow(Row row) throws Exception{
        for(Column column : row.columns()){
            if(!columns.containsKey(column.getName())){
                throw new Exception("Table '" + name + "' have not collumn '" + column + "'");
            }
        }
        rows.add(row);
    }

    public Row emptyRow(){
        List<Cell> cellList = new ArrayList<>();
        for(Column c : columns.values()){
            if(c.isNumeric()){
                cellList.add(new Cell(c, 0D));
            }else if(c.isLOB()){
                cellList.add(new Cell(c, 0D));
            }else if(c.isDate()){
                cellList.add(new Cell(c, ""));
            }else if(c.isString()){
                cellList.add(new Cell(c, ""));
            }else{
                cellList.add(new Cell(c, ""));
            }
        }
        Row row = new Row(cellList);
        return row;
    }

    public void addColumn(Column c) throws Exception{
        if(columns.containsKey(c.getName())){
            throw new Exception("This colunm already exist!");
        }
        columns.put(c.getName(), c);
    }

}
