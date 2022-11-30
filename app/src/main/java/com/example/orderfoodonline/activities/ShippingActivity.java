package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.TextView;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ShippingActivity extends AppCompatActivity {
    FoodAppApi foodAppApi;
    TextView tvsdt, tv_tratienmat, back_container;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AppCompatButton btnDanhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
    }
    private void initView() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        tvsdt = findViewById(R.id.tv_sdt);
        back_container = findViewById(R.id.back_container);
        tv_tratienmat = findViewById(R.id.tv_tratienmat);
        btnDanhan = findViewById(R.id.btndathang);
    }
    private void initControl(){
        tv_tratienmat.setText("Trả tiền mặt: "+getIntent().getIntExtra("tongtien", 0) + " VND");
        tvsdt.setText("SDT: "+ Paper.book().read("sdt"));
    }
}