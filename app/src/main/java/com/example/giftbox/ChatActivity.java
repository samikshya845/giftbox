package com.example.giftbox;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    private EditText etMessage;
    private ImageButton btnSend;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_chat);

        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sending the message here
                String message = etMessage.getText().toString();
                etMessage.setText("");
            }
        });
    }
}
