package com.example.giftbox.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giftbox.R;

public class DeliveryAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_delivery_address);

        ImageView ivBackAddress = findViewById(R.id.ivBackAddress);
        ivBackAddress.setOnClickListener(v -> finish());

        Button btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        btnAddNewAddress.setOnClickListener(v -> {
            Intent intent = new Intent(DeliveryAddressActivity.this, AddAddressActivity.class);
            // optional: tell EditProfileActivity why it was opened
            intent.putExtra("from", "delivery_address");
            startActivity(intent);
        });

    }
}
