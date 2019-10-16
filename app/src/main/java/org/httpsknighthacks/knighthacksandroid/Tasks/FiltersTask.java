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

import org.httpsknighthacks.knighthacksandroid.Models.Filter;
import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FiltersTask {

    public static final String FILTERS_COLLECTION = "filters";

    private WeakReference<Context> mContext;
    private ResponseListener<Filter> mResponseListener;

    private FirebaseFirestore mFirestore;

    public FiltersTask(Context context, ResponseListener<Filter> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mResponseListener = responseListener;
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void retrieveFilters() {
        showLoading();
        mFirestore.collection(FILTERS_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Filter> filters = new ArrayList<>();
                        if (task.isSuccessful()) {
                            filters = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                filters.add(document.toObject(Filter.class));
                            }
                            mResponseListener.onSuccess(filters);
                        } else {
                            mResponseListener.onFailure();
                        }
                        mResponseListener.onComplete(filters);
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
