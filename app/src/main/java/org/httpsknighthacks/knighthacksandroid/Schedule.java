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
import org.httpsknighthacks.knighthacksandroid.Models.ScheduleEvent;
import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.ScheduleEventsTask;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

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

    private LinearLayoutManager scheduleEventsLinearLayoutManager;
    private RecyclerView scheduleEventsRecyclerView;
    private HorizontalSectionCard_RecyclerViewAdapter scheduleEventsRecyclerViewAdapter;
    private LinearLayoutManager searchFilterLinearLayoutManager;
    private RecyclerView searchFilterRecyclerView;
    private SharedFilterSearchComponent_RecyclerViewAdapter searchFilterRecyclerViewAdapter;

    private ProgressBar mProgressBar;

    private ArrayList<ScheduleEvent> scheduleEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

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

        mProgressBar = findViewById(R.id.schedule_progress_bar);

        scheduleEvents = new ArrayList<>();

        loadSchedule();
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

    private void addScheduleEventCard(ScheduleEvent event) {
        addHorizontalSectionCard(null,
                event.getTitleOptional().getValue(),
                null,
                event.getLocationOptional().getValue(),
                null,
                null,
                null,
                DateTimeUtils.getTime(event.getStartTimeOptional().getValue()),
                null);
    }

    private void loadSchedule() {
        ScheduleEventsTask scheduleEventsTask = new ScheduleEventsTask(getApplicationContext(), new ResponseListener<ScheduleEvent>() {
            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<ScheduleEvent> response) {
                Optional<String> lastStartTime = Optional.empty();
                int numEvents = response.size();

                for (int i = 0; i < numEvents; i++) {
                    ScheduleEvent currEvent = response.get(i);

                    if (ScheduleEvent.isValid(currEvent)) {

                        Optional<String> currStartTime = currEvent.getStartTimeOptional();

                        if (!lastStartTime.isPresent() || (lastStartTime.isPresent() && DateTimeUtils.daysAreDifferent(lastStartTime.getValue(), currStartTime.getValue()))) {
                            addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime.getValue()));
                            lastStartTime = currStartTime;
                        }

                        addScheduleEventCard(currEvent);
                        scheduleEvents.add(currEvent);
                    }
                }

                scheduleEventsRecyclerViewAdapter.notifyDataSetChanged();
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

        scheduleEventsTask.execute();
    }

    private void clearScheduleEvents() {
        mViewTypeList.clear();
        mSubSectionTitleList.clear();
        mCardTitleList.clear();
        mCardSubtitleList.clear();
        mCardSideSubtitleList.clear();
        mCardTimestampList.clear();
        scheduleEventsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadRecyclerView() {
        scheduleEventsLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scheduleEventsRecyclerView = findViewById(R.id.schedule_horizontal_section_card_container);
        scheduleEventsRecyclerView.setLayoutManager(scheduleEventsLinearLayoutManager);

        scheduleEventsRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this, mViewTypeList,
                        mSubSectionTitleList, mCardImageList, mCardTitleList, mCardSideSubtitleList,
                        mCardSubtitleList, mCardFirstTextTagList, mCardSecondTextTagList, mCardBodyList, mCardTimestampList, mCardFooterList);
      
        scheduleEventsRecyclerView.setAdapter(scheduleEventsRecyclerViewAdapter);

        // Recycler Filter Search Bar
        searchFilterLinearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        searchFilterLinearLayoutManager.setStackFromEnd(true);
        searchFilterRecyclerView = findViewById(R.id.shared_horizontal_filter_search_component_container);
        searchFilterRecyclerView.setLayoutManager(searchFilterLinearLayoutManager);

        searchFilterRecyclerViewAdapter  =
                new SharedFilterSearchComponent_RecyclerViewAdapter(this, mFilterSearchImageList, mSearchFilterTypeList, new SearchFilterListener() {
                    @Override
                    public void setSearchFilters(SharedFilterSearchComponent_RecyclerViewAdapter.ViewHolder holder, int position) {
                        filterScheduleEventsByType(holder.mSearchFilterType);
                    }
                });
        searchFilterRecyclerView.setAdapter(searchFilterRecyclerViewAdapter);
    }

    private ArrayList<ScheduleEvent> getScheduleEventsByType(SearchFilterTypes type) {
        if (type.equals(SearchFilterTypes.ALL)) {
            return scheduleEvents;
        }

        ArrayList<ScheduleEvent> events = new ArrayList<>();
        int numEvents = scheduleEvents.size();

        for (int i = 0; i < numEvents; i++) {
            ScheduleEvent event = scheduleEvents.get(i);

            if (event.getEventType().equals(type)) {
                events.add(event);
            }
        }

        return events;
    }

    private void filterScheduleEventsByType(SearchFilterTypes eventType) {
        mProgressBar.setVisibility(View.VISIBLE);
        clearScheduleEvents();

        Optional<String> lastStartTime = Optional.empty();
        ArrayList<ScheduleEvent> events = getScheduleEventsByType(eventType);
        int numEvents = events.size();

        for (int i = 0; i < numEvents; i++) {
            ScheduleEvent currEvent = events.get(i);
            Optional<String> currStartTime = currEvent.getStartTimeOptional();

            if (!lastStartTime.isPresent() || (lastStartTime.isPresent() && DateTimeUtils.daysAreDifferent(lastStartTime.getValue(), currStartTime.getValue()))) {
                addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime.getValue()));
                lastStartTime = currStartTime;
            }

            addScheduleEventCard(currEvent);
        }

        mProgressBar.setVisibility(View.GONE);
        scheduleEventsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getFilterSearchComponents() {
        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_development));
        mSearchFilterTypeList.add(SearchFilterTypes.DEV);

        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_design));
        mSearchFilterTypeList.add(SearchFilterTypes.DESIGN);

        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_talks));
        mSearchFilterTypeList.add(SearchFilterTypes.TALK);

        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_workshops));
        mSearchFilterTypeList.add(SearchFilterTypes.WORKSHOP);

        mFilterSearchImageList.add(getResources().getString(R.string.shared_filter_search_component_all));
        mSearchFilterTypeList.add(SearchFilterTypes.ALL);
    }
}
