package com.example.giftbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.giftbox.view.HomeActivity;
import com.google.android.material.button.MaterialButton;

public class CartActivity extends AppCompatActivity {

    private TextView subtotalText;
    private TextView totalText;

    private final int SHIPPING_FEE = 100;

    private TextView item1Quantity, item2Quantity, item3Quantity;
    private View item1Container, item2Container, item3Container;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // --- View Initialization ---
        item1Container = findViewById(R.id.item1Container);
        item2Container = findViewById(R.id.item2Container);
        item3Container = findViewById(R.id.item3Container);

        ImageView item1Delete = findViewById(R.id.item1Delete);
        ImageView item2Delete = findViewById(R.id.item2Delete);
        ImageView item3Delete = findViewById(R.id.item3Delete);

        MaterialButton checkoutButton = findViewById(R.id.btnProceed);
        ImageView backButton = findViewById(R.id.backButton);

        item1Quantity = findViewById(R.id.item1Quantity);
        item2Quantity = findViewById(R.id.item2Quantity);
        item3Quantity = findViewById(R.id.item3Quantity);

        ImageView item1Increase = findViewById(R.id.item1Increase);
        ImageView item1Decrease = findViewById(R.id.item1Decrease);
        ImageView item2Increase = findViewById(R.id.item2Increase);
        ImageView item2Decrease = findViewById(R.id.item2Decrease);
        ImageView item3Increase = findViewById(R.id.item3Increase);
        ImageView item3Decrease = findViewById(R.id.item3Decrease);

        subtotalText = findViewById(R.id.subtotalText);
        TextView shippingText = findViewById(R.id.shippingText);
        totalText = findViewById(R.id.totalText);

        shippingText.setText("NPR " + SHIPPING_FEE);

        // --- Listeners for Quantity ---
        item1Increase.setOnClickListener(v -> { changeQuantity(item1Quantity, +1); updateSummary(); });
        item1Decrease.setOnClickListener(v -> { changeQuantity(item1Quantity, -1); updateSummary(); });
        item2Increase.setOnClickListener(v -> { changeQuantity(item2Quantity, +1); updateSummary(); });
        item2Decrease.setOnClickListener(v -> { changeQuantity(item2Quantity, -1); updateSummary(); });
        item3Increase.setOnClickListener(v -> { changeQuantity(item3Quantity, +1); updateSummary(); });
        item3Decrease.setOnClickListener(v -> { changeQuantity(item3Quantity, -1); updateSummary(); });

        // --- Listeners for Deleting Items ---
        item1Delete.setOnClickListener(v -> { item1Container.setVisibility(View.GONE); updateSummary(); });
        item2Delete.setOnClickListener(v -> { item2Container.setVisibility(View.GONE); updateSummary(); });
        item3Delete.setOnClickListener(v -> { item3Container.setVisibility(View.GONE); updateSummary(); });

        // --- Listeners for Navigation ---
        backButton.setOnClickListener(v -> {
            // It's better practice to just finish the activity than to start a new one
            // if HomeActivity is the previous screen.
            finish();
        });

        checkoutButton.setOnClickListener(v -> {
            int subtotal = computeSubtotal();

            // This logic to build the summary is correct and preserved.
            int itemCount = 0;
            StringBuilder summaryBuilder = new StringBuilder();
            if (item1Container.getVisibility() == View.VISIBLE) {
                int q1 = Integer.parseInt(item1Quantity.getText().toString());
                itemCount += q1;
                summaryBuilder.append("• Premium gift box x").append(q1).append("\n");
            }
            if (item2Container.getVisibility() == View.VISIBLE) {
                int q2 = Integer.parseInt(item2Quantity.getText().toString());
                itemCount += q2;
                summaryBuilder.append("• Chocolate deluxe x").append(q2).append("\n");
            }
            if (item3Container.getVisibility() == View.VISIBLE) {
                int q3 = Integer.parseInt(item3Quantity.getText().toString());
                itemCount += q3;
                summaryBuilder.append("• Birthday celebration cake x").append(q3).append("\n");
            }
            String itemsSummary = summaryBuilder.toString().trim();

            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("subtotal", subtotal);
            intent.putExtra("shipping_fee", SHIPPING_FEE);
            intent.putExtra("total", subtotal + SHIPPING_FEE);
            intent.putExtra("item_count", itemCount);
            intent.putExtra("items_summary", itemsSummary);
            startActivity(intent);
        });

        // This is for handling system UI insets, it's correct.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initial summary calculation
        updateSummary();
    }

    private void changeQuantity(TextView qtyView, int delta) {
        int qty = Integer.parseInt(qtyView.getText().toString());
        qty += delta;
        if (qty < 1) qty = 1;
        qtyView.setText(String.valueOf(qty));
    }

    @SuppressLint("SetTextI18n")
    private void updateSummary() {
        // This method now just calls the calculation method.
        computeSubtotal();
    }

    @SuppressLint("SetTextI18n")
    private int computeSubtotal() {
        int subtotal = 0;

        // Prices for items
        final int PRICE_ITEM1 = 1499;
        final int PRICE_ITEM2 = 500;
        final int PRICE_ITEM3 = 850;

        if (item1Container.getVisibility() == View.VISIBLE) {
            subtotal += Integer.parseInt(item1Quantity.getText().toString()) * PRICE_ITEM1;
        }
        if (item2Container.getVisibility() == View.VISIBLE) {
            subtotal += Integer.parseInt(item2Quantity.getText().toString()) * PRICE_ITEM2;
        }
        if (item3Container.getVisibility() == View.VISIBLE) {
            subtotal += Integer.parseInt(item3Quantity.getText().toString()) * PRICE_ITEM3;
        }

        // Update UI Text
        int total = subtotal + SHIPPING_FEE;

        subtotalText.setText("NPR " + subtotal);
        totalText.setText("NPR " + total);

        // Return the subtotal to be passed in the intent
        return subtotal;
    }
}