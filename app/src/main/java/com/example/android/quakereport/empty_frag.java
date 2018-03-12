package com.example.android.quakereport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Panda on 3/5/2018.
 */

public class empty_frag extends Fragment implements View.OnClickListener {
    private View myView;
    @Override
    public void onClick(View view) {
        myView = view;
        //Handle spamming with touches by removing the listener
        myView.setOnClickListener(null);
        //Start cooldown
        new CountDownTimer(5000,5000){
            public void onTick(long millisUntilFinished){
                //do nothing
            }
            public void onFinish(){
                //Set listener back after cooldown
                setThisListener();
            }
        }.start();
        Toast.makeText(getActivity(),R.string.connect_try,Toast.LENGTH_SHORT).show();
        if(checkInternet()){
            Fragment initialFrag = new main_frag();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag,initialFrag);
            fragmentTransaction.commit();
        }
        else{
            Toast.makeText(getActivity(),R.string.still_no_internet,Toast.LENGTH_SHORT).show();
        }
    }
    private void setThisListener(){
        myView.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.empty_frag,container,false);

        v.setOnClickListener(this);
        return v;
    }
    private Boolean checkInternet(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
