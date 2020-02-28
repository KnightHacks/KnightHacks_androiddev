package org.httpsknighthacks.knighthacksandroid;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Switch mGeneralSwitch;
    Switch mFoodSwitch;
    Switch mEmergencySwitch;

    private static final String GENERAL_TOPIC = "GENERAL";
    public Boolean GENERAL_NOTI = true;

    private static final String FOOD_TOPIC = "FOOD";
    public Boolean FOOD_NOTI = true;

    private static final String EMERGENCY_TOPIC = "EMERGENCY";
    public Boolean EMERGENCY_NOTI = true;

    private SharedPreferences mSharedPreferences;
    private static final String MY_PREFERNCES = "MY_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSharedPreferences = getSharedPreferences(MY_PREFERNCES, MODE_PRIVATE);

        mGeneralSwitch = findViewById(R.id.generalSwitch);
        mFoodSwitch = findViewById(R.id.foodSwitch);
        mEmergencySwitch = findViewById(R.id.emergencySwitch);

        mGeneralSwitch.setChecked(mSharedPreferences.getBoolean(GENERAL_TOPIC, GENERAL_NOTI));
        mFoodSwitch.setChecked(mSharedPreferences.getBoolean(FOOD_TOPIC, FOOD_NOTI));
        mEmergencySwitch.setChecked(mSharedPreferences.getBoolean(EMERGENCY_TOPIC, EMERGENCY_NOTI));

        mGeneralSwitch.setTrackResource(mSharedPreferences.getBoolean(GENERAL_TOPIC, GENERAL_NOTI)
                ? R.drawable.on_switch : R.drawable.off_switch);
        mGeneralSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    mGeneralSwitch.setTrackResource(R.drawable.off_switch);
                    GENERAL_NOTI = false;
                    unsubscribeToTopic(GENERAL_TOPIC);
                }
                else {
                    mGeneralSwitch.setTrackResource(R.drawable.on_switch);
                    GENERAL_NOTI = true;
                    subscribeToTopic(GENERAL_TOPIC);
                }
            }
        });

        mFoodSwitch.setTrackResource(mSharedPreferences.getBoolean(FOOD_TOPIC, FOOD_NOTI)
                ? R.drawable.on_switch : R.drawable.off_switch);
        mFoodSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    mFoodSwitch.setTrackResource(R.drawable.off_switch);
                    FOOD_NOTI = false;
                    unsubscribeToTopic(FOOD_TOPIC);
                }
                else {
                    mFoodSwitch.setTrackResource(R.drawable.on_switch);
                    FOOD_NOTI = true;
                    subscribeToTopic(FOOD_TOPIC);
                }
            }
        });

        mEmergencySwitch.setTrackResource(mSharedPreferences.getBoolean(EMERGENCY_TOPIC, EMERGENCY_NOTI)
                ? R.drawable.on_switch : R.drawable.off_switch);
        mEmergencySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    mEmergencySwitch.setTrackResource(R.drawable.off_switch);
                    EMERGENCY_NOTI = false;
                    unsubscribeToTopic(EMERGENCY_TOPIC);
                }
                else {
                    mEmergencySwitch.setTrackResource(R.drawable.on_switch);
                    EMERGENCY_NOTI = true;
                    subscribeToTopic(EMERGENCY_TOPIC);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putBoolean(GENERAL_TOPIC, GENERAL_NOTI);
        editor.putBoolean(FOOD_TOPIC, FOOD_NOTI);
        editor.putBoolean(EMERGENCY_TOPIC, EMERGENCY_NOTI);
        editor.apply();
    }

    private void subscribeToTopic(final String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful: Subscribed to " + topic;
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d("Subscription to " + topic, msg);
                        Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void unsubscribeToTopic(final String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful: Unsubscribed to " + topic;
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d("Subscription to " + topic, msg);
                        Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
