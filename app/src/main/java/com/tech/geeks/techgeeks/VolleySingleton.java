package com.tech.geeks.techgeeks;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * VolleySingleton class to be used to get instance of RequestQueue and ImageLoader
 * Singleton format is used so that a single RequestQueue will be used for the lifetime of the app.
 */

public class VolleySingleton {

    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton() {

        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String,Bitmap> mCache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url,bitmap);

            }
        });

    }

    public static VolleySingleton getInstance() {
        if(mInstance == null); {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public RequestQueue getmRequestQueue() {
        return this.mRequestQueue;
    }
}
