package com.example.foodplanner.view.meal_by_category;

import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.ListsDetailsBy;

import java.util.List;


public interface MealView {
    void showMealSuccessMessage(List<ListsDetailsBy> mealItems);
    void showCategoryErrorMessage(String error);
}
