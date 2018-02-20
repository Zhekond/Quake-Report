package com.example.android.quakereport;

/**
 * Created by Panda on 12/30/2017.
 */

public class Report  {
    public Report(double mag, String loc, long time, String url){
        this.mag = mag;
        this.loc = loc;
        this.time = time;
        this.url = url;
    }
    private double mag;
    private String loc;
    private long time;
    private String url;

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
