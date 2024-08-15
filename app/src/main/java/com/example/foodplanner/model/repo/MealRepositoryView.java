package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.AreaCallback;
import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.model.dto.AreaItemResponse;
import com.example.foodplanner.model.dto.IngredientsItemResponse;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.dto.MealItem;

import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepositoryView {
    //////
    //remote
    public void CategoryNetworkCall(CategoryCallback categoryCallBack);
    Single<ListsDetailsbyResponse> CategoryDetailsNetworkCall(String category);
    Single<MealsDetailResponse> getMealByIdNetworkCall(String id);
    Single<MealsDetailResponse> getMealByNameNetworkCall(String name);
    Single<MealsDetailResponse> getRandomNetworkCall();
    public void AreasNetworkCall(AreaCallback areaMealCallback);
    //Single<AreaItemResponse> getAreaNetworkCall();
    public Single<ListsDetailsbyResponse> AreaDetailsNetworkCall(String category);
    @NonNull Single<IngredientsItemResponse> getingredientworkCall();




    /////////
    //local
    Flowable<List<MealItem>> getAllFavMeals();
    Completable insertMealToFavl(MealItem mealsItem);
    Completable deleteFromFav(MealItem mealsItem);

    Flowable<List<WeekPlan>> getAllweekPlanMeals();
    Flowable<List<WeekPlan>> getmealbydate(String date);
    Completable insertweekplanMeal(WeekPlan weekPlanitem);
    Completable deleteFromweekplan(WeekPlan weekPlanitem);

    public void deleteAllTheCalenderList();
    public void deleteAllTheFavoriteList();


    //
    //remote DB
    void insertMealRemoteToFavorite(MealItem mealsItem);
    void insertMealRemoteToWeekPlan(WeekPlan weekPlan);
    void deleteMealRemoteFromFavorite(MealItem mealsItem);
    void deleteMealRemoteFromWeekPlan(WeekPlan weekPlan);

}
