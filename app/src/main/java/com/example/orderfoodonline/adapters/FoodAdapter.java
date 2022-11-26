package com.example.orderfoodonline.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.databinding.ItemCategoryFoodBinding;
import com.example.orderfoodonline.databinding.ItemFoodBinding;
import com.example.orderfoodonline.listener.DetailListener;
import com.example.orderfoodonline.models.Ramen;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    private List<Ramen> foodList;
    private DetailListener detailListener;

    public FoodAdapter(List<Ramen> foodList, DetailListener detailListener) {
        this.foodList = foodList;
        this.detailListener = detailListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryFoodBinding itemCategoryFoodBinding = ItemCategoryFoodBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new MyViewHolder(itemCategoryFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setBinding(foodList.get(position));
        if (foodList.get(position).getFoodThumb().contains("https")){
            Glide.with(holder.itemView).load(foodList.get(position).getFoodThumb()).into(holder.binding.imageCategoryFood);
        }else{
            Glide.with(holder.itemView).load(Utils.hinh+foodList.get(position).getFoodThumb()).into(holder.binding.imageCategoryFood);
        }

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryFoodBinding binding;

        public MyViewHolder(ItemCategoryFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setBinding(Ramen ramen){
            binding.setFooditem(ramen);
            binding.executePendingBindings();
            //bat su kien click cho home click food
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailListener.onMenuClick(ramen);
                }
            });
        }
    }
}
