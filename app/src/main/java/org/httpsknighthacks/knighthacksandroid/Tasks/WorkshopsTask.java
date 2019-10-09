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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    private FirebaseFirestore mFirestore;

    public WorkshopsTask(Context context, ResponseListener<Workshop> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mResponseListener = responseListener;
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void retrieveWorkshops() {
        showLoading();
        mFirestore.collection(WORKSHOPS_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Workshop> workshops = new ArrayList<>();
                        if (task.isSuccessful()) {
                            workshops = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                workshops.add(document.toObject(Workshop.class));
                            }
                            mResponseListener.onSuccess(workshops);
                        } else {
                            mResponseListener.onFailure();
                        }
                        mResponseListener.onComplete(workshops);
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
