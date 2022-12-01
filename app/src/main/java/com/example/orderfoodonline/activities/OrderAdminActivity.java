package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.adapters.OrderAdapter;
import com.example.orderfoodonline.adapters.OrdersAdminAdapter;
import com.example.orderfoodonline.models.DonHang;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderAdminActivity  extends AppCompatActivity {
    DonHang donHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodAppApi foodAppApi;
    TextView txtidtrangthai;
    RecyclerView rcvdonhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_admin);
        initView();
        Paper.init(this);
        initget();
    }

    private void initView() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        rcvdonhang = findViewById(R.id.rv_ordersadmin);
        txtidtrangthai = findViewById(R.id.idtinhtrang);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvdonhang.setLayoutManager(manager);
    }

    private void initget() {
        compositeDisposable.add(foodAppApi.getFullDonHang(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModels -> {
                            OrdersAdminAdapter adapter = new OrdersAdminAdapter(getApplicationContext(), donHangModels.getResult());
                            rcvdonhang.setAdapter(adapter);
                        },throwable -> {

                        }
                ));
    }
    private void updatetinhtrangdon(int tt){
        compositeDisposable.add(foodAppApi.updatetinhtrang(donHang.getId(),tt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModels -> {
                            trangthaiDon(tt);
                        },throwable -> {

                        }));
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
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}