package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText repassword;
    TextView next;
    FoodAppApi foodAppApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
        initControl();
    }

    private void initControl() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                String str_pass = password.getText().toString().trim();
                String str_repass = repassword.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(ForgotPasswordActivity.this, "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(str_pass)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(str_repass)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                }{
                    //luu username vao paper
                    Paper.book().write("email", str_email);
                    Paper.book().write("pass", str_pass);
                    Paper.book().write("repass", str_repass);
                    compositeDisposable.add(foodAppApi.quenMatKhau(str_email, str_pass,str_repass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModels -> {
                                        if (userModels.isSuccess()) {
                                            Toast.makeText(getApplicationContext(),userModels.getMessage(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(), userModels.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));


                }
            }
        });
    }

    private void initView() {
        //FoodAppApi = Retrofitinstance.getRetrofit(Utils.user_current).create(FoodAppApi.class);
        email = findViewById(R.id.edtEmail_Forgot);
        password = findViewById(R.id.edtPassword_Forgot);
        repassword = findViewById(R.id.edtRePassword_Forgot);
        next = findViewById(R.id.btnRegister);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}