package com.example.archer.joy_db.modules;

import android.content.Context;

import com.example.archer.joy_db.providers.Api;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MainModule {

    private static final String BASE_URL = "http://192.168.1.12:8080/Joy_DB_2/";
    private static final String BASE_URL_MOBILE = "http://192.168.43.155:8080/Joy_DB_2/";
    private static final String BASE_URL_AWS = "http://52.87.92.107:8080/Joy_DB_2/";

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    OkHttpClient provideClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Api provideApi(OkHttpClient client){
        return new Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build().create(Api.class);
    }


}
