package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.ScheduleEvent;
import org.httpsknighthacks.knighthacksandroid.Resources.ListResponseListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ScheduleEventsTask {

    public static final String TAG = ScheduleEventsTask.class.getSimpleName();
    public static final String EVENTS_COLLECTION = "events";

    private WeakReference<Context> mContext;
    private ListResponseListener<ScheduleEvent> mListResponseListener;

    private DatabaseReference mReference;

    public ScheduleEventsTask(Context context, ListResponseListener<ScheduleEvent> listResponseListener) {
        this.mContext = new WeakReference<>(context);
        this.mListResponseListener = listResponseListener;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public void retrieveScheduleEvents() {
        showLoading();
        mReference.child(EVENTS_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ScheduleEvent> events = new ArrayList<>();
                for (DataSnapshot workshopDataSnapshot : dataSnapshot.getChildren()) {
                    ScheduleEvent event = workshopDataSnapshot.getValue(ScheduleEvent.class);
                    events.add(event);
                }
                mListResponseListener.onSuccess(events);
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
