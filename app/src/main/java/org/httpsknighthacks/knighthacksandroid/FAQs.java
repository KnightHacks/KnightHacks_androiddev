package org.httpsknighthacks.knighthacksandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.httpsknighthacks.knighthacksandroid.Models.FAQ;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.httpsknighthacks.knighthacksandroid.Tasks.FAQsTask;

import java.util.ArrayList;

public class FAQs extends AppCompatActivity {

    public static final String TAG = FAQs.class.getSimpleName();
    private ArrayList<String> mCardImageList;
    private ArrayList<String> mCardTitleList;
    private ArrayList<String> mCardSubtitleList;
    private ArrayList<String> mCardDetailsList;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private VerticalSectionCard_RecyclerViewAdapter horizontalSectionCardRecyclerViewAdapter;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mProgressBar = findViewById(R.id.faqs_progress_bar);
        mCardDetailsList = new ArrayList<>();

        loadFAQs();
        loadRecyclerView();
    }

    private void loadFAQs() {
        FAQsTask faqsTask = new FAQsTask(getApplicationContext(), new ResponseListener<FAQ>() {
            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<FAQ> response) {
                mProgressBar.setVisibility(View.GONE);

                int numFAQs = response.size();
                for (int i = 0; i < numFAQs; i++) {
                    FAQ currFAQ = response.get(i);

                    if (FAQ.isValid(currFAQ)) {
                        mCardTitleList.add(currFAQ.getQuestionOptional().getValue());
                        mCardSubtitleList.add(currFAQ.getAnswerOptional().getValue());
                    }
                }

                horizontalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }
        });

        faqsTask.execute();
    }

    private void loadRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.faqs_vertical_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        horizontalSectionCardRecyclerViewAdapter =
                new VerticalSectionCard_RecyclerViewAdapter(this, mCardImageList,
                        mCardTitleList, mCardSubtitleList, mCardDetailsList, TAG);
        recyclerView.setAdapter(horizontalSectionCardRecyclerViewAdapter);
    }
}
