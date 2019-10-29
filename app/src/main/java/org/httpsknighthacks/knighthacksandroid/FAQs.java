package org.httpsknighthacks.knighthacksandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    private ArrayList<String> mCardOptionalImageList;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private VerticalSectionCard_RecyclerViewAdapter verticalSectionCardRecyclerViewAdapter;

    private ProgressBar mProgressBar;
    private View mEmptyScreenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        mCardImageList = new ArrayList<>();
        mCardTitleList = new ArrayList<>();
        mCardSubtitleList = new ArrayList<>();
        mCardDetailsList = new ArrayList<>();
        mCardOptionalImageList = new ArrayList<>();

        mProgressBar = findViewById(R.id.faqs_progress_bar);
        mEmptyScreenView = findViewById(R.id.faqs_empty_screen_view);

        loadFAQs();
        loadRecyclerView();
    }

    private void loadFAQs() {
        FAQsTask faqsTask = new FAQsTask(getApplicationContext(), new ResponseListener<FAQ>() {
            @Override
            public void onStart() {
                mEmptyScreenView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<FAQ> response) {
                int numFAQs = response.size();
                for (int i = 0; i < numFAQs; i++) {
                    FAQ currFAQ = response.get(i);

                    if (FAQ.isValid(currFAQ)) {
                        mCardTitleList.add(currFAQ.getQuestionOptional().getValue());
                        mCardDetailsList.add(currFAQ.getAnswerOptional().getValue());
                    }
                }

                verticalSectionCardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete(ArrayList<FAQ> response) {
                if (response.size() == 0) {
                    mEmptyScreenView.setVisibility(View.VISIBLE);
                }

                mProgressBar.setVisibility(View.GONE);
            }
        });

        faqsTask.execute();
    }

    private void loadRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.faqs_vertical_section_card_container);
        recyclerView.setLayoutManager(linearLayoutManager);

        verticalSectionCardRecyclerViewAdapter =
                new VerticalSectionCard_RecyclerViewAdapter(this, mCardImageList,
                        mCardTitleList, mCardSubtitleList, mCardDetailsList, mCardOptionalImageList, TAG);
        recyclerView.setAdapter(verticalSectionCardRecyclerViewAdapter);
    }
}
