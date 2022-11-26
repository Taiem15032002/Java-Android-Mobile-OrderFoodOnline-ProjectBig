package com.example.orderfoodonline.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.databinding.ItemFoodBinding;
import com.example.orderfoodonline.listener.DetailListener;
import com.example.orderfoodonline.models.Ramen;

import java.util.List;

import okhttp3.MediaType;

public class RamenAdapter extends RecyclerView.Adapter<RamenAdapter.MyViewHolder> {
    private List<Ramen> list;
    private DetailListener detailListener;
// , DetailListener detailListener
    public RamenAdapter(List<Ramen> list, DetailListener detailListener) {
        this.list = list;
        this.detailListener = detailListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding itemFoodBinding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new MyViewHolder(itemFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setBinding(list.get(position));
        if (list.get(position).getFoodThumb().contains("https")){
            Glide.with(holder.itemView).load(list.get(position).getFoodThumb()).into(holder.binding.imageFood);
        }else{
            Glide.with(holder.itemView).load(Utils.hinh+list.get(position).getFoodThumb()).into(holder.binding.imageFood);
        }
//        Glide.with(holder.itemView).load(list.get(position).getFoodThumb()).into(holder.binding.imageFood);
//        Glide.with(holder.itemView).load(list.get(position).getNamefood()).into(holder.binding.imageFood);
//        Glide.with(holder.itemView).load(list.get(position).getFoodPrice()).into(holder.binding.imageFood);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
//Khoi tao myviewholder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemFoodBinding binding;

        public MyViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        private void setBinding(Ramen ramen){
            binding.setItemfood(ramen);
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
