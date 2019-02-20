package com.example.archer.joy_db.interfaces.information;

import com.example.archer.joy_db.model.Schemas;

public interface IInformationRepositoryCallback {
    void getSchemas(Schemas schemas);
    void showError(String error);
}
