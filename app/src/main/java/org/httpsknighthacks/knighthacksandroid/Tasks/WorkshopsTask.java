package org.httpsknighthacks.knighthacksandroid.Tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WorkshopsTask extends AsyncTask<Void, Void, ArrayList<Workshop>> {

    public static final String TAG = WorkshopsTask.class.getSimpleName();
    public static final String GET_WORKSHOPS_ROUTE = "/api/get_workshops";

    private WeakReference<Context> mContext;
    private ArrayList<Workshop> mWorkshops;
    private ResponseListener<Workshop> mResponseListener;

    public WorkshopsTask(Context context, ResponseListener<Workshop> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mWorkshops = new ArrayList<>();
        this.mResponseListener = responseListener;
    }

    @Override
    protected void onPreExecute() {
        mResponseListener.onStart();
    }

    @Override
    protected ArrayList<Workshop> doInBackground(Void... voids) {
        String requestURL = RequestQueueSingleton.REQUEST_API_PREFIX_URL + GET_WORKSHOPS_ROUTE;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int numWorkshops = response.length();

                for (int i = 0; i < numWorkshops; i++) {
                    try {
                        mWorkshops.add(new Workshop(response.getJSONObject(i)));
                    } catch (JSONException ex) {
                        Toast.makeText(getContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    }
                }

                mResponseListener.onSuccess(mWorkshops);
                mResponseListener.onComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResponseListener.onFailure();
                mResponseListener.onComplete();
            }
        });

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request, TAG);

        return mWorkshops;
    }

    @Override
    protected void onPostExecute(ArrayList<Workshop> workshops) {
        mResponseListener.onComplete();
    }

    public Context getContext() {
        return mContext.get();
    }
}
