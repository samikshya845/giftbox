package com.example.giftbox;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;

public class MyOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_my_orders);

        ImageView backButton = findViewById(R.id.back_button);
        
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyOrdersActivity.this, homepage.class);
            startActivity(intent);
            finish(); // close MyOrders so back does not return here
        });
    }
}

