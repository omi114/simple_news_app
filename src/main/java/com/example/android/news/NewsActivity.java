package com.example.android.news;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>{

    private String NEWS_QUERY = "http://content.guardianapis.com/search?q=debates&api-key=test";

    private int LOADER_ID =1;

    public NewsAdapter newsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Get the list view to operate on
        ListView newsListView = (ListView) findViewById(R.id.news_list_view);

        //Setup an adapter using an empty List
        newsArrayAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Attach the adapter to the list view
        newsListView.setAdapter(newsArrayAdapter);
        Log.i("", "Adapter set");

        //What happens when a story is clicked
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                News clickedNews = newsArrayAdapter.getItem(i);
                Intent newsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedNews.getUrl()));
                startActivity(newsIntent);
            }
        });

        // Initialize loader tasks in background
        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, NEWS_QUERY);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {

        newsArrayAdapter.clear();

        if (newsList != null && !newsList.isEmpty()) {
            newsArrayAdapter.addAll(newsList);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        newsArrayAdapter.clear();

    }
}
