package com.example.archer.joy_db.view;

import android.graphics.Color;

public class MyColor{

    public static final int BRIGHTER = 0;
    public static final int DARKER = 1;

    private int red;
    private int green;
    private int blue;

    public MyColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public MyColor() {
        this(255, 255, 255);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int asInt(){
        return Color.rgb(red, green, blue);
    }

    public MyColor copy(){
        return new MyColor(red, green, blue);
    }

    public MyColor copy(int shift, int light){
        int newR = red;
        int newG = green;
        int newB = blue;
        if(light == BRIGHTER){
            newR = newR + shift < 255 ? newR + shift : 255;
            newG = newG + shift < 255 ? newG + shift : 255;
            newB = newB + shift < 255 ? newB + shift : 255;
        }else if(light == DARKER){
            newR = newR - shift > 0 ? newR - shift : 0;
            newG = newG - shift > 0 ? newG - shift : 0;
            newB = newB - shift > 0 ? newB - shift : 0;
        }
        return new MyColor(newR, newG, newB);
    }

    public static MyColor whiteColor(){
        return new MyColor(255, 255, 255);
    }

}
