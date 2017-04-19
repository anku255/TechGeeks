package com.tech.geeks.techgeeks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    // Log tag for log messages
    private static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * URL for News data from The Guardian API
     */
    private static final String REQUEST_URL = "https://content.guardianapis.com/search?" +
            "q=technology&section=technology&order-by=newest&page-size=15&show-fields=thumbnail&api-key=323ed496-4420-4f66-8010-972a9b4aa1fc";


    /**
     * Adapter for the list of News
     */
    NewsAdapter mNewsAdapter;


    /**
     * Empty TextView for ListView
     */
    TextView mEmptyTextView;

    /**
     * RequestQueue for Volley
     * Using VolleySingleton class to initialize it
     */
    RequestQueue mRequestQueue = VolleySingleton.getInstance().getmRequestQueue();

    /**
     * List of News Objects
     */
    List<News> newsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);


        // Find a reference to the ListView Layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter which takes an empty list of News objects as list
        mNewsAdapter = new NewsAdapter(this, newsList);

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
                customTabsIntent.launchUrl(NewsActivity.this, newsUri);
            }
        });

        // Set the emptyTextView for the newsListView
        // First get a reference to TextView defined in layout file
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);

        // Set this emptyTextView to newsListView
        newsListView.setEmptyView(mEmptyTextView);

        // Check for Network connection before initialzing loader
        // First get a reference to ConnectivityManager class
        ConnectivityManager cnnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details of the currently active data network
        NetworkInfo networkInfo = cnnMgr.getActiveNetworkInfo();

        // if there is network connection then fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Make JsonObject Request

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // call AddNewsToList to extract News Objects from jsonResponse
                    // and add News objects to newsList
                    AddNewsToList(response);

                    // Hide the loading indicator after loading has been done
                    ProgressBar loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);

                    // set the text "No news found" to emptyTextView
                    mEmptyTextView.setText(R.string.no_news_found);

                    Log.v(LOG_TAG, "Notifying for data change");
                    // notify adapter that dataSet has been changed
                    mNewsAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e(LOG_TAG, "Error in making JsonObjectResponse", error);
                    // set the text "No news found" to emptyTextView
                    mEmptyTextView.setText(R.string.no_news_found);

                }
            });

            // Add jsonObjectRequest to RequestQueue
            mRequestQueue.add(jsonObjectRequest);

        } else {
            // Otherwise display error
            // First, hide the loading indicator so that error will be visible
            ProgressBar loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Set the text of emptyTextView to "No internet connection"
            mEmptyTextView.setText(R.string.no_internet_connection);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        recreate();
    }

    /**
     * Parse the baseJsonResponse , create News Objects and
     * add it to newsList
     */

    private void AddNewsToList(JSONObject baseJsonResponse) {

        // Try to parse the baseJsonResponse If an exception occurs then
        // print the error message to log

        try {
            // Extract the JSONObject associated with the key "response"
            JSONObject response = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key "results"
            JSONArray newsArray = response.getJSONArray("results");

            // For each news in newsArray create a News object
            for (int i = 0; i < newsArray.length(); i++) {

                // Get the currentNews JSONObject
                JSONObject currentNews = newsArray.getJSONObject(i);

                // Extract the value for key called "webTitle"
                String title = currentNews.getString("webTitle");

                // Extract the value for key called "webUrl"
                String newsUrl = currentNews.getString("webUrl");

                // Extract he value for "webPublicationDate"
                String pulishDate = currentNews.getString("webPublicationDate");

                // Get the JSONObject for key "fields"
                JSONObject fields = currentNews.getJSONObject("fields");

                // Extract the value for key called "thumbnail"
                String thumbnailUrl = fields.getString("thumbnail");

                // Create a new News Object and add it to ArrayList
                News news = new News(title, thumbnailUrl, newsUrl, pulishDate);
                newsList.add(news);

            }


        } catch (JSONException e) {

            // If an exception is thrown for executing any of the above line then
            // print the error message to log

            Log.e(LOG_TAG, "Problem parsing the JSONResponse", e);
        }

    }

}
