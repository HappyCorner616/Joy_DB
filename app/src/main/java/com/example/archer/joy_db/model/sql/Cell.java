package com.example.archer.joy_db.model.sql;

import android.view.inputmethod.EditorInfo;

import com.example.archer.joy_db.model.interfaces.EditablePropertyable;

public class Cell implements EditablePropertyable, Comparable<Column>{

    private Column column;
    private Object val;

    public Cell(Column column, Object val) {
        this.column = column;
        this.val = val;
    }

    public void setVal(Object val) {
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

    @Override
    public int typeForEditField(){
        switch (column.getType()){
            case SHORTINT:
            case INT:
            case BIGINT:
                return EditorInfo.TYPE_NUMBER_VARIATION_NORMAL;
            case VARCHAR:
                return EditorInfo.TYPE_CLASS_TEXT;
            case DATE:
                return EditorInfo.TYPE_CLASS_DATETIME;
            default:
                return EditorInfo.TYPE_CLASS_TEXT;
        }
    }

}