/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class EarthquakeActivity extends AppCompatActivity {
    //implements LoaderManager.LoaderCallbacks<ArrayList<Report>>
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final String USGS_HTTP_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//        earthquakeListView.setEmptyView(findViewById(R.id.noquakes));
//        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

//        if(connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting() && connectivityManager.getActiveNetworkInfo().isAvailable()){
//        Log.i(LOG_TAG,"initLoader()");
//        getLoaderManager().initLoader(0,null,this);
//        }else if(getLoaderManager().getLoader(0)!=null) {
//            Log.i(LOG_TAG,"initLoader()");
//            getLoaderManager().initLoader(0,null,this);}
//            else {
//            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progBar);
//            progressBar.setVisibility(View.GONE);
//            TextView emptyText = (TextView) findViewById(R.id.noquakes);
//            emptyText.setText(R.string.no_internet);
//            mSwipeRefreshLayout.setVisibility(View.GONE);
//            //TODO add button on emptyview to reload
//        }
        Fragment initialFrag = new main_frag();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag,initialFrag);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
