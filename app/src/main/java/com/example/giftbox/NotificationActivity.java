package com.example.giftbox;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {


    private LinearLayout tabNotifications;
    private LinearLayout tabOrder;
    private LinearLayout tabPromo;


    private ImageView ivTabNotificationIcon, ivTabOrderIcon, ivTabPromoIcon;
    private TextView tvTabNotifications, tvTabOrder, tvTabPromo;


    private int currentTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupTabClickListeners();
        selectTab(0);
    }

    private void initViews() {

        tabNotifications = findViewById(R.id.tabNotifications);
        tabOrder = findViewById(R.id.tabOrder);
        tabPromo = findViewById(R.id.tabPromo);


        ivTabNotificationIcon = findViewById(R.id.ivTabNotificationIcon);
        ivTabOrderIcon = findViewById(R.id.ivTabOrderIcon);
        ivTabPromoIcon = findViewById(R.id.ivTabPromoIcon);

        tvTabNotifications = findViewById(R.id.tvTabNotifications);
        tvTabOrder = findViewById(R.id.tvTabOrder);
        tvTabPromo = findViewById(R.id.tvTabPromo);
    }

    private void setupTabClickListeners() {
        tabNotifications.setOnClickListener(v -> selectTab(0));

        tabOrder.setOnClickListener(v -> selectTab(1));

        tabPromo.setOnClickListener(v -> selectTab(2));
    }

    private void selectTab(int tabIndex) {
        if (currentTabIndex == tabIndex) {
            return;
        }


        resetAllTabs();


        updateActiveTab(tabIndex);

        currentTabIndex = tabIndex;


        switchContent(tabIndex);
    }

    private void resetAllTabs() {

        int inactiveIconColor = 0xFF999999;
        int inactiveTextColor = 0xFF777777;


        ivTabNotificationIcon.setColorFilter(inactiveIconColor);
        tvTabNotifications.setTextColor(inactiveTextColor);


        ivTabOrderIcon.setColorFilter(inactiveIconColor);
        tvTabOrder.setTextColor(inactiveTextColor);


        ivTabPromoIcon.setColorFilter(inactiveIconColor);
        tvTabPromo.setTextColor(inactiveTextColor);
    }

    private void updateActiveTab(int tabIndex) {

        int activeIconColor = 0xFFB00020;
        int activeTextColor = 0xFFB00020;

        switch (tabIndex) {
            case 0:
                ivTabNotificationIcon.setColorFilter(activeIconColor);
                tvTabNotifications.setTextColor(activeTextColor);
                break;

            case 1:
                ivTabOrderIcon.setColorFilter(activeIconColor);
                tvTabOrder.setTextColor(activeTextColor);
                break;

            case 2:
                ivTabPromoIcon.setColorFilter(activeIconColor);
                tvTabPromo.setTextColor(activeTextColor);
                break;
        }
    }

    private void switchContent(int tabIndex) {

        switch (tabIndex) {
            case 0:

                showNotificationsContent();
                break;

            case 1:

                showOrdersContent();
                break;

            case 2:

                showPromoContent();
                break;
        }
    }

    private void showNotificationsContent() {


    }

    private void showOrdersContent() {

    }

    private void showPromoContent() {

    }
}
