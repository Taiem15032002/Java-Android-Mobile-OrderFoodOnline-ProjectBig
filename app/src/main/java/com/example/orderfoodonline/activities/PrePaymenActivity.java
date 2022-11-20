package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;

import io.paperdb.Paper;

public class PrePaymenActivity extends AppCompatActivity {

    TextView tvuser, tvsdt, tvtongtienthanhtoan, back_container;
    RadioButton rd50per, rd100per;
    AppCompatButton btnMomo, btnZalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_paymen);
        Paper.init(this);
        initView();
        initControl();
    }

    private void initControl() {
        tvtongtienthanhtoan.setText("Tổng tiền: "+getIntent().getIntExtra("tongtien", 0) + " VND");
        String userN = Paper.book().read("username");
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
    }

    private void initView() {
        tvuser =findViewById(R.id.tv_tenUser);
        tvsdt = findViewById(R.id.tv_sdt);
        back_container = findViewById(R.id.back_container);
        tvtongtienthanhtoan = findViewById(R.id.tv_tongtienthanhtoan);
    }
}