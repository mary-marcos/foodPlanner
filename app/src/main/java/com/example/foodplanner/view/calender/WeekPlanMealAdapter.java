package com.example.foodplanner.view.calender;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.model.dto.WeekPlan;
import com.example.foodplanner.view.GlideImage;

import java.util.List;








public class WeekPlanMealAdapter extends RecyclerView.Adapter<WeekPlanMealAdapter.ViewHolder> {
    List<WeekPlan> mealsItems;
    OnDeleteTextClickListener onDeleteTextClickListener;
   OnItemClickListener onItemClickListener;


    public WeekPlanMealAdapter(List<WeekPlan> mealsItems) {
        this.mealsItems = mealsItems;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.fav_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeekPlan mealItem=mealsItems.get(position);
        holder.mealName.setText(mealItem.getStrMeal());
        holder.datetv.setText(mealItem.getDate());
        GlideImage.downloadImageToImageView(holder.mealImg.getContext(),mealItem.getStrMealThumb(),holder.mealImg);
        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteTextClickListener.onDeleteClick(mealItem);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(mealItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public void changeData(List<WeekPlan> mealsItems){
        this.mealsItems=mealsItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImg;
        TextView mealName,deleteTv,datetv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);

        }

        private void initViews(View itemView) {
            mealImg=itemView.findViewById(R.id.meal_img);
            datetv=itemView.findViewById(R.id.date);
            mealName=itemView.findViewById(R.id.meal_title_tv);
            deleteTv=itemView.findViewById(R.id.delete_tv);
        }
    }

    public interface OnDeleteTextClickListener{
        void onDeleteClick(WeekPlan mealsItem);
    }
    public interface OnItemClickListener{
        void onItemClick(WeekPlan mealsItem);
    }
}