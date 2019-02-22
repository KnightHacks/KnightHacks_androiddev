package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.httpsknighthacks.knighthacksandroid.Models.Sponsor;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SponsorsTask extends AsyncTask<Void, Void, ArrayList<Sponsor>> {

    public static final String TAG = SponsorsTask.class.getSimpleName();
    public static final String GET_SPONSORS_ROUTE = "/api/get_sponsors";

    private WeakReference<Context> mContext;
    private ArrayList<Sponsor> mSponsors;
    private ResponseListener<Sponsor> mResponseListener;

    public SponsorsTask(Context context, ResponseListener<Sponsor> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mSponsors = new ArrayList<>();
        this.mResponseListener = responseListener;
    }

    @Override
    protected void onPreExecute() {
        mResponseListener.onStart();
    }

    @Override
    protected ArrayList<Sponsor> doInBackground(Void... voids) {
        String requestURL = RequestQueueSingleton.REQUEST_API_PREFIX_URL + GET_SPONSORS_ROUTE;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int numSponsors = response.length();

                for (int i = 0; i < numSponsors; i++) {
                    try {
                        mSponsors.add(new Sponsor(response.getJSONObject(i)));
                    } catch (JSONException ex) {
                        Toast.makeText(getContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    }
                }

                mResponseListener.onSuccess(mSponsors);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResponseListener.onFailure();
            }
        });

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request, TAG);
        return mSponsors;
    }

    @Override
    protected void onPostExecute(ArrayList<Sponsor> sponsors) {
        mResponseListener.onComplete(sponsors);
    }

    public Context getContext() {
        return mContext.get();
    }
}
