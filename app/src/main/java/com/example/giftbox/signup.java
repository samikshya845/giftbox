package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class signup extends AppCompatActivity {

    private TextInputLayout tilName, tilEmail, tilPassword, tilConfirmPassword;
    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    @SuppressWarnings("FieldCanBeLocal")
    private MaterialButton btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_signup);

        // Fixed IDs - now matches your XML
        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnSignup = findViewById(R.id.btnSignup);

        // ADD THIS RIGHT AFTER findViewById() lines
        tilName.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error);
        tilEmail.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error);
        tilPassword.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error);
        tilConfirmPassword.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error);

        tilName.setErrorTextColor(androidx.core.content.ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        tilEmail.setErrorTextColor(androidx.core.content.ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        tilPassword.setErrorTextColor(androidx.core.content.ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        tilConfirmPassword.setErrorTextColor(androidx.core.content.ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        // Back to Login
        findViewById(R.id.textView).setOnClickListener(v -> {
            startActivity(new Intent(signup.this, login.class));
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Sign Up Button
        btnSignup.setOnClickListener(v -> {
            if (validateSignup()) {
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(signup.this, homepage.class));
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validateSignup() {
        String name = Objects.requireNonNull(etName.getText()).toString().trim();
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();
        String confirm = Objects.requireNonNull(etConfirmPassword.getText()).toString().trim();

        // Clear old errors
        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);

        tilConfirmPassword.setError(null);

        if (name.isEmpty()) {
            tilName.setError("Name cannot be empty");
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty()) {
            tilEmail.setError("Email cannot be empty");
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Please enter a valid email");
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            tilPassword.setError("Password cannot be empty");
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirm.isEmpty()) {
            tilConfirmPassword.setError("Please confirm your password");
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirm.equals(password)) {
            tilConfirmPassword.setError("Passwords do not match");
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}