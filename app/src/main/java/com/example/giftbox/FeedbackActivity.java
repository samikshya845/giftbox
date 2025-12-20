package com.example.giftbox;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView ivBackFeedback = findViewById(R.id.ivBackFeedback);
        ivBackFeedback.setOnClickListener(v -> onBackPressed());




    }
}
