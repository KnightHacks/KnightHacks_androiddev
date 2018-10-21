package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class FAQs extends AppCompatActivity {

    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardDetailsList;
    public static final String TAG = "FAQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardDetailsList = new ArrayList<>();

        getCardComponents();
        loadRecyclerView();
    }

    private void getCardComponents() {
        int tempNumCards = 5;

        for (int i = 0; i < tempNumCards; i++) {
            mCardImageList.add(getResources().getResourceEntryName(R.drawable.ic_faq_plus_icon));
            mCardTitleList.add(getResources().getString(R.string.vertical_card_title_dummy));
            mCardDetailsList.add(getResources().getString(R.string.vertical_card_details_dummy));
        }
    }

    private void loadRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.faqs_vertical_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        VerticalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter =
                new VerticalSectionCard_RecyclerViewAdapter(this, mCardImageList,
                        mCardTitleList, mCardSubtitleList, mCardDetailsList, TAG);
        recyclerView.setAdapter(horizontalSectionCardRecyclerViewAdapter);
    }
}
