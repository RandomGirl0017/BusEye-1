package com.example.lucas.buseye.model;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class OlhoVivo extends Application {
    private RequestQueue mRequestQueue;
    private static OlhoVivo mInstance;
    public static final String TAG = OlhoVivo.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static synchronized OlhoVivo getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        //RequestQueue.getCache().clear();
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }
}