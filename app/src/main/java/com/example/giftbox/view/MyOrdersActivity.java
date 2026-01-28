package com.example.giftbox.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftbox.R;
import com.example.giftbox.adapters.MyOrdersAdapter;
import com.example.giftbox.models.Order;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    private MyOrdersAdapter adapter;

    private TextView chipAll, chipActive, chipCompleted;

    private final List<Order> allOrders = new ArrayList<>();
    private final List<Order> activeOrders = new ArrayList<>();
    private final List<Order> completedOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_my_orders);

        // Back button in header
        ImageView backButton = findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                Intent intent = new Intent(MyOrdersActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            });
        }

        // Find chips
        chipAll = findViewById(R.id.chip_all);
        chipActive = findViewById(R.id.chip_active);
        chipCompleted = findViewById(R.id.chip_completed);

        // RecyclerView setup
        RecyclerView rvMyOrders = findViewById(R.id.rvMyOrders);
        rvMyOrders.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for now
        seedDummyOrders();
        splitOrdersByStatus();

        // Show all orders by default
        adapter = new MyOrdersAdapter(this, new ArrayList<>(allOrders));
        rvMyOrders.setAdapter(adapter);

        // Chip clicks
        setupChipClicks();
        selectChip("all");
    }

    private void seedDummyOrders() {
        allOrders.clear();
        allOrders.add(new Order(
                "GBX-1001",
                "Rose Bouquet Gift Box",
                "25 Dec 2025 • 10:30 AM",
                "Delivered",
                "completed",
                1500));

        allOrders.add(new Order(
                "GBX-1002",
                "Chocolate & Teddy Gift Box",
                "24 Dec 2025 • 5:10 PM",
                "On the way",
                "active",
                2200));

        allOrders.add(new Order(
                "GBX-1003",
                "Birthday Surprise Box",
                "24 Dec 2025 • 3:45 PM",
                "Preparing",
                "active",
                1800));
    }

    private void splitOrdersByStatus() {
        activeOrders.clear();
        completedOrders.clear();
        for (Order o : allOrders) {
            if ("completed".equals(o.getStatusType())) {
                completedOrders.add(o);
            } else if ("active".equals(o.getStatusType())) {
                activeOrders.add(o);
            }
        }
    }

    private void setupChipClicks() {
        chipAll.setOnClickListener(v -> {
            selectChip("all");
            adapter.updateList(new ArrayList<>(allOrders));
        });

        chipActive.setOnClickListener(v -> {
            selectChip("active");
            adapter.updateList(new ArrayList<>(activeOrders));
        });

        chipCompleted.setOnClickListener(v -> {
            selectChip("completed");
            adapter.updateList(new ArrayList<>(completedOrders));
        });
    }

    private void selectChip(String which) {
        chipAll.setBackgroundResource("all".equals(which) ? R.drawable.chip_selected : R.drawable.chip_unselected);
        chipActive.setBackgroundResource("active".equals(which) ? R.drawable.chip_selected : R.drawable.chip_unselected);
        chipCompleted.setBackgroundResource("completed".equals(which) ? R.drawable.chip_selected : R.drawable.chip_unselected);

        int selectedColor = getResources().getColor(R.color.background);   // white
        int unselectedColor = getResources().getColor(R.color.purple_700); // adjust to your purple

        chipAll.setTextColor("all".equals(which) ? selectedColor : unselectedColor);
        chipActive.setTextColor("active".equals(which) ? selectedColor : unselectedColor);
        chipCompleted.setTextColor("completed".equals(which) ? selectedColor : unselectedColor);
    }
}
