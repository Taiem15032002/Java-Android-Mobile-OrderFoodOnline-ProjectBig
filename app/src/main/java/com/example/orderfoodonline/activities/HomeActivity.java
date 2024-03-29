package com.example.orderfoodonline.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.adapters.CategoryAdapter;
import com.example.orderfoodonline.adapters.RamenAdapter;
import com.example.orderfoodonline.databinding.ActivityHomeBinding;
import com.example.orderfoodonline.databinding.ItemFoodBinding;
import com.example.orderfoodonline.listener.CategoryListener;
import com.example.orderfoodonline.listener.DetailListener;
import com.example.orderfoodonline.models.Category;
import com.example.orderfoodonline.models.Ramen;
import com.example.orderfoodonline.viewModels.HomeViewModel;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements CategoryListener,DetailListener{
    HomeViewModel homeViewModel;
    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setBinding cho activity home
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
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
        homeViewModel.ramenModelsMutableLiveData(1).observe(this, ramenModels -> {
           if (ramenModels.isSuccess()){
               RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(),this);
               binding.rvMinhat.setAdapter(adapter);
           }
        });
//        //Mi udon setadapter
//        homeViewModel.ramenModelsMutableLiveData(2).observe(this, ramenModels -> {
//            if (ramenModels.isSuccess()){
//                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(),this);
//                binding.rvMiudon.setAdapter(adapter);
//            }
//        });
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
//        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
// Truyen id
//        intent.putExtra("idcate",category.getId());
//        intent.putExtra("namecate",category.getCategory());
//        startActivity(intent);
        //Mi setadapter
        homeViewModel.ramenModelsMutableLiveData(category.getId()).observe(this, ramenModels -> {
            if (ramenModels.isSuccess()){
                binding.tvMiNhat.setText("Mì "+category.getCategory());
                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(),this);
                binding.rvMinhat.setAdapter(adapter);
            }
        });

    }
    @Override
    public void onMenuClick(Ramen ramen){
        Intent intent = new Intent(getApplicationContext(), DealtailFoodActivity.class);
        //Truyen id
        intent.putExtra("id",ramen.getId());
        startActivity(intent);
    }
}