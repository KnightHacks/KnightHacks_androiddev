package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.httpsknighthacks.knighthacksandroid.Models.Sponsor;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.SponsorsTask;

import java.util.ArrayList;
import java.util.Arrays;

public class Sponsors extends AppCompatActivity {

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<String> mSubSectionTitleList;
    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSideSubtitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardFirstTextTagList;
    private ArrayList<String> mCardSecondTextTagList;
    private ArrayList<String> mCardBodyList;
    private ArrayList<String> mCardTimestampList;
    private ArrayList<String> mCardFooterList;

    private ArrayList<String> mFilterSearchImageList;
    private ArrayList<SearchFilterTypes> mSearchFilterTypeList;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private HorizontalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter;
    private LinearLayoutManager mFilterSearchLinearLayoutManager;
    private RecyclerView mFilterSearchRecyclerView;
    private SharedFilterSearchComponent_RecyclerViewAdapter sharedFilterSearchComponent_RecyclerViewAdapter;

    private ProgressBar mProgressBar;

    private ArrayList<Sponsor> sponsors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        mViewTypeList = new ArrayList<>();
        mSubSectionTitleList = new ArrayList<>();
        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSideSubtitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardFirstTextTagList = new ArrayList<>();
        mCardSecondTextTagList = new ArrayList<>();
        mCardBodyList = new ArrayList<>();
        mCardTimestampList = new ArrayList<>();
        mCardFooterList = new ArrayList<>();

        mFilterSearchImageList = new ArrayList<>();
        mSearchFilterTypeList = new ArrayList<>();

        mProgressBar = findViewById(R.id.sponsor_progress_bar);

        sponsors = new ArrayList<>();

        loadSponsors();
        getFilterSearchComponents();
        loadRecyclerView();
    }

    private void loadSponsors() {
        SponsorsTask sponsorsTask = new SponsorsTask(getApplicationContext(), new ResponseListener<Sponsor>() {
            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<Sponsor> response) {
                int numSponsors = response.size();

                for (int i = 0; i < numSponsors; i++) {
                    Sponsor currSponsor = response.get(i);

                    if (Sponsor.isValid(currSponsor)) {
                        addSponsorCard(currSponsor);
                        sponsors.add(currSponsor);
                    }
                }

                horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
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

        sponsorsTask.execute();
    }

    private void addSubSectionTitle(String title) {
        mViewTypeList.add(HorizontalSectionCard_RecyclerViewAdapter.TitleViewHolder.VIEW_TYPE);

        if (title != null && !title.isEmpty()) {
            mSubSectionTitleList.add(title);
        }
    }


    private void addHorizontalSectionCard(String imageUrl, String cardTitle, String cardSideSubtitle, String cardSubtitle, String cardFirstTextTagSubtitle, String cardSecondTextTagSubtitle, String cardBody, String cardTimestamp, String cardFooter) {
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
        
        if (cardFirstTextTagSubtitle != null && !cardFirstTextTagSubtitle.isEmpty()) {
            mCardFirstTextTagList.add(cardFirstTextTagSubtitle);
        }
        
        if (cardSecondTextTagSubtitle != null && !cardSecondTextTagSubtitle.isEmpty()) {
            mCardSecondTextTagList.add(cardSecondTextTagSubtitle);
        }

        if (cardBody != null && !cardBody.isEmpty()) {
            mCardBodyList.add(cardBody);
        }

        if (cardTimestamp != null && !cardTimestamp.isEmpty()) {
            mCardTimestampList.add(cardTimestamp);
        }

        if (cardFooter != null && !cardFooter.isEmpty()) {
            mCardFooterList.add(cardFooter);
        }
    }

    private void addSponsorCard(Sponsor sponsor) {
        addHorizontalSectionCard(sponsor.getPictureOptional().getValue(),
                sponsor.getNameOptional().getValue(),
                sponsor.getLocationOptional().getValue(),
                null,
                sponsor.getInternship(),
                sponsor.getFullTime(),
                sponsor.getDescriptionOptional().getValue(),
                null,
                null);
    }

    private void clearSponsors() {
        mViewTypeList.clear();
        mCardImageList.clear();
        mCardTitleList.clear();
        mCardSideSubtitleList.clear();
        mCardFirstTextTagList.clear();
        mCardSecondTextTagList.clear();
        mCardBodyList.clear();
        horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.sponsors_horizontal_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        horizontalSectionCardRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this, mViewTypeList,
                        mSubSectionTitleList, mCardImageList, mCardTitleList, mCardSideSubtitleList,
                        mCardSubtitleList, mCardFirstTextTagList, mCardSecondTextTagList, mCardBodyList, mCardTimestampList, mCardFooterList);
        recyclerView.setAdapter(horizontalSectionCardRecyclerViewAdapter);

        // Recycler Filter Search Bar
        mFilterSearchLinearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFilterSearchLinearLayoutManager.setStackFromEnd(true);
        mFilterSearchRecyclerView = findViewById(R.id.shared_horizontal_filter_search_component_container);
        mFilterSearchRecyclerView.setLayoutManager(mFilterSearchLinearLayoutManager);

        sharedFilterSearchComponent_RecyclerViewAdapter =
                new SharedFilterSearchComponent_RecyclerViewAdapter(this, mFilterSearchImageList, mSearchFilterTypeList, new SearchFilterListener() {
                    @Override
                    public void setSearchFilters(SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder holder, int position) {
                        filterSponsorsByOffering(holder.mSearchFilterType);
                    }
                });
        mFilterSearchRecyclerView.setAdapter(sharedFilterSearchComponent_RecyclerViewAdapter);
    }

    private ArrayList<Sponsor> getSponsorsByOfferType(SearchFilterTypes type) {
        if (type.equals(SearchFilterTypes.ALL)) {
            return sponsors;
        }

        ArrayList<Sponsor> sponsors = new ArrayList<>();
        int numSponsors = this.sponsors.size();

        for (int i = 0; i < numSponsors; i++) {
            Sponsor sponsor = this.sponsors.get(i);

            if (Arrays.asList(sponsor.getOfferingsOptional().getValue()).contains(type)) {
                sponsors.add(sponsor);
            }
        }

        return sponsors;
    }

    private void filterSponsorsByOffering(SearchFilterTypes offerType) {
        mProgressBar.setVisibility(View.VISIBLE);
        clearSponsors();

        ArrayList<Sponsor> sponsors = getSponsorsByOfferType(offerType);
        int numSponsors = sponsors.size();

        for (int i = 0; i < numSponsors; i++) {
            addSponsorCard(sponsors.get(i));
        }

        mProgressBar.setVisibility(View.GONE);
        horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getFilterSearchComponents() {
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_full_time));
        mSearchFilterTypeList.add(SearchFilterTypes.FULL_TIME);

        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_internships));
        mSearchFilterTypeList.add(SearchFilterTypes.INTERNSHIP);

        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_all));
        mSearchFilterTypeList.add(SearchFilterTypes.ALL);

    }
}
