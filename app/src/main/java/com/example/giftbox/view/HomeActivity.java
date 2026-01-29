package com.example.giftbox.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giftbox.AnniversaryCategoryActivity;
import com.example.giftbox.BirthdayCategoryActivity;
import com.example.giftbox.CartActivity;
import com.example.giftbox.CorporateCategoryActivity;
import com.example.giftbox.MoreActivity;
import com.example.giftbox.NotificationActivity;
import com.example.giftbox.R;
import com.example.giftbox.SeasonalCategoryActivity;
import com.example.giftbox.controllers.HomeController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_homepage);

        // Init controller
        HomeController homeController = new HomeController();

        // Get username from Intent
        String username = getIntent().getStringExtra("username");

        // Greeting
        TextView tvGreeting = findViewById(R.id.user_name);
        tvGreeting.setText(homeController.getGreetingText(username));

        // Profile image click
        ImageView imageViewProfile = findViewById(R.id.imageView);
        imageViewProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Notification icon click
        ImageView ivNotification = findViewById(R.id.ivNotification);
        ivNotification.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // More icon click
        //ImageView ivMore = findViewById(R.id.ivMore);
        //ivMore.setOnClickListener(v -> {
            //Intent intent = new Intent(HomeActivity.this, MoreActivity.class);
            //intent.putExtra("username", username);
            //startActivity(intent);
           // overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        //});

        // Category cards
        LinearLayout categoryBirthday = findViewById(R.id.category_birthday);
        LinearLayout categoryAnniversary = findViewById(R.id.category_anniversary);
        LinearLayout categoryCorporate = findViewById(R.id.category_corporate);
        LinearLayout categorySeasonal = findViewById(R.id.category_seasonal);

        categoryBirthday.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, BirthdayCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        categoryAnniversary.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, AnniversaryCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        categoryCorporate.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, CorporateCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        categorySeasonal.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, SeasonalCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        // Bottom navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true; // already here
            }

            if (itemId == R.id.nav_cart) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }

            if (itemId == R.id.nav_dot) {
                Intent intent = new Intent(HomeActivity.this, MoreActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }

            if (itemId == R.id.nav_orders) {
                Intent intent = new Intent(HomeActivity.this, MyOrdersActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }



            return false;
        });
    }
}
