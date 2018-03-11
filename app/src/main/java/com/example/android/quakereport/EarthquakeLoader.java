package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Panda on 1/9/2018.
 */

public class EarthquakeLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Report>> {
    private String LOG_TAG = "Loader";
    ArrayList<String> urls = new ArrayList<>();
    private Boolean onContentChanged = false;

    public EarthquakeLoader (Context context, String url){
        super(context);
        onContentChanged = true;
        urls.add(url);
    }

    @Override
    public ArrayList<Report> loadInBackground(){
        Log.i(LOG_TAG,"loadInBackground() method");
        onContentChanged = false;
        return Utils.fetchReportsData(urls.get(0));
    }
    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        Log.i(LOG_TAG,"onStartLoading() method");
        if(onContentChanged)
        forceLoad();
    }

}
