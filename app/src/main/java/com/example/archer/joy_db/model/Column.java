
package com.example.archer.joy_db.model;

import com.example.archer.joy_db.enums.SqlDataTypes;

import java.util.Objects;


public class Column implements Comparable<Column>, Nameable, Propertyable{

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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Column other = (Column) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
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
    public int compareTo(Column o) {
        int res = position - o.position;
        if(res == 0){
            res = name.compareTo(o.name);
        }
        return res;
    }


    @Override
    public String getProperty() {
        return type.toString();
    }

    @Override
    public Object getPropertyVal() {
        return name;
    }
}
