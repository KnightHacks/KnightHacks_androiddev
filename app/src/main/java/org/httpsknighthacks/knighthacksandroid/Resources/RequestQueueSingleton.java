package org.httpsknighthacks.knighthacksandroid.Resources;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;

public class RequestQueueSingleton {

    public static final String TAG = RequestQueueSingleton.class.getSimpleName();
    public static final String REQUEST_API_PREFIX_URL = "http://test-knight-hacks.appspot.com";
    public static final String REQUEST_ERROR_MESSAGE = "Oops! Something went wrong on our end -- please try again.";

    private static RequestQueueSingleton sInstance;
    private RequestQueue mRequestQueue;
    private WeakReference<Context> mContext;

    public RequestQueueSingleton(Context context) {
        this.mContext = new WeakReference<>(context);
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
            mRequestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public Context getContext() {
        return mContext.get();
    }
}
