package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        MaterialButton btnSignup = findViewById(R.id.btnSignup);
        TextView tvSkip = findViewById(R.id.tvSkip);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, login.class));
        });

        btnSignup.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, signup.class));
        });

        tvSkip.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, homepage.class));
        });
    }
}
