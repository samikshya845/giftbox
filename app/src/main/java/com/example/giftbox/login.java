package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class login extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    @SuppressWarnings("FieldCanBeLocal")
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);


        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Sign Up
        findViewById(R.id.tvSignup).setOnClickListener(v -> {
            startActivity(new Intent(login.this, signup.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Forgot Password
        findViewById(R.id.forgotPassword).setOnClickListener(v -> {
            startActivity(new Intent(login.this, forgotpassword.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Login Button

                btnLogin.setOnClickListener(v -> {
                    if (validateLogin()) {
                        // SUCCESS MESSAGE WITH GREEN CHECKMARK
                        Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_LONG).show();

                        // Extract username from email
                        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
                        String username = email.split("@")[0];

                        // Go to homepage with username
                        Intent intent = new Intent(login.this, homepage.class);
                        intent.putExtra("username", username);
                        startActivity(intent);

                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        finish();
                    }
                });
    }

    private boolean validateLogin() {
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

        tilEmail.setError(null);
        tilPassword.setError(null);

        if (email.isEmpty()) {
            tilEmail.setError("Email cannot be empty");
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Please enter a valid email address");
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            tilPassword.setError("Password cannot be empty");
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }

        return true; // Login successful!
    }
}