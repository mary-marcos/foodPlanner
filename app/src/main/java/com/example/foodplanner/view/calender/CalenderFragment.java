package com.example.foodplanner.view.calender;

import android.icu.text.SimpleDateFormat;
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
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.api.MealRemoteDataSourceImpl;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.WeekPlan;
import com.example.foodplanner.model.repo.MealRepositoryImpl;
import com.example.foodplanner.presenter.CalenderPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalenderFragment extends Fragment implements CalendersView {
    CalenderPresenter calendMealPresenter;
    WeekPlanMealAdapter calenderAdapter;
    RecyclerView recyclerView;
   // private CalendarView calendarView;
   private Calendar calendar;
    private CalendarView calendarView;
    public CalenderFragment() {
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
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendMealPresenter=new CalenderPresenter
                (
                        MealRepositoryImpl.getInstance
                                (MealRemoteDataSourceImpl.getInstance(),
                                        MealLocalDataSourceImp.getInstance(this.getContext())
                                ),
                        this
                );


        initViews(view);
        calendMealPresenter.getAllplannedMeals();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
     @Override
             public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               calendar.set(year, month, dayOfMonth);
     String selectedDate = getStringFromDate(calendar.getTime());
         calendMealPresenter.getPlannedMealsByData(selectedDate);

          Log.i("TAG", "onSelectedDayChange: "+selectedDate);
                    }
                                             }
        );
    }
    private String getStringFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return format.format(date);
    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        calendarView.setDate(calendar.getTimeInMillis());
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        calenderAdapter=new WeekPlanMealAdapter(new ArrayList<>());
        recyclerView=view.findViewById(R.id.weekPlanRecyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(
                this.requireContext(), RecyclerView.VERTICAL,false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
       calenderAdapter.onDeleteTextClickListener= this::deleteMealFromFav;
       calenderAdapter.onItemClickListener=this::navtoDetailsFrag;
        recyclerView.setAdapter(calenderAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void deleteMealFromFav(WeekPlan mealsItem) {

        calendMealPresenter.deleteplanesMeals(mealsItem) ;
    }



    private void navtoDetailsFrag(WeekPlan mealsItem) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("categorydetails", (Serializable) mealsItem);
        Toast.makeText(requireActivity(), "fplanedmeal" + mealsItem.getIdMeal(), Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_calenderFragment_to_mealDetailsFragment, bundle);
    }



    @Override
    public void onSuccessDeleteFromPlan() {

    }

    @Override
    public void onFailDeleteFromPlan(String error) {

    }

    @Override
    public void onGetAllPlanMealsError(String errorMessage) {
        Toast.makeText(requireContext(),"cant load data"+errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetAllplanBydateMeals(List<WeekPlan> plannedMeals) {
        calenderAdapter.changeData(plannedMeals);
    }
}