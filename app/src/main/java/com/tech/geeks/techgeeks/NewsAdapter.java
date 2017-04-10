package com.tech.geeks.techgeeks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anku on 4/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, List<News> news) {
        super(context,0,news);
    }

    public View getView(int position,View convertView,ViewGroup parent) {
        // Check if there is an existing View (called convertView) if convertView
        // is null then Inflate a new list item layout

        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent , false);
        }

        // Get the current News Object
        News currentNews = getItem(position);

        // Find the Title TextView in listItemView layout
        TextView title = (TextView) listItemView.findViewById(R.id.Title);

        // Get the title from currentNews object and set it to title TextView
        title.setText(currentNews.getmTitle());


        return listItemView;
    }
}
