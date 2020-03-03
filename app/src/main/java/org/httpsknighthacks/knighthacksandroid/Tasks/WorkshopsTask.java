package org.httpsknighthacks.knighthacksandroid.Tasks;


import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WorkshopsTask {

    public static final String WORKSHOPS_COLLECTION = "workshops";

    private WeakReference<Context> mContext;
    private ListResponseListener<Workshop> mListResponseListener;

    private DatabaseReference mReference;

    public WorkshopsTask(Context context, ListResponseListener<Workshop> listResponseListener) {
        this.mContext = new WeakReference<>(context);
        this.mListResponseListener = listResponseListener;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public void retrieveWorkshops() {
        showLoading();
        mReference.child(WORKSHOPS_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Workshop> workshops = new ArrayList<>();
                for (DataSnapshot workshopDataSnapshot : dataSnapshot.getChildren()) {
                    Workshop workshop = workshopDataSnapshot.getValue(Workshop.class);
                    workshops.add(workshop);
                }
                mListResponseListener.onSuccess(workshops);
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
