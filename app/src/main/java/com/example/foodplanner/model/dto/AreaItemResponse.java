package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaItemResponse {

    @SerializedName("meals")
    private List<AreaItem> meals;

    public List<AreaItem> getAreasList(){
        return meals;
    }
}


