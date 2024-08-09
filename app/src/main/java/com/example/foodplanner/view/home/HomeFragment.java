package com.example.foodplanner.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.presenter.HomePresenter;
import com.google.android.material.card.MaterialCardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

                                              //  // // will edit to be categ_view////////////
public class HomeFragment extends Fragment implements HomeView,OnCategoryClickListener{


    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;
    MaterialCardView cardView;
    CategoryAdapter categoryAdapter;
      RecyclerView categoryRecyclerView, ingredRecyclerView,countryRecyclerView;
    private  LinearLayoutManager linearLayoutManager;
   ProgressBar ingredientProgressBar;
   // CountryAdapter countryAdapter;

  //  MealsItem mealItem;
  //  OnMealByCountryClick onMealByCountryClick;
   // OnMealByIngredientClick onMealByIngredientClick;
   // OnMealByCategoryClick onMealByCategoryClick;
  //  com.example.foodplanner.view.home.IngredientAdapter ingredientAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        intiViews(view);
//        homePresenter = new HomePresenter(this, new MealRepositoryImpl());
//        homePresenter.getRandomMeal();
//        homePresenter.getCategories();
//        homePresenter.getIngredients();
//        homePresenter.getCountries();
//        Log.e("TAG", "onViewCreated: ");
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
homePresenter=new HomePresenter(this,MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance()));



        linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        initallViews();
        homePresenter.getCategory();
    }

   void initallViews(){
       categoryRecyclerView.setLayoutManager(linearLayoutManager);
       categoryAdapter = new CategoryAdapter(this.getContext(),new ArrayList<>(), this);
       categoryRecyclerView.setAdapter(categoryAdapter);
       categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
   }

    @Override
    public void showCategorySuccessMessage(List<CategoriesItem> categoriesItems) {

        categoryAdapter.changeData(categoriesItems);

    }

    @Override
    public void showCategoryErrorMessage(String error) {

    }



     @Override
             public void onCategoryClick(CategoriesItem category) {
               Bundle bundle = new Bundle();
              bundle.putSerializable("category", (Serializable) category);
                Toast.makeText(requireActivity(), "category" + category, Toast.LENGTH_SHORT).show();
         Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_mealFragment, bundle);
      }











           }



