package com.example.orderfoodonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.models.Item;

import java.util.List;

public class ChitietdonhangAdapter extends RecyclerView.Adapter<ChitietdonhangAdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;

    public ChitietdonhangAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitietdonhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.item_idchitiet.setText("Tên món: "+item.getFood_name() +" ");
        holder.item_soluong.setText("Số lượng: "+item.getSoluong()+" ");
        holder.item_tongtien.setText("Tổng tiền: "+item.getPrice());
        Glide.with(context).load(item.getFoodThumb()).into(holder.imgchitiet);
        if (itemList.get(position).getFoodThumb().contains("https")){
            Glide.with(context).load(item.getFoodThumb()).into(holder.imgchitiet);
        }else{
            Glide.with(context).load(Utils.hinh+itemList.get(position).getFoodThumb()).into(holder.imgchitiet);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgchitiet;
        TextView item_soluong, item_tongtien, item_idchitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgchitiet = itemView.findViewById(R.id.imgchitiet);
            item_soluong = itemView.findViewById(R.id.item_soluong);
            item_idchitiet = itemView.findViewById(R.id.item_idchitietten);
            item_tongtien = itemView.findViewById(R.id.item_tongtien);
        }
    }
}
