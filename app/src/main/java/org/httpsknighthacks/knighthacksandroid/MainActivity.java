package org.httpsknighthacks.knighthacksandroid;

import android.app.ActionBar;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Integer> mBgColors = new ArrayList<>();
    private ArrayList<Class> activityList = new ArrayList<>();

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

        setContentView(R.layout.activity_main);

        getImageAndTitles();
    }

    // Get images and titles from the database
    private void getImageAndTitles()
    {
        // TODO: Hard-coded for now, but will have to change later on
        mImageUrls.add(getResources().getString(R.string.home_screen_schedule_card_img));
        mTextList.add(getResources().getString(R.string.schedule_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardOrange));
        activityList.add(Schedule.class);

        mImageUrls.add(getResources().getString(R.string.home_screen_live_updates_card_img));
        mTextList.add(getResources().getString(R.string.live_updates_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardBlue));
        activityList.add(LiveUpdates.class);

        mImageUrls.add(getResources().getString(R.string.home_screen_faqs_card_img));
        mTextList.add(getResources().getString(R.string.faqs_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardGreen));
        activityList.add(FAQs.class);

        mImageUrls.add(getResources().getString(R.string.home_screen_workshops_card_img));
        mTextList.add(getResources().getString(R.string.workshops_card_title));
        mBgColors.add(getResources().getColor(R.color.colorHomePageCardRed));
        activityList.add(Workshops.class);

        mImageUrls.add(getResources().getString(R.string.home_screen_sponsors_card_img));
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
                new Homepage_RecyclerViewAdapter(this, mTextList, mImageUrls, mBgColors, activityList);
        mRecyclerView.setAdapter(homepage_recyclerViewAdapter);
    }

}
