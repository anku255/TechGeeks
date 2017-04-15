package com.tech.geeks.techgeeks;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    // Log tag for log messages
    private static final String LOG_TAG = NewsActivity.class.getName();

    /** URL for News data from The Guardian API*/
    private static final String REQUEST_URL = "https://content.guardianapis.com/search?" +
            "q=technology&section=technology&order-by=newest&page-size=15&show-fields=thumbnail&api-key=323ed496-4420-4f66-8010-972a9b4aa1fc";


    /** Adapter for the list of News*/
    NewsAdapter mNewsAdapter;

    /** ID for News loader, it can be any constant value*/
    public static final int NEWS_LOADER_ID = 1;

    /** Empty TextView for ListView*/
    TextView mEmptyTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);


        // Find a reference to the ListView Layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter which takes an empty list of News objects as list
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the mNewsAdapter to newsListView so the
        // list can be populated in the user interface
        newsListView.setAdapter(mNewsAdapter);

        // Setting onItemClickListener for ListView item
        // It will send a website Intent to browser to open
        // the news website
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the current News Object
                News currentNews = mNewsAdapter.getItem(position);

                // Get the String url from currentNews and create a Uri object from it
                Uri newsUri = Uri.parse(currentNews.getmNewsUrl());

                // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                // call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                CustomTabsIntent customTabsIntent = builder.build();

                // launch the desired Url with CustomTabsIntent.launchUrl()
                customTabsIntent.launchUrl(NewsActivity.this,newsUri);
            }
        });

        // Set the emptyTextView for the newsListView
        // First get a reference to TextView defined in layout file
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);

        // Set this emptyTextView to newsListView
        newsListView.setEmptyView(mEmptyTextView);


        // Get an instance of LoaderManager to manage loaders
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader, pass it the loader ID defined above, pass the bundle
        // as null and pass in this activity for loaderCallbacks method
        // It will work because this activity implements LoaderCallbacks interface
        loaderManager.initLoader(NEWS_LOADER_ID,null,this);


    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {

        // set the text "No news found" to emptyTextView
        mEmptyTextView.setText(R.string.no_news_found);

        // Clear the mNewsAdapter from any previous newsList
        mNewsAdapter.clear();

        // If newsList is not empty and not null then
        // add it mNewsAdapter. This will trigger ListView to update
        if( !newsList.isEmpty() && newsList !=null ) {
            mNewsAdapter.addAll(newsList);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        // Loader reset is called, so clear the mNewsAdapter's data
        mNewsAdapter.clear();

    }
}
