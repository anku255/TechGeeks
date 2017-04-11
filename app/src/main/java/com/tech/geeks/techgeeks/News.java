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
     * Publishing time of the News
     */
    // private String mTime;
    public News(String title, String thumbnailUrl) {

        this.mTitle = title;
        this.mThumbnailUrl = thumbnailUrl;
        //this.mTime = time;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }
}

//    public String getmTime() {
//        return mTime;
//    }
//}

