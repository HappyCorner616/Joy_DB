package com.example.archer.joy_db.components;

import com.example.archer.joy_db.moduleScopes.InformationScope;
import com.example.archer.joy_db.modules.InformationModule;
import com.example.archer.joy_db.view.schemas.SchemasListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {InformationModule.class})
@InformationScope
public interface InformationComponent {
    void inject(SchemasListPresenter presenter);
}
