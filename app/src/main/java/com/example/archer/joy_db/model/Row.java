
package com.example.archer.joy_db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Row {

    private Map<Column, Object> vals;

    public Row() {
        vals = new TreeMap<>();
    }
     
    public Row(Map<Column, Object> vals) {
        this.vals = vals;
    }
    
    public void addVal(Column column, Object val){
        vals.put(column, val);
    }
    
    public Object getVal(Column column){
        return vals.get(column);
    }
    
    public Map<Column, Object> valsAsMap(){
        return vals;
    }
    
    public List<Object> vals(){
        return new ArrayList<>(vals.values());
    }
    
    public Set<Column> columns(){
        return vals.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<Column, Object> entry : vals.entrySet()){
            sb.append("[" + entry.getKey().getName() + ": " + entry.getValue() + "]");
        }
        return sb.toString();
    }

    public List<Cell> getCells(){
        List<Cell> list = new ArrayList<>();
        for(Entry<Column, Object> entry : vals.entrySet()){
            list.add(new Cell(entry.getKey().getName(), entry.getValue()));
        }
        return list;
    }

    public class Cell implements Propertyable{

        private String property;
        private Object val;

        public Cell(String property, Object val) {
            this.property = property;
            this.val = val;
        }

        @Override
        public String getProperty() {
            return property;
        }

        @Override
        public Object getPropertyVal() {
            return val;
        }
    }

}
