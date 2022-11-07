package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.adapters.CartAdapter;
import com.example.orderfoodonline.databinding.ActivityCartBinding;
import com.example.orderfoodonline.listener.NumberListener;
import com.example.orderfoodonline.models.Cart;

import java.util.List;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);
        Paper.init(this);
        initView();
        initData();
        totalPrice();
    }

    private void initData() {
        List<Cart> cartList = Paper.book().read("cart");
        Utils.cartList = cartList;
        CartAdapter adapter = new CartAdapter(this, Utils.cartList, new NumberListener() {
            @Override
            public void change() {
                totalPrice();
            }
        });
        binding.rvCart.setAdapter(adapter);
    }

    private void totalPrice() {
        //Lay so luong tung san pham duoc luu vao listcart nhan voi don gia
        int price = 0;
        for (int i = 0; i < Utils.cartList.size(); i++){
            price = price + Utils.cartList.get(i).getAmount() * Utils.cartList.get(i).getFoodDetail().getPrice();
        }
        binding.tvTongtien.setText("Tổng tiền: "+String.valueOf(price) + "VND");
    }

    private void initView() {
        binding.rvCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvCart.setLayoutManager(layoutManager);
    }
}