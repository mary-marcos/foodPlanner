package com.example.foodplanner.presenter;

import com.example.foodplanner.api.MealDetailsCallback;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.MealsDetail;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.repo.MealRepositoryView;
import com.example.foodplanner.view.meal_details.MealDetailView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter implements  MealDetailsCallback {
    private MealDetailView mealDetailView;
    private MealsDetail mealsDetail;
    private MealRepositoryView mealRepositoryView;

    public MealDetailsPresenter(MealDetailView mealDetailView,MealRepositoryView mealRepositoryView){
        this.mealDetailView = mealDetailView;
        this.mealRepositoryView = mealRepositoryView;
    }

    public Single<MealsDetailResponse> getMealDetail(String name) {
        return mealRepositoryView.getMealByIdNetworkCall(name);
    }

    public void addToFav(MealItem mealItem) {
      //  this.mealsDetail = mealsDetail;
        mealRepositoryView.insertMealToFavl(mealItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mealDetailView.onInsertFavSuccess(),
                        error -> mealDetailView.onInsertFavError(error.getLocalizedMessage()));

    }
    @Override
    public void onSuccessResult(List<MealsDetail> mealsDetailList) {
    mealDetailView.showItemDetailData(mealsDetailList);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        mealDetailView.showItemDetailErrorMsg(errorMsg);
    }



}
