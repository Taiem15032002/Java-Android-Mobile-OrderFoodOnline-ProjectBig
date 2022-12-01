package com.example.orderfoodonline.activities;

import static com.example.orderfoodonline.adapters.CartAdapter.removeCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.models.AddFoodModels;
import com.example.orderfoodonline.models.CreateOrder;
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
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;
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
        //khoi tao zalopay
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        ///
        Paper.init(this);
        initView();
        countTien();
        initControl();
    }

    private void countTien() {
        totalItem = 0;
        for (int i = 0; i < Utils.cartList.size(); i++) {
            totalItem = totalItem + Utils.cartList.get(i).getAmount();
        }
    }

    private void initControl() {
        tvtongtienthanhtoan.setText("Tổng tiền: " + getIntent().getIntExtra("tongtien", 0) + " VND");
        int tongtien = getIntent().getIntExtra("tongtien", 0);
        String userN = Paper.book().read("username");
        String sdtN = Paper.book().read("sdt");
        String emailN = Paper.book().read("email");
//        Paper.book().write("sdT", Utils.user_current.getMobile());
        tvuser.setText("Tên: " + Paper.book().read("username"));
        tvsdt.setText("SDT: " + Paper.book().read("sdt"));

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
                if (TextUtils.isEmpty(str_diachi)) {
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
                } else {
                    if (rd50per.isChecked() || rd100per.isChecked()) {
                        compositeDisposable.add(foodAppApi.createOrder(emailN, str_diachi, sdtN, totalItem, String.valueOf(tongtien), userN, new Gson().toJson(Utils.cartList))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        userModels -> {
                                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                                            builder.setTitle("Thanh toán");
                                            builder.setMessage("Thanh toán thành công!");
                                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    removeCart(Paper.book().read("removecart"));
                                                    requestZalo();
                                                }
                                            });
                                            builder.show();

                                        }, throwable -> {

                                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                                            builder.setTitle("Thanh toán");
                                            builder.setMessage("Thanh toán thành công!");
                                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    removeCart(Paper.book().read("removecart"));
                                                    requestZalo();
                                                }
                                            });
                                            builder.show();
                                        }
                                ));
                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("Thanh toán");
                        builder.setMessage("Vui lòng chọn số tiền thanh toán!");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();

                    }
                }
            }
        });
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)) {
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
                } else {
                    if (rd50per.isChecked() || rd100per.isChecked()) {
                        int id = Utils.user_current.getId();
                        Log.d("dathang", emailN + " " + str_diachi + " " + sdtN + " " + totalItem + " " + String.valueOf(tongtien) + " " + userN + " " + new Gson().toJson(Utils.cartList));
                        compositeDisposable.add(foodAppApi.createOrder(emailN, str_diachi, sdtN, totalItem, String.valueOf(tongtien), userN, new Gson().toJson(Utils.cartList))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        userModels -> {
                                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                                            builder.setTitle("Thanh toán");
                                            builder.setMessage("Thanh toán thành công!");
                                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(getApplicationContext(), ShippingActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    removeCart(Paper.book().read("removecart"));
                                                }
                                            });
                                            builder.show();

                                        }, throwable -> {

                                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                                            builder.setTitle("Thanh toán");
                                            builder.setMessage("Thanh toán thành công!");
                                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(getApplicationContext(), ShippingActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    removeCart(Paper.book().read("removecart"));
                                                }
                                            });
                                            builder.show();
                                        }
                                ));
                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("Thanh toán");
                        builder.setMessage("Vui lòng chọn số tiền thanh toán!");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            }
        });
    }

    private void requestZalo() {
        CreateOrder orderApi = new CreateOrder();

        try {
            int tongtien = getIntent().getIntExtra("tongtien", 0);
            JSONObject data = orderApi.createOrder(String.valueOf(tongtien));
            if (rd50per.isChecked()) {
                tongtien = tongtien / 2;
                data = orderApi.createOrder(String.valueOf(tongtien));
            } else if (rd100per.isChecked()) {
                data = orderApi.createOrder(String.valueOf(tongtien));
            }
            String code = data.getString("return_code");
            if (code.equals("1")) {

                String token = data.getString("zp_trans_token");
                compositeDisposable.add(foodAppApi.updateZalopay(Paper.book().read("email"), token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModels -> {
                                    if (userModels.isSuccess()){
                                    }
                                }, throwable -> {

                                }
                        ));

                ZaloPaySDK.getInstance().payOrder(PrePaymenActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        int tongtien = getIntent().getIntExtra("tongtien", 0);
                        if (rd50per.isChecked()) {
                            tongtien = tongtien / 2;

                        } else if (rd100per.isChecked()) {
                            tongtien = tongtien + 0;
                        }
                        Intent intent = new Intent(getApplicationContext(), ShippingActivity.class);
                        intent.putExtra("giatien",tongtien);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Intent intent = new Intent(getApplicationContext(), PrePaymenActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        tvuser = findViewById(R.id.tv_tenUser);
        tvsdt = findViewById(R.id.tv_sdt);
        back_container = findViewById(R.id.back_container);
        tvtongtienthanhtoan = findViewById(R.id.tv_tongtienthanhtoan);
        btnMomo = findViewById(R.id.btn_zalopay);
        btnDatHang = findViewById(R.id.btndathang);
        edtdiachi = findViewById(R.id.edtdiachi);
        rd100per = findViewById(R.id.rd100);
        rd50per = findViewById(R.id.rd50);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}