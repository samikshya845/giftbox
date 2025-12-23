package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView backButton = findViewById(R.id.backButton);
        RadioGroup rgPayment = findViewById(R.id.rgPayment);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);



        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
            startActivity(intent);

        });



        btnPlaceOrder.setOnClickListener(v -> {
            String payment = "Cash on Delivery";
            int payId = rgPayment.getCheckedRadioButtonId();
            if (payId == R.id.rbKhalti) {
                payment = "Khalti";
            } else if (payId == R.id.rbEsewa) {
                payment = "eSewa";
            }
            Toast.makeText(this, "Order placed with: " + payment, Toast.LENGTH_SHORT).show();
        });
    }
}
