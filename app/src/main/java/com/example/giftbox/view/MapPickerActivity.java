package com.example.giftbox.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giftbox.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class MapPickerActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_ADDRESS = "extra_selected_address";
    private static final int REQUEST_CHECK_SETTINGS = 1001;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        webView = findViewById(R.id.webView);

        // Configure WebView Settings
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);

        // Bridge name "Android" allows window.Android.methodName() in HTML
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        webView.loadUrl("file:///android_asset/map_picker.html");
    }


    /**
     * Checks if the device GPS is enabled. If not, shows the system dialog.
     */
    private void checkGPSSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnCompleteListener(task -> {
                    try {
                        task.getResult(ApiException.class);
                        // GPS is already ON - tell the map to find the user
                        webView.loadUrl("javascript:executeGPS()");
                    } catch (ApiException exception) {
                        if (exception.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the "Turn on Location" popup
                                resolvable.startResolutionForResult(MapPickerActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (Exception e) {
                                Log.e("MapPicker", "Error showing GPS popup", e);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == RESULT_OK) {
            // User enabled GPS via the popup - tell the map to proceed
            webView.loadUrl("javascript:executeGPS()");
        }
    }

    /**
     * The bridge between JavaScript and Java
     */
    public class WebAppInterface {

        @JavascriptInterface
        public void requestSystemGPS() {
            // Must run on UI thread to interact with system dialogs
            runOnUiThread(MapPickerActivity.this::checkGPSSettings);
        }

        @JavascriptInterface
        public void onLocationSelected(double lat, double lng, String address) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_SELECTED_ADDRESS, address);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}