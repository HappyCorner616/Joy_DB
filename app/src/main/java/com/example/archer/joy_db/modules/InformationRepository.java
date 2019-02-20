package com.example.archer.joy_db.modules;

import com.example.archer.joy_db.interfaces.information.IInformationRepository;
import com.example.archer.joy_db.interfaces.information.IInformationRepositoryCallback;
import com.example.archer.joy_db.model.ErrorResponse;
import com.example.archer.joy_db.model.Schemas;
import com.example.archer.joy_db.providers.Api;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationRepository implements IInformationRepository {

    private Api api;
    private Gson gson;

    public InformationRepository(Api api) {
        this.api = api;
        gson = new Gson();
    }

    @Override
    public void getAllSchemas(final IInformationRepositoryCallback callback) {
        api.schemas().enqueue(new Callback<Schemas>() {
            @Override
            public void onResponse(Call<Schemas> call, Response<Schemas> response) {
                if(response.isSuccessful()){
                    callback.getSchemas(response.body());
                }else{
                    try{
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        callback.showError(errorResponse.getError());
                    } catch (IOException e) {
                        callback.showError(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Schemas> call, Throwable t) {
                callback.showError(t.getMessage());
            }
        });
    }
}
