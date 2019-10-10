package org.httpsknighthacks.knighthacksandroid;

import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<Drawable> mImages = new ArrayList<>();
    private ArrayList<Integer> mBgColors = new ArrayList<>();
    private ArrayList<Class> activityList = new ArrayList<>();

    public RequestQueueSingleton mRequestQueueSingleton;

    private String ANNOUNCEMENTS_TOPIC = "ANNOUNCEMENTS";

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

        // Start of firebase notification code.

        // For notifications received in foreground
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        subscribeToTopic(ANNOUNCEMENTS_TOPIC);

        // End of firebase code.

        setContentView(R.layout.activity_main);

        mRequestQueueSingleton = new RequestQueueSingleton(getApplicationContext());
        getImageAndTitles();
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
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Get images and titles from the database
    private void getImageAndTitles()
    {
        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_schedule));
        mTextList.add(getResources().getString(R.string.schedule_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardOrange));
        activityList.add(Schedule.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_live_updates));
        mTextList.add(getResources().getString(R.string.live_updates_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardBlue));
        activityList.add(LiveUpdates.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_faqs));
        mTextList.add(getResources().getString(R.string.faqs_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardGreen));
        activityList.add(FAQs.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_workshops));
        mTextList.add(getResources().getString(R.string.workshops_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardRed));
        activityList.add(Workshops.class);

        mImages.add(getResources().getDrawable(R.drawable.ic_home_screen_sponsors));
        mTextList.add(getResources().getString(R.string.sponsors_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardPurple));
        activityList.add(Sponsors.class);

        loadRecycleViewToHomepage();
    }

    // Add everything into the recycler view and recycler adapter
    private void loadRecycleViewToHomepage()
    {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView mRecyclerView = findViewById(R.id.homepage_list_container);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Homepage_RecyclerViewAdapter homepage_recyclerViewAdapter =
                new Homepage_RecyclerViewAdapter(this, mTextList, mImages, mBgColors, activityList);
        mRecyclerView.setAdapter(homepage_recyclerViewAdapter);
    }

}
