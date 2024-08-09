package com.example.foodplanner.view.meal_by_category;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.foodplanner.view.GlideImage;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.ListsDetailsBy;
import com.example.foodplanner.view.home.CategoryAdapter;
import com.example.foodplanner.view.home.OnCategoryClickListener;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private List<ListsDetailsBy> mealsItems;
    public OnmealClickListener onmealClickListener;
    Context context;


    public MealAdapter(Context context,List<ListsDetailsBy> mealsItems,OnmealClickListener onmealClickListener) {
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

//
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListsDetailsBy mealsItem = mealsItems.get(position);
        holder.mealNameTv.setText(mealsItem.getStrMeal());
        GlideImage.
                downloadImageToImageView(holder.mealImg.getContext(), mealsItem.getStrMealThumb(), holder.mealImg);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onmealClickListener != null) {
                            onmealClickListener.onmealClick(mealsItem);
                        }
                    }
                }



        );

    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public void changeData(List<ListsDetailsBy> mealsItems) {
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