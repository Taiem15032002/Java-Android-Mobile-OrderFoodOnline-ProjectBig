package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.models.Ramen;
import com.example.orderfoodonline.models.User;
import com.example.orderfoodonline.models.UserModels;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    public EditText edtusername;
    public EditText edtpassword;
    public TextView register;
    public TextView forgotpass;
    public TextView btnLogin;
    public boolean islogin = false;
    RadioGroup radioGroup;
    RadioButton rd1, rd2, rd3;
    FoodAppApi foodAppApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initControl();
    }

    public void initControl() {
        //btn Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //btn forgotpass
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //btn login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strusername = edtusername.getText().toString().trim();
                String strPass = edtpassword.getText().toString().trim();
                if (TextUtils.isEmpty(strusername)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(strPass)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    //luu username vao paper

                    Paper.book().write("email", strusername);
                    Paper.book().write("pass", strPass);
                    if (rd1.isChecked() != true && rd2.isChecked() != true && rd3.isChecked() != true){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Hãy chọn 1 quyền đăng nhập!");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    else if (rd1.isChecked() == true){
                        dangnhap(strusername, strPass);
                    }else if(rd2.isChecked() == true){
                        Intent intent = new Intent(getApplicationContext(), HomeManagerActivity.class);
                        startActivity(intent);
                    }else if(rd3.isChecked() == true){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Đang phát triển quyền shipper!");
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

    private void dangnhap(String strusername, String strPass) {
        compositeDisposable.add(foodAppApi.dangNhap(strusername, strPass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModels -> {
                            if (userModels.isSuccess()) {
                                //Toast.makeText(getApplicationContext(), Utils.user_current.getId(), Toast.LENGTH_SHORT).show();
                                islogin = true;
                                Paper.book().read("islogin", islogin);
                                Utils.user_current = userModels.getResult().get(0);
                                //Luu thong tin
                                Paper.book().read("email", userModels.getResult().get(0));
                                Paper.book().write("sdt",Utils.user_current.getMobile());
                                Paper.book().write("username",Utils.user_current.getUsername());
                                Paper.book().write("iduser",Utils.user_current.getId());
                                Paper.book().write("iduser1",Utils.user_current.getId());
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công !" + Utils.user_current.getEmail(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                                startActivity(intent);
//                                finish();
                                onLoginclick(Utils.user_current);
                                Log.d("login","login" +userModels.getMessage()+"sdt: "+Utils.user_current.getId() +" dada: "+Paper.book().read("iduser1"));
                            }else{
                                Toast.makeText(getApplicationContext(), "Email hoặc Password không đúng !", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Hỏng chương trình", Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    public void onLoginclick(User user) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        //Truyen id
        intent.putExtra("id",user.getId());
        startActivity(intent);
        finish();
    }

    public void initView() {
        Paper.init(this);
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        edtusername = findViewById(R.id.edtUsername);
        edtpassword = findViewById(R.id.edtPassword);
        register = findViewById(R.id.tvBanchuacotaikhoan);
        forgotpass = findViewById(R.id.tvQuenmatkhau);
        btnLogin = findViewById(R.id.btnLogin);
        radioGroup = findViewById(R.id.grdlogin);
        rd1 = findViewById(R.id.rduser);
        rd2 = findViewById(R.id.rdnhahang);
        rd3 = findViewById(R.id.rdshipper);

        //doc du lieu tu paper
        if (Paper.book().read("email") != null && Paper.book().read("pass") != null) {
            edtusername.setText(Paper.book().read("email"));
            edtpassword.setText(Paper.book().read("pass"));
            if (Paper.book().read("islogin") != null){
                boolean fla = Paper.book().read("islogin");
                if(fla){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dangnhap(Paper.book().read("email"), Paper.book().read("pass"));
                        }
                    }, 1000);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Kiem tra
        if (Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null) {
            edtusername.setText(Utils.user_current.getEmail());
            edtpassword.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}