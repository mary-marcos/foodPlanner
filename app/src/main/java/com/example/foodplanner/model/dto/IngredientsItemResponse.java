package com.example.foodplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsItemResponse {

    @SerializedName("ingremeals")
    private List<IngredientsItem> ingremeals;

    public List<IngredientsItem> getIngredientList(){
        return ingremeals;
    }
}