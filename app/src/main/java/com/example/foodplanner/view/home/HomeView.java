package com.example.foodplanner.view.home;

import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.CategoryResponse;

import java.util.List;

public interface HomeView {
    void showCategorySuccessMessage(List<CategoriesItem> categoriesItems);
    void showCategoryErrorMessage(String error);
}
