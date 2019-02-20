package com.example.archer.joy_db.components;

import com.example.archer.joy_db.modules.DataModule;
import com.example.archer.joy_db.modules.InformationModule;
import com.example.archer.joy_db.modules.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {MainModule.class})
@Singleton
public interface AppComponent {
    InformationComponent plus(InformationModule module);
    DataComponent plus(DataModule module);
}
