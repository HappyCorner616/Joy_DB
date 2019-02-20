package com.example.archer.joy_db.view.schemas;

import com.arellomobile.mvp.MvpView;
import com.example.archer.joy_db.model.sql.Schema;

import java.util.List;

public interface ISchemasListFragment extends MvpView {
    void setWaitingMode();
    void desetWaitingMode();
    void updateSchemasList(List<Schema> list);
    void showError(String error);
}
