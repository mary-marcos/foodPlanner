package com.example.foodplanner.view.fav_meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.presenter.FavMealPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavoriteMealsFragment extends Fragment implements  FavoriteView{
    FavMealPresenter favMealPresenter;
    FavAdapter favAdapter;
    RecyclerView recyclerView;



    public FavoriteMealsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favMealPresenter=new FavMealPresenter
                (
                        MealRepositoryImpl.getInstance
                                (MealRemoteDataSourceImpl.getInstance(),
                                        MealLocalDataSourceImp.getInstance(this.getContext())
                                ),
                        this
                );
        favMealPresenter.getMeals();
        initViews(view);
    }


    private void initViews(View view) {
        favAdapter=new FavAdapter(new ArrayList<>());
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(
                this.requireContext(), RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favAdapter.onDeleteTextClickListener= this::deleteMealFromFav;
       favAdapter.onItemClickListener=this::navtoDetailsFrag;
        recyclerView.setAdapter(favAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void deleteMealFromFav(MealItem mealsItem) {

        favMealPresenter.deleteFavMeals(mealsItem);
    }



    private void navtoDetailsFrag(MealItem mealsItem) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("categorydetails", (Serializable) mealsItem);
        Toast.makeText(requireActivity(), "favmeal" + mealsItem.getIdMeal(), Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_favoriteMealsFragment_to_mealDetailsFragment, bundle);
}


    @Override
    public void onSuccessDeleteFromFav() {

    }

    @Override
    public void onFailDeleteFromFav(String error) {

    }

    @Override
    public void onGetAllFavoriteMealsError(String errorMessage) {
        Toast.makeText(requireContext(),"cant load data"+errorMessage,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onGetAllFavoriteFireStoreMeals(List<MealItem> favoriteMeals) {
        favAdapter.changeData(favoriteMeals);
    }
}