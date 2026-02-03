package com.example.giftbox;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.giftbox.view.AddAddressActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = "CheckoutActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    // Constants for fees
    private final int NOTE_FEE = 100;
    private final int GIFT_WRAP_FEE = 100;

    // Geolocation Variables
    private FusedLocationProviderClient fusedLocationClient;
    private double deliveryLat = 0.0;
    private double deliveryLng = 0.0;

    // UI and Data Variables
    private TextInputEditText edtFullName, edtPhone, edtDeliveryDate;
    private RadioGroup rgPayment;
    private TextView tvSubtotalLabel, tvSubtotal, tvDeliveryFee, tvTotal;
    private int subtotal, shippingFee, total, itemCount;
    private String itemsSummary;
    private LinearLayout layoutAddAddress, layoutSelectedAddress;
    private TextView tvSelectedAddress, tvChangeAddress;
    private String savedAddressTitle = "";
    private String savedCustomerName = "";
    private String savedDetailAddress = "";
    private String savedPhone = "";

    // Note and Gift Wrapping Variables
    private RadioGroup rgNote;
    private TextInputLayout noteInputLayout;
    private TextInputEditText etPersonalisedNote;
    private CheckBox giftWrappingCheckbox;
    private int initialSubtotal;
    private int initialTotal;

    /**
     * This launcher starts the AddAddressActivity and waits for the complete address
     * details to be sent back.
     */
    private final ActivityResultLauncher<Intent> addAddressLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                    savedAddressTitle = result.getData().getStringExtra("addressTitle");
                    savedCustomerName = result.getData().getStringExtra("customerName");
                    savedDetailAddress = result.getData().getStringExtra("detailAddress");
                    savedPhone = result.getData().getStringExtra("phone");

                    // Update the UI with the new text-based address.
                    updateAddressDisplay();

                    // Convert the final address string into coordinates for the backend.
                    getCoordinatesFromAddress(savedDetailAddress);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize the location client.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Prompt for location permission on startup if it's not already granted.
        checkLocationPermission();

        // --- Standard View Initialization ---
        ImageView backButton = findViewById(R.id.backButton);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhone = findViewById(R.id.edtPhone);
        edtDeliveryDate = findViewById(R.id.edtDeliveryDate);
        rgPayment = findViewById(R.id.rgPayment);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        LinearLayout esewaRow = findViewById(R.id.esewaRow);
        LinearLayout codRow = findViewById(R.id.codRow);
        tvSubtotalLabel = findViewById(R.id.tvSubtotalLabel);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvTotal = findViewById(R.id.tvTotal);

        // Initialize note and gift wrapping views
        rgNote = findViewById(R.id.rgNote);
        noteInputLayout = findViewById(R.id.tilPersonalisedNote);
        etPersonalisedNote = findViewById(R.id.etPersonalisedNote);
        giftWrappingCheckbox = findViewById(R.id.giftWrappingCheckbox);

        // Get data passed from CartActivity
        Intent in = getIntent();
        initialSubtotal = in.getIntExtra("subtotal", 0);
        shippingFee = in.getIntExtra("shipping_fee", 0);
        initialTotal = in.getIntExtra("total", 0);
        subtotal = initialSubtotal;
        total = initialTotal;
        itemCount = in.getIntExtra("item_count", 0);
        itemsSummary = in.getStringExtra("items_summary");

        // --- UI Setup and Listeners ---
        updateSummaryUI();
        initAddressUI();
        initNoteAndGiftWrappingUI();
        rgPayment.clearCheck();

        esewaRow.setOnClickListener(v -> rgPayment.check(R.id.rbEsewa));
        codRow.setOnClickListener(v -> rgPayment.check(R.id.rbCod));
        backButton.setOnClickListener(v -> onBackPressed());

        edtDeliveryDate.setOnClickListener(v -> showDatePicker());
        edtDeliveryDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });

        btnPlaceOrder.setOnClickListener(v -> {
            if (placeOrder()) {
                placeOrderToLaravel(); // This function now handles proceeding to the success screen
            }
        });
    }

    /**
     * Sets up the listeners for the note and gift wrapping UI elements.
     */
    private void initNoteAndGiftWrappingUI() {
        // Note options
        rgNote.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPersonalisedNote) {
                noteInputLayout.setVisibility(View.VISIBLE);
            } else {
                noteInputLayout.setVisibility(View.GONE);
                etPersonalisedNote.setText("");
            }
            updateOrderSummary();
        });

        // Gift wrapping
        giftWrappingCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateOrderSummary();
        });
    }

    /**
     * Updates the order summary based on selected options.
     */
    private void updateOrderSummary() {
        int additionalFees = 0;

        // Check if personalized note is selected
        if (rgNote.getCheckedRadioButtonId() == R.id.rbPersonalisedNote) {
            additionalFees += NOTE_FEE;
        }

        // Check if gift wrapping is selected
        if (giftWrappingCheckbox.isChecked()) {
            additionalFees += GIFT_WRAP_FEE;
        }

        // Update the subtotal and total
        subtotal = initialSubtotal + additionalFees;
        total = subtotal + shippingFee;

        // Update the UI
        tvSubtotal.setText("NPR." + subtotal);
        tvTotal.setText("NPR." + total);
    }

    /**
     * Sets up the listeners for the "Add Address" and "Change Address" UI elements.
     */
    private void initAddressUI() {
        layoutAddAddress = findViewById(R.id.layoutAddAddress);
        layoutSelectedAddress = findViewById(R.id.layoutSelectedAddress);
        tvSelectedAddress = findViewById(R.id.tvSelectedAddress);
        tvChangeAddress = findViewById(R.id.tvChangeAddress);

        View.OnClickListener startAddAddressActivity = v -> {
            Intent intent = new Intent(this, AddAddressActivity.class);
            addAddressLauncher.launch(intent);
        };

        if (layoutAddAddress != null) layoutAddAddress.setOnClickListener(startAddAddressActivity);
        if (tvChangeAddress != null) tvChangeAddress.setOnClickListener(startAddAddressActivity);
    }

    /**
     * Updates the UI to show the selected address and hides the "Add Address" prompt.
     */
    private void updateAddressDisplay() {
        if (savedAddressTitle == null || savedAddressTitle.isEmpty()) {
            layoutAddAddress.setVisibility(View.VISIBLE);
            layoutSelectedAddress.setVisibility(View.GONE);
            return;
        }
        String addressText = savedAddressTitle + "\n" + savedCustomerName + "\n" + savedDetailAddress + "\n" + savedPhone;
        tvSelectedAddress.setText(addressText);
        layoutAddAddress.setVisibility(View.GONE);
        layoutSelectedAddress.setVisibility(View.VISIBLE);
        if (edtFullName.getText().toString().trim().isEmpty()) { edtFullName.setText(savedCustomerName); }
        if (edtPhone.getText().toString().trim().isEmpty()) { edtPhone.setText(savedPhone); }
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
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            edtDeliveryDate.setText(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();
    }

    private Intent buildOrderSuccessIntent() {
        Intent intent = new Intent(this, OrdersucessActivity.class);
        intent.putExtra("order_id", "#GBX" + System.currentTimeMillis());
        intent.putExtra("order_date", edtDeliveryDate.getText().toString().trim());
        intent.putExtra("total_paid", String.format(Locale.US, "NPR %,d", total));
        intent.putExtra("items_summary", itemsSummary);
        intent.putExtra("delivery_name", edtFullName.getText().toString().trim());
        intent.putExtra("delivery_address", savedDetailAddress);
        intent.putExtra("delivery_phone", edtPhone.getText().toString().trim());
        intent.putExtra("delivery_lat", deliveryLat);
        intent.putExtra("delivery_lng", deliveryLng);

        // Add note and gift wrapping info to the intent
        boolean hasNote = rgNote.getCheckedRadioButtonId() == R.id.rbPersonalisedNote;
        intent.putExtra("has_note", hasNote);
        if (hasNote) {
            intent.putExtra("note_text", etPersonalisedNote.getText().toString().trim());
        }
        intent.putExtra("has_gift_wrapping", giftWrappingCheckbox.isChecked());

        return intent;
    }

    /**
     * Checks if ACCESS_FINE_LOCATION permission is granted. If not, requests it.
     */
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            Log.d(TAG, "Location permission already granted.");
        }
    }

    /**
     * Handles the result of the permission request.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location Permission Denied. Address must be entered manually.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Converts a human-readable address string into Lat/Lng coordinates using Geocoder.
     */
    private void getCoordinatesFromAddress(String addressString) {
        if (addressString == null || addressString.isEmpty()) {
            Toast.makeText(this, "Address is empty, cannot get coordinates.", Toast.LENGTH_SHORT).show();
            return;
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(addressString, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                deliveryLat = location.getLatitude();
                deliveryLng = location.getLongitude();
                Log.d(TAG, "SUCCESS: Address converted to Lat/Lng: " + deliveryLat + ", " + deliveryLng);
                Toast.makeText(this, "Delivery coordinates locked!", Toast.LENGTH_SHORT).show();
            } else {
                Log.w(TAG, "Address not found by geocoder: " + addressString);
                Toast.makeText(this, "Could not determine coordinates for the selected address.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.e(TAG, "Geocoder service failed", e);
            Toast.makeText(this, "Network error. Could not get coordinates.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Validates all required fields before placing an order.
     * @return true if all fields are valid, false otherwise.
     */
    private boolean placeOrder() {
        String name = edtFullName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = savedDetailAddress;
        String date = edtDeliveryDate.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all delivery details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (rgPayment.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (deliveryLat == 0.0 || deliveryLng == 0.0) {
            Toast.makeText(this, "Delivery coordinates have not been set. Please select an address again.", Toast.LENGTH_LONG).show();
            return false;
        }

        // Validate personalized note if selected
        if (rgNote.getCheckedRadioButtonId() == R.id.rbPersonalisedNote) {
            if (etPersonalisedNote.getText().toString().trim().isEmpty()) {
                etPersonalisedNote.setError("Please enter your personalized message");
                Toast.makeText(this, "Please enter your personalized message", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    /**
     * This is where you would call your API to send the order to your Laravel backend.
     * After the API call is successful, it proceeds to the success screen.
     */
    private void placeOrderToLaravel() {
        // Get payment method
        String paymentMethod = "";
        int selectedPaymentId = rgPayment.getCheckedRadioButtonId();
        if (selectedPaymentId == R.id.rbEsewa) {
            paymentMethod = "eSewa";
        } else if (selectedPaymentId == R.id.rbCod) {
            paymentMethod = "Cash on Delivery";
        }

        // Get note details
        String noteText = "";
        boolean hasNote = rgNote.getCheckedRadioButtonId() == R.id.rbPersonalisedNote;
        if (hasNote) {
            noteText = etPersonalisedNote.getText().toString().trim();
        }

        // Get gift wrapping status
        boolean hasGiftWrapping = giftWrappingCheckbox.isChecked();

        // This is a placeholder for your actual API call (e.g., using Retrofit or Volley)
        Log.d("API_CALL", "Placing Order to Laravel backend...");
        Log.d("API_CALL", "Recipient Name: " + savedCustomerName);
        Log.d("API_CALL", "Full Address: " + savedDetailAddress);
        Log.d("API_CALL", "Delivery Latitude: " + deliveryLat);
        Log.d("API_CALL", "Delivery Longitude: " + deliveryLng);
        Log.d("API_CALL", "Phone: " + savedPhone);
        Log.d("API_CALL", "Payment Method: " + paymentMethod);
        Log.d("API_CALL", "Has Note: " + hasNote);
        if (hasNote) {
            Log.d("API_CALL", "Note Text: " + noteText);
        }
        Log.d("API_CALL", "Has Gift Wrapping: " + hasGiftWrapping);

        // Assuming the API call is successful, we then launch the success activity.
        try {
            Intent intent = buildOrderSuccessIntent();
            Log.d(TAG, "Starting OrderSuccessActivity");
            startActivity(intent);
            finish(); // Finish CheckoutActivity so the user cannot go back to it
        } catch (Exception e) {
            Log.e(TAG, "Error starting OrderSuccessActivity", e);
            Toast.makeText(this, "Error processing order.", Toast.LENGTH_SHORT).show();
        }
    }
}