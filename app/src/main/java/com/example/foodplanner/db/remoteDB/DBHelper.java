package com.example.foodplanner.db.remoteDB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.db.MyDatabase;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.WeekPlan;
import com.example.foodplanner.model.repo.MealRepositoryView;
import com.example.foodplanner.presenter.MealDetailsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DBHelper {
   // MealRepositoryView mealRepositoryView;

    private DatabaseReference databaseReference;

    public DBHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }



    public void addMealToFavorite(String email, MealItem mealsItem) {
        databaseReference.child("favMeal").child(email).child(mealsItem.getIdMeal()).setValue(mealsItem);
    }

    public void deleteMealFromFavorite(String email, MealItem mealsItem) {
        databaseReference.child("favMeal").child(email).child(mealsItem.getIdMeal()).removeValue();
    }

//    public void addToFav(MealItem mealItem) {
//        //  this.mealsDetail = mealsDetail;
//        mealRepositoryView.insertMealToFavl(mealItem)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        () -> mealDetailView.onInsertFavSuccess(),
//                        error -> mealDetailView.onInsertFavError(error.getLocalizedMessage()));
//
//    }
    public void getAllFavorite( Context context) {
        String encodeEmail = encodeEmailForFirebase(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        databaseReference.child("favMeal")
                .child(encodeEmail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<MealItem> favItemList = new ArrayList<>();

                        for(DataSnapshot data: snapshot.getChildren()){
                            favItemList.add(data.getValue(MealItem.class));
                        }
                        new Thread(()->{  for (MealItem item : favItemList){
                            ;

                            MyDatabase.getInstance(context).getmealDao()
                                   .insertMealToFavorite(item)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                               () ->{} ,
                                                    throwable -> {
                                                        // Handle the error
                                                        Log.e("Database Insert Error", "Failed to insert meal: " + item.getStrMeal(), throwable);
                                                    }

                                            );

                        }}).start();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void addMealToPlan(String email, WeekPlan weekPlan) {
        databaseReference.child("planMeal").child(email).child(weekPlan.getIdMeal()).setValue(weekPlan);
    }


    public void deleteMealPlan(String email, WeekPlan weekPlan) {
        databaseReference.child("planMeal").child(email).child(weekPlan.getIdMeal()).removeValue();
    }


    public void getAllWeelPlan( Context context) {
        String encodeEmail = encodeEmailForFirebase(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        databaseReference.child("planMeal")
                .child(encodeEmail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<WeekPlan>weekPlanItemList = new ArrayList<>();

                        for(DataSnapshot data: snapshot.getChildren()){
                            weekPlanItemList.add(data.getValue(WeekPlan.class));
                        }
                        new Thread(()->{  for (WeekPlan weekPlan  : weekPlanItemList){
                            MyDatabase.getInstance(context).getmealDao()
                                    .insertWeekPlanMealToCalender(weekPlan).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            () ->{} ,
                                            throwable -> {
                                                // Handle the error
                                                Log.e("Database Insert Error", "Failed to insert meal: " + weekPlan.getStrMeal(), throwable);
                                            }

                                    );

                            ;
                        }}).start();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



    private String encodeEmailForFirebase(String email) {
        return email.replace(".", ",");
    }
}
