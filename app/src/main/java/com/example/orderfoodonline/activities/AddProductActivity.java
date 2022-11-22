package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AndroidException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.databinding.ActivityAddProductBinding;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddProductActivity extends AppCompatActivity {

    Spinner spLoaiSP;
    AppCompatActivity btnThemsp;
    EditText edtTenSP, edtGia, edtHinhAnh, edtMota;
    int loai = 0;
    ActivityAddProductBinding binding;
    FoodAppApi foodAppApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        setContentView(binding.getRoot());
        initview();
        initData();
        initControl();
    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Chọn loại Mì Nhật");
        stringList.add("Ramen");
        stringList.add("Udon");
        stringList.add("Soba");
        stringList.add("Shirataki");
        stringList.add("Somen");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, io.paperdb.R.layout.support_simple_spinner_dropdown_item, stringList);
        spLoaiSP.setAdapter(stringArrayAdapter);
        spLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnThemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themmonan(v);
            }
        });

    }

    private void themmonan(View v) {
        String str_tensp = binding.edtTenmonan.getText().toString().trim();
        String str_gia = binding.edtGiamonan.getText().toString().trim();
        String str_hinhanh = binding.edtHinhAnh.getText().toString().trim();
        String str_mota = binding.edtmota.getText().toString().trim();
        if (TextUtils.isEmpty(str_tensp) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_hinhanh) || TextUtils.isEmpty(str_mota) || loai == 0) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getRootView().getContext());
            builder.setTitle("Thông báo");
            builder.setMessage("Vui lòng nhập đầy đủ thông tin!");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            compositeDisposable.add(foodAppApi.themSp(str_tensp, str_hinhanh, str_gia, str_mota, (loai), spLoaiSP.getSelectedItem().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            addFoodModels -> {
                                if(addFoodModels.isSuccess()){
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
                                    builder.setTitle("Thông báo");
                                    builder.setMessage("Thêm món ăn Thành công!");
                                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                }else{
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
                                    builder.setTitle("Thông báo");
                                    builder.setMessage("Thêm món ăn thất bại!");
                                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                }
                            }, throwable -> {
                            }
                    ));
        }
    }

    private void initControl() {
    }

    private void initview() {
        spLoaiSP = findViewById(R.id.spLoai);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}