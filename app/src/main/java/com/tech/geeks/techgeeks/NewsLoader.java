package com.tech.geeks.techgeeks;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Anku on 4/14/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /** Tag for log messages*/
    private static final String LOG_TAG = NewsLoader.class.getSimpleName();

    /** Query URL*/
    private String mUrl;

    /**
     * Constructs a new NewsLoader object
     *
     * @param context of the activity
     * @param url to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        // This will call the loadInBackground method
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        // If mUrl is not null then call QueryUtils.fetchNewsData method to
        // get a list of News Objects and return it
        List<News> newsList = QueryUtils.fetchNewsData(mUrl);
        return newsList;
    }
}
