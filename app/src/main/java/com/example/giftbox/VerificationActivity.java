package com.example.giftbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;

public class VerificationActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_verification);
        ImageView backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(v -> {
            finish(); // This closes current activity and returns to previous one
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Find the Verify button from your OTP layout
        MaterialButton verifyButton = findViewById(R.id.verifyButton);

        // Set click listener to launch Reset Password screen
        verifyButton.setOnClickListener(v -> {
            String enteredOtp = getEnteredOtp();

            if (isOtpValid(enteredOtp)) {
                // Launch Reset Password Activity
                Intent intent = new Intent(VerificationActivity.this, ResetPasswordActivity.class);
                // Pass email from previous screen if needed
                String email = getIntent().getStringExtra("email");
                if (email != null) {
                    intent.putExtra("email", email);
                }
                startActivity(intent);
            } else {
                // Show error for invalid OTP
                Toast.makeText(VerificationActivity.this,
                        "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getEnteredOtp() {
        // Get text from all 6 OTP EditText fields
        // You'll need to findViewById for otp1, otp2, otp3, otp4, otp5, otp6
        return ""; // Replace with actual OTP collection logic
    }

    private boolean isOtpValid(String otp) {
        // Replace with your actual OTP validation logic
        // For now, returns true if OTP has 6 digits
        return otp.length() == 6 && otp.matches("\\d+");
    }
}
