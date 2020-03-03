package org.httpsknighthacks.knighthacksandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.Filter;
import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.FiltersTask;
import org.httpsknighthacks.knighthacksandroid.Tasks.WorkshopsTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import static org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils.DATE_TIME_STRING_PATTERN;

public class Workshops extends AppCompatActivity {

    private static final String ALLFILTER = "All";
    private static final String FILTERTYPE = "workshop";
    private static final String TAG = Workshops.class.getSimpleName();

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

    private ArrayList<Workshop> workshops;
    private ArrayList<Filter> filters;

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
        mCardTagSubtitleList = new ArrayList<>();
        mCardBodyList = new ArrayList<>();
        mCardTimestampList = new ArrayList<>();
        mCardFooterList = new ArrayList<>();
        mCardMapEventList = new ArrayList<>();

        mFilterSearchImageList = new ArrayList<>();
        mSearchFilterTypeList = new ArrayList<>();

        mProgressBar = findViewById(R.id.workshops_progress_bar);
        mEmptyScreenView = findViewById(R.id.workshops_empty_screen_view);

        workshops = new ArrayList<>();
        filters = new ArrayList<>();

        loadFilters();
        loadWorkshops();
        loadRecyclerView();
    }

    private void addSubSectionTitle(String title) {
        mViewTypeList.add(HorizontalSectionCard_RecyclerViewAdapter.TitleViewHolder.VIEW_TYPE);

        if (title != null && !title.isEmpty()) {
            mSubSectionTitleList.add(title);
        }
    }

    private void addHorizontalSectionCard(String imageUrl, String cardTitle, String cardSideSubtitle,
                                          String cardSubtitle, String cardTagSubtitle, String cardBody,
                                          String cardTimestamp, String cardFooter, String url) {
        mViewTypeList.add(HorizontalSectionCard_RecyclerViewAdapter.ContentViewHolder.VIEW_TYPE);
        mCardMapEventList.add(url);

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

        if (cardTagSubtitle != null && !cardSubtitle.isEmpty()) {
            mCardTagSubtitleList.add(cardTagSubtitle);
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

    private void addWorkshopCard(Workshop workshop) {
        addHorizontalSectionCard(workshop.getPicture(),
                workshop.getName(),
                null,
                null,
                null,
                workshop.getDescription(),
                DateTimeUtils.getTime(getWorkshopDate(workshop)),
                workshop.getSkillLevel(),
                workshop.getMapUrl());
    }

    private String getWorkshopDate(Workshop workshop) {
        String seconds = String.valueOf(workshop.getStartTime().get("seconds"));
        Long sec = Long.parseLong(seconds);

        return new Date(sec * 1000).toString();
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

    private void loadWorkshops() {
        WorkshopsTask workshopsTask = new WorkshopsTask(getApplicationContext(), new ListResponseListener<Workshop>() {
            @Override
            public void onStart() {
                mEmptyScreenView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<Workshop> response) {
                String lastStartTime = null;
                int numWorkshops = response.size();
                if (numWorkshops == 0) {
                    mEmptyScreenView.setVisibility(View.VISIBLE);
                }

                Collections.sort(response);
                
                for (int i = 0; i < numWorkshops; i++) {
                    Workshop currWorkshop = response.get(i);

                    if (Workshop.isValid(currWorkshop)) {
                        String currStartTime = getWorkshopDate(currWorkshop);

                        if (i == 0 || DateTimeUtils.daysAreDifferent(lastStartTime, currStartTime)) {
                            addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime));
                            lastStartTime = currStartTime;
                        }

                        addWorkshopCard(currWorkshop);
                        workshops.add(currWorkshop);
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

        workshopsTask.retrieveWorkshops();
    }

    private void clearWorkshops() {
        mViewTypeList.clear();
        mSubSectionTitleList.clear();
        mCardImageList.clear();
        mCardTitleList.clear();
        mCardBodyList.clear();
        mCardTimestampList.clear();
        mCardFooterList.clear();
        mCardMapEventList.clear();
        horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView = findViewById(R.id.workshops_horizontal_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        horizontalSectionCardRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this, mViewTypeList,
                        mSubSectionTitleList, mCardImageList, mCardTitleList, mCardSideSubtitleList,
                        mCardSubtitleList, mCardTagSubtitleList, mCardBodyList, mCardTimestampList, mCardFooterList, mCardMapEventList, TAG);
        recyclerView.setAdapter(horizontalSectionCardRecyclerViewAdapter);

        // Recycler Filter Search Bar
        mFilterSearchLinearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFilterSearchLinearLayoutManager.setStackFromEnd(true);
        mFilterSearchRecyclerView = findViewById(R.id.shared_horizontal_filter_search_component_container);
        mFilterSearchRecyclerView.setLayoutManager(mFilterSearchLinearLayoutManager);

        if (filters == null)
            sharedFilterSearchComponent_RecyclerViewAdapter =
                    new SharedFilterSearchComponent_RecyclerViewAdapter(this, mFilterSearchImageList, mSearchFilterTypeList);

        else
            sharedFilterSearchComponent_RecyclerViewAdapter =
                    new SharedFilterSearchComponent_RecyclerViewAdapter(this, mFilterSearchImageList, mSearchFilterTypeList, new SearchFilterListener() {
                        @Override
                        public void setSearchFilters(SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder holder, int position) {
                            filterScheduleEventsByType(holder.mSearchFilterType);
                        }
                    });

        mFilterSearchRecyclerView.setAdapter(sharedFilterSearchComponent_RecyclerViewAdapter);
    }

    private ArrayList<Workshop> getWorkshopsByType(String type) {
        if (type.equals(ALLFILTER)) {
            return workshops;
        }

        ArrayList<Workshop> workshops = new ArrayList<>();
        int numWorkshops = this.workshops.size();

        for (int i = 0; i < numWorkshops; i++) {
            Workshop workshop = this.workshops.get(i);

            if (workshop.getWorkshopType().equals(type) || workshop.getSkillLevel().equals(type)) {
                workshops.add(workshop);
            }
        }

        return workshops;
    }

    private void filterScheduleEventsByType(String workshopType) {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyScreenView.setVisibility(View.GONE);
        clearWorkshops();

        String lastStartTime = null;
        ArrayList<Workshop> workshops = getWorkshopsByType(workshopType);
        int numWorkshops = workshops.size();

        for (int i = 0; i < numWorkshops; i++) {
            Workshop currWorkshop = workshops.get(i);
            String currStartTime = getWorkshopDate(currWorkshop);

            if (i == 0 || DateTimeUtils.daysAreDifferent(lastStartTime, currStartTime)) {
                addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime));
                lastStartTime = currStartTime;
            }

            addWorkshopCard(currWorkshop);
        }

        if (numWorkshops == 0) {
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



