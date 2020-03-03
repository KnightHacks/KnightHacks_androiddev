package org.httpsknighthacks.knighthacksandroid;

import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.httpsknighthacks.knighthacksandroid.Resources.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<Drawable> mImages = new ArrayList<>();
    private ArrayList<Integer> mBgColors = new ArrayList<>();
    private ArrayList<Class> activityList = new ArrayList<>();

    public RequestQueueSingleton mRequestQueueSingleton;

    private String ANNOUNCEMENTS_TOPIC = "ANNOUNCEMENTS";

    private SharedPreferences mPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();

            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        // For notifications received in foreground
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        subscribeToTopic(ANNOUNCEMENTS_TOPIC);
        setContentView(R.layout.activity_main);

        mPref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mRequestQueueSingleton = new RequestQueueSingleton(getApplicationContext());

        getImageAndTitles();
        getEndTime();
    }

    private void getEndTime() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference endTimeRef = storageRef.child("config/eventEndTime.json");

        try {
            final File localFile = File.createTempFile("endTime", "json");
            endTimeRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                try {
                    FileReader fr = new FileReader(localFile);
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    StringBuilder jsonString = new StringBuilder();

                    while((line = br.readLine()) != null) {
                        jsonString.append(line);
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(jsonString.toString());
                    String time = jsonObject.getString("time");
                    String format = jsonObject.getString("format");
                    setEventEndTime(time);
                    setEventEndTimeFormat(format);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setEventEndTime(String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("time", value);
        editor.apply();
    }

    private void setEventEndTimeFormat(String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("format", value);
        editor.apply();
    }

    private void subscribeToTopic(final String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                    String msg = "Successful: Subscribed to " + topic;
                    if (!task.isSuccessful()) {
                        msg = "Failed";
                    }
                    Log.d("Subscription to " + topic, msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }

    // Get images and titles from the database
    private void getImageAndTitles()
    {
        mImages.add(getResources().getDrawable(R.drawable.ic_profile_home_page));
        mTextList.add("Profile");
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardGreen));
        activityList.add(Profile.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_schedule));
        mTextList.add(getResources().getString(R.string.schedule_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardOrange));
        activityList.add(Schedule.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_live_updates));
        mTextList.add(getResources().getString(R.string.live_updates_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardBlue));
        activityList.add(LiveUpdates.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_workshops));
        mTextList.add(getResources().getString(R.string.workshops_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardRed));
        activityList.add(Workshops.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_sponsors));
        mTextList.add(getResources().getString(R.string.sponsors_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardPurple));
        activityList.add(Sponsors.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_faqs));
        mTextList.add(getResources().getString(R.string.faqs_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardLightBlue));
        activityList.add(FAQs.class);

        loadRecycleViewToHomepage();
    }

    // Add everything into the recycler view and recycler adapter
    private void loadRecycleViewToHomepage()
    {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView mRecyclerView = findViewById(R.id.homepage_list_container);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Homepage_RecyclerViewAdapter homepage_recyclerViewAdapter =
                new Homepage_RecyclerViewAdapter(this, mTextList, mImages, mBgColors, activityList);
        mRecyclerView.setAdapter(homepage_recyclerViewAdapter);
    }

}
