package com.tech.geeks.techgeeks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Get the list of news objects from QueryUtils class
        ArrayList<News> newsList = QueryUtils.extractNews();

        // Find a reference to the ListView Layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter which takes a list of news objects as input
        NewsAdapter newsAdapter = new NewsAdapter(this,newsList);

        // Set the newsAdapter to newsListView so the
        // list can be populated in the user interface
        newsListView.setAdapter(newsAdapter);


    }
}
