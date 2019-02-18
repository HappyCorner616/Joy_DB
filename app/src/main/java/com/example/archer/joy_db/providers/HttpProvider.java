package com.example.archer.joy_db.providers;

import android.util.Log;

import com.example.archer.joy_db.model.ErrorResponse;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.sql.Table;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.archer.joy_db.App.MY_TAG;

public class HttpProvider {

    private static final String BASE_URL = "http://192.168.1.12:8080/Joy_DB_2/";
    private static final String BASE_URL_MOBILE = "http://192.168.43.155:8080/Joy_DB_2/";
    private static final String BASE_URL_AWS = "http://52.87.92.107:8080/Joy_DB_2/";

    private static HttpProvider instance = new HttpProvider();

    private Api api;
    private Gson gson;

    private HttpProvider(){

        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();

        api = new Retrofit.Builder()
                .baseUrl(BASE_URL_AWS)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);
        gson = new Gson();
    }

    public static HttpProvider getInstance(){
        return instance;
    }

    public List<Schema> getSchemas() throws Exception {
        Log.d(MY_TAG, "getSchemas start: ");
        Call<Schemas> call = api.schemas();
        Response<Schemas> response = call.execute();
        if(response.isSuccessful()){
            return response.body().getSchemas();
        }else{
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            throw new Exception(errorResponse.getError());
        }
    }

    public Table getTable(String schemaName, String tableName, boolean filled) throws Exception {
        Call<Table> call = api.table(schemaName, tableName, filled);
        Response<Table> response = call.execute();
        if(response.isSuccessful()){
            return response.body();
        }else{
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            throw new Exception(errorResponse.getError());
        }
    }

    public Row addRow(Row row, String schemaName, String tableName) throws Exception {
        Call<Row> call = api.addRow(schemaName, tableName, row);
        Response<Row> response = call.execute();
        if(response.isSuccessful()){
            return response.body();
        }else{
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            throw new Exception(errorResponse.getError());
        }
    }

    public Row updateRow(Row row, String schemaName, String tableName) throws Exception {
        Log.d(MY_TAG, "updateRow: " + schemaName + "-" + tableName + "-(" + row.toString() + ")");
        Call<Row> call = api.updateRow(schemaName, tableName, row);
        Response<Row> response = call.execute();
        if(response.isSuccessful()){
            return response.body();
        }else{
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            throw new Exception(errorResponse.getError());
        }
    }

}
