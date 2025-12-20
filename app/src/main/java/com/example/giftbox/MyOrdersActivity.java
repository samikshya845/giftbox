package com.example.giftbox;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
public class MyOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ImageView backButton = findViewById(R.id.back_button);
        
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyOrdersActivity.this, homepage.class);
            startActivity(intent);
            finish(); // close MyOrders so back does not return here
        });
    }
}

