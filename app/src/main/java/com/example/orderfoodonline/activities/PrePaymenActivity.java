package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
//import vn.momo.momo_partner.AppMoMoLib;
//import vn.momo.momo_partner.MoMoParameterNameMap;

public class PrePaymenActivity extends AppCompatActivity {

    TextView tvuser, tvsdt, tvtongtienthanhtoan, back_container;
    RadioButton rd50per, rd100per;
    AppCompatButton btnMomo, btnZalo, btnDatHang;
    EditText edtdiachi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodAppApi foodAppApi;
    int totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_paymen);
        Paper.init(this);
        initView();
        countTien();
        initControl();
    }

    private void countTien() {
        totalItem = 0;
        for (int i = 0;i < Utils.cartList.size();i++){
            totalItem = totalItem + Utils.cartList.get(i).getAmount();
        }
    }

    private void initControl() {
        tvtongtienthanhtoan.setText("Tổng tiền: "+getIntent().getIntExtra("tongtien", 0) + " VND");
        int tongtien = getIntent().getIntExtra("tongtien",0);
        String userN = Paper.book().read("username");
        String sdtN =  Paper.book().read("sdt");
        int idN =  getIntent().getIntExtra("idUserY",0);
//        Paper.book().write("sdT", Utils.user_current.getMobile());
        tvuser.setText("Tên: "+ Paper.book().read("username"));
        tvsdt.setText("SDT: "+ Paper.book().read("sdt"));

        //set click Back
        back_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        btnMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Hãy nhập địa chỉ giao hàng!");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else{
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
//                    compositeDisposable.add(foodAppApi.createOrder(str_email, str_sdt,String.valueOf(tvtongtienthanhtoan),id,str_diachi,new Gson().toJson()))
                }
            }
        });
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Hãy nhập địa chỉ giao hàng!");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else{
                    String str_email = Utils.user_current.getEmail();
                    String str_user = Utils.user_current.getUsername();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    Log.d("dathang", new Gson().toJson(Utils.cartList));
                    compositeDisposable.add(foodAppApi.createOrder(id, str_diachi,sdtN,totalItem,String.valueOf(tongtien),userN,new Gson().toJson(Utils.cartList))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModels -> {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }, throwable -> {
                                    }
                            ));
                }
            }
        });
    }

    private void initView() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        tvuser =findViewById(R.id.tv_tenUser);
        tvsdt = findViewById(R.id.tv_sdt);
        back_container = findViewById(R.id.back_container);
        tvtongtienthanhtoan = findViewById(R.id.tv_tongtienthanhtoan);
        btnMomo = findViewById(R.id.btn_momo);
        btnDatHang = findViewById(R.id.btndathang);
        edtdiachi = findViewById(R.id.edtdiachi);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}