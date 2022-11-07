package com.example.orderfoodonline.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.orderfoodonline.R;

public class SettingActivity extends AppCompatActivity {

    public TextView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Anhxa();
        testchuyenhuong();
    }

    private void testchuyenhuong() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Anhxa() {
        btnLogout = findViewById(R.id.btnlogout);
    }

}