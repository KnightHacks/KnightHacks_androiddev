package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.Hacker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    public static final String HACKERS_COLLECTION = "hackers";

    Button loginBtn;
    EditText uuidText;
    private DatabaseReference mReference;
    SharedPreferenceConfig sharedPreferenceConfig;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        loginBtn = findViewById(R.id.loginButton);
        uuidText = findViewById(R.id.uuid);
        mReference = FirebaseDatabase.getInstance().getReference();
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        context = this;

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uuid = uuidText.getText().toString();

                mReference.child(HACKERS_COLLECTION).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean isUuidExisted = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Hacker hacker = snapshot.getValue(Hacker.class);
                            if (hacker != null && hacker.getPrivateUuid() != null && hacker.getPrivateUuid().equals(uuid)) {
                                Log.d("Submit LogIn Button", "SUCCESSFUL LOGIN");
                                isUuidExisted = true;

                                // Store user data
                                sharedPreferenceConfig.writeLoginStatus(true);
                                sharedPreferenceConfig.writeUUID(uuid);
                                Intent intent = new Intent(context, Profile.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        if (!isUuidExisted) {
                            Toast toast =Toast.makeText(Login.this, "Your UUID is not correct.", Toast.LENGTH_LONG) ;
                            View view = toast.getView();
                            view.setBackgroundColor(Color.RED);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
