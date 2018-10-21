package org.httpsknighthacks.knighthacksandroid.Resources;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {

    public static final String TAG = RequestQueueSingleton.class.getSimpleName();
    public static final String REQUEST_API_PREFIX_URL = "https://test-knight-hacks.appspot.com";

    private static RequestQueueSingleton sInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    public RequestQueueSingleton(Context mContext) {
        this.mContext = mContext;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestQueueSingleton(context);
        }

        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }
}
