package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class LiveUpdates extends AppCompatActivity {

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_updates);

        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();

        getCardComponents();
        loadRecyclerView();
    }

    private void getCardComponents() {
        int tempNumCards = 5;

        for (int i = 0; i < tempNumCards; i++) {
            mCardImageList.add(getResources().getString(R.string.vertical_card_image_dummy));
            mCardTitleList.add(getResources().getString(R.string.vertical_card_title_dummy));
            mCardSubtitleList.add(getResources().getString(R.string.vertical_card_subtitle_dummy));
        }
    }

    private void loadRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.live_updates_vertical_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        VerticalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter =
                new VerticalSectionCard_RecyclerViewAdapter(this, mCardImageList,
                        mCardTitleList, mCardSubtitleList);
        recyclerView.setAdapter(horizontalSectionCardRecyclerViewAdapter);
    }
}