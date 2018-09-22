package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Workshops extends AppCompatActivity {

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<String> mSubSectionTitleList;
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
        setContentView(R.layout.activity_workshops);

        mViewTypeList = new ArrayList<>();
        mSubSectionTitleList = new ArrayList<>();
        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSideSubtitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardBodyList = new ArrayList<>();
        mCardTimestampList = new ArrayList<>();
        mFilterSearchTextList = new ArrayList<>();
        mFilterSearchImageList = new ArrayList<>();

        getCardComponents();
        getFilterSearchComponents();
        loadRecyclerView();
    }

    private void addSubSectionTitle(String title) {
        mViewTypeList.add(HorizontalSectionCard_RecyclerViewAdapter.TitleViewHolder.VIEW_TYPE);

        if (title != null && !title.isEmpty()) {
            mSubSectionTitleList.add(title);
        }
    }

    private void addHorizontalSectionCard(String imageUrl, String cardTitle, String cardSideSubtitle, String cardSubtitle, String cardBody, String cardTimestamp) {
        mViewTypeList.add(HorizontalSectionCard_RecyclerViewAdapter.ContentViewHolder.VIEW_TYPE);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            mCardImageList.add(imageUrl);
        }

        if (cardTitle != null && !cardTitle.isEmpty()) {
            mCardTitleList.add(cardTitle);
        }

        if (cardSideSubtitle != null && !cardSideSubtitle.isEmpty()) {
            mCardSideSubtitleList.add(cardSideSubtitle);
        }

        if (cardSubtitle != null && !cardSubtitle.isEmpty()) {
            mCardSubtitleList.add(cardSubtitle);
        }

        if (cardBody != null && !cardBody.isEmpty()) {
            mCardBodyList.add(cardBody);
        }

        if (cardTimestamp != null && !cardTimestamp.isEmpty()) {
            mCardTimestampList.add(cardTimestamp);
        }
    }

    private void getCardComponents() {
        int tempNumCards = 10;

        for (int i = 0; i < tempNumCards; i++) {
            if (i == 0 || i == tempNumCards / 2) {
                addSubSectionTitle(getResources().getString(R.string.horizontal_card_sub_section_title));
            } else {
                addHorizontalSectionCard(getResources().getString(R.string.horizontal_card_image_dummy),
                        getResources().getString(R.string.horizontal_card_title_dummy),
                        null,
                        getResources().getString(R.string.horizontal_card_subtitle_dummy),
                        getResources().getString(R.string.horizontal_card_body_dummy),
                        getResources().getString(R.string.horizontal_card_timestamp_dummy));
            }
        }
    }

    private void loadRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.workshops_horizontal_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        HorizontalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this, mViewTypeList,
                        mSubSectionTitleList, mCardImageList, mCardTitleList, mCardSideSubtitleList,
                        mCardSubtitleList, mCardBodyList, mCardTimestampList);
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
    private void getFilterSearchComponents() {
        mFilterSearchTextList.add(getResources().getString(R.string.search_filter_full_time));
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_full_time));

        mFilterSearchTextList.add(getResources().getString(R.string.search_filter_internship));
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_internships));

        mFilterSearchTextList.add(getResources().getString(R.string.search_filter_dev));
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_development));

        mFilterSearchTextList.add(getResources().getString(R.string.search_filter_design));
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_design));

        mFilterSearchTextList.add(getResources().getString(R.string.search_filter_talks));
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_talks));

        mFilterSearchTextList.add(getResources().getString(R.string.search_filter_workshops));
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_workshops));

        mFilterSearchTextList.add(getResources().getString(R.string.search_filter_all));
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_all));
    }

}
