package com.example.giftbox.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giftbox.R;

public class AddAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_add_address);

        // Back arrow
        ImageView ivBackAddAddress = findViewById(R.id.ivBackAddAddress);
        ivBackAddAddress.setOnClickListener(v -> finish());

        // ADD button (frontend only)
        Button btnSaveAddress = findViewById(R.id.btnSaveAddress);
        btnSaveAddress.setOnClickListener(v -> {
            // Here you could read EditTexts, but for now just show a message
            Toast.makeText(
                    AddAddressActivity.this,
                    "Address added for your gifts!",
                    Toast.LENGTH_SHORT
            ).show();
            finish();   // go back to DeliveryAddressActivity
        });
    }
}
