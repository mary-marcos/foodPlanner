package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.MealsDetail;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.db.MealLocalDataSourceImp;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepositoryImpl implements MealRepositoryView{

    MealRemoteDataSourceImpl mealRemoteDataSource;
MealLocalDataSourceImp mealLocalDataSourceImp;
    static MealRepositoryImpl mealRepository;
                     ///////////// lsa hata5od localRepo////////
  private   MealRepositoryImpl(MealRemoteDataSourceImpl mealRemoteDataSource,MealLocalDataSourceImp mealLocalDataSourceImp){
        this.mealRemoteDataSource=mealRemoteDataSource;
      this.mealLocalDataSourceImp=mealLocalDataSourceImp;
    }
    public static MealRepositoryImpl getInstance(MealRemoteDataSourceImpl mealRemoteDataSource,MealLocalDataSourceImp mealLocalDataSourceImp)
    {
        if(mealRepository == null)
            mealRepository = new MealRepositoryImpl(mealRemoteDataSource,mealLocalDataSourceImp);

        return  mealRepository;
    }



    @Override
    public void CategoryNetworkCall(CategoryCallback categoryCallBack) {

        mealRemoteDataSource.CategoryNetworkCall(categoryCallBack);

    }

    public Single<ListsDetailsbyResponse> CategoryDetailsNetworkCall(String category) {
        return mealRemoteDataSource.CategoryDetailsNetworkCall(category).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    public Single<MealsDetailResponse> getMealByIdNetworkCall(String name) {
        return mealRemoteDataSource.getMealDetailNetworkCall(name)
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }





///////////////
    //local


    @Override
    public Flowable<List<MealItem>> getAllFavMeals() {

        return mealLocalDataSourceImp.getFavMeals();
    }

    @Override
    public Completable insertMealToFavl(MealItem mealsItem) {
        return mealLocalDataSourceImp.insertProductToFavorite(mealsItem);
    }

    @Override
    public Completable deleteFromFav(MealItem mealsItem) {
        return mealLocalDataSourceImp.deleteFavoriteProduct(mealsItem);
    }


}
