package com.example.archer.joy_db.providers;

import com.example.archer.joy_db.model.MessageResponse;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.sql.Table;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Api {
    @GET("Schema")
    Call<Schemas> schemas();

    @GET("Table")
    Call<Table> table(@Header("schemaName") String schemaName, @Header("tableName") String tableName, @Header("filled") boolean filled);

    @POST("Row")
    Call<Row> addRow(@Header("schemaName") String schemaName, @Header("tableName") String tableName, @Body Row row);

    @PUT("Row")
    Call<Row> updateRow(@Header("schemaName") String schemaName, @Header("tableName") String tableName, @Body Row row);

}
