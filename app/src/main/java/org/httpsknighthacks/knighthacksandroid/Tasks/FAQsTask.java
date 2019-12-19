package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.FAQ;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FAQsTask {

    public static final String TAG = FAQsTask.class.getSimpleName();
    public static final String FAQS_COLLECTION = "faqs";

    private WeakReference<Context> mContext;
    private ArrayList<FAQ> mFAQs;
    private ResponseListener<FAQ> mResponseListener;
    private DatabaseReference mReference;

    public FAQsTask(Context context, ResponseListener<FAQ> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mFAQs = new ArrayList<>();
        this.mResponseListener = responseListener;
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
                mResponseListener.onSuccess(faqs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mResponseListener.onFailure();
            }
        });
    }

    public void showLoading() {
        mResponseListener.onStart();
    }

    public Context getContext() {
        return mContext.get();
    }
}
