
package com.example.archer.joy_db.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Table {
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

    public String getName(){
        return name;
    }

    public List<Row> getRows(){
        return rows;
    }

    public List<Column> getColumns(){
        List<Column> list = new ArrayList<>(columns.values());
        Collections.sort(list);
        return list;
    }

    public boolean emptyRows(){
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

    public void initializeColumns(Row row) throws Exception{
        if(!emptyColumns()){
            throw new Exception("Column already initialized");
        }
        for(Column c : row.columns()){
            columns.put(c.getName(), c);
        }
    }

    public void addRow(Row row) throws Exception{
        for(Column c : row.columns()){
            if(!c.equals(columns.get(c.getName()))){
                throw new Exception("Table '" + name + "' have not collumn '" + c.toString());
            }
        }
        rows.add(row);
    }

    public void addColumn(Column c) throws Exception{
        if(columns.containsKey(c.getName())){
            throw new Exception("This colunm already exist!");
        }
        columns.put(c.getName(), c);
    }
}
