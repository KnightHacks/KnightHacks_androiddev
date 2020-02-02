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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    CircleImageView mCircleImageView;
    Bitmap imgBm;

    private static final int IMG_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    SharedPreferences mSharedPreferences;
    static final String MY_PREFERENCE = "MY_PREF";
    static final String IMAGE_PATH = "image_path";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        /*CardView scanQrCodeButton = findViewById(R.id.scan_qr_code_button);
        scanQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(view.getContext(), LiveBarcodeScanningActivity.class);
                view.getContext().startActivity(newActivity);
            }
        });

        CardView showQRCodeBtn = findViewById(R.id.show_qr_btn);
        showQRCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CardView myGroupsBtn = findViewById(R.id.my_groups_btn);
        myGroupsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CardView settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add settings fragment and portion

                Toast.makeText(Profile.this, "No Settings page yet",
                        Toast.LENGTH_SHORT).show();
            }
        });

        CardView loginEmailBtn = findViewById(R.id.email_login_btn);
        loginEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CardView logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        mCircleImageView = findViewById(R.id.profile_image);

        retrieveImage();

        CircleImageView profileImageBtn = findViewById(R.id.profileImageBtn);
        profileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        pickImgFromGallery();
                    }
                }
                else {
                    pickImgFromGallery();
                }
            }
        });
    }

    private void pickImgFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMG_PICK_CODE);
    }

    private void saveImage(Bitmap imgPath) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(IMAGE_PATH, encodeTobase64(imgPath));
        editor.apply();

        Toast.makeText(this, "Profile Image Saved", Toast.LENGTH_SHORT).show();
    }

    private void retrieveImage() {
        mSharedPreferences = getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);

        if (mSharedPreferences.contains(IMAGE_PATH)) {
            Glide.with(this)
                    .asBitmap()
                    .load(decodeBase64(mSharedPreferences.getString(IMAGE_PATH, "")))
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
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    pickImgFromGallery();
                }
                else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMG_PICK_CODE) {
            try {
                imgBm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                mCircleImageView.setImageBitmap(imgBm);
                saveImage(imgBm);
            }
            catch (Exception e){
                Log.d("GALLERY_PICK", "onActivityResult: " + e);
            }
        }
    }
}
