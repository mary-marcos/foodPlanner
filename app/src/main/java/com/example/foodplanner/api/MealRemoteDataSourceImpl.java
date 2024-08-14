package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.AreaItemResponse;
import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.IngredientsItemResponse;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class MealRemoteDataSourceImpl //implements MealRemoteDataSource
{

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private WebService mealService;
    private static Retrofit retrofit = null;

    private static MealRemoteDataSourceImpl mealRemoteDataSource = null;

    private MealRemoteDataSourceImpl (){
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()  // Use the class-level retrofit variable
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(WebService.class);
    }

    public static MealRemoteDataSourceImpl getInstance() {
        if (mealRemoteDataSource == null)
        {  mealRemoteDataSource = new MealRemoteDataSourceImpl();}

        return mealRemoteDataSource;
    }


    public void CategoryNetworkCall(CategoryCallback categoryCallBack){
        Call<CategoryResponse> call = mealService.getCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    //4.calling the methods inside the interface
                    categoryCallBack.onSuccessResult(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                //4
                categoryCallBack.onCategoryFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }




    public Single<ListsDetailsbyResponse> CategoryDetailsNetworkCall(String category) {
        return mealService.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//public  Single<AreaItemResponse> AreaNetworkCall(){
//         return mealService.getAreas()
//             .subscribeOn(Schedulers.io())
//             .observeOn(AndroidSchedulers.mainThread());
//}



    public void AreasNetworkCall(AreaCallback areaMealCallback) {
        Call<AreaItemResponse> call = mealService.getAreas();
        call.enqueue(new Callback<AreaItemResponse>() {
            @Override
            public void onResponse(Call<AreaItemResponse> call, Response<AreaItemResponse> response) {
                if (response.isSuccessful()) {
                    //4.calling the methods inside the interface
                    areaMealCallback.onSuccessareaResult(response.body().getAreasList());
                    System.out.println("response.body().getIngredientList()" +response.body().getAreasList());
                }
            }

            @Override
            public void onFailure(Call<AreaItemResponse> call, Throwable t) {
                //4
                areaMealCallback.onFailureareaResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public  Single<IngredientsItemResponse> ingredientNetworkCall(){
        return mealService.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public Single<MealsDetailResponse> getMealDetailNetworkCall(String id) {
        return mealService.getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MealsDetailResponse> getMealbynameDetailNetworkCall(String name) {
        return mealService.searchByName(name)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<MealsDetailResponse> getRandomMealbyNetworkCall (){
        return mealService.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());


    }

}