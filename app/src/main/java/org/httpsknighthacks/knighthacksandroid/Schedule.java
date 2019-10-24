package org.httpsknighthacks.knighthacksandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.httpsknighthacks.knighthacksandroid.Models.Filter;
import org.httpsknighthacks.knighthacksandroid.Models.Optional;
import org.httpsknighthacks.knighthacksandroid.Models.ScheduleEvent;
import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Resources.SearchFilterListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.FiltersTask;
import org.httpsknighthacks.knighthacksandroid.Tasks.ScheduleEventsTask;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {

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

    private LinearLayoutManager scheduleEventsLinearLayoutManager;
    private RecyclerView scheduleEventsRecyclerView;
    private HorizontalSectionCard_RecyclerViewAdapter scheduleEventsRecyclerViewAdapter;
    private LinearLayoutManager searchFilterLinearLayoutManager;
    private RecyclerView searchFilterRecyclerView;
    private SharedFilterSearchComponent_RecyclerViewAdapter searchFilterRecyclerViewAdapter;

    private ProgressBar mProgressBar;
    private View mEmptyScreenView;

    private ArrayList<Filter> filters;
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
        mCardTagSubtitleList = new ArrayList<>();
        mCardBodyList = new ArrayList<>();
        mCardTimestampList = new ArrayList<>();
        mCardFooterList = new ArrayList<>();

        mFilterSearchImageList = new ArrayList<>();
        mSearchFilterTypeList = new ArrayList<>();

        mProgressBar = findViewById(R.id.schedule_progress_bar);
        mEmptyScreenView = findViewById(R.id.schedule_empty_screen_view);

        scheduleEvents = new ArrayList<>();

        loadFilters();
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


    private void addHorizontalSectionCard(String imageUrl, String cardTitle, String cardSideSubtitle, String cardSubtitle, String cardTagSubtitle, String cardBody, String cardTimestamp, String cardFooter) {
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

        if (cardTagSubtitle != null && !cardTagSubtitle.isEmpty()) {
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

    private void addScheduleEventCard(ScheduleEvent event) {
        addHorizontalSectionCard(null,
                event.getTitle(),
                null,
                event.getLocation(),
                null,
                null,
                DateTimeUtils.getTime(event.getStartTime().toDate().toString()),
                null);
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

    private void loadSchedule() {
        ScheduleEventsTask scheduleEventsTask = new ScheduleEventsTask(getApplicationContext(), new ResponseListener<ScheduleEvent>() {
            @Override
            public void onStart() {
                mEmptyScreenView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<ScheduleEvent> response) {
                String lastStartTime = null;
                int numEvents = response.size();

                if (numEvents == 0) {
                    mEmptyScreenView.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < numEvents; i++) {
                    ScheduleEvent currEvent = response.get(i);
                    String currStartTime = currEvent.getStartTime().toDate().toString();

                    if (i == 0 || DateTimeUtils.daysAreDifferent(lastStartTime, currStartTime)) {
                        addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime));
                        lastStartTime = currStartTime;
                    }

                    addScheduleEventCard(currEvent);
                }

                scheduleEvents = response;
                scheduleEventsRecyclerViewAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }
        });

        scheduleEventsTask.retrieveScheduleEvents();
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
        scheduleEventsLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        scheduleEventsRecyclerView = findViewById(R.id.schedule_horizontal_section_card_container);
        scheduleEventsRecyclerView.setLayoutManager(scheduleEventsLinearLayoutManager);

        scheduleEventsRecyclerViewAdapter =
                new HorizontalSectionCard_RecyclerViewAdapter(this, mViewTypeList,
                        mSubSectionTitleList, mCardImageList, mCardTitleList, mCardSideSubtitleList,
                        mCardSubtitleList, mCardTagSubtitleList, mCardBodyList, mCardTimestampList, mCardFooterList);
      
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
        mEmptyScreenView.setVisibility(View.GONE);
        clearScheduleEvents();

        String lastStartTime = null;
        ArrayList<ScheduleEvent> events = getScheduleEventsByType(eventType);
        int numEvents = events.size();

        for (int i = 0; i < numEvents; i++) {
            ScheduleEvent currEvent = events.get(i);
            String currStartTime = currEvent.getStartTime().toDate().toString();

            if (i == 0 || DateTimeUtils.daysAreDifferent(lastStartTime, currStartTime)) {
                addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime));
                lastStartTime = currStartTime;
            }

            addScheduleEventCard(currEvent);
        }

        if (numEvents == 0) {
            mEmptyScreenView.setVisibility(View.VISIBLE);
        }

        mProgressBar.setVisibility(View.GONE);
        scheduleEventsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getFilterSearchComponents() {
        mFilterSearchImageList.add(R.drawable.ic_schedule_food);
        mSearchFilterTypeList.add(SearchFilterTypes.FOOD);

        mFilterSearchImageList.add(R.drawable.ic_schedule_talk);
        mSearchFilterTypeList.add(SearchFilterTypes.TALK);

        mFilterSearchImageList.add(R.drawable.ic_schedule_workshop);
        mSearchFilterTypeList.add(SearchFilterTypes.WORKSHOP);

        mFilterSearchImageList.add(R.drawable.ic_schedule_main_events);
        mSearchFilterTypeList.add(SearchFilterTypes.MAIN_EVENTS);

        mFilterSearchImageList.add(R.drawable.ic_filter_all);
        mSearchFilterTypeList.add(SearchFilterTypes.ALL);
    }
}
