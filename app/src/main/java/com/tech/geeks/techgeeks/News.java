package com.tech.geeks.techgeeks;


/**
 * This class represents a News object, which has a title, a thumbnail and time (of publish)
 */


public class News {
    /**
     * Title of News
     */
    private String mTitle;

    /**
     * Thumbnail image of News
     */
    private String mThumbnailUrl;

    /**
     * URL for the News
     */
    private String mNewsUrl;

    /**
     * Publishing time of the News
     * It's format is "2017-04-10T12:33:38Z"
     */
    private String mPulishDate;


    public News(String title, String thumbnailUrl, String newsUrl, String pulishDate) {

        this.mTitle = title;
        this.mThumbnailUrl = thumbnailUrl;
        this.mNewsUrl = newsUrl;
        this.mPulishDate = pulishDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getmNewsUrl() {
        return mNewsUrl;
    }

    public String getmPulishDate() {
        return mPulishDate;
    }
}



