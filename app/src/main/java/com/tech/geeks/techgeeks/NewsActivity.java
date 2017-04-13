package com.tech.geeks.techgeeks;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Get the list of news objects by creating an instance of
        // QueryUtils class and passing the context
        ArrayList<News> newsList = new QueryUtils(this).extractNews();

        // Find a reference to the ListView Layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter which takes a list of news objects as input
        final NewsAdapter newsAdapter = new NewsAdapter(this,newsList);

        // Set the newsAdapter to newsListView so the
        // list can be populated in the user interface
        newsListView.setAdapter(newsAdapter);

        // Setting onItemClickListener for ListView item
        // It will send a website Intent to browser to open
        // the news website
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the current News Object
                News currentNews = newsAdapter.getItem(position);

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


    }
}
