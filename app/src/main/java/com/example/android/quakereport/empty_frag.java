package com.example.android.quakereport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by Panda on 3/5/2018.
 */

public class empty_frag extends Fragment implements View.OnClickListener {
    //TODO setOnclickListener to whole layout
    @Override
    public void onClick(View view) {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.empty_frag,container,false);
        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progBar);
        progressBar.setVisibility(View.GONE);
        return v;
    }
}
