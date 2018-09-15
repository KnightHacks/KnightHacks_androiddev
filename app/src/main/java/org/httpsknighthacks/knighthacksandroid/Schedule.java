package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSideSubtitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardBodyList;
    private ArrayList<String> mCardTimestampList;
    private ArrayList<String> mFilterSearchTextList;
    private ArrayList<String> mFilterSearchImageList;

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
        mFilterSearchTextList = new ArrayList<>();
        mFilterSearchImageList = new ArrayList<>();

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
        mFilterSearchTextList.add("Full-time");
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_full_time));

        mFilterSearchTextList.add("Internship");
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_internships));

        mFilterSearchTextList.add("Dev");
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_development));

        mFilterSearchTextList.add("Design");
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_design));

        mFilterSearchTextList.add("Talks");
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_talks));

        mFilterSearchTextList.add("Workshops");
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_workshops));

        mFilterSearchTextList.add("All");
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_all));
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

        // Recycler Filter Search Bar
        LinearLayoutManager mFilterSearchLinearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFilterSearchLinearLayoutManager.setStackFromEnd(true);
        RecyclerView mFilterSearchRecyclerView = findViewById(R.id.shared_horizontal_filter_search_component_container);
        mFilterSearchRecyclerView.setLayoutManager(mFilterSearchLinearLayoutManager);

        SharedFilterSearchComponent_RecyclerViewAdapter sharedFilterSearchComponent_RecyclerViewAdapter =
                new SharedFilterSearchComponent_RecyclerViewAdapter(this, mFilterSearchTextList, mFilterSearchImageList);
        mFilterSearchRecyclerView.setAdapter(sharedFilterSearchComponent_RecyclerViewAdapter);
    }
}
