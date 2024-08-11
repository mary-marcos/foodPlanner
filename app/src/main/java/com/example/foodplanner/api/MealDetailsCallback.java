package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.MealsDetail;

import java.util.List;

public interface MealDetailsCallback {
    public void onSuccessResult(List<MealsDetail> mealsDetailList);
    public void onFailureResult(String errorMsg);

}