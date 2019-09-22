package com.example.android.news;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.List;

/**
 * Created by omkarpathak on 4/12/18.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mNewsUrl;

    public NewsLoader(Context context, String newsUrl) {
        super(context);
        mNewsUrl = newsUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {

        if (mNewsUrl == null) {
            return null;
        }
        try {
            return Utilities.fetchNews(mNewsUrl);
        } catch (JSONException e) {
            Log.e("loadInBackground","Problem fetching news");
            return null;
        }
    }
}
