package com.example.archer.joy_db.providers;

import com.example.archer.joy_db.model.Schema;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.model.Table;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpProvider {

    private static final String BASE_URL = "http://192.168.1.11:8080/Joy_DB/";

    private static HttpProvider instance = new HttpProvider();

    private Api api;
    private Gson gson;

    private HttpProvider(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);
        gson = new Gson();
    }

    public static HttpProvider getInstance(){
        return instance;
    }

    public List<Schema> getSchemas() throws IOException {
        /*Call<Schemas> call = api.schemas();
        Response<Schemas> response = call.execute();
        if(response.isSuccessful()){
            return response.body().getSchemas();
        }else{
            return new ArrayList<>();
        }*/
        return getTESTSchemas();
    }

    public Table getTable(String schemaName, String tableName, boolean filled) throws IOException {
        Call<Table> call = api.table(schemaName, tableName, filled);
        Response<Table> response = call.execute();
        if(response.isSuccessful()){
            return response.body();
        }else{
            return null;
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
