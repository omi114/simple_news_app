package com.example.android.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by omkarpathak on 4/12/18.
 */

public class Utilities {

    public Utilities(){}

    public static ArrayList<News> fetchNews(String newsUrl) throws JSONException {

        ArrayList<News> newsList = new ArrayList<>();

        if (newsUrl == null) {
            return null;
        }
        //Create URL out of the incoming string URL
        URL realURL = createURL(newsUrl);

        Log.i("",""+realURL);
        String jsonString = getJsonString(realURL);

        JSONObject baseObj = new JSONObject(jsonString).getJSONObject("response");

        JSONArray resultsArray = baseObj.getJSONArray("results");

        for(int i=0;i<resultsArray.length();i++) {
            JSONObject newsStory = resultsArray.getJSONObject(i);
            String storyTitle = newsStory.getString("webTitle");
            //Log.i("storyTitle",""+storyTitle);
            String storyUrl = newsStory.getString("webUrl");
            //Log.i("storyUrl",""+storyUrl);
            String storyDate = newsStory.getString("webPublicationDate");
            //Log.i("storyDate",""+storyDate);
            //String storyAuthor = "xyz";
            String section = newsStory.getString("sectionName");

            newsList.add(new News(storyTitle, storyUrl, section, storyDate));
        }

        return newsList;
    }

    public static URL createURL(String stringURL) {
        if (stringURL == null){
            return null;
        }
        try {
            return new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e("createURL","Problem with string url");
            return null;
        }
    }

    public static String getJsonString(URL urlToFetch){

        String jsonResponse = "";
        HttpURLConnection urlConnection;

        if(urlToFetch == null) {
            return null;
        }
        try {
            urlConnection = (HttpURLConnection) urlToFetch.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(1500);

            if (urlConnection.getResponseCode() != 200) {
                Log.e("gettingResposeCode","Not 200");
                return null;
            }

            Log.i("getResponseCode","response was 200");
            InputStream inputStream = urlConnection.getInputStream();
            jsonResponse = getStringFrominputStream(inputStream);

            return jsonResponse;
        } catch (IOException e) {
            Log.e("Connect to URL", "Problem setting up connection");
            return null;
        }
    }

    public static String getStringFrominputStream(InputStream inputStream) throws IOException {

        StringBuilder outputString = new StringBuilder();

        if(inputStream == null) {return null;}
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            outputString.append(line);
            line = bufferedReader.readLine();
        }

        return outputString.toString();
    }
}
