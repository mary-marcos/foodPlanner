package com.example.foodplanner.view.meal_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.IngredientMeasure;

import java.util.List;



    public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
        private Context context;
        private List<IngredientMeasure> mealItemDetailList;

        public IngredientAdapter(Context context,List<IngredientMeasure> mealItemDetailList) {
            this.context = context;
            this.mealItemDetailList = mealItemDetailList;

        }

        public void setMealItemDetailList(List<IngredientMeasure> mealItemDetailList) {
            this.mealItemDetailList = mealItemDetailList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.ingredient_item, parent, false);
            context = parent.getContext();
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            IngredientMeasure mealsItem = mealItemDetailList.get(position);
            holder.tvIngridentName.setText(mealsItem.getIngredientName());
            holder.tvIngridentMeasure.setText(mealsItem.getIngredientMeasure());
            Glide.with(context).load("https://www.themealdb.com/images/ingredients/" + mealsItem.getIngredientName()+ "-Small.png").into(holder.ingridentImage);
        }



        @Override
        public int getItemCount() {
            // if (mealItemDetailList == null) return 0;
            return mealItemDetailList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ingridentImage;
            private TextView tvIngridentName;
            private TextView tvIngridentMeasure;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ingridentImage = itemView.findViewById(R.id.imageViewIngredientImageBGItem);
                tvIngridentName = itemView.findViewById(R.id.tvIngredientNameItem);
                tvIngridentMeasure = itemView.findViewById(R.id.textViewIngredientMeasureItem);
            }
        }
    }