package com.example.foodplanner.db.remoteDB;

import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.WeekPlan;

import io.reactivex.rxjava3.core.Completable;

public interface RemoteDatabaseListener {
    Completable insertToFavorite(MealItem mealsItem);
    Completable insertToWeekPlan(WeekPlan weekPlan);
    void deleteFromWeekPlane(WeekPlan weekPlan);
    void deleteFromFavorite(MealItem mealsItem);
}
