package com.example.messmagic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //login();
    }

    public void loginClick(View view) {

        Intent mainIntent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(mainIntent);
        finish(); // Close the start screen activity
    }
}