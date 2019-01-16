
package com.example.archer.joy_db.model;

import com.example.archer.joy_db.enums.SqlDataTypes;

import java.util.Comparator;
import java.util.Objects;


public class Column implements Nameable, Propertyable{

    private String name;
    private SqlDataTypes type;
    private int position;
    
    public Column(){
        name = "_";
        type = SqlDataTypes.VARCHAR;
    }
    
    public Column(String name){
        this.name = name;
        type = SqlDataTypes.VARCHAR;
    }
    
    public Column(String name, int position){
        this.name = name;
        this.position = position;
        type = SqlDataTypes.VARCHAR;
    }
    
    public Column(String name, SqlDataTypes type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName(){
        return name;
    }

    public SqlDataTypes getType() {
        return type;
    }

    public void setType(SqlDataTypes type){
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + type + ") " + name;
    }

    @Override
    public boolean equals(Object obj) {
       if(this == obj){
           return true;
       }
       if(obj == null){
           return false;
       }
       if(obj instanceof Column){
           Column other = (Column) obj;
           if(this.name.equalsIgnoreCase(other.name)){
               return true;
           }
       }
       return false;
    }
    
    public static SqlDataTypes mapType(String typeName){
        switch(typeName){
            case "shortint":
                return SqlDataTypes.SHORTINT;
            case "int":
                return SqlDataTypes.INT;
            case "biggint":
                return SqlDataTypes.BIGINT;
            case "varchar":
                return SqlDataTypes.VARCHAR;
            case "date":
                return SqlDataTypes.DATE;
            case "longblob":
                return SqlDataTypes.BLOB;
            default:
                return SqlDataTypes.VARCHAR;
        }
    }

    @Override
    public String getProperty() {
        return type.toString();
    }

    @Override
    public Object getPropertyVal() {
        return name;
    }

    public static class PositionComparator implements Comparator<Column>{

        @Override
        public int compare(Column o1, Column o2) {
            return o1.position - o2.position;
        }
    }

}
