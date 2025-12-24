package com.example.giftbox;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CheckoutActivity extends AppCompatActivity {

    private TextInputEditText edtFullName, edtPhone, edtAddress, edtDeliveryDate;
    private RadioGroup rgPayment;
    private RadioButton rbEsewa, rbCod;

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

        // start with no method selected (or set rbCod.setChecked(true) if you want default)
        rbEsewa.setChecked(false);
        rbCod.setChecked(false);

        // single-click select, second click unselect, only one at a time
        esewaRow.setOnClickListener(v -> {
            boolean newState = !rbEsewa.isChecked();   // toggle
            rbEsewa.setChecked(newState);
            if (newState) {
                rbCod.setChecked(false);               // ensure only one is on
            }
        });

        codRow.setOnClickListener(v -> {
            boolean newState = !rbCod.isChecked();     // toggle
            rbCod.setChecked(newState);
            if (newState) {
                rbEsewa.setChecked(false);
            }
        });

        // Back
        backButton.setOnClickListener(v -> onBackPressed());

        // Date picker
        edtDeliveryDate.setOnClickListener(v -> showDatePicker());
        edtDeliveryDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });


        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (DatePicker view, int y, int m, int d) -> {
                    String date = String.format("%04d-%02d-%02d", y, m + 1, d);
                    edtDeliveryDate.setText(date);
                },
                year, month, day
        );

        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();
    }

    private void placeOrder() {
        String name = edtFullName.getText() != null ? edtFullName.getText().toString().trim() : "";
        String phone = edtPhone.getText() != null ? edtPhone.getText().toString().trim() : "";
        String address = edtAddress.getText() != null ? edtAddress.getText().toString().trim() : "";
        String date = edtDeliveryDate.getText() != null ? edtDeliveryDate.getText().toString().trim() : "";

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all delivery details", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = rgPayment.getCheckedRadioButtonId();
        boolean esewaChecked = rbEsewa.isChecked();
        boolean codChecked   = rbCod.isChecked();
        if (!esewaChecked && !codChecked) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }


        String payment;
        if (selectedId == R.id.rbEsewa) {
            payment = "eSewa";
        }
      else  {
            payment = "Cash on Delivery";
        }

        String msg = "Order placed for " + date + " via " + payment;
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
