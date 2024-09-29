package com.example.messmagic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the specified time (e.g., 3 seconds).
                Intent mainIntent = new Intent(SplashScreen.this, StartActivity.class);
                startActivity(mainIntent);
                finish(); // Close the splash screen activity
            }
        }, 1500);
    }
}