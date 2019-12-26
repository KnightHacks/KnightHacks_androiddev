package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    SharedPreferenceConfig sharedPreferenceConfig;
    Button alternativeLoginButton;
    Button logoutButton;
    Button settingsButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        setContentView(R.layout.activity_profile);
        context = this;

        settingsButton = findViewById(R.id.settings_button);
        alternativeLoginButton = findViewById(R.id.alternative_login);
        logoutButton = findViewById(R.id.logout_button);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add settings fragment and portion

                Toast.makeText(Profile.this, "No Settings page yet",
                        Toast.LENGTH_SHORT).show();
            }
        });

        alternativeLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferenceConfig.readLoginStatus()) {
                    Intent intent = new Intent(context, Login.class);
                    startActivity(intent);
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Clear user data
                sharedPreferenceConfig.writeLogoutStatus(false);
                sharedPreferenceConfig.clearUUID("");
            }
        });

        Button scanQrCodeButton = findViewById(R.id.scan_qr_code_button);
        scanQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Add QR scan feature

                Toast.makeText(Profile.this, "Not Successful",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
