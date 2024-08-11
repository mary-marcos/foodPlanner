package com.example.foodplanner.view.meal_details;

import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.MealsDetail;

import java.util.List;

public interface MealDetailView {

    void onInsertFavSuccess();
    void onInsertFavError(String error);

    public void showItemDetailData(List<MealsDetail> mealsItem);
   // public void showItemDetailData(MealItem mealsItem);
   // public void addToFav(MealsItem mealsItem);
    public void showItemDetailErrorMsg(String error);


}