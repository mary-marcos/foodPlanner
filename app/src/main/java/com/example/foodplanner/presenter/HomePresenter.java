package com.example.foodplanner.presenter;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.repo.MealRepositoryView;
import com.example.foodplanner.view.home.HomeView;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

/////////////////////// will edit for each class have presenter ////////////////////////////////
public class HomePresenter implements CategoryCallback {
    private final HomeView homeMealView;
    private final MealRepositoryView repositoryView;
    public HomePresenter(HomeView homeMealView, MealRepositoryView repositoryView){
        this.homeMealView = homeMealView;
        this.repositoryView = repositoryView;
    }
    public void getCategory() {

        repositoryView.CategoryNetworkCall(this);
    }
    @Override
    public void onSuccessResult(List<CategoriesItem> categoriesItems) {
        homeMealView.showCategorySuccessMessage(categoriesItems);
    }

    @Override
    public void onCategoryFailure(String error) {

    }

    public Single<MealsDetailResponse> getRandomMeal() {
        return repositoryView.getRandomNetworkCall();
    }



    public void deleteAllTheCalendermealList() {
        repositoryView.deleteAllTheCalenderList();
    }


    public void deleteAllTheFavoritemealList() {
        repositoryView.deleteAllTheFavoriteList();
    }
}
