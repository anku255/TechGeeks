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
     */
    // private String mTime;
    public News(String title, String thumbnailUrl, String newsUrl) {

        this.mTitle = title;
        this.mThumbnailUrl = thumbnailUrl;
        this.mNewsUrl = newsUrl;
        //this.mTime = time;
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
}

//    public String getmTime() {
//        return mTime;
//    }
//}

