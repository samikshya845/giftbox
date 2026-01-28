package com.example.giftbox;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private TextInputEditText edtFullName, edtPhone, edtAddress, edtDeliveryDate;
    private RadioGroup rgPayment;
    private RadioButton rbEsewa, rbCod;

    // summary info from CartActivity
    private TextView tvSubtotalLabel, tvSubtotal, tvDeliveryFee, tvTotal;
    private int subtotal, shippingFee, total, itemCount;
    private String itemsSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Views
        ImageView backButton = findViewById(R.id.backButton);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtDeliveryDate = findViewById(R.id.edtDeliveryDate);
        rgPayment = findViewById(R.id.rgPayment);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        rbEsewa = findViewById(R.id.rbEsewa);
        rbCod  = findViewById(R.id.rbCod);

        LinearLayout esewaRow = findViewById(R.id.esewaRow);
        LinearLayout codRow   = findViewById(R.id.codRow);

        // order summary views
        tvSubtotalLabel = findViewById(R.id.tvSubtotalLabel);
        tvSubtotal      = findViewById(R.id.tvSubtotal);
        tvDeliveryFee   = findViewById(R.id.tvDeliveryFee);
        tvTotal         = findViewById(R.id.tvTotal);

        // ==== get same numbers that CartActivity calculated ====
        Intent in = getIntent();
        subtotal     = in.getIntExtra("subtotal", 0);
        shippingFee  = in.getIntExtra("shipping_fee", 0);
        total        = in.getIntExtra("total", 0);
        itemCount    = in.getIntExtra("item_count", 0);
        itemsSummary = in.getStringExtra("items_summary");

        updateSummaryUI();

        // start with no method selected (or set rbCod.setChecked(true) if you want default)
        rbEsewa.setChecked(false);
        rbCod.setChecked(false);

        // payment selection rows
        esewaRow.setOnClickListener(v -> {
            boolean newState = !rbEsewa.isChecked();
            rbEsewa.setChecked(newState);
            if (newState) rbCod.setChecked(false);
        });

        codRow.setOnClickListener(v -> {
            boolean newState = !rbCod.isChecked();
            rbCod.setChecked(newState);
            if (newState) rbEsewa.setChecked(false);
        });

        // Back
        backButton.setOnClickListener(v -> onBackPressed());

        // Date picker
        edtDeliveryDate.setOnClickListener(v -> showDatePicker());
        edtDeliveryDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });

        btnPlaceOrder.setOnClickListener(v -> {
            if (!placeOrder()) return;   // keep your validation

            Intent intent = buildOrderSuccessIntent();
            startActivity(intent);
            // IMPORTANT: prevent going back to checkout -> optional
            // finish();
        });

    }

    private void updateSummaryUI() {
        String label = "Subtotal (" + itemCount + " items)";
        tvSubtotalLabel.setText(label);

        tvSubtotal.setText("NPR." + subtotal);
        tvDeliveryFee.setText("NPR." + shippingFee);
        tvTotal.setText("NPR." + total);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (DatePicker view, int y, int m, int d) -> {
                    String date = String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d);
                    edtDeliveryDate.setText(date);
                },
                year, month, day
        );

        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();
    }

    private boolean placeOrder() {
        String name = edtFullName.getText() != null ? edtFullName.getText().toString().trim() : "";
        String phone = edtPhone.getText() != null ? edtPhone.getText().toString().trim() : "";
        String address = edtAddress.getText() != null ? edtAddress.getText().toString().trim() : "";
        String date = edtDeliveryDate.getText() != null ? edtDeliveryDate.getText().toString().trim() : "";

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all delivery details", Toast.LENGTH_SHORT).show();
            return false;
        }

        int selectedId = rgPayment.getCheckedRadioButtonId();
        boolean esewaChecked = rbEsewa.isChecked();
        boolean codChecked   = rbCod.isChecked();
        if (!esewaChecked && !codChecked) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return false;
        }

        String payment;
        if (selectedId == R.id.rbEsewa) {
            payment = "eSewa";
        } else {
            payment = "Cash on Delivery";
        }

        String msg = "Order placed for " + date + " via " + payment;
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        return true;
    }
    private String getPaymentStatus() {
        int selectedId = rgPayment.getCheckedRadioButtonId();
        if (selectedId == R.id.rbEsewa) {
            return "Paid";      // eSewa
        } else {
            return "Pending";   // Cash on Delivery
        }
    }

    private Intent buildOrderSuccessIntent() {
        String currentOrderId = "#GBX12345";

        String formattedDate = edtDeliveryDate.getText() != null
                ? edtDeliveryDate.getText().toString().trim()
                : "";

        String deliveryName = edtFullName.getText() != null
                ? edtFullName.getText().toString().trim()
                : "";

        String deliveryAddress = edtAddress.getText() != null
                ? edtAddress.getText().toString().trim()
                : "";

        String deliveryPhone = edtPhone.getText() != null
                ? edtPhone.getText().toString().trim()
                : "";

        String subtotalText    = "NPR " + subtotal;
        String deliveryFeeText = "NPR " + shippingFee;
        String totalText       = String.format(Locale.US, "NPR %,d", total);
        String paymentStatus   = getPaymentStatus();

        Intent intent = new Intent(CheckoutActivity.this, OrdersucessActivity.class);
        intent.putExtra("order_id", currentOrderId);
        intent.putExtra("order_date", formattedDate);

        // IMPORTANT: send the summary and delivery info
        intent.putExtra("total_paid",
                String.format(Locale.US, "NPR %,.2f", (double) total));
        intent.putExtra("items_summary", itemsSummary);         // uses 'itemsSummary' from Cart
        intent.putExtra("delivery_name", deliveryName);
        intent.putExtra("delivery_address", deliveryAddress);
        intent.putExtra("delivery_phone", deliveryPhone);

        return intent;
    }



}
