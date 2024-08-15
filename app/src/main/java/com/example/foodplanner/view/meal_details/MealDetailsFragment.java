package com.example.foodplanner.view.meal_details;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.model.dto.ListIngredientMeasure;
import com.example.foodplanner.model.dto.ListsDetailsBy;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.MealsDetail;
import com.example.foodplanner.model.dto.MealsDetailResponse;
import com.example.foodplanner.model.dto.WeekPlan;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.model.repo.MealRepositoryView;
import com.example.foodplanner.presenter.MealDetailsPresenter;
import com.example.foodplanner.view.activity.HomeActivity;
import com.example.foodplanner.view.activity.MainActivity;
import com.example.foodplanner.view.auth.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private ImageView addTocalenderImage;
    private MealDetailsPresenter mealdetailsPresenter ;
    private ListsDetailsBy retreved_DetailsBy;
    private ListsDetailsBy listAreaDetails;
    private ListsDetailsBy listingredientDetails;
    private MealItem searchByName;
    private MealItem favMeal;
    private WeekPlan weekPlanMeal;

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
   HomeActivity homeActivityty;
    MealsDetail target_mealsDetail;
    String mymealId="53043";
    MealItem retrevefavmeal;
    WeekPlan retrevedPlan;
    MealsDetail retrevedRandom;
    FirebaseAuth mAuth;

    boolean isGuest=false;
HomeActivity homeActivity;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof HomeActivity) {
//            homeActivityty = (HomeActivity) context;
//        } else {
//            throw new RuntimeException(context.toString() + " must implement Home interface");
//        }
//    }

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

    private void checkUserAuthentication() {
        if (isAdded() && getContext() != null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
               isGuest=true;
            } else {
                isGuest=false;


               }

            if(isGuest==true)
            {Toast.makeText(MealDetailsFragment.this.getContext(), "isGuest", Toast.LENGTH_SHORT).show();}

        }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getActivity();
//        checkUserAuthentication();
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            isGuest =true;
//            if(isGuest==true)
//            {Toast.makeText(MealDetailsFragment.this.getContext(), "isGuest", Toast.LENGTH_SHORT).show();}
//           }

        if(LoginFragment.isguest==true)
            {Toast.makeText(MealDetailsFragment.this.getContext(), "isGuest", Toast.LENGTH_SHORT).show();
                isGuest=true;}

        super.onViewCreated(view, savedInstanceState);
        tvItemName = view.findViewById(R.id.tviewMealNameItem);
        tvItemCountry = view.findViewById(R.id.tvMealCountryDetails);
        tvItemCategory = view.findViewById(R.id.tvMealCategDetail);
        tvProcedures = view.findViewById(R.id.tvProcedure);
        itemImage = view.findViewById(R.id.mealDetailImage);
        addToFavImage = view.findViewById(R.id.FavItemImage);
        addTocalenderImage=view.findViewById(R.id.CalendarItemImage);
        youTubePlayerView = view.findViewById(R.id.ytPlayerVideo);
        recyclerView = view.findViewById(R.id.viewIngredients);

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
            mymealId=retreved_DetailsBy.getIdMeal();
            getmeal(mymealId);
        }
        else if (retreveItem instanceof MealsDetail) {
            retrevedRandom  = (MealsDetail) retreveItem;
           Toast.makeText(requireActivity(),"meal name :"+  retrevedRandom.getIdMeal(), Toast.LENGTH_SHORT).show();
         setdta(retrevedRandom);
          //  mymealId=retrevedRandom.getIdMeal();
          //  getmeal(mymealId);
        }
        else if (retreveItem instanceof MealItem) {
            retrevefavmeal  = (MealItem) retreveItem;


            mymealId=retrevefavmeal.getIdMeal();
            getmeal(mymealId);
        }
        else if (retreveItem instanceof WeekPlan) {
            retrevedPlan  = (WeekPlan) retreveItem;
       

            mymealId=retrevedPlan.getIdMeal();
            getmeal(mymealId);
        }

//    pass the id of meal to get meal details  it retreve  a list have one item




//        addToFavImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MealDetailsPresenter.addToFav(target_meals);
//
//            }
//        });
    }


   void getmeal(String id){

       Single<MealsDetailResponse> mealsDetailSingle =mealdetailsPresenter.getMealDetail(id);
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




        addToFavImage.setOnClickListener(v -> {
//                    if (LoginFragment.isguest) {
//                    //    homeActivity.showGuestModeAlert();
//                    }

                    if (LoginFragment.isguest) {

                        showalert();
                    }
                    else {
                        mealdetailsPresenter.addToFav(setDatatofav(target_mealsDetail));
                   }
                }
        );


        addTocalenderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginFragment.isguest) {

                    showalert();
                }
                else {
                setDatatoplan(target_mealsDetail);}

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




void showalert(){
    new AlertDialog.Builder(context)
            .setTitle("Sign Up Required")
            .setMessage("Please sign up to proceed. Do you want to sign up now?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("navigateToLogin", true);
                    context.startActivity(intent);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            })
            .show();
}
void  setDatatoplan(MealsDetail currentmeal){
      Calendar calendar = Calendar.getInstance();
      int year = calendar.get(Calendar.YEAR);
      int month = calendar.get(Calendar.MONTH);
      int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

      DatePickerDialog datePickerDialog = new
              DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {

          @Override
          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
              String date = getDateString(year,month,dayOfMonth);
              WeekPlan weekPlan = new WeekPlan();

              weekPlan.setDate(date);
              weekPlan.setIdMeal(currentmeal.getIdMeal());
              weekPlan.setStrMeal(currentmeal.getStrMeal());
              weekPlan.setStrMealThumb(currentmeal.getStrMealThumb());

              mealdetailsPresenter.addToWeekplan(weekPlan);

          }
      }, year, month, dayOfMonth);
      datePickerDialog.show();



  }

    public static String getDateString(int year, int month, int dayOfMonth){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return  format.format(calendar.getTime());
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
    public void onInsertWeekplanSuccess() {
        if (isAdded()) {
            //AlertMessage.showToastMessage("Meal added to favorite", requireContext());
            Toast.makeText(requireActivity(), "Meal added weekplan", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onInsertWeekplanError(String error) {
        Toast.makeText(requireActivity(), " cant add Meal toweekplan "+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showItemDetailData(List<MealsDetail> mealsItem) {


    }

    @Override
    public void showItemDetailErrorMsg(String error) {




    }
}