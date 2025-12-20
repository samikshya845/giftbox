package com.example.giftbox;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView ivBack = findViewById(R.id.ivBackFavourites);
        ivBack.setOnClickListener(v -> onBackPressed());
    }
}
