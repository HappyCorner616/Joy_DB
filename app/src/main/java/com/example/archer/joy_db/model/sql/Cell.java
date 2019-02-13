package com.example.archer.joy_db.model.sql;

public class Cell implements Comparable<Cell>{

    private Column column;
    private Object val;

    public Cell(Column column, Object val) {
        this.column = column;
        this.val = val;
    }

    public void setVal(Object val) {
        if(getColumn().isInt()){
            this.val = Integer.valueOf("" + val);
        }else if(getColumn().isDecimal()){
            this.val = Double.valueOf("" + val);
        }else{
            this.val = val;
        }
    }

    public Column getColumn() {
        return column;
    }

    public Object getVal() {
        return val;
    }

    public int intVal(){
        if(column.isInt()){
            return ((Double)val).intValue();
        }else{
            return 0;
        }
    }

    public double decVal(){
        if(column.isDecimal() || column.isInt()){
            return (Double)val;
        }else{
            return 0D;
        }
    }

    public Cell copy(){
        return new Cell(column.copy(), val);
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
    public int compareTo(Cell c) {
        return -this.column.compareTo(c.column);
    }

}