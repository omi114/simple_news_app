package com.example.android.news;

/**
 * Created by omkarpathak on 4/12/18.
 */

public class News {

    private String mTitle;
    private String mUrl;
    private String mSection;
    private String mDate;

    public News(String title, String newsUrl, String section, String date) {
        mTitle = title;
        mUrl = newsUrl;
        mSection = section;
        mDate = date;
    }

    public String getTitle () {
        return mTitle;
    }

    public String getUrl () {
        return mUrl;
    }

    public String getSection () {
        return mSection;
    }

    public String getDate () {
        return mDate;
    }
}
