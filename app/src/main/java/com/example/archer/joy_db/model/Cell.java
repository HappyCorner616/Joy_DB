package com.example.archer.joy_db.model;

public class Cell implements Propertyable, Comparable<Column>{

    private Column column;
    private Object val;

    public Cell(Column column, Object val) {
        this.column = column;
        this.val = val;
    }

    public Column getColumn() {
        return column;
    }

    @Override
    public String getProperty() {
        return column.getName();
    }

    @Override
    public Object getPropertyVal() {
        return val;
    }

    @Override
    public boolean equals(Object obj) {
       if(obj == null) return false;
       if(this == obj) return true;
       if(obj instanceof Cell){
           Cell tmp = (Cell)obj;
           return this.column.equals(tmp.column);
       }
       return false;
    }

    @Override
    public int compareTo(Column o) {
        return this.column.compareTo(o);
    }
}