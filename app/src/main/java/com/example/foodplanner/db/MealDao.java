package com.example.foodplanner.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.dto.MealItem;


import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
@Dao
public interface MealDao {
    @Query("Select * from mealItemDetail WHERE isFavorite = 1")
    Flowable<List<MealItem>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealToFavorite(MealItem mealsItem);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addMealToPlan(MealItem mealsItem);
    @Delete
    Completable deleteMealFromFavorite(MealItem mealsItem);
//
//    @Query("select * from mealItemDetail where dateModified = :date AND is_planned=1")
//    Flowable<List<MealsItem>> getPlannedMealsByDate(String date);
}
