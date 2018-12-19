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

public class LiveUpdatesTask extends AsyncTask<Void, Void, ArrayList<LiveUpdate>> {

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

    @Override
    protected void onPreExecute() {
        mResponseListener.onStart();
    }

    @Override
    protected ArrayList<LiveUpdate> doInBackground(Void... voids) {
        String requestURL = RequestQueueSingleton.REQUEST_API_PREFIX_URL + GET_LIVE_UPDATES_ROUTE;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int numUpdates = response.length();

                for (int i = 0; i < numUpdates; i++) {
                    try {
                        mLiveUpdates.add(new LiveUpdate(response.getJSONObject(i)));
                    } catch (JSONException ex) {
                        Toast.makeText(getContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    }
                }

                mResponseListener.onSuccess(mLiveUpdates);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResponseListener.onFailure();
            }
        });

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request, TAG);
        return mLiveUpdates;
    }

    @Override
    protected void onPostExecute(ArrayList<LiveUpdate> liveUpdates) {
        mResponseListener.onComplete();
    }

    public Context getContext() {
        return mContext.get();
    }
}