package com.example.archer.joy_db;

import android.app.Application;

import com.example.archer.joy_db.components.AppComponent;
import com.example.archer.joy_db.components.DaggerAppComponent;
import com.example.archer.joy_db.components.DataComponent;
import com.example.archer.joy_db.components.InformationComponent;
import com.example.archer.joy_db.modules.DataModule;
import com.example.archer.joy_db.modules.InformationModule;
import com.example.archer.joy_db.modules.MainModule;

public class App extends Application {

    public static final String MY_TAG = "MY_TAG";
    public static App app;

    private AppComponent mainComponent;
    private InformationComponent informationComponent;
    private DataComponent dataComponent;

    public static App get(){
        return app;
    }

    public App(){
        app = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainComponent = DaggerAppComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }

    public InformationComponent informationComponent(){
        if(informationComponent == null){
            informationComponent = mainComponent.plus(new InformationModule());
        }
        return informationComponent;
    }

    public DataComponent dataComponent(){
        if(dataComponent == null){
            dataComponent = mainComponent.plus(new DataModule());
        }
        return dataComponent;
    }
}
