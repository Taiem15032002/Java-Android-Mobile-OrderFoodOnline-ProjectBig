package com.example.orderfoodonline.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.adapters.CategoryAdapter;
import com.example.orderfoodonline.adapters.RamenAdapter;
import com.example.orderfoodonline.databinding.ActivityHomeBinding;
import com.example.orderfoodonline.databinding.ItemFoodBinding;
import com.example.orderfoodonline.listener.CategoryListener;
import com.example.orderfoodonline.listener.DetailListener;
import com.example.orderfoodonline.models.Category;
import com.example.orderfoodonline.models.Ramen;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;
import com.example.orderfoodonline.viewModels.HomeViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements CategoryListener,DetailListener{
    HomeViewModel homeViewModel;
    ActivityHomeBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodAppApi foodAppApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setBinding cho activity home
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        foodAppApi  = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        Paper.init(this);
        initView();
        getToken();
        initData();
        initBottombar();
    }

    private void initBottombar() {
        //-------------------------------//
        //set click cho btn to Cart
            binding.toCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                    startActivity(intent);
                }
            });
        //set click cho btn to Setting
        binding.toSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        //set click cho btn to favourites
        binding.toFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Chức năng sắp ra mắt", Toast.LENGTH_SHORT).show();
            }
        }); 
        //set click cho btn to orders
        binding.toOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);
            }
        });
        //set click cho btn to chat
        binding.toChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //Set Recycle View
        binding.rvCategory.setHasFixedSize(true);
        //set RV theo chieu ngang
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        binding.rvCategory.setLayoutManager(layoutManager);
        //set RV theo chieu doc mi ramen
        binding.rvMinhat.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2);
        binding.rvMinhat.setLayoutManager(layoutManager1);
    }
    //get Data tu Database vao RVcategory
    private void initData() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.categoryModelsMutableLiveData().observe(this, categoryModels -> {
            if (categoryModels.isSuccess()){
                //setAdapter cho RVcategory
                CategoryAdapter adapter = new CategoryAdapter(categoryModels.getResult(), this);
                binding.rvCategory.setAdapter(adapter);
            }
        });
        //Mi ramen setadapter
//        homeViewModel.ramenModelsMutableLiveData(0).observe(this, ramenModels -> {
//           if (ramenModels.isSuccess()){
//               RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(),this);
//               binding.rvMinhat.setAdapter(adapter);
//           }
//        });
//        //Mi udon setadapter
        homeViewModel.ramenallModelsMutableLiveData(0).observe(this, ramenModels -> {
            if (ramenModels.isSuccess()){
                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(),this);
                binding.rvMinhat.setAdapter(adapter);
                binding.tvMiNhat.setText("Tất cả");
            }
        });
//        //Mi soba setadapter
//        homeViewModel.ramenModelsMutableLiveData(3).observe(this, ramenModels -> {
//            if (ramenModels.isSuccess()){
//                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(),this);
//                binding.rvSoba.setAdapter(adapter);
//            }
//        });
//        //Mi shirataki setadapter
//        homeViewModel.ramenModelsMutableLiveData(4).observe(this, ramenModels -> {
//            if (ramenModels.isSuccess()){
//                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(),this);
//                binding.rvShirataki.setAdapter(adapter);
//            }
//        });
//        //Mi somen setadapter
//        homeViewModel.ramenModelsMutableLiveData(5).observe(this, ramenModels -> {
//            if (ramenModels.isSuccess()){
//                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(), this);
//                binding.rvSomen.setAdapter(adapter);
//            }
//        });
    }

    @Override
    public void onCategoryClick(Category category) {
        if (category.getId() != 0) {
            homeViewModel.ramenModelsMutableLiveData(category.getId()).observe(this, ramenModels -> {
                if (ramenModels.isSuccess()) {
                    binding.tvMiNhat.setText("Mì " + category.getCategory());
                    RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(), this);
                    binding.rvMinhat.setAdapter(adapter);
                }
            });
        }else{
            homeViewModel.ramenallModelsMutableLiveData(0).observe(this, ramenModels -> {
                if (ramenModels.isSuccess()) {
                    binding.tvMiNhat.setText("Tất cả");
                    RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(), this);
                    binding.rvMinhat.setAdapter(adapter);
                }
            });
        }

    }
    @Override
    public void onMenuClick(Ramen ramen){
        Intent intent = new Intent(getApplicationContext(), DealtailFoodActivity.class);
        //Truyen id
        intent.putExtra("id",ramen.getId());
        startActivity(intent);
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)){
                            compositeDisposable.add(foodAppApi.updateToken(Utils.user_current.getEmail(), s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            addFoodModels  -> {
                                                Log.d("token123456789", s);
                                            },throwable -> {
                                                Log.d("token123456789", throwable.getMessage());
                                            }
                                    ));
                        }
                    }
                });
        compositeDisposable.add(foodAppApi.getToken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModels -> {
                            if (userModels.isSuccess()){
                                Utils.ID_RECEIVE = String.valueOf(userModels.getResult().get(0).getId());
                            }
                        },throwable -> {

                        }
                ));
    }
}