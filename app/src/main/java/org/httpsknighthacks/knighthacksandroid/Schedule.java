package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.Optional;
import org.httpsknighthacks.knighthacksandroid.Models.ScheduleEvent;
import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
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
    private ArrayList<String> mFilterSearchTextList;
    private ArrayList<String> mFilterSearchImageList;
    private ArrayList<String> mCardFooterList;

    private LinearLayoutManager scheduleEventsLinearLayoutManager;
    private RecyclerView scheduleEventsRecyclerView;
    private HorizontalSectionCard_RecyclerViewAdapter scheduleEventsRecyclerViewAdapter;


    private ProgressBar mProgressBar;

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
        mFilterSearchTextList = new ArrayList<>();
        mFilterSearchImageList = new ArrayList<>();
        mCardFooterList = new ArrayList<>();
        mProgressBar = findViewById(R.id.schedule_progress_bar);

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

    private void addScheduleEvent(String title, String location, String time) {
        addHorizontalSectionCard(null,
                title,
                null,
                location,
                null,
                null,
                null,
                time,
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
                mProgressBar.setVisibility(View.GONE);

                Optional<String> lastStartTime = Optional.empty();
                int numEvents = response.size();

                for (int i = 0; i < numEvents; i++) {
                    ScheduleEvent currEvent = response.get(i);

                    if (ScheduleEvent.isValid(currEvent)) {

                        String currStartTime = currEvent.getStartTimeOptional().getValue();

                        if ((lastStartTime.isPresent() && !lastStartTime.getValue().equals(currStartTime)) || i == 0) {
                            addSubSectionTitle(DateTimeUtils.getWeekDayString(currStartTime));
                        }

                        addScheduleEvent(currEvent.getTitleOptional().getValue(),
                                currEvent.getLocationOptional().getValue(),
                                DateTimeUtils.getTime(currStartTime));
                    }
                }

                scheduleEventsRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }
        });

        scheduleEventsTask.execute();
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
