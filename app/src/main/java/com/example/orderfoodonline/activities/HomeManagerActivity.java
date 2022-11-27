package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.adapters.CategoryAdapter;
import com.example.orderfoodonline.adapters.RamenAdapter;
import com.example.orderfoodonline.databinding.ActivityHomeBinding;
import com.example.orderfoodonline.databinding.ActivityHomeManagerBinding;
import com.example.orderfoodonline.listener.CategoryListener;
import com.example.orderfoodonline.listener.DetailListener;
import com.example.orderfoodonline.models.Category;
import com.example.orderfoodonline.models.EventBus.UpdateFood;
import com.example.orderfoodonline.models.Ramen;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;
import com.example.orderfoodonline.viewModels.HomeViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeManagerActivity  extends AppCompatActivity implements CategoryListener,DetailListener {
ImageView toHome, toChat, toAdd, toOrder, toSetting;
CompositeDisposable compositeDisposable = new CompositeDisposable();
FoodAppApi foodAppApi;
HomeViewModel homeViewModel;
ActivityHomeManagerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_manager);
        initView();
        initBottombar();
        getSp();
        initControl();
    }

    private void initBottombar() {
        toAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initControl() {
    }
    private void getSp(){
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.categoryModelsMutableLiveData().observe(this, categoryModels -> {
            if (categoryModels.isSuccess()){
                //setAdapter cho RVcategory
                CategoryAdapter adapter = new CategoryAdapter(categoryModels.getResult(), this);
                binding.rvCategory.setAdapter(adapter);
            }
        });
        homeViewModel.ramenModelsMutableLiveData(1).observe(this, ramenModels -> {
            if (ramenModels.isSuccess()){
                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(), this);
                binding.rvSPmoi.setAdapter(adapter);
            }
        });
    }

    private void initView() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        toChat = findViewById(R.id.toChatAdmin);
        toAdd = findViewById(R.id.toAddSP);
        toOrder = findViewById(R.id.toOrdersAdmin);
        toSetting = findViewById(R.id.toSettingAdmin);


        binding.rvCategory.setHasFixedSize(true);
        //set RV theo chieu ngang
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        binding.rvCategory.setLayoutManager(layoutManager);
        //set RV theo chieu doc mi ramen
        binding.rvSPmoi.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2);
        binding.rvSPmoi.setLayoutManager(layoutManager1);
    }

    public void onCategoryClick(Category category) {
        //Mi setadapter
        homeViewModel.ramenModelsMutableLiveData(category.getId()).observe(this, ramenModels -> {
            if (ramenModels.isSuccess()){
                binding.tvMiNhat.setText("Mì "+category.getCategory());
                RamenAdapter adapter = new RamenAdapter(ramenModels.getResult(), this);
                binding.rvSPmoi.setAdapter(adapter);
            }
        });
    }

    public void onMenuClick(Ramen ramen){
//        Intent intent = new Intent(getApplicationContext(), DealtailFoodActivity.class);
//        //Truyen id
//        intent.putExtra("id",ramen.getId());
//        startActivity(intent);

    }

}