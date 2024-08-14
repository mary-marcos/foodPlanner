package com.example.foodplanner.view.fav_meal;

import com.example.foodplanner.model.dto.MealItem;

import java.util.List;

public interface FavoriteView {
    public void onSuccessDeleteFromFav();
    public void onFailDeleteFromFav(String error);
    void onGetAllFavoriteMealsError(String errorMessage);
    void onGetAllFavoriteFireStoreMeals(List<MealItem>favoriteMeals);
}
