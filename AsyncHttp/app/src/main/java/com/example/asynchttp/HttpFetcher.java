package com.example.asynchttp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpFetcher extends AsyncTask <String, Integer, String> {

    public interface ReporterInterface{
        void networkFetchDone(String data);
    }

    ReporterInterface callbackInterface;

    public HttpFetcher(ReporterInterface callbackInterface){
        this.callbackInterface = callbackInterface;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = strings[0];
        //loadFromWeb(data);
        return loadFromWeb(data);
    }

    @Override
    protected void onPostExecute(String data){
        if(callbackInterface != null){
            callbackInterface.networkFetchDone(data);
        }
    }

    private String loadFromWeb(String urlString){
        try{
            java.net.URL url = new URL(urlString);
            Log.d("TAG", "loadFromWeb: " + urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String htmlText = Utility.fromStream(inputStream);
            return htmlText;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
