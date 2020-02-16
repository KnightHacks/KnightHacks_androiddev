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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.httpsknighthacks.knighthacksandroid.Tasks.AdministrativeFieldsTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SignedInFragment extends Fragment implements AdministrativeFieldsTask.StringResponseListener {
    private static final int IMG_PICK = 1000;
    private static final int PERMISSON_CODE = 1001;
    private static final String IMAGE_PROFILE = "image";

    CircleImageView mPhotoPickerButton;
    CircleImageView mProfilePicture;
    TextView mPointsTextView;

    SharedPreferences mPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signed_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPhotoPickerButton = view.findViewById(R.id.profileImageBtn);
        mProfilePicture = view.findViewById(R.id.profileImage);
        mPointsTextView = view.findViewById(R.id.profile_points_txt);

        mPhotoPickerButton.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
        });

        getPointsCount();
        retrieveImage();
    }

    private void getPointsCount() {
        AdministrativeFieldsTask administrativeFieldsTask = new AdministrativeFieldsTask(getActivity(), this);
        administrativeFieldsTask.retrieveAdministrativeFields();
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMG_PICK);
    }

    private void retrieveImage() {
        mPreferences = getActivity().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        if (mPreferences.contains(IMAGE_PROFILE)) {
            Glide.with(this)
                    .asBitmap()
                    .load(decodeBase64(mPreferences.getString(IMAGE_PROFILE, "")))
                    .into(mProfilePicture);
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

    private void saveImage(Bitmap bm) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(IMAGE_PROFILE, encodeTobase64(bm));
        editor.apply();

        Toast.makeText(getActivity(), "Profile Image Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSON_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMG_PICK) {
            try {
                Bitmap imgBM = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                mProfilePicture.setImageBitmap(imgBM);

                saveImage(imgBM);
            }
            catch (Exception e){

            }
        }
    }

    @Override
    public void onSuccess(String value) {
        mPointsTextView.setText(value);
    }

    @Override
    public void onFailure() {

    }
}
