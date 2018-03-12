package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.view.LayoutInflater;

import static com.example.android.quakereport.EarthquakeActivity.USGS_HTTP_URL;

/**
 * Created by Panda on 3/5/2018.
 */

public class main_frag extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Report>> {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView earthquakeListView;
    private Activity myAc;
    private String LOG_TAG = "main_frag";
    private ProgressBar progressBar;
    private TextView emptyList;

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Report>> loader, ArrayList<Report> data) {
        Log.i(LOG_TAG, "onLoadFinished callback");
        progressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            ReportAdapter myAdapter = new ReportAdapter(myAc, data);
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
        else{
            emptyList.setVisibility(View.VISIBLE);
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Report>> loader) {
        //Invalidate or store some data here
        Log.i(LOG_TAG,"onLoaderReset() callback");
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Report>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"onCreateLoader callback");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String minMagnitude = sharedPrefs.getString(getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        earthquakeListView = (ListView) view.findViewById(R.id.list);
        myAc = getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout)getView().findViewById(R.id.main_frag);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        progressBar = (ProgressBar)getView().findViewById(R.id.progBar);
        emptyList = (TextView)getView().findViewById(R.id.empty_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getSupportLoaderManager().initLoader(0,null,this);

    }

    @Override
    public void onRefresh() {
        if(checkInternet()) {
            Toast.makeText(getContext(), R.string.refreshed, Toast.LENGTH_SHORT).show();
            getLoaderManager().restartLoader(0, null, this);
            mSwipeRefreshLayout.setRefreshing(true);
        }else
        {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(),R.string.no_internet,Toast.LENGTH_LONG).show();
        }
    }
    private Boolean checkInternet(){
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
