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

import org.httpsknighthacks.knighthacksandroid.Models.LiveUpdate;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LiveUpdatesTask {

    public static final String TAG = LiveUpdatesTask.class.getSimpleName();
    public static final String UPDATES_COLLECTION = "live_updates";

    private WeakReference<Context> mContext;
    private ResponseListener<LiveUpdate> mResponseListener;

    private DatabaseReference mReference;
    public LiveUpdatesTask(Context context, ResponseListener<LiveUpdate> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mResponseListener = responseListener;

        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public void retrieveUpdates() {
        showLoading();
        mReference.child(UPDATES_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<LiveUpdate> updates = new ArrayList<>();
                for (DataSnapshot liveUpdateDataSnapshot : dataSnapshot.getChildren()) {
                    LiveUpdate event = liveUpdateDataSnapshot.getValue(LiveUpdate.class);
                    updates.add(event);
                }
                mResponseListener.onSuccess(updates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mResponseListener.onFailure();
            }
        });
    }

    private void showLoading() {
        mResponseListener.onStart();
    }

    public Context getContext() {
        return mContext.get();
    }
}