package org.httpsknighthacks.knighthacksandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.Filter;
import org.httpsknighthacks.knighthacksandroid.Models.Sponsor;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.FiltersTask;
import org.httpsknighthacks.knighthacksandroid.Tasks.SponsorsTask;

import java.util.ArrayList;

public class Sponsors extends AppCompatActivity {

    private static final String ALLFILTER = "All";
    private static final String FILTERTYPE= "sponsor";
    public static final String TAG = Sponsors.class.getSimpleName();

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
    private ArrayList<String> mCardMapEventList;

    private ArrayList<String> mFilterSearchImageList;
    private ArrayList<String> mSearchFilterTypeList;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private HorizontalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter;
    private LinearLayoutManager mFilterSearchLinearLayoutManager;
    private RecyclerView mFilterSearchRecyclerView;
    private SharedFilterSearchComponent_RecyclerViewAdapter sharedFilterSearchComponent_RecyclerViewAdapter;

    private ProgressBar mProgressBar;
    private View mEmptyScreenView;

    private ArrayList<Filter> filters = new ArrayList<>();
    private ArrayList<Sponsor> sponsors = new ArrayList<>();

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
        mCardMapEventList = new ArrayList<>();

        mFilterSearchImageList = new ArrayList<>();
        mSearchFilterTypeList = new ArrayList<>();

        mProgressBar = findViewById(R.id.sponsor_progress_bar);
        mEmptyScreenView = findViewById(R.id.sponsors_empty_screen_view);

        sponsors = new ArrayList<>();

        loadFilters();
        loadSponsors();
        loadRecyclerView();
    }

    private void loadFilters() {
        FiltersTask filtersTask = new FiltersTask(getApplicationContext(), new ListResponseListener<Filter>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ArrayList<Filter> response) {
                filters = response;
                getFilterSearchComponents();
                sharedFilterSearchComponent_RecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });

        filtersTask.retrieveFilters();
    }

    private void loadSponsors() {
        SponsorsTask sponsorsTask = new SponsorsTask(getApplicationContext(), new ListResponseListener<Sponsor>() {
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


    private void addHorizontalSectionCard(String imageUrl, String cardTitle, String cardSideSubtitle,
                                          String cardSubtitle, String cardTextTagSubtitle, String cardBody,
                                          String cardTimestamp, String cardFooter, String cardMap) {
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

        if (cardMap != null && !cardMap.isEmpty()) {
            mCardMapEventList.add(cardMap);
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
        mCardMapEventList.clear();
        horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView = findViewById(R.id.sponsors_horizontal_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        horizontalSectionCardRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this, mViewTypeList,
                        mSubSectionTitleList, mCardImageList, mCardTitleList, mCardSideSubtitleList,
                        mCardSubtitleList, mCardTagSubtitleList, mCardBodyList, mCardTimestampList,
                        mCardFooterList, mCardMapEventList, TAG);
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

    private ArrayList<Sponsor> getSponsorsByOfferType(String type) {

        if (type.equals(ALLFILTER)) {
            return sponsors;
        }

        ArrayList<Sponsor> sponsors = new ArrayList<>();
        int numSponsors = this.sponsors.size();

        for (int i = 0; i < numSponsors; i++) {
            Sponsor sponsor = this.sponsors.get(i);


            if (sponsor.getOfferings() == "" || sponsor.getOfferings() == null) {
                continue;
            }

            if (sponsor.getOfferings().toLowerCase().contains(type.toLowerCase())) {
                sponsors.add(sponsor);
            }
        }

        return sponsors;
    }

    private void filterSponsorsByOffering(String offerType) {
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
        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getType().equals(FILTERTYPE)) {
                String filterType = filters.get(i).getName();
                String picturePath = filters.get(i).getPicture();
                mFilterSearchImageList.add(picturePath);
                mSearchFilterTypeList.add(filterType);
            }
        }

        // The Adapter handles the ALL filter image
        mFilterSearchImageList.add("");
        mSearchFilterTypeList.add(ALLFILTER);
    }
}

