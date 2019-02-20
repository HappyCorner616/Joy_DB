package com.example.archer.joy_db.modules;

import com.example.archer.joy_db.interfaces.information.IInformationRepository;
import com.example.archer.joy_db.moduleScopes.InformationScope;
import com.example.archer.joy_db.providers.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class InformationModule {

    @Provides
    @InformationScope
    IInformationRepository provideInformationRepository(Api api){
        return new InformationRepository(api);
    }

}
