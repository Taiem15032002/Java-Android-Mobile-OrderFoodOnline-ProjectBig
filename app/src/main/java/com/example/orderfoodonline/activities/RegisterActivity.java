package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.bumptech.glide.util.Util;
import com.example.orderfoodonline.R;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;
import com.example.orderfoodonline.viewModels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText repass;
    EditText username;
    TextView btnRegister,haveaaccount, btnBack;
    RegisterViewModel registerViewModel;
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
    private void dangKi(){
//        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        String stremail = email.getText().toString().trim();
        String strpass = password.getText().toString().trim();
        String strrepass = repass.getText().toString().trim();
        String strusername = username.getText().toString().trim();
        if (TextUtils.isEmpty(stremail)){
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(strpass)){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(strrepass)){
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(strusername)){
            Toast.makeText(this, "Vui lòng nhập tên người dùng", Toast.LENGTH_SHORT).show();
        } else{
            if (strpass.equals(strrepass)){
                registerViewModel.userModelsMutableLiveData(stremail, strpass, strusername).observe(this, userModels -> {
                    if (userModels.isSuccess()){
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(this, "Mật khẩu phải trùng lập", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        email = findViewById(R.id.edtEmail_register);
        password = findViewById(R.id.edtPassword_Register);
        repass = findViewById(R.id.edtRePassword_Register);
        username = findViewById(R.id.edtUsername);
        btnRegister = findViewById(R.id.btnRegister);
        haveaaccount = findViewById(R.id.tvBandacotaikhoan);
        btnBack = findViewById(R.id.back_container);
    }
}