package com.example.foodplanner.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.MealsDetail;



@Database
        (entities = {MealItem.class},version = 1,exportSchema = false)


public abstract class MyDatabase extends RoomDatabase {
    public static final String DATABASE_NAME="Mdatabase";
    public static MyDatabase database=null;
    public abstract MealDao getmealDao();
    public static synchronized MyDatabase getInstance(Context context){
        if(database==null){
            database= Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class,DATABASE_NAME) .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

}
