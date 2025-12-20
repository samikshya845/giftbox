package com.example.giftbox;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TermsandConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termsand_conditions);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ImageView ivBackTerms = findViewById(R.id.ivBackTerms);
        ivBackTerms.setOnClickListener(v -> onBackPressed());

    }
}