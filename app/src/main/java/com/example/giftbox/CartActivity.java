package com.example.giftbox;

import android.annotation.SuppressLint;
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

import com.example.giftbox.view.HomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CartActivity extends AppCompatActivity {

    private TextInputLayout noteInputLayout;
    private TextInputEditText etPersonalisedNote;
    private LinearLayout layoutGiftWrapOptions;
    private RadioGroup rgGiftWrap;


    private TextView subtotalText;
    private TextView totalText;

    private final int SHIPPING_FEE = 100;


    private TextView item1Quantity, item2Quantity, item3Quantity;
    private View item1Container, item2Container, item3Container;


    private RadioGroup rgNote;
    private CheckBox giftWrappingCheckbox;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // ---------- item containers ----------
        item1Container = findViewById(R.id.item1Container);
        item2Container = findViewById(R.id.item2Container);
        item3Container = findViewById(R.id.item3Container);

        ImageView item1Delete = findViewById(R.id.item1Delete);
        ImageView item2Delete = findViewById(R.id.item2Delete);
        ImageView item3Delete = findViewById(R.id.item3Delete);

        item1Delete.setOnClickListener(v -> {
            item1Container.setVisibility(View.GONE);
            updateSummary();
        });
        item2Delete.setOnClickListener(v -> {
            item2Container.setVisibility(View.GONE);
            updateSummary();
        });
        item3Delete.setOnClickListener(v -> {
            item3Container.setVisibility(View.GONE);
            updateSummary();
        });


        rgNote = findViewById(R.id.rgNote);
        noteInputLayout = findViewById(R.id.tilPersonalisedNote);
        etPersonalisedNote = findViewById(R.id.etPersonalisedNote);
        giftWrappingCheckbox = findViewById(R.id.giftWrappingCheckbox);
        layoutGiftWrapOptions = findViewById(R.id.layoutGiftWrapOptions);
        rgGiftWrap = findViewById(R.id.rgGiftWrap);

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
        totalText    = findViewById(R.id.totalText);

        shippingText.setText("NPR " + SHIPPING_FEE);


        item1Increase.setOnClickListener(v -> {
            changeQuantity(item1Quantity, +1);
            updateSummary();
        });
        item1Decrease.setOnClickListener(v -> {
            changeQuantity(item1Quantity, -1);
            updateSummary();
        });

        item2Increase.setOnClickListener(v -> {
            changeQuantity(item2Quantity, +1);
            updateSummary();
        });
        item2Decrease.setOnClickListener(v -> {
            changeQuantity(item2Quantity, -1);
            updateSummary();
        });

        item3Increase.setOnClickListener(v -> {
            changeQuantity(item3Quantity, +1);
            updateSummary();
        });
        item3Decrease.setOnClickListener(v -> {
            changeQuantity(item3Quantity, -1);
            updateSummary();
        });


        rgNote.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPersonalisedNote) {
                noteInputLayout.setVisibility(View.VISIBLE);
            } else {
                noteInputLayout.setVisibility(View.GONE);
                etPersonalisedNote.setText("");
            }
            updateSummary();
        });


        giftWrappingCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            layoutGiftWrapOptions.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (!isChecked) {
                rgGiftWrap.clearCheck();
            }
            updateSummary();
        });

        rgGiftWrap.setOnCheckedChangeListener((group, checkedId) -> updateSummary());


        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        checkoutButton.setOnClickListener(v -> {

            int subtotal = computeSubtotalAndExtras();


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
            intent.putExtra("total", subtotal+SHIPPING_FEE);
            intent.putExtra("item_count", itemCount);
            intent.putExtra("items_summary", itemsSummary);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        computeSubtotalAndExtras();
    }


    @SuppressLint("SetTextI18n")
    private int computeSubtotalAndExtras() {
        int subtotal = 0;

        int PRICE_ITEM1 = 1499;
        int PRICE_ITEM2 = 500;
        int PRICE_ITEM3 = 850;

        if (item1Container.getVisibility() == View.VISIBLE) {
            int q1 = Integer.parseInt(item1Quantity.getText().toString());
            subtotal += q1 * PRICE_ITEM1;
        }

        if (item2Container.getVisibility() == View.VISIBLE) {
            int q2 = Integer.parseInt(item2Quantity.getText().toString());
            subtotal += q2 * PRICE_ITEM2;
        }

        if (item3Container.getVisibility() == View.VISIBLE) {
            int q3 = Integer.parseInt(item3Quantity.getText().toString());
            subtotal += q3 * PRICE_ITEM3;
        }

        int extras = 0;

        int selectedNoteId = rgNote.getCheckedRadioButtonId();
        if (selectedNoteId == R.id.rbPersonalisedNote &&
                etPersonalisedNote.getText() != null &&
                !etPersonalisedNote.getText().toString().trim().isEmpty()) {
            int NOTE_FEE = 100;
            extras += NOTE_FEE;
        }

        if (giftWrappingCheckbox.isChecked()) {
            int wrapId = rgGiftWrap.getCheckedRadioButtonId();
            if (wrapId == R.id.rbSingleWrap) {
                int SINGLE_WRAP_FEE = 50;
                extras += SINGLE_WRAP_FEE;
            } else if (wrapId == R.id.rbDoubleWrap) {
                int DOUBLE_WRAP_FEE = 100;
                extras += DOUBLE_WRAP_FEE;
            }
        }

        int total = subtotal + extras + SHIPPING_FEE;

        subtotalText.setText("NPR " + (subtotal + extras));
        totalText.setText("NPR " + total);


        return subtotal + extras;
    }
}
