package com.example.archer.joy_db.modules;

import com.example.archer.joy_db.interfaces.data.IDataRepository;
import com.example.archer.joy_db.interfaces.data.IDataRepositoryCallback;
import com.example.archer.joy_db.model.ErrorResponse;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.sql.Table;
import com.example.archer.joy_db.providers.Api;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository implements IDataRepository {

    private Api api;
    private Gson gson;

    public DataRepository(Api api) {
        this.api = api;
        gson = new Gson();
    }

    @Override
    public void getTableData(String schemaName, String tableName, final IDataRepositoryCallback callback) {
       api.table(schemaName, tableName, true).enqueue(new Callback<Table>() {
           @Override
           public void onResponse(Call<Table> call, Response<Table> response) {
               if(response.isSuccessful()){
                   callback.getTable(response.body());
               }else{
                   try {
                       ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                       callback.showError(errorResponse.getError());
                   } catch (IOException e) {
                       callback.showError(e.getMessage());
                   }
               }
           }

           @Override
           public void onFailure(Call<Table> call, Throwable t) {
               callback.showError(t.getMessage());
           }
       });
    }
}
