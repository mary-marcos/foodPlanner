package com.example.foodplanner.model.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "mealItemDetail")
public class MealItem implements Serializable {


    @PrimaryKey
    @NonNull
    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    private String  strMealThumb;

    private boolean isFavorite;







    //////////////////////////////



    public String getIdMeal(){
        return idMeal;
    }
    public String getStrMealThumb(){ return strMealThumb;}

    public String getStrMeal(){
        return strMeal;
    }
    public boolean isFavorite() {
        return isFavorite;
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

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }



}