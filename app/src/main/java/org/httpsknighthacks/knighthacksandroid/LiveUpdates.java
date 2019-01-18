package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.LiveUpdate;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.LiveUpdatesTask;

import java.util.ArrayList;

public class LiveUpdates extends AppCompatActivity {

    private static final String TAG = LiveUpdates.class.getSimpleName();
    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardDetailsList;

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private VerticalSectionCard_RecyclerViewAdapter mHorizontalSectionCardRecyclerViewAdapter;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_updates);

        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardDetailsList = new ArrayList<>();

        mProgressBar = findViewById(R.id.live_updates_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        loadLiveUpdates();
        loadRecyclerView();
    }

    private void loadLiveUpdates() {
        LiveUpdatesTask liveUpdatesTask = new LiveUpdatesTask(getApplicationContext(), new ResponseListener<LiveUpdate>() {
            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<LiveUpdate> response) {
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
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                mProgressBar.setVisibility(View.GONE);
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
