package com.example.foodplanner.view.search_meal;

import com.example.foodplanner.model.dto.AreaItem;
import com.example.foodplanner.model.dto.CategoriesItem;

import java.util.List;

public interface SearchView {
    void showCategorySuccessMessage(List<CategoriesItem> categoriesItems);
    void showCategoryErrorMessage(String error);

    void showareaSuccessMessage(List<AreaItem> areaItems);
    void showareaErrorMessage(String error);
}
