package com.example.android.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by omkarpathak on 4/12/18.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private ArrayList<News> mNewsArrayList;

    private Context mContext ;

    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> newsList) {
        super(context, 0, newsList);
        mNewsArrayList = newsList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mNewsArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (convertView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        News currentNews = getItem(position);

        // Set Title
        TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentNews.getTitle());

        //get date, format it and set it
        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        String finalTimeString = "" + formatDate(currentNews.getDate());
        dateTextView.setText(finalTimeString);

        // Get section name, color it and set it
        TextView sectionTextView = listItemView.findViewById(R.id.section_text_view);
        sectionTextView.setText("Section : " + currentNews.getSection());
        sectionTextView.setTextColor(mContext.getResources().getColor(getTextColor(currentNews.getSection())));

        return listItemView;
    }

    // Take date in one format and convert to another
    private String formatDate(String dateString){
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
        Date dateObj = null;
        try {
            dateObj = inputDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outDateFormat = new SimpleDateFormat("EEE, MMM dd, ''yy 'at' HH:mm:ss z", Locale.US);
        outDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outDateFormat.format(dateObj);
    }

    // Set Section color based on name
    private int getTextColor(String sectionName){
        if (sectionName.contains("Music")){
            return R.color.Music;
        } else if(sectionName.contains("Politics")) {
            return R.color.Politics;
        } else if(sectionName.contains("Society")) {
            return R.color.Society;
        }else if(sectionName.contains("Science")) {
            return R.color.Science;
        }else if(sectionName.contains("Teacher")) {
            return R.color.Teacher;
        }else if(sectionName.contains("Education")) {
            return R.color.Education;
        }else if(sectionName.contains("Opinion")) {
            return R.color.Opinion;
        }else if(sectionName.contains("Technology")) {
            return R.color.Technology;
        } else {
            return R.color.def;
        }
    }

}
