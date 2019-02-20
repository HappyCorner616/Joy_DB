package com.example.archer.joy_db.modules;

import com.example.archer.joy_db.interfaces.data.IDataRepository;
import com.example.archer.joy_db.moduleScopes.DataScope;
import com.example.archer.joy_db.providers.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Provides
    @DataScope
    IDataRepository provideDataRepository(Api api){
        return new DataRepository(api);
    }
}
