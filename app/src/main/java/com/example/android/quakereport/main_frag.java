package com.example.android.quakereport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Panda on 3/5/2018.
 */

public class main_frag extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_frag,container,false);
        return v;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(),"OnRefresh!",Toast.LENGTH_SHORT).show();
        //TODO Handle refresh
    }
}
