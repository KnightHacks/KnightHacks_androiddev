package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.httpsknighthacks.knighthacksandroid.Models.LiveUpdate;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.LiveUpdatesTask;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LiveUpdates extends AppCompatActivity {

    private static final String TAG = LiveUpdates.class.getSimpleName();

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardDetailsList;
    private ArrayList<String> mCardOptionalImageList;

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private VerticalSectionCard_RecyclerViewAdapter mVerticalSectionCardRecyclerViewAdapter;

    private TextView mCountdown;
    private TextView mLiveIndicator;
    private TextView mLiveKnightHacks;
    private TextView mNotLiveKnightHacks;
    private CountDownTimer mCountDownTimer;
    private long currentTimeInMillis;

    private ProgressBar mProgressBar;
    private View mEmptyScreenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_updates);
      
        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardDetailsList = new ArrayList<>();
        mCardOptionalImageList = new ArrayList<>();

        mCountdown = findViewById(R.id.countdown_timer);
        mLiveIndicator = findViewById(R.id.live_indicator);
        mLiveKnightHacks = findViewById(R.id.live_knighthacks);
        mNotLiveKnightHacks = findViewById(R.id.not_live_knighthacks);
        mProgressBar = findViewById(R.id.live_updates_progress_bar);
        mEmptyScreenView = findViewById(R.id.live_updates_empty_screen_view);

        //setupCountDownTimer();
        setupTimer();
        loadLiveUpdates();
        loadRecyclerView();
    }

    private void setupCountDownTimer() {
        Calendar startDay = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        startDay.setTime(new Date(0));
        startDay.set(Calendar.AM_PM, 1);
        startDay.set(Calendar.HOUR_OF_DAY, 23);
        startDay.set(Calendar.DAY_OF_MONTH, 1);
        startDay.set(Calendar.MONTH, 2);
        startDay.set(Calendar.YEAR, 2019);

        Calendar endDay = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        endDay.setTime(new Date(0));
        endDay.set(Calendar.AM_PM, 0);
        endDay.set(Calendar.HOUR_OF_DAY, 11);
        endDay.set(Calendar.DAY_OF_MONTH, 3);
        endDay.set(Calendar.MONTH, 2);
        endDay.set(Calendar.YEAR, 2019);

        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));

        if(today.getTimeInMillis() >= startDay.getTimeInMillis() && today.getTimeInMillis() <= endDay.getTimeInMillis()) {
            mNotLiveKnightHacks.setVisibility(View.INVISIBLE);
            mLiveIndicator.setVisibility(View.VISIBLE);
            mLiveKnightHacks.setVisibility(View.VISIBLE);

            long duration = endDay.getTimeInMillis() - today.getTimeInMillis();
            startCountDownTimer(duration);
        }
    }

    private void setupTimer() {
        Calendar startDay = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        startDay.setTime(new Date(0));
        startDay.set(Calendar.AM_PM, 1);
        startDay.set(Calendar.HOUR_OF_DAY, 22);
        startDay.set(Calendar.MINUTE, 30);
        startDay.set(Calendar.DAY_OF_MONTH, 27);
        startDay.set(Calendar.MONTH, 2);
        startDay.set(Calendar.YEAR, 2020);

        try {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String format = preferences.getString("format", null);
            String time = preferences.getString("time", null);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            Date endDate = simpleDateFormat.parse(time);
            Date currentDate = new Date();

            if (currentDate.getTime() >= startDay.getTimeInMillis() && currentDate.getTime() <= endDate.getTime()) {
                mNotLiveKnightHacks.setVisibility(View.INVISIBLE);
                mLiveIndicator.setVisibility(View.VISIBLE);
                mLiveKnightHacks.setVisibility(View.VISIBLE);
                long timeLeft = endDate.getTime() - currentDate.getTime();
                startCountDownTimer(timeLeft);
            }
        }
         catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void startCountDownTimer(long duration) {
        mCountDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                currentTimeInMillis -= 1;
                long secondsTilEnd =
                        (millisUntilFinished - currentTimeInMillis) / 1000;

                int hoursLeft = (int) (secondsTilEnd / 3600);
                int minutesLeft = (int) (secondsTilEnd  % 3600 / 60);
                int secondsLeft = (int) (secondsTilEnd % 3600 % 60);

                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursLeft, minutesLeft, secondsLeft);
                mCountdown.setText(timeLeftFormatted);

                if(mCountdown.getText().toString().equals("00:00:00")) {
                    mCountDownTimer.cancel();
                    revertUIState();
                }
            }

            @Override
            public void onFinish() { }
        }.start();
    }

    private void revertUIState() {
        mLiveIndicator.setVisibility(View.INVISIBLE);
        mLiveKnightHacks.setVisibility(View.INVISIBLE);
        mNotLiveKnightHacks.setVisibility(View.VISIBLE);
    }

    private void loadLiveUpdates() {
        LiveUpdatesTask liveUpdatesTask = new LiveUpdatesTask(getApplicationContext(), new ListResponseListener<LiveUpdate>() {
            @Override
            public void onStart() {
                mEmptyScreenView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<LiveUpdate> response) {
                int numUpdates = response.size();

                if (numUpdates == 0) {
                    mEmptyScreenView.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < numUpdates; i++) {
                    LiveUpdate currUpdate = response.get(i);

                    if (LiveUpdate.isValid(currUpdate)) {
                        mCardImageList.add(currUpdate.getPicture());
                        mCardTitleList.add(currUpdate.getMessage());
                        mCardSubtitleList.add(getScheduleEventDate(currUpdate));
                    }

                    mVerticalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }
        });
        liveUpdatesTask.retrieveUpdates();
    }

    private String getScheduleEventDate(LiveUpdate liveUpdate) {
        String seconds = String.valueOf(liveUpdate.getTimeSent().get("seconds"));
        Long sec = Long.parseLong(seconds);

        return new Date(sec * 1000).toString();
    }

    private void loadRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView = findViewById(R.id.live_updates_vertical_section_card_container);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mVerticalSectionCardRecyclerViewAdapter =
                new VerticalSectionCard_RecyclerViewAdapter(this, mCardImageList,
                        mCardTitleList, mCardSubtitleList, mCardDetailsList, mCardOptionalImageList, TAG);
        mRecyclerView.setAdapter(mVerticalSectionCardRecyclerViewAdapter);
    }
}