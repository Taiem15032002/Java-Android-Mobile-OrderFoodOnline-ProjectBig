package com.example.orderfoodonline.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfoodonline.databinding.ItemHomeCategoryBinding;
import com.example.orderfoodonline.listener.CategoryListener;
import com.example.orderfoodonline.listener.DetailListener;
import com.example.orderfoodonline.models.Category;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<Category> list;
    private CategoryListener categoryListener;

    public CategoryAdapter(List<Category> list, CategoryListener categoryListener) {
        this.list = list;
        this.categoryListener = categoryListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHomeCategoryBinding itemHomeCategoryBinding = ItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(itemHomeCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //tu dong setText
        holder.setBinding(list.get(position));
        //get image cho Category su dung thu vien Glide de lay
        Glide.with(holder.itemView).load(list.get(position).getCategoryThumb()).into(holder.binding.imageCategory);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private ItemHomeCategoryBinding binding;

        public MyViewHolder(ItemHomeCategoryBinding binding) {
            super(binding.getRoot() );
            this.binding = binding;
        }
        public void setBinding(Category category){
            binding.setItemcategory(category);
            binding.executePendingBindings();
            //set Binding cho category on click
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryListener.onCategoryClick(category);
                }
            });
        }
    }
}
