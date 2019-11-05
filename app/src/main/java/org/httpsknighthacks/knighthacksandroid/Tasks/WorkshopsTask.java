package org.httpsknighthacks.knighthacksandroid.Tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WorkshopsTask {

    public static final String WORKSHOPS_COLLECTION = "workshops";

    private WeakReference<Context> mContext;
    private ResponseListener<Workshop> mResponseListener;

    private DatabaseReference mReference;

    public WorkshopsTask(Context context, ResponseListener<Workshop> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mResponseListener = responseListener;
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
                mResponseListener.onSuccess(workshops);
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
