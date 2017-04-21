package com.tech.geeks.techgeeks;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Anku on 4/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getName();

    private ImageLoader mImageLoader;


    public NewsAdapter(Context context, List<News> news) {
        super(context,0,news);

        mImageLoader = VolleySingleton.getInstance().getImageLoader();
    }


    static class ViewHolder {
        TextView title;
        NetworkImageView thumbnail;
        String thumbnailUrl;
        Bitmap image;
        TextView time;
    }

    public View getView(int position,View convertView,ViewGroup parent) {

        ViewHolder viewHolder;

        // Check if there is an existing View (called convertView) if convertView
        // is null then Inflate a new list item layout
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent , false);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) listItemView.findViewById(R.id.Title);
            viewHolder.thumbnail = (NetworkImageView) listItemView.findViewById(R.id.thumbnail);
            viewHolder.time = (TextView) listItemView.findViewById(R.id.time);
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        // Get the current News Object
        News currentNews = getItem(position);

        viewHolder.title.setText(currentNews.getmTitle());

        // Get the publishDate for currentNews
        String publishDate = currentNews.getmPulishDate();

        // Get the Date in TimeAgo format
        String formmatedDate = getTimeAgoString(publishDate);

        // Set formattedDate to the time TextView of viewHolder
        viewHolder.time.setText(formmatedDate);

        viewHolder.thumbnailUrl = currentNews.getmThumbnailUrl();

        viewHolder.thumbnail.setImageUrl(viewHolder.thumbnailUrl,mImageLoader);

        return listItemView;
    }

    public long getTimeInMillis(String srcDate) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        Date date;
        long timeInMilli = 0;
        try {
            date = simpleDate.parse(srcDate);
            timeInMilli = date.getTime();
        } catch(ParseException e) {
            e.printStackTrace();
        }

        return timeInMilli;
    }

    public String getTimeAgoString(String publishDate)
    {
        long publishDateTimeInMilli = getTimeInMillis(publishDate);

        // Creating a date object to get current time in Milli Seconds
        Date date = new Date();
        long currentTimeInMill = date.getTime();

        // Call DateUtils.getRelativeTimeSpanString() to get time in
        // "42 min ago" format
        String timeAgoString = DateUtils.
                                getRelativeTimeSpanString(publishDateTimeInMilli,currentTimeInMill,
                                DateUtils.MINUTE_IN_MILLIS).toString();

        // Return timeAgoString
        return timeAgoString;
    }
}
