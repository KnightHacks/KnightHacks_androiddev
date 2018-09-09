package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSideSubtitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardBodyList;
    private ArrayList<String> mCardTimestampList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSideSubtitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardBodyList = new ArrayList<>();
        mCardTimestampList = new ArrayList<>();

        getCardComponents();
        loadRecyclerView();
    }

    private void getCardComponents() {
        int tempNumCards = 5;

        for (int i = 0; i < tempNumCards; i++) {
            mCardTitleList.add(getResources().getString(R.string.horizontal_card_title_dummy));
            mCardSubtitleList.add(getResources().getString(R.string.horizontal_card_subtitle_dummy));
            mCardTimestampList.add(getResources().getString(R.string.horizontal_card_timestamp_dummy));
        }
    }

    private void loadRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.schedule_horizontal_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        HorizontalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this,mCardImageList,
                        mCardTitleList, mCardSideSubtitleList, mCardSubtitleList, mCardBodyList,
                        mCardTimestampList);
        recyclerView.setAdapter(horizontalSectionCardRecyclerViewAdapter);
    }
}
