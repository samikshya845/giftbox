package com.example.giftbox.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giftbox.R;
import com.example.giftbox.controllers.LoginController;
import com.example.giftbox.forgotpassword;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    @SuppressWarnings("FieldCanBeLocal")
    private MaterialButton btnLogin;

    // Controller instance
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);

        // Init views
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Init controller
        loginController = new LoginController();

        // Sign Up
        findViewById(R.id.tvSignup).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Forgot Password
        findViewById(R.id.forgotPassword).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, forgotpassword.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        // Login Button
        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

        // Clear previous errors
        tilEmail.setError(null);
        tilPassword.setError(null);

        // Ask controller to validate
        LoginController.LoginError error = loginController.validateCredentials(email, password);

        switch (error) {
            case EMPTY_EMAIL:
                tilEmail.setError("Email cannot be empty");
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                etEmail.requestFocus();
                break;

            case INVALID_EMAIL:
                tilEmail.setError("Please enter a valid email address");
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                etEmail.requestFocus();
                break;

            case EMPTY_PASSWORD:
                tilPassword.setError("Password cannot be empty");
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                etPassword.requestFocus();
                break;

            case SHORT_PASSWORD:
                tilPassword.setError("Password must be at least 6 characters");
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                etPassword.requestFocus();
                break;

            case NONE:

                String username = email.split("@")[0];

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;
        }
    }
}
