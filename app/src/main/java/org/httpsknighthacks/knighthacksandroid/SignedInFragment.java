package org.httpsknighthacks.knighthacksandroid;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.httpsknighthacks.knighthacksandroid.Tasks.AdministrativeFieldsTask;
import org.httpsknighthacks.knighthacksandroid.Tasks.LogoutTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SignedInFragment extends Fragment implements AdministrativeFieldsTask.ResponseListener {
    private static final int IMG_PICK = 1000;
    private static final int PERMISSON_CODE = 1001;
    private static final String IMAGE_PROFILE = "image";

    private CardView mGroupsCardView;
    private CardView mLogoutCardView;
    private CardView mShowQrCodeCardView;
    private CircleImageView mPhotoPickerButton;
    private CircleImageView mProfilePicture;
    private TextView mPointsTextView;
    private ImageView mQrCloseButton;
    private Dialog mQrCodeDialog;

    private SharedPreferences mPreferences;
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
        mShowQrCodeCardView = view.findViewById(R.id.show_qr_btn);
        mLogoutCardView = view.findViewById(R.id.logout_btn);
        mGroupsCardView = view.findViewById(R.id.my_groups_btn);

        mGroupsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(view.getContext(), Groups.class);
                view.getContext().startActivity(newActivity);
            }
        });

        mLogoutCardView.setOnClickListener(v -> {

            LogoutBottomSheet bottomSheet = new LogoutBottomSheet();
            bottomSheet.show(getChildFragmentManager(), "logoutBottomSheet");
        });
        mShowQrCodeCardView.setOnClickListener(v -> showQrCodePopup());

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

        getAdministrativeFieldsInfo();
        setNotificationToken();
        retrieveImage();
    }

    private void getAdministrativeFieldsInfo() {
        AdministrativeFieldsTask administrativeFieldsTask = new AdministrativeFieldsTask(getActivity(), this);
        administrativeFieldsTask.retrieveAdministrativeFields();
    }

    private void setNotificationToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }

                    String notificationToken = task.getResult().getToken();
                    String key = mPreferences.getString("hackerKey", "");
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    database.child("administrative_fields").child(key).child("notificationToken").setValue(notificationToken);
                });
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

    private void generateQrBitmap(ImageView imageView) {
        String text = mPreferences.getString("publicUuid", "");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void showQrCodePopup() {
        mQrCodeDialog = new Dialog(getActivity());
        mQrCodeDialog.setContentView(R.layout.qr_code_popup);
        ImageView imageView = mQrCodeDialog.findViewById(R.id.qr_code_placeholder);
        generateQrBitmap(imageView);

        try {
            mQrCodeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        catch (NullPointerException e) {

        }

        mQrCodeDialog.show();
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
    public void onSuccess() {
        int i = mPreferences.getInt("pointsCount", 0);
        mPointsTextView.setText(String.valueOf(i));
    }

    @Override
    public void onFailure() {

    }
}
