package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.CategoryResponse;

import java.util.List;

public interface CategoryCallback {
    void onSuccessResult(List<CategoriesItem> categoriesItems);
    void onCategoryFailure(String error);
}
