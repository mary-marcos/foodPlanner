package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepositoryImpl implements MealRepositoryView{

    MealRemoteDataSourceImpl mealRemoteDataSource;
    static MealRepositoryImpl mealRepository;
                     ///////////// lsa hata5od localRepo////////
  private   MealRepositoryImpl(MealRemoteDataSourceImpl mealRemoteDataSource){
        this.mealRemoteDataSource=mealRemoteDataSource;
    }
    public static MealRepositoryImpl getInstance(MealRemoteDataSourceImpl mealRemoteDataSource)
    {
        if(mealRepository == null)
            mealRepository = new MealRepositoryImpl(mealRemoteDataSource);

        return  mealRepository;
    }



    @Override
    public void CategoryNetworkCall(CategoryCallback categoryCallBack) {

        mealRemoteDataSource.CategoryNetworkCall(categoryCallBack);

    }

    public Single<ListsDetailsbyResponse> CategoryDetailsNetworkCall(String category) {
        return mealRemoteDataSource.CategoryDetailsNetworkCall(category).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }



}
