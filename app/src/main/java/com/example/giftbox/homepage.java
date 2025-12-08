package com.example.giftbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homepage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_homepage);



        TextView tvGreeting = findViewById(R.id.user_name);
        String username = getIntent().getStringExtra("username");

        if (username != null && !username.isEmpty()) {
            tvGreeting.setText("Hi, " + username + "!");
        } else {
            tvGreeting.setText("Hi there!");
        }


        ImageView imageViewProfile = findViewById(R.id.imageView);
        imageViewProfile.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, ProfileActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });


        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true;
            }

            if (itemId == R.id.nav_cart) {
                Intent intent = new Intent(homepage.this, CartActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }



            return false;
        });
    }
}