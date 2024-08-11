package com.example.foodplanner.presenter;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.api.CategoryDetailsCallback;
import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.ListsDetailsBy;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.repo.MealRepositoryView;
import com.example.foodplanner.view.home.HomeView;
import com.example.foodplanner.view.meal_by_category.MealView;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

//public class MealPresenter {
//}

//import com.example.foodplanner.api.CategoryCallback;
//import com.example.foodplanner.model.dto.CategoriesItem;
//import com.example.foodplanner.model.repo.MealRepositoryView;
//import com.example.foodplanner.view.home.HomeView;
//
//import java.util.List;
/////////////////////// will edit for each class have presenter ////////////////////////////////
////// will edit MealPresenter  implements CategoryDetailsCallback , ingredicallback , counyrtcallback

public class MealPresenter implements CategoryDetailsCallback {


    private final MealView mealView;
    private final MealRepositoryView mealRepositoryView;
    public MealPresenter(MealView mealView, MealRepositoryView repositoryView){
        this.mealView = mealView;
        this.mealRepositoryView = repositoryView;
    }


    public Single<ListsDetailsbyResponse> getmealsinCategoryDetail(String category) {
        return mealRepositoryView.CategoryDetailsNetworkCall(category);
    }

    @Override
    public void onSuccessResult(List<ListsDetailsBy> categoryDetailsList) {

    }

    @Override
    public void onFailureResult(String errorMsg) {

    }

//    @Override
//    public void onSuccessResult(List<CategoriesItem> categoriesItems) {
//        mealView.showMealSuccessMessage(meali);
//    }
//
//    @Override
//    public void onCategoryFailure(String error) {
//
//    }
}
