package com.example.foodplanner.view.home;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.foodplanner.view.GlideImage;

import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.MealsDetail;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.presenter.HomePresenter;
import com.example.foodplanner.view.activity.HomeActivity;
import com.example.foodplanner.view.activity.MainActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

//  // // will edit to be categ_view////////////
public class HomeFragment extends Fragment implements HomeView,OnCategoryClickListener{

Button logout;
    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;
    MaterialCardView cardView;
    CategoryAdapter categoryAdapter;
      RecyclerView categoryRecyclerView, ingredRecyclerView,countryRecyclerView;
    MealsDetail target_mealsDetail;

    private  LinearLayoutManager linearLayoutManager;
   ProgressBar ingredientProgressBar;
   // CountryAdapter countryAdapter;




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
//
        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                gomainActivity();
            }
        });
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
homePresenter=new HomePresenter(this,MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImp.getInstance(this.getContext()) ));

       mealImg=view.findViewById(R.id.meal_img);
       mealTitle=view.findViewById(R.id.meal_title_tv);


        linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        initallViews();
        homePresenter.getCategory();
         setRandom();


    }


  void  onrandomclicked(MealsDetail target){
      mealImg.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Bundle bundle = new Bundle();
                                         bundle.putSerializable("categorydetails", (Serializable) target);
                                         Toast.makeText(requireActivity(), "categorydetails" + target.getStrMeal(), Toast.LENGTH_SHORT).show();
                                         Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_mealDetailsFragment, bundle);}



                                 }
      );
  }




//    private void navigateToMealDetailsFragment() {
//        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
//                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealItem);
//        Navigation.findNavController(requireView()).navigate(action);
//    }
void setRandom(){
    Single<MealsDetailResponse> mealsDetailSingle =  homePresenter.getRandomMeal();
    mealsDetailSingle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    mealsDetailResponse ->
                    {
                       target_mealsDetail = mealsDetailResponse.getMeals().get(0);

                        Log.i("TAG", "mealsDetailResponse.getMeals().get(0)=" + target_mealsDetail);

                        setRandomdata(target_mealsDetail);
                        onrandomclicked(target_mealsDetail);

                    }
                    ,throwable ->{}
            );
}


   void setRandomdata(MealsDetail meal){
       GlideImage.downloadImageToImageView(mealImg.getContext(), meal.getStrMealThumb(), mealImg);
       mealTitle.setText(meal.getStrMeal());
   }
   void initallViews(){

       categoryRecyclerView.setLayoutManager(linearLayoutManager);
       categoryAdapter = new CategoryAdapter(this.getContext(),new ArrayList<>(), this);
       categoryRecyclerView.setAdapter(categoryAdapter);
       categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
   }
      private void gomainActivity() {
          Intent intent = new Intent(this.getContext(), MainActivity.class);
                                                      startActivity(intent);
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



