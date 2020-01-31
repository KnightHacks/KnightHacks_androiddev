package org.httpsknighthacks.knighthacksandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        CardView scanQrCodeButton = findViewById(R.id.scan_qr_code_button);
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
        });
    }
}
