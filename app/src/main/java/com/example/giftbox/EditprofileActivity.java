package com.example.giftbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.giftbox.view.ProfileActivity;

public class EditprofileActivity extends AppCompatActivity {

    private static final int REQ_CAMERA = 1;
    private static final int REQ_GALLERY = 2;

    ImageView ivProfilePhoto, ivChangePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile);   // set layout FIRST

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // find views AFTER setContentView
        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);
        ivChangePhoto  = findViewById(R.id.ivChangePhoto);
        ImageView ivBack = findViewById(R.id.ivBack);

        // back button
        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(EditprofileActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

        // change photo click
        ivChangePhoto.setOnClickListener(v -> showImagePickerDialog());

        // insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void showImagePickerDialog() {
        String[] options = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else if (which == 1) {
                openGallery();
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQ_CAMERA);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_GALLERY);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        if (requestCode == REQ_CAMERA) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivProfilePhoto.setImageBitmap(imageBitmap);
            }
        } else if (requestCode == REQ_GALLERY) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                ivProfilePhoto.setImageURI(selectedImage);
            }
        }
    }
}
