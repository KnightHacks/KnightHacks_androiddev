package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.httpsknighthacks.knighthacksandroid.Models.LiveUpdate;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LiveUpdatesTask {

    public static final String TAG = LiveUpdatesTask.class.getSimpleName();
    public static final String GET_LIVE_UPDATES_ROUTE = "/api/get_live_updates";

    private WeakReference<Context> mContext;
    private ArrayList<LiveUpdate> mLiveUpdates;
    private ResponseListener<LiveUpdate> mResponseListener;

    public LiveUpdatesTask(Context context, ResponseListener<LiveUpdate> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mLiveUpdates = new ArrayList<>();
        this.mResponseListener = responseListener;
    }

    public Context getContext() {
        return mContext.get();
    }
}