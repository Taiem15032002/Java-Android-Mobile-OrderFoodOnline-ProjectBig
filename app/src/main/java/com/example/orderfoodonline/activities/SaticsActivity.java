package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.models.ThongKe;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SaticsActivity extends AppCompatActivity {
    PieChart pieChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodAppApi foodAppApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satics);
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
        initView();
        datachar();
    }

    private void datachar() {
        List<PieEntry> thongKeList = new ArrayList<>();
        compositeDisposable.add(foodAppApi.getThongke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModels -> {
                            if (thongKeModels.isSuccess()){
                                for (int i = 0; i<thongKeModels.getResult().size(); i++){
                                    String tensp = thongKeModels.getResult().get(i).getNamefood();
                                    int tong = thongKeModels.getResult().get(i).getTong();
                                    thongKeList.add(new PieEntry(tong,tensp));
                                }
                                PieDataSet pieDataSet = new PieDataSet(thongKeList, "Thống kê");
                                PieData pieData = new PieData();
                                pieData.setDataSet(pieDataSet);
                                pieData.setValueTextSize(15f);
                                pieData.setValueTextColor(0);
                                pieData.setDrawValues(true);
                                pieData.setValueFormatter(new PercentFormatter());
                                pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                                pieChart.setData(pieData);
                                pieChart.animateXY(2000,2000);
                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.invalidate();
                                pieChart.setEntryLabelColor(Color.BLACK);
                                pieChart.setEntryLabelTextSize(10);
                            }
                        }
                ));
    }

    private void initView() {
        pieChart = findViewById(R.id.pie);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}