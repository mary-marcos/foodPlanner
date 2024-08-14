package com.example.foodplanner.view.search_meal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.model.dto.AreaItem;
import com.example.foodplanner.model.dto.AreaItemResponse;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.ListsDetailsbyResponse;
import com.example.foodplanner.model.dto.MealsDetail;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.presenter.HomePresenter;
import com.example.foodplanner.presenter.SearchPresenter;
import com.example.foodplanner.view.home.CategoryAdapter;
import com.example.foodplanner.view.home.OnCategoryClickListener;
import com.example.foodplanner.view.meal_details.IngredientAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchMealFragment extends Fragment implements SearchView,OnSearchedMealClickclist, OnCategoryClickListener,OnAreaClickListen {
    private SearchPresenter searchPresenter;
    private RecyclerView categoryRecyclerView;
    private RecyclerView areaRecyclerView;
    private RecyclerView mealrecyclerView;
    private RecyclerView ingredRecyclerView;
    private ChipGroup chipgroup;
    private LinearLayoutManager linearLayoutManager;
    CategoryAdapterSrch categoryAdapterSrch;
    AreaAdaptersrch areaAdaptersrch;
    ResultSearchAdapter resultSearchAdapter;
    ingredientAdapterSrch ingredientAdapterSrchs;
    EditText searchEditText;
    private List<MealsDetail> meals = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Any additional setup can be done here if needed
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
searchEditText=view.findViewById(R.id.search_tv);
mealrecyclerView=view.findViewById(R.id.mealnameRecyclerView_search);

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView_search);
        areaRecyclerView= view.findViewById(R.id.areaRecyclerView_search);
        //ingredRecyclerView= view.findViewById(R.id.ingredRecyclerView__search);
        chipgroup = view.findViewById(R.id.chipgroup);
        searchPresenter=new SearchPresenter(this, MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImp.getInstance(this.getContext()) ));
        initMealsViews();
        Disposable textObservableDisposable = RxTextView.textChanges(searchEditText)
                .debounce(1, TimeUnit.SECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        query->{
                            getsearchedmeal(query);
                        },

                        throwable -> {
                            Toast.makeText(requireContext(), "Error while filtering: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            throwable.printStackTrace();
                        }
                        );



        setupGroupFilter();
    }
    void initallcategoryViews(){

        categoryAdapterSrch  = new CategoryAdapterSrch(this.getContext(),new ArrayList<>(), this) ;
        categoryRecyclerView.setAdapter(categoryAdapterSrch);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



    }

    void initallareaViews(){

        areaAdaptersrch  = new AreaAdaptersrch(this.getContext(),new ArrayList<>(), this) ;
        areaRecyclerView.setAdapter(areaAdaptersrch);
        areaRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    void initMealsViews(){

        resultSearchAdapter  = new ResultSearchAdapter(this.getContext(),new ArrayList<>(), this) ;
        mealrecyclerView.setAdapter(resultSearchAdapter);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);

        mealrecyclerView.setLayoutManager(horizontalLayoutManager);

    }

    void initgredieViews(){

        categoryAdapterSrch  = new CategoryAdapterSrch(this.getContext(),new ArrayList<>(), this) ;
        categoryRecyclerView.setAdapter(categoryAdapterSrch);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }
    private void setupGroupFilter() {
        for (int i = 0; i < chipgroup.getChildCount(); i++) {
            Chip chip = (Chip) chipgroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        String chipText = chip.getText().toString();
                        Toast.makeText(SearchMealFragment.this.getContext(), chipText, Toast.LENGTH_SHORT).show();
                        switch (chipText) {
                            case "category":

                                initallcategoryViews();
                                searchPresenter.getCategory();
                                break;

                            case "country":
                                initallareaViews();
                                searchPresenter.getArea();
                              // getArea();
                                // Handle ingredients logic here
                                break;
                        }
                    }else{

                        String chipText = chip.getText().toString();
                        Toast.makeText(SearchMealFragment.this.getContext(), chipText, Toast.LENGTH_SHORT).show();
                        switch (chipText) {
                            case "category":
                                initallcategoryViews();
                               categoryAdapterSrch.changeData(new ArrayList<>());
                               // searchPresenter.getCategory();
                                break;

                            case "country":
                               // initallareaViews();
                                areaAdaptersrch.changeData(new ArrayList<>());
                                // getArea();
                                // Handle ingredients logic here
                                break;
                        }
                    }
                }
            });
        }
    }


    void getsearchedmeal(String text){

        Single<MealsDetailResponse> mealsDetailSingle =searchPresenter.getMealbynameDetail(text);

        compositeDisposable.add(mealsDetailSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsDetailResponse ->
                        {meals.clear();
                            meals.addAll( mealsDetailResponse.getMeals());
                            resultSearchAdapter.changeData(meals);
                          // MealsDetail target_mealsDetail = mealsDetailResponse.getMeals().get(0);

                         //   Log.i("TAG", "mealsDetailResponse.getMeals().get(0)=" + target_mealsDetail);
                           // Toast.makeText(SearchMealFragment.this.getContext(), target_mealsDetail.getStrMeal(), Toast.LENGTH_SHORT).show();
                          //  setdta(target_mealsDetail);

                        }
                        ,throwable ->
                        {Log.e("TAG", "Error fetching meal details: " + throwable.getMessage());
                            Toast.makeText(requireContext(), "Error fetching meal details", Toast.LENGTH_SHORT).show();
                        }




                ) );
    }

//   void getArea(){
//
//
//       Single<AreaItemResponse> areaList   =searchPresenter.getArea();
//
//  Log.i("TAG", "areaListList " +areaList);
//       areaList.subscribeOn(Schedulers.io())
//               .observeOn(AndroidSchedulers.mainThread())
//               .subscribe(item -> {
//                   List<AreaItem> mm=item.getAreasList();
//                  String ar= mm.get(0).getStrArea();
//                           Toast.makeText(SearchMealFragment.this.getContext(), ar, Toast.LENGTH_SHORT).show();
//                           areaAdaptersrch.changeData(item.getAreasList());
//
//                           areaRecyclerView.setAdapter(areaAdaptersrch);
//                       },
//                       throwable -> {
//                           Log.i("TAG", "showCategoryDetail: unable to show products because: " + throwable.getMessage());
//
//                           Toast.makeText(SearchMealFragment.this.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//               });
//
//
//   }
    void getIngredients(){}

    @Override
    public void showCategorySuccessMessage(List<CategoriesItem> categoriesItems) {
        categoryAdapterSrch.changeData(categoriesItems);
    }

    @Override
    public void showCategoryErrorMessage(String error) {

    }

    @Override
    public void showareaSuccessMessage(List<AreaItem> areaItems) {
     String s=   areaItems.get(1).getStrArea();
        Toast.makeText(SearchMealFragment.this.getContext(), s, Toast.LENGTH_SHORT).show();
       areaAdaptersrch.changeData(areaItems);
    }

    @Override
    public void showareaErrorMessage(String error) {

    }

    @Override
    public void onCategoryClick(CategoriesItem category) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", (Serializable) category);
        Toast.makeText(requireActivity(), "category" + category, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_searchMealFragment_to_mealFragment, bundle);
    }

    @Override
    public void onareaClick(AreaItem area) {

    }
}
