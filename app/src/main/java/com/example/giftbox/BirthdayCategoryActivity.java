package com.example.giftbox;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class BirthdayCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_birthday_category); // your XML above

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());   // go back to previous Activity
    }
}