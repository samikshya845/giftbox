package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_checkout);
        ImageView backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
            startActivity(intent);
            finish();   // optional: so Back does not return to checkout
        });

    }
}



