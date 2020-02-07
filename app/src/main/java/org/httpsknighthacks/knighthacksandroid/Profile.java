package org.httpsknighthacks.knighthacksandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    CircleImageView mCircleImageView;

    private static final int IMG_PICK = 1000;
    private static final int PERMISSON_CODE = 1001;

    SharedPreferences mSharedPreferences;
    private static final String MY_PREFERNCES = "MY_PREF";
    private static final String IMAGE_PROFILE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        CardView scanQrCodeButton = findViewById(R.id.profile_scan_qr_code_button);
        scanQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(view.getContext(), LiveBarcodeScanningActivity.class);
                view.getContext().startActivity(newActivity);
            }
        });

        mCircleImageView = findViewById(R.id.profileImage);

        retrieveImage();

        CircleImageView profileImageBtn = findViewById(R.id.profileImageBtn);
        profileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions, PERMISSON_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMG_PICK);
    }

    private void saveImage(Bitmap bm) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(IMAGE_PROFILE, encodeTobase64(bm));
        editor.apply();

        Toast.makeText(this, "Profile Image Saved", Toast.LENGTH_SHORT).show();
    }

    private void retrieveImage() {
        mSharedPreferences =getSharedPreferences(MY_PREFERNCES, Context.MODE_PRIVATE);

        if (mSharedPreferences.contains(IMAGE_PROFILE)) {
            Glide.with(this)
                    .asBitmap()
                    .load(decodeBase64(mSharedPreferences.getString(IMAGE_PROFILE, "")))
                    .into(mCircleImageView);
        }
    }

    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSON_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMG_PICK) {
            try {
                Bitmap imgBM = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                mCircleImageView.setImageBitmap(imgBM);

                saveImage(imgBM);
            }
            catch (Exception e){

            }
        }
    }
}
