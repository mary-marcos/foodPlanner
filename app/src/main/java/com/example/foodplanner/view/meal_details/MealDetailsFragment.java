package com.example.foodplanner.view.meal_details;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.model.dto.ListIngredientMeasure;
import com.example.foodplanner.model.dto.ListsDetailsBy;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.MealsDetail;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.model.repo.MealRepositoryView;
import com.example.foodplanner.presenter.MealDetailsPresenter;
import com.example.foodplanner.presenter.MealPresenter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetailsFragment extends Fragment implements  MealDetailView{


    private ImageView itemImage;
    private TextView tvItemName;
    private TextView tvItemCountry;
    private TextView tvItemCategory;
    private TextView tvProcedures;
    private ImageView addToFavImage;
    private MealDetailsPresenter mealdetailsPresenter ;
    private ListsDetailsBy retreved_DetailsBy;
    private ListsDetailsBy listAreaDetails;
    private ListsDetailsBy listingredientDetails;
    private MealItem searchByName;
    private MealItem favMeal;
   // private WeekPlan weekPlanMeal;
    private Context context;
    private IngredientAdapter ingrAdapter;
    private RecyclerView recyclerView;
    private Single<MealsDetailResponse> mealsDetailList;
    private ImageView itemingredientImage;
    private TextView itemingredientName;
    private LinearLayoutManager linearLayoutManager;
    private CardView ingridentCardView;
    private YouTubePlayerView youTubePlayerView;
    private MealRepositoryView mealRepositoryView;
    MealsDetail target_mealsDetail;


    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvItemName = view.findViewById(R.id.tviewMealNameItem);
        tvItemCountry = view.findViewById(R.id.tvMealCountryDetails);
        tvItemCategory = view.findViewById(R.id.tvMealCategoryDetails);
        tvProcedures = view.findViewById(R.id.tvProcedure);
        itemImage = view.findViewById(R.id.mealDetailImage);
        addToFavImage = view.findViewById(R.id.FavItemImage);
        youTubePlayerView = view.findViewById(R.id.ytPlayerVideo);
        recyclerView = view.findViewById(R.id.rviewIngredients);

        linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        ingrAdapter = new IngredientAdapter(this.getContext(),new ArrayList<>());
        recyclerView.setAdapter(ingrAdapter);

        mealdetailsPresenter = new MealDetailsPresenter(this,
                MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(),MealLocalDataSourceImp.getInstance(this.getContext())
                     //   , MealLocalDataSourceImpl.getInstance(requireActivity())
                ));


        //  retreved_DetailsBy= (ListsDetailsBy) getArguments().getSerializable("categorydetails");


       Serializable retreveItem = getArguments().getSerializable("categorydetails");


        if (retreveItem instanceof ListsDetailsBy) {
            retreved_DetailsBy = (ListsDetailsBy)  retreveItem;
            // Handle CategoriesItem type
        }
//        else if (retreveItem instanceof IngredientItem) {
//            IngredientItem ingredient = (IngredientItem) retreveItem;
//            // Handle IngredientItem type
//        }

//    pass the id of meal to get meal details  it retreve  a list have one item
        Single<MealsDetailResponse> mealsDetailSingle =mealdetailsPresenter.getMealDetail(retreved_DetailsBy.getIdMeal());
        mealsDetailSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsDetailResponse ->
                        {
                     target_mealsDetail = mealsDetailResponse.getMeals().get(0);

                            Log.i("TAG", "mealsDetailResponse.getMeals().get(0)=" + target_mealsDetail);

                             setdta(target_mealsDetail);

                          }
                        ,throwable ->
                        {Log.e("TAG", "Error fetching meal details: " + throwable.getMessage());
                            Toast.makeText(requireContext(), "Error fetching meal details", Toast.LENGTH_SHORT).show();
                        }




                ) ;



//        addToFavImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MealDetailsPresenter.addToFav(target_meals);
//
//            }
//        });
    }

    private void loadVideo(@NonNull YouTubePlayer youTubePlayer, String youtubeVideoId) {
        youTubePlayer.loadVideo(getVideoId(youtubeVideoId), 0);
    }

    private String getVideoId(String videoUrl) {
        String videoId = null;
        String[] urlParts = videoUrl.split("v=");
        if (urlParts.length > 1) {
            videoId = urlParts[1];
        }
        return videoId;
    }

    void setdta(MealsDetail target_mealsDetail)
    {

        tvItemName.setText(target_mealsDetail.getStrMeal());
        tvItemCountry.setText(target_mealsDetail.getStrArea());
        tvItemCategory.setText(target_mealsDetail.getStrCategory());
        tvProcedures.setText(target_mealsDetail.getStrInstructions());
        Glide.with(requireActivity()).load(target_mealsDetail.getStrMealThumb()).into(itemImage);
        ingrAdapter.setMealItemDetailList(ListIngredientMeasure.getIngridentsListArray(target_mealsDetail));

   //DB


        addToFavImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mealdetailsPresenter.addToFav(setDatatofav(target_mealsDetail));
            }
        });





        String youtubeURL=target_mealsDetail.getStrYoutube();
        if(youtubeURL != null && !youtubeURL.isEmpty()){
            youTubePlayerView.setVisibility(View.VISIBLE);
            youTubePlayerView.addYouTubePlayerListener(
                    new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            super.onReady(youTubePlayer);
                            loadVideo(youTubePlayer, youtubeURL);
                        }
                    });

        }else{ youTubePlayerView.setVisibility(View.GONE);}
    }


   MealItem setDatatofav(MealsDetail currentmeal){
        MealItem mealtofav=new MealItem();
       mealtofav.setFavorite(true);
       mealtofav.setIdMeal(currentmeal.getIdMeal());
       mealtofav.setStrMeal(currentmeal.getStrMeal());
       mealtofav.setStrMealThumb(currentmeal.getStrMealThumb());

       return  mealtofav;
   }

    @Override
    public void onInsertFavSuccess() {
//        fillHeartImg.setVisibility(View.VISIBLE);
//        emptyHeartImg.setVisibility(View.INVISIBLE);
        if (isAdded()) {
            //AlertMessage.showToastMessage("Meal added to favorite", requireContext());
            Toast.makeText(requireActivity(), "Meal added to favorite", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onInsertFavError(String error) {

        Toast.makeText(requireActivity(), " cant add Meal to favorite "+error, Toast.LENGTH_SHORT).show();

   System.out.println(" cant add Meal to favorite "+error);
    }

    @Override
    public void showItemDetailData(List<MealsDetail> mealsItem) {


    }

    @Override
    public void showItemDetailErrorMsg(String error) {




    }
}