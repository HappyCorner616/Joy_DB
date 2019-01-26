package com.example.archer.joy_db.model.interfaces;

public interface EditablePropertyable extends Propertyable {
    int typeForEditField();
    void setVal(Object val);
}
