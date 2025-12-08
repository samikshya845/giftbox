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

import java.util.Objects;

public class forgotpassword extends AppCompatActivity {

    private TextInputEditText etEmail;
    @SuppressWarnings("FieldCanBeLocal")
    private MaterialButton btnSendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_forgotpassword);


        etEmail = findViewById(R.id.etEmail);
        btnSendCode = findViewById(R.id.btnSendCode);


        findViewById(R.id.tvBackToLogin).setOnClickListener(v -> {
            startActivity(new Intent(forgotpassword.this, login.class));
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        // Send code button click listener - FIXED
        btnSendCode.setOnClickListener(v -> {
            String email = Objects.requireNonNull(etEmail.getText()).toString().trim();
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(forgotpassword.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(forgotpassword.this, "Code sent to " + email + "!", Toast.LENGTH_LONG).show();

            // Navigate to  verification activity
            Intent intent = new Intent(forgotpassword.this, VerificationActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
