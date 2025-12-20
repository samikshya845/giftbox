package com.example.giftbox;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ChangepasswordActivity extends AppCompatActivity {

    private TextInputLayout tilOldPassword, tilNewPassword, tilConfirmPassword;
    private TextInputEditText etOldPassword, etNewPassword, etConfirmPassword;
    @SuppressWarnings("FieldCanBeLocal")
    private MaterialButton btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        tilOldPassword = findViewById(R.id.tilOldPassword);
        tilNewPassword = findViewById(R.id.tilNewPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);

        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnChangePassword = findViewById(R.id.btnChangePassword);
        ImageView ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(v -> onBackPressed());

        btnChangePassword.setOnClickListener(v -> {
            String oldP = etOldPassword.getText() != null ? etOldPassword.getText().toString().trim() : "";
            String newP = etNewPassword.getText() != null ? etNewPassword.getText().toString().trim() : "";
            String confirmP = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

            tilOldPassword.setError(null);
            tilNewPassword.setError(null);
            tilConfirmPassword.setError(null);

            if (oldP.isEmpty()) {
                tilOldPassword.setError("Enter old password");
                return;
            }
            if (newP.isEmpty()) {
                tilNewPassword.setError("Enter new password");
                return;
            }
            if (newP.length() < 6) {
                tilNewPassword.setError("At least 6 characters");
                return;
            }
            if (!newP.equals(confirmP)) {
                tilConfirmPassword.setError("Passwords do not match");
                return;
            }

            // TODO: actually update password in backend / SharedPreferences
            Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
