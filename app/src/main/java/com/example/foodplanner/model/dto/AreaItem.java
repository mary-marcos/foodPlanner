package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AreaItem implements Serializable {


    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @SerializedName("strArea")
    private String strArea;

    public String getStrArea() {
        return strArea;
    }
//    public void setStrArea(String strArea) {
//        this.strArea = strArea;
//    }
//
//    public String getStrArea(){
//        return this.strArea;
//    }
}