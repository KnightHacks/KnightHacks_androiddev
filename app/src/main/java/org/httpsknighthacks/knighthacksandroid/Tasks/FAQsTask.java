package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.httpsknighthacks.knighthacksandroid.Models.FAQ;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FAQsTask extends AsyncTask<Void, Void, ArrayList<FAQ>> {

    public static final String TAG = FAQsTask.class.getSimpleName();
    public static final String GET_FAQS_ROUTE = "/FAQs";

    private WeakReference<Context> mContext;
    private ArrayList<FAQ> mFAQs;
    private ResponseListener<FAQ> mResponseListener;

    public FAQsTask(Context context, ResponseListener<FAQ> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mFAQs = new ArrayList<>();
        this.mResponseListener = responseListener;
    }

    @Override
    protected void onPreExecute() {
        mResponseListener.onStart();
    }

    @Override
    protected ArrayList<FAQ> doInBackground(Void... voids) {
        String requestURL = RequestQueueSingleton.REQUEST_API_PREFIX_URL + GET_FAQS_ROUTE;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int numFAQs = response.length();

                for (int i = 0; i < numFAQs; i++) {
                    try {
                        mFAQs.add(new FAQ(response.getJSONObject(i)));
                    } catch (JSONException ex) {
                        Toast.makeText(getContext(), RequestQueueSingleton.REQUEST_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    }
                }

                mResponseListener.onSuccess(mFAQs);
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse (VolleyError error){
                mResponseListener.onFailure();
            }
        });

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request, TAG);
        return mFAQs;
    }

    @Override
    protected void onPostExecute(ArrayList<FAQ> faqs) {
        mResponseListener.onComplete(faqs);
    }

    public Context getContext() {
        return mContext.get();
    }
}
