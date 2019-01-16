
package com.example.archer.joy_db.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Row implements Nameable{

    private Map<String, Object> vals;

    public Row() {
        vals = new TreeMap<>();
    }

    public Row(Map<String, Object> vals) {
        this.vals = vals;
    }

    public void addVal(String columnName, Object val){
        vals.put(columnName, val);
    }

    public Object getVal(String columnName){
        return vals.get(columnName);
    }

    public Map<String, Object> getVals(){
        return vals;
    }

    public void setVals(Map<String, Object> vals){
        this.vals = vals;
    }

    public List<Object> vals(){
        return new ArrayList<>(vals.values());
    }

    public Set<String> columns(){
        return vals.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Object> entry : vals.entrySet()){
            sb.append("[" + entry.getKey() + ": " + entry.getValue() + "]");
        }
        return sb.toString();
    }

    public List<Cell> getCells(){
        List<Cell> list = new ArrayList<>();
        for(Entry<String, Object> entry : vals.entrySet()){
            list.add(new Cell(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    @Override
    public String getName() {
        Object idVal = vals.get("id");
        if(idVal != null){
            return "(id) " + idVal.toString();
        }

        Set<Entry<String, Object>> entrySet = vals.entrySet();
        Iterator<Entry<String, Object>> iterator = entrySet.iterator();
        if(iterator.hasNext()){
            Entry<String, Object> entry = iterator.next();
            return "(" + entry.getKey() + ") " + entry.getValue();
        }else{
            return "()";
        }
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
