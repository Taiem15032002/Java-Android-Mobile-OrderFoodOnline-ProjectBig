package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.adapters.OrderAdapter;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodAppApi foodAppApi;
    RecyclerView rcvdonhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        Paper.init(this);
        initget();
    }

    private void initView() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        rcvdonhang = findViewById(R.id.rv_orders);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvdonhang.setLayoutManager(manager);
    }

    private void initget() {
        compositeDisposable.add(foodAppApi.getDonHang(Paper.book().read("email"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModels -> {
                            OrderAdapter adapter = new OrderAdapter(getApplicationContext(), donHangModels.getResult());
                            rcvdonhang.setAdapter(adapter);
                        },throwable -> {

                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}