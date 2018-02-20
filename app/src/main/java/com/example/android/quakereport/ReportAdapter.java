package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Panda on 12/30/2017.
 */

public class ReportAdapter extends ArrayAdapter<Report> {
    public ReportAdapter(Activity context, ArrayList<Report> reports){
        super(context,0,reports);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
//        return super.getView(position, convertView, parent);
        Report currentReport = getItem(position);
        TextView magText = (TextView)listItemView.findViewById(R.id.mag);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String out = formatter.format(currentReport.getMag());
        magText.setText(out);
        GradientDrawable magnitudeCircle = (GradientDrawable) magText.getBackground();
        int magColor = getMagnitudeColor(currentReport.getMag());
        magnitudeCircle.setColor(getContext().getResources().getColor(magColor));
        magText.setBackgroundDrawable(magnitudeCircle);
        TextView locText = (TextView)listItemView.findViewById(R.id.loc);
        TextView dateText = (TextView)listItemView.findViewById(R.id.date);
        TextView timeText = (TextView)listItemView.findViewById(R.id.time);
        long timeInMilliseconds = currentReport.getTime();
        Date dateObject = new Date(timeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String timeToDisplay = timeFormatter.format(dateObject);
        dateText.setText(dateToDisplay);
        timeText.setText(timeToDisplay);
        TextView place = (TextView)listItemView.findViewById(R.id.place);
        int indexofcoma = currentReport.getLoc().indexOf("f");
        String loc = currentReport.getLoc().substring(0,indexofcoma+1);
        String place2 = currentReport.getLoc().substring(indexofcoma+1);
        place.setText(place2);
        locText.setText(loc);
//        dateText.setText(currentReport.getDate());
        return listItemView;
    }
    private int getMagnitudeColor(double mag){
        String iPart = Double.toString(mag).substring(0,1);
        int result = 0;
        switch (iPart){
            case"0":
            case"1":

                result = R.color.magnitude1;
                break;
            case"2":
                result = R.color.magnitude2;
                break;
            case"3":
                result = R.color.magnitude3;
                break;
            case"4":
                result = R.color.magnitude4;
                break;
            case"5":
                result = R.color.magnitude5;
                break;
            case"6":
                result = R.color.magnitude6;
                break;
            case"7":
                result = R.color.magnitude7;
                break;
            case"8":
                result = R.color.magnitude8;
                break;
            case"9":
                result = R.color.magnitude9;
                break;
            case"10":
                default:
                    result = R.color.magnitude10plus;
                    break;
        }
        return result;
    }
}
