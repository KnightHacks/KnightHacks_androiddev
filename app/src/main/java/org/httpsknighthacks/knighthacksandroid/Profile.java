package org.httpsknighthacks.knighthacksandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        Button settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add settings fragment and portion

                Toast.makeText(Profile.this, "No Settings page yet",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button scanQrCodeButton = findViewById(R.id.scan_qr_code_button);
        scanQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(view.getContext(), LiveBarcodeScanningActivity.class);
                view.getContext().startActivity(newActivity);
            }
        });
    }
}
