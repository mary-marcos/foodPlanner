package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.WeekPlan;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.view.calender.CalendersView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CalenderPresenter {
    MealRepositoryImpl mealRepo;
    CalendersView calenderView;

    public CalenderPresenter(MealRepositoryImpl mealRepo, CalendersView calenderView) {
        this.mealRepo = mealRepo;
        this.calenderView = calenderView;
    }


    public void getAllplannedMeals() {
        mealRepo.getAllweekPlanMeals(). subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(planMeals->calenderView
                                .onGetAllplanBydateMeals((List<WeekPlan>)planMeals)
                        ,
                        error-> calenderView.onGetAllPlanMealsError(error.getLocalizedMessage()));


    }

    public void getPlannedMealsByData(String date) {
        mealRepo.getmealbydate(date). subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(planMeals->calenderView.onGetAllplanBydateMeals((List<WeekPlan>)planMeals)
                                ,
                        error-> calenderView.onGetAllPlanMealsError(error.getLocalizedMessage()));



    }



    public void deleteplanesMeals(WeekPlan mealItem) {
        mealRepo.deleteFromweekplan(mealItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> calenderView.onSuccessDeleteFromPlan(),
                        error -> calenderView.onFailDeleteFromPlan(error.getLocalizedMessage())
                );
    }
}