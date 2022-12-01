package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.orderfoodonline.R;
import com.github.mikephil.charting.charts.PieChart;

public class ThongKeActivity extends AppCompatActivity {
PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        pieChart = findViewById(R.id.pie);
    }
}