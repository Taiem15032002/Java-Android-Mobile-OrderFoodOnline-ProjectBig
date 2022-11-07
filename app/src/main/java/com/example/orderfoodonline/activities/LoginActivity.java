package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodonline.R;

public class LoginActivity extends AppCompatActivity {

    public EditText edtusername;
    public EditText edtpassword;
    public TextView register;
    public TextView forgotpass;
    public TextView btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initLogin();
        initRegister();
        initForgotpassword();
    }

    private void initRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initForgotpassword() {
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
               startActivity(intent);
               finish();
            }
        });
    }



    public void initView() {
        edtusername = findViewById(R.id.edtUsername);
        edtpassword = findViewById(R.id.edtPassword);
        register = findViewById(R.id.tvBanchuacotaikhoan);
        forgotpass = findViewById(R.id.tvQuenmatkhau);
        btnLogin =  findViewById(R.id.btnLogin);
    }

}