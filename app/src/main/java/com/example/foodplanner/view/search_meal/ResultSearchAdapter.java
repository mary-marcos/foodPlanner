package com.example.foodplanner.view.search_meal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.ListsDetailsBy;
import com.example.foodplanner.model.dto.MealsDetail;
import com.example.foodplanner.view.GlideImage;
import com.example.foodplanner.view.meal_by_category.MealAdapter;
import com.example.foodplanner.view.meal_by_category.OnmealClickListener;

import java.util.List;

public class ResultSearchAdapter extends RecyclerView.Adapter<ResultSearchAdapter.ViewHolder> {


    private List<MealsDetail> mealsItems;
    public OnSearchedMealClickclist onmealClickListener;
    Context context;


    public ResultSearchAdapter(Context context,List<MealsDetail> mealsItems,OnSearchedMealClickclist onmealClickListener) {
        this.mealsItems = mealsItems;
        this.context=context;
        this.onmealClickListener=onmealClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_meal_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealsDetail mealsItem = mealsItems.get(position);
        holder.mealNameTv.setText(mealsItem.getStrMeal());
        GlideImage.
                downloadImageToImageView(holder.mealImg.getContext(), mealsItem.getStrMealThumb(), holder.mealImg);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onmealClickListener != null) {
                           // onmealClickListener.onmealClick(mealsItem);
                        }
                    }
                }



        );
    }





    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public void changeData(List<MealsDetail> mealsItems) {
        this.mealsItems = mealsItems;
        notifyDataSetChanged();
    }

    public void clearData() {
        mealsItems.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImg;
        TextView mealNameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View view) {
            mealImg = view.findViewById(R.id.meal_img);
            mealNameTv = view.findViewById(R.id.meal_title_tv);
        }
    }
}
