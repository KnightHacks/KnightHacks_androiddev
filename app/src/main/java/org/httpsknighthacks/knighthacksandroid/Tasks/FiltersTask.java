package org.httpsknighthacks.knighthacksandroid.Tasks;


import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.Filter;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FiltersTask {

    public static final String FILTERS_COLLECTION = "filters";

    private WeakReference<Context> mContext;
    private ListResponseListener<Filter> mListResponseListener;

    private DatabaseReference mReference;


    public FiltersTask(Context context, ListResponseListener<Filter> listResponseListener) {
        this.mContext = new WeakReference<>(context);
        this.mListResponseListener = listResponseListener;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public void retrieveFilters() {
        showLoading();
        mReference.child(FILTERS_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Filter> filters = new ArrayList<>();
                for (DataSnapshot workshopDataSnapshot : dataSnapshot.getChildren()) {
                    Filter filter = workshopDataSnapshot.getValue(Filter.class);
                    filters.add(filter);
                }
                mListResponseListener.onSuccess(filters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mListResponseListener.onFailure();
            }
        });
    }

    private void showLoading() {
        mListResponseListener.onStart();
    }

    public Context getContext() {
        return mContext.get();
    }
}
