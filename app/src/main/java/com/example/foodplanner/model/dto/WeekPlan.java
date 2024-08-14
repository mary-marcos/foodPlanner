package com.example.foodplanner.model.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "weekplan")
public class WeekPlan implements Serializable {


    @PrimaryKey
    @NonNull
    // @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    private String  strMealThumb;



    @SerializedName("date")
    public String date;







    //////////////////////////////



    public String getDate() {
        return date;
    }
    public String getIdMeal(){
        return idMeal;
    }
    public String getStrMealThumb(){ return strMealThumb;}

    public String getStrMeal(){
        return strMeal;
    }





    ////////////







    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }




    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }


    public void setDate(String date) {
        this.date = date;
    }


}