package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.dto.MealItem;

import com.example.foodplanner.model.dto.MealsDetailResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepositoryView {
    //////
    //remote
    public void CategoryNetworkCall(CategoryCallback categoryCallBack);
    Single<ListsDetailsbyResponse> CategoryDetailsNetworkCall(String category);
    Single<MealsDetailResponse> getMealByIdNetworkCall(String name);





    /////////
    //local
    Flowable<List<MealItem>> getAllFavMeals();
    Completable insertMealToFavl(MealItem mealsItem);
    Completable deleteFromFav(MealItem mealsItem);
}
