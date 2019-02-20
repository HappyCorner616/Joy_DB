package com.example.archer.joy_db.components;

import com.example.archer.joy_db.moduleScopes.DataScope;
import com.example.archer.joy_db.modules.DataModule;
import com.example.archer.joy_db.view.schemas.SchemasListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {DataModule.class})
@DataScope
public interface DataComponent {

}
