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
import com.example.foodplanner.model.dto.AreaItem;
import com.example.foodplanner.model.dto.CategoriesItem;
import com.example.foodplanner.view.GlideImage;
import com.example.foodplanner.view.home.OnCategoryClickListener;

import java.util.ArrayList;
import java.util.List;

public class AreaAdaptersrch extends RecyclerView.Adapter<AreaAdaptersrch.ViewHolder> {


    List<AreaItem> areaItemss= new ArrayList<>();
    public OnAreaClickListen onareaClickListen;
    Context context;

    public AreaAdaptersrch(Context context , List<AreaItem> areaItemss, OnAreaClickListen onareaClickListen) {
        this.areaItemss = areaItemss;
        this.onareaClickListen=onareaClickListen;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.area_item_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AreaItem areaItem=areaItemss.get(position);
        holder.countryName.setText(areaItem.getStrArea());
      //  GlideImage.downloadImageToImageView(holder.categoryImg.getContext(),categoriesItem.getStrCategoryThumb(),holder.categoryImg);

        holder.itemView.setOnClickListener(view->{
            onareaClickListen.onareaClick(areaItem);

        });
    }



    @Override
    public int getItemCount() {
        return areaItemss.size();
    }


    public void changeData(List<AreaItem> areaItemss){
        this.areaItemss=areaItemss;
        notifyDataSetChanged();

    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView countryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName=itemView.findViewById(R.id.area_item);


        }

    }
}


