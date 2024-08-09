package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;



public class ListsDetailsBy implements Serializable {

    @SerializedName("strMealThumb")
    private String strMealThumb;

    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    public String getStrMealThumb(){
        return strMealThumb;
    }

    public String getIdMeal(){
        return idMeal;
    }

    public String getStrMeal(){
        return strMeal;
    }
}