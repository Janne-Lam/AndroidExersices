package com.example.httpvolley;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpFetcher extends AsyncTask <String, Integer, String> {

    public interface ReporterInterface{
        void notifySuccess(String data);
        void notifyError(String data);
    }

    private ReporterInterface reporterInterface;
    Context mContext;

    public HttpFetcher(ReporterInterface resultCallback, Context context){
        this.reporterInterface = resultCallback;
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = strings[0];
        return volleyMethod(data);
    }

    @Override
        protected void onPostExecute(String data){
            if(reporterInterface != null){
                if(data == "That did not work"){
                    reporterInterface.notifyError(data);
                }
                else{
                    reporterInterface.notifySuccess(data);
                }
            }
    }

    private String volleyMethod(String urlString){
        if(urlString != null){
            try{
                RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String newData = response.substring(0, 500);
                        reporterInterface.notifySuccess(newData);
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String newData = "That did not work";
                        reporterInterface.notifyError(newData);
                    }
                });
                queue.add(stringRequest);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}