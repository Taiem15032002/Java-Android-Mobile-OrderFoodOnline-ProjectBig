package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    AppCompatButton buttonCart;
    TextView btnBack;
    int price;

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

        //click payment
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.size() < 1){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Hãy thêm món ăn vào giỏ hàng!");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), PrePaymenActivity.class);
                    intent.putExtra("tongtien",price);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void totalPrice() {
        //Lay so luong tung san pham duoc luu vao listcart nhan voi don gia
        price = 0;
        for (int i = 0; i < Utils.cartList.size(); i++){
            price = price + Utils.cartList.get(i).getAmount() * Utils.cartList.get(i).getFoodDetail().getPrice();
        }
        binding.tvTongtien.setText("Tổng tiền: "+String.valueOf(price) + "VND");
    }

    private void initView() {
        binding.rvCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvCart.setLayoutManager(layoutManager);
        buttonCart = findViewById(R.id.btn_checkout);
        btnBack = findViewById(R.id.back_container);
    }
}