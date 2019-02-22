package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.httpsknighthacks.knighthacksandroid.Models.Optional;
import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.WorkshopsTask;

import java.util.ArrayList;

public class Workshops extends AppCompatActivity {

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

    private ArrayList<Workshop> workshops;

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
        mCardFirstTextTagList = new ArrayList<>();
        mCardSecondTextTagList = new ArrayList<>();
        mCardBodyList = new ArrayList<>();
        mCardTimestampList = new ArrayList<>();
        mCardFooterList = new ArrayList<>();

        mFilterSearchImageList = new ArrayList<>();
        mSearchFilterTypeList = new ArrayList<>();

        mProgressBar = findViewById(R.id.workshops_progress_bar);
        mEmptyScreenView = findViewById(R.id.workshops_empty_screen_view);

        workshops = new ArrayList<>();

        loadWorkshops();
        getFilterSearchComponents();
        loadRecyclerView();
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

    private void addWorkshopCard(Workshop workshop) {
        addHorizontalSectionCard(workshop.getPictureOptional().getValue(),
                workshop.getNameOptional().getValue(),
                null,
                null,
                null,
                null,
                workshop.getDescriptionOptional().getValue(),
                DateTimeUtils.getTime(workshop.getStartTimeOptional().getValue()),
                workshop.getSkillLevelOptional().getValue());
    }

    private void loadWorkshops() {
        WorkshopsTask workshopsTask = new WorkshopsTask(getApplicationContext(), new ResponseListener<Workshop>() {
            @Override
            public void onStart() {
                mEmptyScreenView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<Workshop> response) {
                Optional<String> lastStartTime = Optional.empty();
                int numWorkshops = response.size();

                for (int i = 0; i < numWorkshops; i++) {
                    Workshop currWorkshop = response.get(i);

                    if (Workshop.isValid(currWorkshop)) {

                        Optional<String> currStartTime = currWorkshop.getStartTimeOptional();

                        if (!lastStartTime.isPresent() || (lastStartTime.isPresent() && DateTimeUtils.daysAreDifferent(lastStartTime.getValue(), currStartTime.getValue()))) {
                            addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime.getValue()));
                            lastStartTime = currStartTime;
                        }

                        addWorkshopCard(currWorkshop);
                        workshops.add(currWorkshop);
                    }
                }

                horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete(ArrayList<Workshop> response) {
                if (response.size() == 0) {
                    mEmptyScreenView.setVisibility(View.VISIBLE);
                }

                mProgressBar.setVisibility(View.GONE);
            }
        });

        workshopsTask.execute();
    }

    private void clearWorkshops() {
        mViewTypeList.clear();
        mSubSectionTitleList.clear();
        mCardImageList.clear();
        mCardTitleList.clear();
        mCardBodyList.clear();
        mCardTimestampList.clear();
        mCardFooterList.clear();
        horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.workshops_horizontal_section_card_container);
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
                        filterScheduleEventsByType(holder.mSearchFilterType);
                    }
                });
        mFilterSearchRecyclerView.setAdapter(sharedFilterSearchComponent_RecyclerViewAdapter);
    }

    private ArrayList<Workshop> getWorkshopsByType(SearchFilterTypes type) {
        if (type.equals(SearchFilterTypes.ALL)) {
             return workshops;
        }

        ArrayList<Workshop> workshops = new ArrayList<>();
        int numWorkshops = workshops.size();

        for (int i = 0; i < numWorkshops; i++) {
            Workshop workshop = workshops.get(i);

            if (workshop.getWorkshopType().equals(type)) {
                workshops.add(workshop);
            }
        }

        return workshops;
    }

    private void filterScheduleEventsByType(SearchFilterTypes workshopType) {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyScreenView.setVisibility(View.GONE);
        clearWorkshops();

        Optional<String> lastStartTime = Optional.empty();
        ArrayList<Workshop> workshops = getWorkshopsByType(workshopType);
        int numWorkshops = workshops.size();

        for (int i = 0; i < numWorkshops; i++) {
            Workshop currWorkshop = workshops.get(i);
            Optional<String> currStartTime = currWorkshop.getStartTimeOptional();

            if (!lastStartTime.isPresent() || (lastStartTime.isPresent() && DateTimeUtils.daysAreDifferent(lastStartTime.getValue(), currStartTime.getValue()))) {
                addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime.getValue()));
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
        mFilterSearchImageList.add(R.drawable.ic_workshops_career);
        mSearchFilterTypeList.add(SearchFilterTypes.CAREER);

        mFilterSearchImageList.add(R.drawable.ic_workshops_hardware);
        mSearchFilterTypeList.add(SearchFilterTypes.HARDWARE);

        mFilterSearchImageList.add(R.drawable.ic_workshops_design);
        mSearchFilterTypeList.add(SearchFilterTypes.DESIGN);

        mFilterSearchImageList.add(R.drawable.ic_workshops_dev);
        mSearchFilterTypeList.add(SearchFilterTypes.DEV);

        mFilterSearchImageList.add(R.drawable.ic_workshops_advanced);
        mSearchFilterTypeList.add(SearchFilterTypes.ADVANCED);

        mFilterSearchImageList.add(R.drawable.ic_workshops_beginner);
        mSearchFilterTypeList.add(SearchFilterTypes.BEGINNER);

        mFilterSearchImageList.add(R.drawable.ic_filter_all);
        mSearchFilterTypeList.add(SearchFilterTypes.ALL);

    }

}
