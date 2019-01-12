package com.example.archer.joy_db.providers;

import com.example.archer.joy_db.model.Schema;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.Table;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @GET("Schema")
    Call<Schemas> schemas();

    @GET("Table")
    Call<Table> table(@Header("schemaName") String schemaName, @Header("tableName") String tableName, @Header("filled") boolean filled);
}
