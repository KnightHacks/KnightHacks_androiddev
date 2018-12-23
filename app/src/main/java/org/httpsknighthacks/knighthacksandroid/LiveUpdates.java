package org.httpsknighthacks.knighthacksandroid;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.LiveUpdate;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.LiveUpdatesTask;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LiveUpdates extends AppCompatActivity {

    private static final String TAG = LiveUpdates.class.getSimpleName();
    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardDetailsList;

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private VerticalSectionCard_RecyclerViewAdapter mHorizontalSectionCardRecyclerViewAdapter;

    private TextView mCountdown;
    private TextView mLiveIndicator;
    private TextView mLiveKnightHacks;
    private TextView mNotLiveKnightHacks;
    private CountDownTimer mCountDownTimer;
    private boolean isCountDownLive;
    private long startTime;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_updates);

        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardDetailsList = new ArrayList<>();

        mCountdown = findViewById(R.id.countdown_timer);
        mLiveIndicator = findViewById(R.id.live_indicator);
        mLiveKnightHacks = findViewById(R.id.live_knighthacks);
        mNotLiveKnightHacks = findViewById(R.id.not_live_knighthacks);
        mProgressBar = findViewById(R.id.live_updates_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        isCountDownLive = true;

        if(isCountDownLive) {
            mNotLiveKnightHacks.setVisibility(View.INVISIBLE);
            mLiveIndicator.setVisibility(View.VISIBLE);
            mLiveKnightHacks.setVisibility(View.VISIBLE);
            setupCountDownTimer();
        }

        loadLiveUpdates();
        loadRecyclerView();
    }

    private void setupCountDownTimer() {
        // Temporary dummy time that's always 24 hours ahead of start time.
        Date startDate = new Date();
        startTime = startDate.getTime();
        long endTimeMilli = startDate.getTime() + (24 * 60 * 60 * 1000);

        /*
        This is for when we get the actual date string for the hackathon end time.

        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        formatter.setLenient(false);

        // Random date I chose when testing
        String endTime = "23.12.2018, 01:40:00";
        Date endDate;

        try {
            endDate = formatter.parse(endTime);
            endTimeMilli = endDate.getTime();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        startTime = startDate.getTime();
        */

        if(startTime >= endTimeMilli)
            return;

        mCountDownTimer = new CountDownTimer(endTimeMilli, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                startTime -= 1;
                long serverUptimeSeconds =
                        (millisUntilFinished - startTime) / 1000;

                int hoursLeft = (int) (serverUptimeSeconds / 3600);
                int minutesLeft = (int) (serverUptimeSeconds  % 3600 / 60);
                int secondsLeft = (int) (serverUptimeSeconds % 3600 % 60);

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
        LiveUpdatesTask liveUpdatesTask = new LiveUpdatesTask(getApplicationContext(), new ResponseListener<LiveUpdate>() {
            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<LiveUpdate> response) {
                mProgressBar.setVisibility(View.GONE);

                int numUpdates = response.size();
                for (int i = 0; i < numUpdates; i++) {
                    LiveUpdate currUpdate = response.get(i);

                    if (LiveUpdate.isValid(currUpdate)) {
                        mCardImageList.add(currUpdate.getPictureOptional().getValue());
                        mCardTitleList.add(currUpdate.getMessageOptional().getValue());
                        mCardSubtitleList.add(currUpdate.getTimeSentOptional().getValue());
                    }
                }

                mHorizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }
        });

        liveUpdatesTask.execute();
    }

    private void loadRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView = findViewById(R.id.live_updates_vertical_section_card_container);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mHorizontalSectionCardRecyclerViewAdapter =
                new VerticalSectionCard_RecyclerViewAdapter(this, mCardImageList,
                        mCardTitleList, mCardSubtitleList, mCardDetailsList, TAG);
        mRecyclerView.setAdapter(mHorizontalSectionCardRecyclerViewAdapter);
    }
}
