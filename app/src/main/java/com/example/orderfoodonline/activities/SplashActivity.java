package com.example.orderfoodonline.activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import com.example.orderfoodonline.R;
public class SplashActivity extends AppCompatActivity {
    //Splash Time
    int SPLASH_TIME_OUT = 3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
//        new Handler().postDelayed(() -> {
//            //Time out splash screen
//            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//            startActivity(intent);
//            finish();
//        },SPLASH_TIME_OUT);
    }
}