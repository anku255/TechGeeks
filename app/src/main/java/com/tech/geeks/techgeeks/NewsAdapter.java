package com.tech.geeks.techgeeks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Anku on 4/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getName();


    public NewsAdapter(Context context, List<News> news) {
        super(context,0,news);
    }


    static class ViewHolder {
        TextView title;
        ImageView thumbnail;
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
            viewHolder.thumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail);
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

        new ThumbnailDownloader().execute(viewHolder);


        return listItemView;
    }

    public class ThumbnailDownloader extends AsyncTask<ViewHolder,Void,ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            ViewHolder viewHolder = params[0];

            viewHolder.image = null;
            URL url = null;
            try {
                url = new URL(viewHolder.thumbnailUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG,"Problem in building the URL",e);
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;


            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = urlConnection.getInputStream();
                    viewHolder.image = BitmapFactory.decodeStream(inputStream);
                }
                else {
                    Log.e(LOG_TAG,"Error response code : " + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                Log.e(LOG_TAG,"Problem downloading thumbnail image",e);
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }

                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    Log.e(LOG_TAG,"Problem in closing inputStream",e);
                }
            }

            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder viewHolder) {
            if(viewHolder.image == null) {
                viewHolder.thumbnail.setImageResource(R.mipmap.ic_launcher);
            } else {
                viewHolder.thumbnail.setImageBitmap(viewHolder.image);
            }
        }
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
