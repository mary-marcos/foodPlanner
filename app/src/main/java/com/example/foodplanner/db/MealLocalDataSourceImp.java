package com.example.foodplanner.db;


import android.content.Context;
import android.util.Log;

import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.WeekPlan;


import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


   public  class MealLocalDataSourceImp  {
        private MealDao mealDao;
        private Flowable<List<MealItem>> favProducts;
        private static MealLocalDataSourceImp productRepoImp =null;

        public MealLocalDataSourceImp(Context ctx) {
            MyDatabase database= MyDatabase.getInstance(ctx);
            mealDao =database.getmealDao();
            favProducts = mealDao.getAllFavMeals();

        }
        public static MealLocalDataSourceImp getInstance(Context context){
            if(productRepoImp ==null) {
                productRepoImp = new MealLocalDataSourceImp(context);
            }
            return productRepoImp;
        }

        public Flowable<List<MealItem>> getFavMeals() {
            return mealDao.getAllFavMeals();
        }






        public Completable deleteFavoriteProduct(MealItem mealsItem){
            return mealDao.deleteMealFromFavorite(mealsItem).
                    subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }



        public Completable insertProductToFavorite(MealItem mealItem){
            mealItem.setFavorite(true);
            return mealDao.insertMealToFavorite(mealItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }



//////////////


       public Flowable<List<WeekPlan>> getallweekplan()
       {
           return mealDao.getWeekPlanMeals();
       }




       public Flowable<List<WeekPlan>> getallmealbydate(String date)
       {
           return mealDao.getMealsForDate(date);
       }

       public Completable deleteweakplanmeal(WeekPlan weekPlan){
           return mealDao.deleteWeekPlanMealFromCalender(weekPlan)
                  .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread());
       }



       public Completable insertProductToweekplan(WeekPlan weekPlan){
           //mealItem.setFavorite(true);
           return mealDao.insertWeekPlanMealToCalender(weekPlan)

                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread());
       }

       ////////////

public void deleteAllTheCalenderList() {
    new Thread(() -> {
        mealDao.deleteAllTheCalenderList();
        Log.i("TAG", "Deleting all meals from the calendar list");
    }).start();
}


       public void deleteAllTheFavoriteList() {
           new Thread(() -> {
               mealDao.deleteAllTheFavoriteList();
               Log.i("TAG", "Deleting all meals from the favorite list");
           }).start();
       }

    }
