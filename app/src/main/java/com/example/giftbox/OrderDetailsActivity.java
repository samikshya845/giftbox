package com.example.giftbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.giftbox.view.HomeActivity;
import com.google.android.material.button.MaterialButton;

public class OrderDetailsActivity extends AppCompatActivity {

    private ImageView backButton;
    private MaterialButton btnBackHome;

    private TextView txtOrderId;
    private TextView txtOrderDate;
    private TextView txtOrderStatus;

    private TextView txtItemsList;
    private TextView txtSubtotal;
    private TextView txtDeliveryFee;
    private TextView txtTotalPaid;

    private TextView txtDeliveryName;
    private TextView txtDeliveryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_order_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        readIntentData();
        setupClicks();
    }

    private void initViews() {
        backButton   = findViewById(R.id.backButton);
        btnBackHome  = findViewById(R.id.btnBackHome);

        txtOrderId     = findViewById(R.id.txtOrderId);
        txtOrderDate   = findViewById(R.id.txtOrderDate);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);

        txtItemsList   = findViewById(R.id.txtItemsList);
        txtSubtotal    = findViewById(R.id.txtSubtotal);
        txtDeliveryFee = findViewById(R.id.txtDeliveryFee);
        txtTotalPaid   = findViewById(R.id.txtTotalPaid);

        txtDeliveryName    = findViewById(R.id.txtDeliveryName);
        txtDeliveryAddress = findViewById(R.id.txtDeliveryAddress);
    }

    @SuppressLint("SetTextI18n")
    private void readIntentData() {
        Intent intent = getIntent();
        if (intent == null) return;

        String orderId        = intent.getStringExtra("order_id");
        String orderDate      = intent.getStringExtra("order_date");
        String itemsList      = intent.getStringExtra("items_list");
        String totalPaid      = intent.getStringExtra("total_paid");
        String deliveryBlock  = intent.getStringExtra("delivery_address");

        String subtotalStr    = intent.getStringExtra("subtotal_text");
        String deliveryFeeStr = intent.getStringExtra("delivery_fee_text");
        String paymentStatus  = intent.getStringExtra("payment_status");
        String deliveryName   = intent.getStringExtra("delivery_name");
        intent.putExtra("delivery_fee", 100);
        if (orderId != null && !orderId.isEmpty()) {
            txtOrderId.setText(orderId);
        }
        if (orderDate != null && !orderDate.isEmpty()) {
            txtOrderDate.setText(orderDate);
        }

        if (paymentStatus != null && !paymentStatus.isEmpty()) {
            txtOrderStatus.setText("Status: " + paymentStatus);
        } else {
            txtOrderStatus.setText("Status: Confirmed");
        }

        if (itemsList != null && !itemsList.isEmpty()) {
            txtItemsList.setText(itemsList);
        }


        if (subtotalStr != null && !subtotalStr.isEmpty()) {
            txtSubtotal.setText(subtotalStr);
        } else if (totalPaid != null && !totalPaid.isEmpty()) {

            txtSubtotal.setText(totalPaid);
        }

        if (totalPaid != null && !totalPaid.isEmpty()) {
            txtTotalPaid.setText(totalPaid);
        }

        if (deliveryFeeStr != null && !deliveryFeeStr.isEmpty()) {
            txtDeliveryFee.setText(deliveryFeeStr);
        }

        if (deliveryName != null && !deliveryName.isEmpty()) {
            txtDeliveryName.setText(deliveryName);
        }
        if (deliveryBlock != null && !deliveryBlock.isEmpty()) {
            txtDeliveryAddress.setText(deliveryBlock);
        }
    }

    private void setupClicks() {
        backButton.setOnClickListener(v -> finish());

        btnBackHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(OrderDetailsActivity.this, HomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();
        });
    }
}