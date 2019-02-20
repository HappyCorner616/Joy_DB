package com.example.archer.joy_db.interfaces.data;

import com.example.archer.joy_db.model.sql.Table;

public interface IDataRepositoryCallback {
    void getTable(Table table);
    void showError(String error);
}
