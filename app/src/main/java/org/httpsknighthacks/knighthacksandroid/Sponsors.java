package org.httpsknighthacks.knighthacksandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.httpsknighthacks.knighthacksandroid.Models.Filter;
import org.httpsknighthacks.knighthacksandroid.Models.Sponsor;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.FiltersTask;
import org.httpsknighthacks.knighthacksandroid.Tasks.SponsorsTask;

import java.util.ArrayList;
import java.util.Arrays;

public class Sponsors extends AppCompatActivity {

    private static final String TAG = Sponsors.class.getSimpleName();

    private ArrayList<Integer> mViewTypeList;
    private ArrayList<String> mSubSectionTitleList;
    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSideSubtitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardTagSubtitleList;
    private ArrayList<String> mCardBodyList;
    private ArrayList<String> mCardTimestampList;
    private ArrayList<String> mCardFooterList;

    private ArrayList<Integer> mFilterSearchImageList;
    private ArrayList<SearchFilterTypes> mSearchFilterTypeList;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private HorizontalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter;
    private LinearLayoutManager mFilterSearchLinearLayoutManager;
    private RecyclerView mFilterSearchRecyclerView;
    private SharedFilterSearchComponent_RecyclerViewAdapter sharedFilterSearchComponent_RecyclerViewAdapter;

    private ProgressBar mProgressBar;
    private View mEmptyScreenView;

    private ArrayList<Filter> filters;
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
        mCardTagSubtitleList = new ArrayList<>();
        mCardBodyList = new ArrayList<>();
        mCardTimestampList = new ArrayList<>();
        mCardFooterList = new ArrayList<>();

        mFilterSearchImageList = new ArrayList<>();
        mSearchFilterTypeList = new ArrayList<>();

        mProgressBar = findViewById(R.id.sponsor_progress_bar);
        mEmptyScreenView = findViewById(R.id.sponsors_empty_screen_view);

        sponsors = new ArrayList<>();

        loadFilters();
        loadSponsors();
        getFilterSearchComponents();
        loadRecyclerView();
    }

    private void loadFilters() {
        FiltersTask filtersTask = new FiltersTask(getApplicationContext(), new ResponseListener<Filter>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ArrayList<Filter> response) {
                filters = response;
            }

            @Override
            public void onFailure() {

            }
        });

        filtersTask.retrieveFilters();
    }

    private void loadSponsors() {
        SponsorsTask sponsorsTask = new SponsorsTask(getApplicationContext(), new ResponseListener<Sponsor>() {
            @Override
            public void onStart() {
                mEmptyScreenView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<Sponsor> response) {
                int numSponsors = response.size();

                if (numSponsors == 0) {
                    mEmptyScreenView.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < numSponsors; i++) {
                    Sponsor currSponsor = response.get(i);
                    
                    if (Sponsor.isValid(currSponsor)) {
                        addSponsorCard(currSponsor);
                        sponsors.add(currSponsor);
                    }
                }

                horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }
        });

        sponsorsTask.retrieveSponsors();
    }

    private void addSubSectionTitle(String title) {
        mViewTypeList.add(HorizontalSectionCard_RecyclerViewAdapter.TitleViewHolder.VIEW_TYPE);

        if (title != null && !title.isEmpty()) {
            mSubSectionTitleList.add(title);
        }
    }


    private void addHorizontalSectionCard(String imageUrl, String cardTitle, String cardSideSubtitle, String cardSubtitle, String cardTextTagSubtitle, String cardBody, String cardTimestamp, String cardFooter) {
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
        
        if (cardTextTagSubtitle != null) {
            mCardTagSubtitleList.add(cardTextTagSubtitle);
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
        addHorizontalSectionCard(sponsor.getPicture(),
                sponsor.getName(),
                sponsor.getLocation(),
                null,
                sponsor.getOfferings(),
                sponsor.getDescription(),
                null,
                null);
    }

    private void clearSponsors() {
        mViewTypeList.clear();
        mCardImageList.clear();
        mCardTitleList.clear();
        mCardSideSubtitleList.clear();
        mCardTagSubtitleList.clear();
        mCardBodyList.clear();
        horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView = findViewById(R.id.sponsors_horizontal_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        horizontalSectionCardRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this, mViewTypeList,
                        mSubSectionTitleList, mCardImageList, mCardTitleList, mCardSideSubtitleList,
                        mCardSubtitleList, mCardTagSubtitleList, mCardBodyList, mCardTimestampList, mCardFooterList);
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
        mEmptyScreenView.setVisibility(View.GONE);
        clearSponsors();

        ArrayList<Sponsor> sponsors = getSponsorsByOfferType(offerType);
        int numSponsors = sponsors.size();

        for (int i = 0; i < numSponsors; i++) {
            addSponsorCard(sponsors.get(i));
        }

        if (numSponsors == 0) {
            mEmptyScreenView.setVisibility(View.VISIBLE);
        }

        mProgressBar.setVisibility(View.GONE);
        horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getFilterSearchComponents() {
        mFilterSearchImageList.add(R.drawable.ic_sponsors_full_time);
        mSearchFilterTypeList.add(SearchFilterTypes.FULL_TIME);

        mFilterSearchImageList.add(R.drawable.ic_sponsors_internships);
        mSearchFilterTypeList.add(SearchFilterTypes.INTERNSHIP);

        mFilterSearchImageList.add(R.drawable.ic_filter_all);
        mSearchFilterTypeList.add(SearchFilterTypes.ALL);

    }
}
