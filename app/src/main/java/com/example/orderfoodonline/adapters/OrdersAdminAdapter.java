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
import com.example.orderfoodonline.listener.ItemClickLis;
import com.example.orderfoodonline.models.DonHang;

import java.util.List;

public class OrdersAdminAdapter extends RecyclerView.Adapter<OrdersAdminAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<DonHang> listDonhang;
    Context context;

    public OrdersAdminAdapter( Context context,List<DonHang> listDonhang) {
        this.listDonhang = listDonhang;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_admin, parent, false);
        return new OrdersAdminAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdminAdapter.MyViewHolder holder, int position) {
        DonHang donHang = listDonhang.get(position);
        holder.txtiddonhang.setText("Mã đơn hàng: "+donHang.getId() + " ");
        holder.txtidtongtien.setText("Tổng tiên: "+donHang.getTongtien()+" ");
        holder.txtidtrangthai.setText(trangthaiDon(donHang.getTrangthai()));
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

    private String trangthaiDon(int tt){
        String res = "";
        switch (tt){
            case 0:
                res = "Đơn hàng đang được xử lý!";
                break;
            case  1:
                res = "Đơn hàng đã được chấp nhận!";
                break;
            case 2:
                res = "Thành công";
                break;
            case 3:
                res = "Đang giao!";
                break;
            case 4:
                res = "Đơn hàng đã hủy";
                break;
        }

        return res;
    }

    @Override
    public int getItemCount() {
        return listDonhang.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtiddonhang, txtidtongtien, txtidtrangthai;
        RecyclerView rcchitiet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtiddonhang = itemView.findViewById(R.id.iddonhang);
            txtidtongtien = itemView.findViewById(R.id.idtongtien);
            txtidtrangthai = itemView.findViewById(R.id.idtinhtrang);
            rcchitiet = itemView.findViewById(R.id.rcvdonhang);
        }

    }
}
