package com.example.foodplanner.view.meal_by_category;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.ListsDetailsBy;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.presenter.MealPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealFragment extends Fragment implements MealView ,OnmealClickListener{




    private Context context;
    private MealAdapter mealAdapter;
    private RecyclerView recyclerView;
    Single<ListsDetailsbyResponse> categoryDetailsList;
    private MealPresenter mealPresenter;
    private LinearLayoutManager linearLayoutManager;
    CardView randomCardView;
    CategoriesItem category;


    public MealFragment() {
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
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.meal_recycler_view);
        mealAdapter = new MealAdapter(requireActivity(),new ArrayList<>(),this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mealPresenter = new MealPresenter(this, MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImp.getInstance(this.getContext())));/////add local later

        Serializable retreveItem = getArguments().getSerializable("category");

        if (retreveItem instanceof CategoriesItem) {
             category = (CategoriesItem) retreveItem;
            // Handle CategoriesItem type
        }
//        else if (retreveItem instanceof IngredientItem) {
//            IngredientItem ingredient = (IngredientItem) retreveItem;
//            // Handle IngredientItem type
//        }

      // category = (CategoriesItem) getArguments().getSerializable("category");
        Toast.makeText(requireActivity(), "strmeal"+category.getStrCategory(), Toast.LENGTH_SHORT).show();


        if (category != null) {
            categoryDetailsList =
                    mealPresenter.getmealsinCategoryDetail(category.getStrCategory());
            Log.i("TAG", "s()categoryDetailsList " +categoryDetailsList);
            categoryDetailsList.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                                mealAdapter.changeData(item.getListDetails());

                                recyclerView.setAdapter(mealAdapter);
                            },
                            throwable -> {
                                Log.i("TAG", "showCategoryDetail: unable to show products because: " + throwable.getMessage());
                            });
        }


    }

    @Override
    public void showMealSuccessMessage(List<ListsDetailsBy> mealItems) {

    }

    @Override
    public void showCategoryErrorMessage(String error) {

    }

    @Override
    public void onmealClick(ListsDetailsBy meal)

    { Bundle bundle = new Bundle();
        bundle.putSerializable("categorydetails", (Serializable) meal);
        Toast.makeText(requireActivity(), "categorydetails" + meal, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_mealFragment_to_mealDetailsFragment, bundle);}

}