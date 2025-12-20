package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class CartActivity extends AppCompatActivity {

    private TextInputLayout noteInputLayout;
    private TextView noteLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_cart2);

        // Initialize UI elements after setContentView
        CheckBox personalizedNoteCheckbox = findViewById(R.id.personalizedNoteCheckbox);
        noteInputLayout = findViewById(R.id.noteInputLayout);
        noteLabel = findViewById(R.id.noteLabel);
        MaterialButton checkoutButton = findViewById(R.id.checkoutButton);

        // Set up the checkbox listener
        personalizedNoteCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                noteLabel.setVisibility(View.VISIBLE);
                noteInputLayout.setVisibility(View.VISIBLE);
            } else {
                noteLabel.setVisibility(View.GONE);
                noteInputLayout.setVisibility(View.GONE);
            }
        });
        ImageView backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, homepage.class);
            startActivity(intent);
            finish();   // optional: close Cart so back doesn't return here
        });
        checkoutButton.setOnClickListener(v -> {
            // code that runs when button is clicked
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        // Fix the reference to the main view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}