package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText repass;
    EditText username;
    EditText mobile;
    TextView btnRegister, haveaaccount, btnBack;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodAppApi api;

    //    RegisterViewModel registerViewModel;
    //    CompositeDisposable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }

    private void initControl() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKi();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dangKi() {
//        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        String stremail = email.getText().toString().trim();
        String strpass = password.getText().toString().trim();
        String strrepass = repass.getText().toString().trim();
        String strusername = username.getText().toString().trim();
        String strmobile = mobile.getText().toString().trim();
        if (TextUtils.isEmpty(stremail)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strpass)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strrepass)) {
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strusername)) {
            Toast.makeText(this, "Vui lòng nhập tên người dùng", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strmobile)) {
            Toast.makeText(this, "Vui lòng nhập SDT người dùng", Toast.LENGTH_SHORT).show();
        } else {
            if (strpass.equals(strrepass)) {
                compositeDisposable.add(api.dangKi(stremail, strpass, strusername, strmobile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModels -> {
                                    if (userModels.isSuccess()) {
                                        //Sau khi nguoi dung dang ky thanh cong
                                        //Day vao Utils email pass
                                        Utils.user_current.setEmail(stremail);
                                        Utils.user_current.setPass(strpass);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), userModels.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), "Hỏng chương trình", Toast.LENGTH_SHORT).show();
                                }
                        ));
            } else {
                Toast.makeText(this, "Mật khẩu phải trùng lập", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        api = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        email = findViewById(R.id.edtEmail_register);
        password = findViewById(R.id.edtPassword_Register);
        repass = findViewById(R.id.edtRePassword_Register);
        username = findViewById(R.id.edtUsername_register);
        mobile = findViewById(R.id.edtMobile_register);
        btnRegister = findViewById(R.id.btnRegister);
        haveaaccount = findViewById(R.id.tvBandacotaikhoan);
        btnBack = findViewById(R.id.back_container);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}