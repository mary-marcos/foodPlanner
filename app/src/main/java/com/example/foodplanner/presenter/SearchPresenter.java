package com.example.foodplanner.presenter;

import com.example.foodplanner.api.AreaCallback;
import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.api.IngredientCallback;
import com.example.foodplanner.model.dto.AreaItem;
import com.example.foodplanner.model.dto.AreaItemResponse;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.IngredientsItem;
import com.example.foodplanner.model.dto.IngredientsItemResponse;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.repo.MealRepositoryView;
import com.example.foodplanner.view.home.HomeView;
import com.example.foodplanner.view.search_meal.SearchView;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchPresenter implements CategoryCallback, AreaCallback, IngredientCallback {

    private SearchView searchView;
    private MealRepositoryView repositoryView;
    public SearchPresenter(SearchView searchView, MealRepositoryView repositoryView){
        this.searchView = searchView;
        this.repositoryView = repositoryView;
    }


// category
public void getCategory() {

    repositoryView.CategoryNetworkCall(this);
}

    @Override
    public void onSuccessResult(List<CategoriesItem> categoriesItems) {
        searchView.showCategorySuccessMessage(categoriesItems);
    }

    @Override
    public void onCategoryFailure(String error) {

    }
    ////area

    public void getArea() {

         repositoryView.AreasNetworkCall(this);
    }




    @Override
    public void onSuccessareaResult(List<AreaItem> areaItemsItem) {
        searchView.showareaSuccessMessage(areaItemsItem);
    }

    @Override
    public void onFailureareaResult(String errorMsg) {

    }
    /////ingredients

    public Single<IngredientsItemResponse> getingredients() {
        return repositoryView.getingredientworkCall();
    }

    @Override
    public void onSuccessingredResult(List<IngredientsItem> IngredientsItemList) {

    }

    @Override
    public void onFailureingredResult(String errorMsg) {

    }

    /////search by name

    public Single<MealsDetailResponse> getMealbynameDetail(String name) {
        return repositoryView.getMealByNameNetworkCall(name);
    }
}
