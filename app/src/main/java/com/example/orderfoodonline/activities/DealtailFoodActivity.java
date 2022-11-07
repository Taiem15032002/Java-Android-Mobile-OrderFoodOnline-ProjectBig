package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.databinding.ActivityDealtailFoodBinding;
import com.example.orderfoodonline.models.Cart;
import com.example.orderfoodonline.models.FoodDetail;
import com.example.orderfoodonline.viewModels.FoodDetailViewModel;
import com.example.orderfoodonline.viewModels.HomeViewModel;

import java.util.List;

import io.paperdb.Paper;

public class DealtailFoodActivity extends AppCompatActivity {
    FoodDetailViewModel viewModel;
    ActivityDealtailFoodBinding activityDealtailFoodBinding;
    FoodDetail foodDetail;
    int amount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDealtailFoodBinding = DataBindingUtil.setContentView(this, R.layout.activity_dealtail_food);
        //Khoi tao Paper
        Paper.init(this);
        int id = getIntent().getIntExtra("id", 1);
        initData(id);
        initView();
        Event();
        eventClick();
        showData(id);
    }

    private void showData(int id) {
        //Doc du lieu da luu vao paper
        if(Paper.book().read("cart") != null){
            List<Cart> list = Paper.book().read("cart");
            Utils.cartList = list;
        }


        if (Utils.cartList.size() > 0){
            for (int i = 0; i < Utils.cartList.size(); i++){
                if (Utils.cartList.get(i).getFoodDetail().getId() == id){
                    activityDealtailFoodBinding.tvMany.setText(Utils.cartList.get(i).getAmount() + "");
                }
            }
        }else{
            activityDealtailFoodBinding.tvMany.setText(amount+"");
        }
    }

    private void eventClick() {
        activityDealtailFoodBinding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt(activityDealtailFoodBinding.tvMany.getText().toString()) + 1;
                activityDealtailFoodBinding.tvMany.setText(String.valueOf(amount));
            }
        });
        activityDealtailFoodBinding.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(activityDealtailFoodBinding.tvMany.getText().toString()) > 1){
                    amount = Integer.parseInt(activityDealtailFoodBinding.tvMany.getText().toString()) - 1;
                    activityDealtailFoodBinding.tvMany.setText(String.valueOf(amount));
                }
            }
        });
        activityDealtailFoodBinding.btnTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart(amount);
            }
        });
    }
    private void addtoCart(int amount){
        boolean checkExit = false;
        int n = 0;
        if (Utils.cartList.size()>0){
            for (int i = 0; i < Utils.cartList.size(); i++){
                if (Utils.cartList.get(i).getFoodDetail().getId() == foodDetail.getId()){
                    checkExit = true;
                    n=i;
                    break;
                }
            }
        }
        if (checkExit){
            Utils.cartList.get(n).setAmount(amount);
        }else{
            Cart cart = new Cart();
            cart.setFoodDetail(foodDetail);
            cart.setAmount(amount);
            Utils.cartList.add(cart);
        }
        Toast.makeText(getApplicationContext(), "Add to cart", Toast.LENGTH_LONG).show();
        //Luu vao paper
        Paper.book().write("cart", Utils.cartList);
    }

    //Test
    private void Event() {
        activityDealtailFoodBinding.btnFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        activityDealtailFoodBinding.backContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData(int id){
        viewModel = new ViewModelProvider(this).get(FoodDetailViewModel.class);
        viewModel.foodDetailModelsMutableLiveData(id).observe(this,foodDetailModels -> {
            if (foodDetailModels.isSuccess()){
                foodDetail = foodDetailModels.getResult().get(0);
                Log.d("test",foodDetailModels.getResult().get(0).getFood_name());
                activityDealtailFoodBinding.tvFoodDetail.setText(foodDetail.getFood_name());
                activityDealtailFoodBinding.tvFoodPrice.setText("Giá tiền: "+foodDetail.getPrice()+"VND");
                activityDealtailFoodBinding.textDesciptions.setText(foodDetail.getDecripstion());
                Glide.with(this).load(foodDetail.getFoodThumb()).into(activityDealtailFoodBinding.imagedetail);
            }
        });
    }
}