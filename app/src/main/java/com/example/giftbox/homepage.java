package com.example.giftbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        ImageView ivNotification = findViewById(R.id.ivNotification);
        ivNotification.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, NotificationActivity.class);
            intent.putExtra("username", username); // optional
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        ImageView ivMore = findViewById(R.id.ivMore);
        ivMore.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, MoreActivity.class);
            intent.putExtra("username", username); // optional, if you need it
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        LinearLayout categoryBirthday    = findViewById(R.id.category_birthday);
        LinearLayout categoryAnniversary = findViewById(R.id.category_anniversary);
        LinearLayout categoryCorporate   = findViewById(R.id.category_corporate);
        LinearLayout categorySeasonal    = findViewById(R.id.category_seasonal);

        categoryBirthday.setOnClickListener(v -> {
            Intent i = new Intent(homepage.this, BirthdayCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        categoryAnniversary.setOnClickListener(v -> {
            Intent i = new Intent(homepage.this, AnniversaryCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        categoryCorporate.setOnClickListener(v -> {
            Intent i = new Intent(homepage.this, CorporateCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        categorySeasonal.setOnClickListener(v -> {
            Intent i = new Intent(homepage.this, SeasonalCategoryActivity.class);
            i.putExtra("username", username);
            startActivity(i);
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

            if (itemId == R.id.nav_chat) {
                Intent intent = new Intent(homepage.this, ChatActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }

            if (itemId == R.id.nav_orders) {
                Intent intent = new Intent(homepage.this, MyOrdersActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }

            if (itemId == R.id.nav_fav) {
                Intent intent = new Intent(homepage.this, FavouritesActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }
            return false;
        });
    }
}
