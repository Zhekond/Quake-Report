package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Panda on 1/8/2018.
 */

public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();
    public static ArrayList<Report> fetchReportsData(String requestUrl){
        Log.i(EarthquakeActivity.LOG_TAG,"fetchReportsData() from: "+requestUrl);
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Error closing input stream",e);
        }
        ArrayList<Report> result = new ArrayList<Report>();
        try{
            result = getReportsFromJson(jsonResponse);
        }catch (JSONException e){
            Log.e(LOG_TAG,"Error parsing json",e);
        }
        return result;
    }

    private static URL createUrl (String requestUrl){
        URL url = null;
        try {
            url = new URL(requestUrl);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG,"failed to form a url", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else
                Log.e(LOG_TAG,"Error response code: "+urlConnection.getResponseCode());
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving the earthquake JSON results.",e);
        }finally {
            if(urlConnection !=null)
                urlConnection.disconnect();
            if (inputStream !=null)
                inputStream.close();
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream !=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Report> getReportsFromJson(String jsonResponse) throws JSONException{
        ArrayList<Report> reports = new ArrayList<Report>();
        JSONObject root = new JSONObject(jsonResponse);
        JSONArray features = root.getJSONArray("features");
        for(int i = 0; i<features.length();i++){
            JSONObject current = features.getJSONObject(i);
            JSONObject currentproper = current.getJSONObject("properties");
            reports.add(new Report(currentproper.getDouble("mag"),currentproper.getString("place"),currentproper.getLong("time"), currentproper.getString("url")));
        }
        return reports;
    }

}
