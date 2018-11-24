package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.httpsknighthacks.knighthacksandroid.Models.ScheduleEvent;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ScheduleEventsTask extends AsyncTask<Void, Void, ArrayList<ScheduleEvent>> {

    public static final String TAG = ScheduleEventsTask.class.getSimpleName();
    public static final String GET_SCHEDULE_ROUTE = "/api/get_schedule";

    private WeakReference<Context> mContext;
    private ArrayList<ScheduleEvent> mScheduleEvents;
    private ResponseListener<ScheduleEvent> mResponseListener;

    public ScheduleEventsTask(Context context, ResponseListener<ScheduleEvent> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mScheduleEvents = new ArrayList<>();
        this.mResponseListener = responseListener;
    }

    @Override
    protected void onPreExecute() {
        mResponseListener.onStart();
    }

    @Override
    protected ArrayList<ScheduleEvent> doInBackground(Void... voids) {
        String requestURL = RequestQueueSingleton.REQUEST_API_PREFIX_URL + GET_SCHEDULE_ROUTE;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int numEvents = response.length();

                for (int i = 0; i < numEvents; i++) {
                    try {
                        mScheduleEvents.add(new ScheduleEvent(response.getJSONObject(i)));
                    } catch (JSONException ex) {
                        Toast.makeText(getContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    }
                }

                mResponseListener.onSuccess(mScheduleEvents);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResponseListener.onFailure();
            }
        });

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request, TAG);

        return mScheduleEvents;
    }

    public Context getContext() {
        return mContext.get();
    }
}
