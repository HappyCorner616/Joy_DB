package com.example.archer.joy_db.providers;

import com.example.archer.joy_db.model.ErrorResponse;
import com.example.archer.joy_db.model.MessageResponse;
import com.example.archer.joy_db.model.sql.Row;
import com.example.archer.joy_db.model.sql.Schema;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.sql.Table;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpProvider {

    private static final String BASE_URL = "http://192.168.1.12:8080/Joy_DB_2/";
    private static final String BASE_URL_MOBILE = "http://192.168.43.155:8080/Joy_DB_2/";

    private static HttpProvider instance = new HttpProvider();

    private Api api;
    private Gson gson;

    private HttpProvider(){

        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();

        api = new Retrofit.Builder()
                //.baseUrl(BASE_URL)
                .baseUrl(BASE_URL_MOBILE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);
        gson = new Gson();
    }

    public static HttpProvider getInstance(){
        return instance;
    }

    public List<Schema> getSchemas() throws Exception {
        Call<Schemas> call = api.schemas();
        Response<Schemas> response = call.execute();
        if(response.isSuccessful()){
            return response.body().getSchemas();
        }else{
            throw new Exception(response.errorBody().string());
        }
        //return getTESTSchemas();
    }

    public Table getTable(String schemaName, String tableName, boolean filled) throws Exception {
        Call<Table> call = api.table(schemaName, tableName, filled);
        Response<Table> response = call.execute();
        if(response.isSuccessful()){
            return response.body();
        }else{
            throw new Exception(response.errorBody().string());
        }
    }

    public String addRow(Row row, String schemaName, String tableName) throws IOException {
        Call<MessageResponse> call = api.addRow(schemaName, tableName, row);
        Response<MessageResponse> response = call.execute();
        if(response.isSuccessful()){
            return response.body().getMessage();
        }else{
            ErrorResponse error = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            return error.getError();
        }
    }

    public String updateRow(Row row, String schemaName, String tableName) throws IOException {
        Call<MessageResponse> call = api.updateRow(schemaName, tableName, row);
        Response<MessageResponse> response = call.execute();
        if(response.isSuccessful()){
            return response.body().getMessage();
        }else{
            ErrorResponse error = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            return error.getError();
        }
    }

    // TEST
    private static List<Schema> getTESTSchemas(){
        List<Schema> list = new ArrayList<>();
        list.add(new Schema("users"));
        list.add(new Schema("people"));
        list.add(new Schema("countries"));
        list.add(new Schema("colors"));
        list.add(new Schema("products"));
        list.add(new Schema("dogs"));
        list.add(new Schema("cats"));
        return list;
    }

}
