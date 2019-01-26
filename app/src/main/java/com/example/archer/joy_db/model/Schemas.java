/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.archer.joy_db.model;

import com.example.archer.joy_db.model.sql.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Archer
 */
public class Schemas {
    private List<Schema> schemas;

    public Schemas() {
        schemas = new ArrayList<>();
    }

    public Schemas(List<Schema> schemas) {
        this.schemas = schemas;
    }
    
    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }

    @Override
    public String toString() {
        return "Schemas{" + "schemas=" + schemas + '}';
    }
    
    
    
}
