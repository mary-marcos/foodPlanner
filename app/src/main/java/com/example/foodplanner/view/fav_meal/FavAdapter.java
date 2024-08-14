package com.example.foodplanner.view.fav_meal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.MealItem;
import com.example.foodplanner.view.GlideImage;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    List<MealItem> mealsItems;
    OnDeleteTextClickListener onDeleteTextClickListener;
    OnItemClickListener onItemClickListener;


    public FavAdapter(List<MealItem> mealsItems) {
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
        MealItem mealItem=mealsItems.get(position);
        holder.mealName.setText(mealItem.getStrMeal());
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

    public void changeData(List<MealItem> mealsItems){
        this.mealsItems=mealsItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImg;
        TextView mealName,deleteTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);

        }

        private void initViews(View itemView) {
            mealImg=itemView.findViewById(R.id.meal_img);
            mealName=itemView.findViewById(R.id.meal_title_tv);
            deleteTv=itemView.findViewById(R.id.delete_tv);
        }
    }

    public interface OnDeleteTextClickListener{
        void onDeleteClick(MealItem mealsItem);
    }
    public interface OnItemClickListener{
        void onItemClick(MealItem mealsItem);
    }
}
