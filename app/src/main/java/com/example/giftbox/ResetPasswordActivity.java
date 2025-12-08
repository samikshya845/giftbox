package com.example.giftbox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {
    private TextInputEditText newPassword, confirmPassword;


    private TextInputLayout newPasswordLayout, confirmPasswordLayout;
    private TextView passwordStrength;
    private MaterialButton resetButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_reset_password);

        // Initialize views
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        newPasswordLayout = findViewById(R.id.newPasswordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        passwordStrength = findViewById(R.id.passwordStrength);
        resetButton = findViewById(R.id.resetButton);

        // Real-time validation
        newPassword.addTextChangedListener(new PasswordValidator());
        confirmPassword.addTextChangedListener(new PasswordValidator());

        resetButton.setOnClickListener(v -> validateAndReset());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private class PasswordValidator implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            String pass1 = Objects.requireNonNull(newPassword.getText()).toString();
            String pass2 = confirmPassword.getText().toString();

            if (pass1.length() < 8) {
                passwordStrength.setText("Weak - At least 8 characters required");
                passwordStrength.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else if (!pass1.equals(pass2)) {
                confirmPasswordLayout.setError("Passwords do not match");
            } else {
                passwordStrength.setText("Strong password");
                passwordStrength.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                confirmPasswordLayout.setError(null);
            }
        }
    }

    private void validateAndReset() {
        String pass1 = newPassword.getText().toString();
        String pass2 = confirmPassword.getText().toString();

        if (pass1.length() < 8 || !pass1.equals(pass2)) {
            Toast.makeText(this, "Please enter matching strong passwords", Toast.LENGTH_SHORT).show();
            return;
        }

        // API call to reset password here
        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
        // Navigate to LoginActivity
        finish();
    }
}
