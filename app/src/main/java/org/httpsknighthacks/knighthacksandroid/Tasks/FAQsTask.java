package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.FAQ;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FAQsTask {

    public static final String TAG = FAQsTask.class.getSimpleName();
    public static final String FAQS_COLLECTION = "faqs";

    private WeakReference<Context> mContext;
    private ArrayList<FAQ> mFAQs;
    private ListResponseListener<FAQ> mListResponseListener;
    private DatabaseReference mReference;

    public FAQsTask(Context context, ListResponseListener<FAQ> listResponseListener) {
        this.mContext = new WeakReference<>(context);
        this.mFAQs = new ArrayList<>();
        this.mListResponseListener = listResponseListener;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public void retrieveFAQs() {
        showLoading();
        mReference.child(FAQS_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<FAQ> faqs = new ArrayList<>();
                for (DataSnapshot workshopDataSnapshot : dataSnapshot.getChildren()) {
                    FAQ faq = workshopDataSnapshot.getValue(FAQ.class);
                    faqs.add(faq);
                }
                mListResponseListener.onSuccess(faqs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mListResponseListener.onFailure();
            }
        });
    }

    public void showLoading() {
        mListResponseListener.onStart();
    }

    public Context getContext() {
        return mContext.get();
    }
}
