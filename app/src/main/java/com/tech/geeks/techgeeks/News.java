package com.tech.geeks.techgeeks;

import android.media.Image;

/**
 * This class represents a News object, which has a title, a thumbnail and time (of publish)
 */


public class News {
    /** Title of News*/
    private String mTitle;

    /** Thumbnail image of News*/
    private Image mThumbnail;

    /** Publishing time of the News*/
    private String mTime;

    public News(String title, Image thumbnail , String time) {

        this.mTitle = title;
        this.mThumbnail = thumbnail;
        this.mTime = time;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Image getmThumbnail() {
        return mThumbnail;
    }

    public String getmTime() {
        return mTime;
    }
}

