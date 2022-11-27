package com.example.orderfoodonline.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.adapters.RamenAdapter;
import com.example.orderfoodonline.databinding.ActivityAddProductBinding;
import com.example.orderfoodonline.models.AddFoodModels;
import com.example.orderfoodonline.models.Ramen;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    Spinner spLoaiSP;
    AppCompatActivity btnThemsp;
    int loai = 0;
    ActivityAddProductBinding binding;
    FoodAppApi foodAppApi;
    String mediaPath;
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
        binding.imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddProductActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
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
                                    Toast.makeText(getApplicationContext(), "them thanh cong", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getApplicationContext(), "them that bai", Toast.LENGTH_SHORT).show();
                                }
                            }, throwable -> {
                                Toast.makeText(getApplicationContext(), "hong chuong chinh", Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }
    ///Lay duong dan that cua Image
    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri,null,null,null, null);
        if (cursor == null){
            result = uri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }
    // Uploading Image/Video
    private void uploadMultipleFiles() {
        Uri uri = Uri.parse(mediaPath);

        File file = new File(getPath(uri));
        // Parsing any Media type file

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Call<AddFoodModels> call = foodAppApi.uploadFile(fileToUpload);
        call.enqueue(new Callback<AddFoodModels>() {
            @Override
            public void onResponse(Call<AddFoodModels> call, Response<AddFoodModels> response) {
                AddFoodModels serverResponse = response.body();
                Log.d("testimage","Trong call"+ Utils.hinh +serverResponse.getName());
                Log.d("testimage","Trong call"+mediaPath);
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                       binding.edtHinhAnh.setText(serverResponse.getName());
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("testimage","onAct"+mediaPath);
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    assert serverResponse != null;
                    Log.d("Response",   serverResponse.getName());
                    Log.d("testimage","qua sai"+mediaPath);
                }
            }
            @Override
            public void onFailure(Call<AddFoodModels> call, Throwable t) {
                Log.d("log", t.getMessage());
            }
        });
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