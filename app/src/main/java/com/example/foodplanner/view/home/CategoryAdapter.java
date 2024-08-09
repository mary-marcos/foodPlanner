package com.example.foodplanner.view.home;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplanner.view.GlideImage;
import com.example.foodplanner.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.model.dto.CategoriesItem;

import com.example.foodplanner.model.dto.CategoryResponse;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoriesItem> categoriesItems;
    public OnCategoryClickListener onCategoryItemClickListener;
    Context context;

    public CategoryAdapter(Context context ,List<CategoriesItem> categoriesItems,OnCategoryClickListener onCategoryItemClickListener) {
        this.categoriesItems = categoriesItems;
        this.onCategoryItemClickListener=onCategoryItemClickListener;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesItem categoriesItem=categoriesItems.get(position);
        holder.categoryName.setText(categoriesItem.getStrCategory());
        GlideImage.downloadImageToImageView(holder.categoryImg.getContext(),categoriesItem.getStrCategoryThumb(),holder.categoryImg);

        holder.itemView.setOnClickListener(view->{
            onCategoryItemClickListener.onCategoryClick(categoriesItem);
        });
    }

    @Override
    public int getItemCount() {
        return categoriesItems.size();
    }


    public void changeData(List<CategoriesItem> categoriesItems){
        this.categoriesItems=categoriesItems;
        notifyDataSetChanged();

    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView categoryImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.category_name);
            categoryImg=itemView.findViewById(R.id.category_img);

        }

    }
}
