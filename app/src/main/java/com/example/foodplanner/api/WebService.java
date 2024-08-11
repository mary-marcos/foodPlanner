package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.dto.MealsDetailResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {
//    @GET("/api/json/v1/1/categories.php")
//    Observable<CategoryResponse> getCategories();

    @GET("categories.php")
    public Call<CategoryResponse>getCategory();

    @GET("filter.php")
    public Single<ListsDetailsbyResponse> getMealsByCategory(@Query("c") String category);

    @GET("lookup.php")
    public Single<MealsDetailResponse> getMealById(@Query("i") String id);

}
