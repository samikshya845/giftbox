package com.example.giftbox;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FaqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faq); // this XML you posted

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        View main = findViewById(R.id.main_faqs);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView ivBackFaqs = findViewById(R.id.ivBackFaqs);
        ivBackFaqs.setOnClickListener(v -> onBackPressed());

        TextView tvQ1 = findViewById(R.id.tvQ1);
        TextView tvA1 = findViewById(R.id.tvA1);
        tvQ1.setOnClickListener(v -> toggleVisibility(tvA1));

        TextView tvQ2 = findViewById(R.id.tvQ2);
        TextView tvA2 = findViewById(R.id.tvA2);
        tvQ2.setOnClickListener(v -> toggleVisibility(tvA2));
    }

    private void toggleVisibility(View answerView) {
        answerView.setVisibility(
                answerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
        );
    }
}
