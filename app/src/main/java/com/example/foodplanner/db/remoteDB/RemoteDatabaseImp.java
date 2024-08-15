package com.example.foodplanner.db.remoteDB;

import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.WeekPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;

public class RemoteDatabaseImp implements RemoteDatabaseListener {

    private DBHelper dbHelper;
    private FirebaseAuth firebaseAuth;
   // FirebaseUser currentUserr = firebaseAuth.getCurrentUser();

    public RemoteDatabaseImp() {
        dbHelper = new DBHelper();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public Completable insertToFavorite(MealItem mealsItem) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String encodeEmail = encodeEmailForFirebase(email);
            dbHelper.addMealToFavorite(encodeEmail, mealsItem);
            return Completable.complete();
        } else {
            return Completable.error(new IllegalStateException("User is not authenticated"));
        }
    }

    @Override
    public Completable insertToWeekPlan(WeekPlan weekPlan) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String email = firebaseAuth.getCurrentUser().getEmail();
            String encodeEmail = encodeEmailForFirebase(email);
            dbHelper.addMealToPlan(encodeEmail, weekPlan);
            return Completable.complete();

        } else {
            return Completable.error(new IllegalStateException("User is not authenticated"));
        }
    }

    @Override
    public void deleteFromWeekPlane(WeekPlan weekPlan) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null)
        {
            String email = firebaseAuth.getCurrentUser().getEmail();
            String encodeEmail = encodeEmailForFirebase(email);
            dbHelper.deleteMealPlan(encodeEmail,weekPlan);

        }

    }

    @Override
    public void deleteFromFavorite(MealItem mealsItem) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null)
        {
            String email = firebaseAuth.getCurrentUser().getEmail();
            String encodeEmail = encodeEmailForFirebase(email);
            dbHelper.deleteMealFromFavorite(encodeEmail,mealsItem);
        }
    }

    private String encodeEmailForFirebase(String email) {
        return email.replace(".", ",");
    }
}
