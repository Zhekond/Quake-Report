package com.example.android.quakereport;


import android.content.Context;
import android.content.Intent;

import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import java.util.ArrayList;
import android.support.v4.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;


import static com.example.android.quakereport.EarthquakeActivity.USGS_HTTP_URL;
import static com.example.android.quakereport.Utils.LOG_TAG;

/**
 * Created by Panda on 3/5/2018.
 */

public class main_frag extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Report>> {
            //LoaderManager.LoaderCallbacks<ArrayList<Report>>
    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Report>> loader, ArrayList<Report> data) {
        if(data !=null&&!data.isEmpty()){
//        Log.i(LOG_TAG, "onLoadFinished() callback");
//        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progBar);
//        progressBar.setVisibility(View.GONE);
//        TextView emptyText = (TextView) findViewById(R.id.noquakes);
//        emptyText.setText(R.string.no_quakes);
            ListView earthquakeListView = (ListView) getView().findViewById(R.id.list);//TODO Bind to listView of fragment
            ReportAdapter myAdapter = new ReportAdapter(getActivity(), data);
            earthquakeListView.setAdapter(myAdapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Report selectedReport = (Report) adapterView.getItemAtPosition(i);
                    String url = selectedReport.getUrl();
                    Uri address = Uri.parse(url);
                    Intent openLinkInternet = new Intent(Intent.ACTION_VIEW, address);
                    if (openLinkInternet.resolveActivity(getContext().getPackageManager()) != null)
                        startActivity(openLinkInternet);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Report>> loader) {
        //do somethibg
        Log.i(LOG_TAG,"onLoaderReset() callback");
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Report>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String minMagnitude = sharedPrefs.getString(getString(R.string.settings_min_magnitude_key),getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(USGS_HTTP_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("format","geojson");
        uriBuilder.appendQueryParameter("limit","10");
        uriBuilder.appendQueryParameter("minmag",minMagnitude);
        uriBuilder.appendQueryParameter("orderby",orderBy);
        Log.i(LOG_TAG,"onCreateLoader() callback");

        return new EarthquakeLoader(getContext(), uriBuilder.toString());
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_frag,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(0,null,this);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(),"OnRefresh!",Toast.LENGTH_SHORT).show();
        //TODO Handle refresh
    }
}
