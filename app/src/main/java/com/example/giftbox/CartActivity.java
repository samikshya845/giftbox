package com.example.giftbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CartActivity extends AppCompatActivity {

    private TextInputLayout noteInputLayout;
    private TextInputEditText etPersonalisedNote;
    private RadioGroup rgNote;
    private CheckBox giftWrappingCheckbox;
    private LinearLayout layoutGiftWrapOptions;
    private RadioGroup rgGiftWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_cart2);


        rgNote = findViewById(R.id.rgNote);
        noteInputLayout = findViewById(R.id.tilPersonalisedNote);
        etPersonalisedNote = findViewById(R.id.etPersonalisedNote);


        giftWrappingCheckbox = findViewById(R.id.giftWrappingCheckbox);
        layoutGiftWrapOptions = findViewById(R.id.layoutGiftWrapOptions);
        rgGiftWrap = findViewById(R.id.rgGiftWrap);


        MaterialButton checkoutButton = findViewById(R.id.checkoutButton);
        ImageView backButton = findViewById(R.id.backButton);


        rgNote.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPersonalisedNote) {
                noteInputLayout.setVisibility(View.VISIBLE);
            } else {
                noteInputLayout.setVisibility(View.GONE);
                etPersonalisedNote.setText("");
            }
        });


        giftWrappingCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            layoutGiftWrapOptions.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (!isChecked) {
                rgGiftWrap.clearCheck();
            }
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, homepage.class);
            startActivity(intent);
            finish();
        });

        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
