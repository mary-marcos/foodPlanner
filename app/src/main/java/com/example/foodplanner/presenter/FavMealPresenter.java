package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.view.fav_meal.FavoriteView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavMealPresenter {
    MealRepositoryImpl mealRepo;
    FavoriteView favoriteView;

    public FavMealPresenter(MealRepositoryImpl mealRepo, FavoriteView favoriteView) {
        this.mealRepo = mealRepo;
        this.favoriteView = favoriteView;
    }


    public void getMeals() {
        mealRepo.getAllFavMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favMeals->favoriteView.onGetAllFavoriteFireStoreMeals((List<MealItem>)  favMeals),
                        error-> favoriteView.onGetAllFavoriteMealsError(error.getLocalizedMessage()));

    }


    public void deleteFavMeals(MealItem mealItem) {
        mealRepo.deleteFromFav(mealItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> favoriteView.onSuccessDeleteFromFav(),
                        error -> favoriteView.onFailDeleteFromFav(error.getLocalizedMessage())
                );
    }
}
