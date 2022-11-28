package com.example.orderfoodonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.models.DonHang;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<DonHang> listDonhang;
    Context context;

    public OrderAdapter( Context context,List<DonHang> listDonhang) {
        this.listDonhang = listDonhang;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = listDonhang.get(position);
        holder.txtiddonhang.setText("Mã đơn hàng: "+donHang.getId() + " ");
        holder.txtidtongtien.setText("Tổng tiên: "+donHang.getTongtien()+" ");
        LinearLayoutManager manager = new LinearLayoutManager(
                holder.rcchitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        manager.setInitialPrefetchItemCount(donHang.getItem().size());
        
        //adapter ch tiet
        ChitietdonhangAdapter adapter = new ChitietdonhangAdapter(context,donHang.getItem());
        holder.rcchitiet.setLayoutManager(manager);
        holder.rcchitiet.setAdapter(adapter);
        holder.rcchitiet.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return listDonhang.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtiddonhang, txtidtongtien;
        RecyclerView rcchitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtiddonhang = itemView.findViewById(R.id.iddonhang);
            txtidtongtien = itemView.findViewById(R.id.idtongtien);
            rcchitiet = itemView.findViewById(R.id.rcvdonhang);
        }
    }
}
