package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.IngredientsItem;

import java.util.List;

public interface IngredientCallback {
    public void onSuccessingredResult(List<IngredientsItem> IngredientsItemList);

    public void onFailureingredResult(String errorMsg);
}
