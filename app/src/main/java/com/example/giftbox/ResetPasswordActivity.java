package com.example.giftbox;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPasswordActivity extends AppCompatActivity {
    private TextInputLayout newPasswordLayout, confirmPasswordLayout;
    private TextInputEditText newPasswordEditText, confirmPasswordEditText;
    @SuppressWarnings("FieldCanBeLocal")
    private MaterialButton resetButton;
    @SuppressWarnings("FieldCanBeLocal")
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        newPasswordLayout = findViewById(R.id.newPasswordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        newPasswordEditText = findViewById(R.id.newPassword);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        resetButton = findViewById(R.id.resetButton);
        backArrow = findViewById(R.id.backArrow);


        backArrow.setOnClickListener(v -> onBackPressed());




        resetButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // TODO: actually call your API / Firebase / DB here to update password
                Toast.makeText(ResetPasswordActivity.this,
                        "Password reset successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean validateInputs() {
        String newPass = newPasswordEditText.getText() != null
                ? newPasswordEditText.getText().toString().trim() : "";
        String confirmPass = confirmPasswordEditText.getText() != null
                ? confirmPasswordEditText.getText().toString().trim() : "";


        newPasswordLayout.setError(null);
        confirmPasswordLayout.setError(null);


        if (TextUtils.isEmpty(newPass)) {
            newPasswordLayout.setError("Please enter new password");
            newPasswordLayout.requestFocus();
            return false;
        }
        if (newPass.length() < 8) {
            newPasswordLayout.setError("Password must be at least 8 characters");
            newPasswordLayout.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(confirmPass)) {
            confirmPasswordLayout.setError("Please confirm your password");
            confirmPasswordLayout.requestFocus();
            return false;
        }
        if (!newPass.equals(confirmPass)) {
            confirmPasswordLayout.setError("Passwords do not match");
            confirmPasswordLayout.requestFocus();
            return false;
        }

        return true;
    }
}
