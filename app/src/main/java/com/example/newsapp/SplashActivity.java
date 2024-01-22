package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 3000; // Durasi splash screen dalam milidetik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Menggunakan Handler untuk menjalankan kode setelah jangka waktu tertentu
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kode yang dijalankan setelah splash timeout
                Intent mainIntent = new Intent(SplashActivity.this, OnboardingActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
