package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ListsDetailsbyResponse {

    @SerializedName("meals")
    private List<ListsDetailsBy> meals;

    public List<ListsDetailsBy> getListDetails(){
        return meals;
    }
}