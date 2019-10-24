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

import org.httpsknighthacks.knighthacksandroid.Models.ScheduleEvent;
import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ScheduleEventsTask {

    public static final String TAG = ScheduleEventsTask.class.getSimpleName();
    public static final String EVENTS_COLLECTION = "events";

    private WeakReference<Context> mContext;
    private ArrayList<ScheduleEvent> mScheduleEvents;
    private ResponseListener<ScheduleEvent> mResponseListener;

    private DatabaseReference mReference;

    public ScheduleEventsTask(Context context, ResponseListener<ScheduleEvent> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mScheduleEvents = new ArrayList<>();
        this.mResponseListener = responseListener;
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
                mResponseListener.onSuccess(events);
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
