package com.example.archer.joy_db.interfaces.data;

public interface IDataRepository {
    void getTableData(String schemaName, String tableName, IDataRepositoryCallback callback);
}
