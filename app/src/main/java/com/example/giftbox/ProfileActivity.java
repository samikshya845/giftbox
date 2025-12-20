package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private LinearLayout rowLogout;
    LinearLayout rowChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_profile);

        // Views
        ImageView ivBack = findViewById(R.id.ivBack);
        ImageView ivEdit = findViewById(R.id.ivEdit);
        TextView tvName = findViewById(R.id.tvName);
        rowLogout = findViewById(R.id.rowLogout);

        rowChangePassword = findViewById(R.id.rowChangePassword);
        rowChangePassword.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, ChangepasswordActivity.class)));

        // Greeting text
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            tvName.setText("Hi, " + username);
        } else {
            tvName.setText("Hi, User");
        }

        // Back to homepage
        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, homepage.class);
            startActivity(intent);
            finish();
        });

        // Edit profile
        ivEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditprofileActivity.class);
            startActivity(intent);
        });

        // Logout row with confirmation dialog
        rowLogout.setOnClickListener(v -> showLogoutConfirmationDialog());
    }


    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you really want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> clearUserSession())
                .setNegativeButton("No", null)
                .show();
    }

    private void clearUserSession() {
        // TODO: clear SharedPreferences / tokens here if you use them

        Intent intent = new Intent(ProfileActivity.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
