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

    private static final String TAG = "MainActivity called!";

    // Properties:
    private ArrayList<String> mTextList = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mBgColors = new ArrayList<>();


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
        // TODO: Hard code for now, but will have to change later on
        //    <color name="Orange">#FFFD9402</color>
        //    <color name="Blue">#0678fe</color>
        //    <color name="Green">#25ad60</color>
        //    <color name="Red">#fc3b32</color>
        //    <color name="Purple">#5653d5</color>
        //    <color name="Pink">#FF4081</color>

        mImageUrls.add("https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg");
        mTextList.add("Schedule");
        mBgColors.add("#FFFD9402");

        mImageUrls.add("");
        mTextList.add("Live Updates");
        mBgColors.add("#0678fe");

        mImageUrls.add("");
        mTextList.add("FAQs");
        mBgColors.add("#25ad60");


        mImageUrls.add("");
        mTextList.add("Workshops");
        mBgColors.add("#fc3b32");


        mImageUrls.add("");
        mTextList.add("Sponsors");
        mBgColors.add("#5653d5");


        mImageUrls.add("");
        mTextList.add("Devs Team");
        mBgColors.add("#FF4081");


        loadRecycleViewToHomepage();
    }

    // Add everything into the recycler view and recycler adapter
    private void loadRecycleViewToHomepage()
    {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView mRecyclerView = findViewById(R.id.homepage_list_container);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Homepage_RecyclerViewAdapter homepage_recyclerViewAdapter = new Homepage_RecyclerViewAdapter(this, mTextList, mImageUrls, mBgColors);
        mRecyclerView.setAdapter(homepage_recyclerViewAdapter);
    }

}
