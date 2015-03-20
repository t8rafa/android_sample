package t8studio.com.br.worker.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by rafael on 19/03/15.
 */
public class ConnectionManager {
    private static ConnectionManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private ConnectionManager(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized ConnectionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ConnectionManager(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
