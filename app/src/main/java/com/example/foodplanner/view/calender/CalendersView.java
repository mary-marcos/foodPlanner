package com.example.foodplanner.view.calender;

import com.example.foodplanner.model.dto.WeekPlan;

import java.util.List;

public interface CalendersView {
    public void onSuccessDeleteFromPlan();
    public void onFailDeleteFromPlan(String error);
    void onGetAllPlanMealsError(String errorMessage);
    void onGetAllplanBydateMeals(List<WeekPlan> plannedMeals);
}
