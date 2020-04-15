package com.example.asynchronoussimplehttp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;

public class StockPriceFetcher extends AsyncTask<String, Integer, String> {

    public interface ReporterInterface{
        void networkFetchDone(String data);
    }

    private ReporterInterface reporterInterface;

    public StockPriceFetcher(ReporterInterface callbackInterface) {
        this.reporterInterface = callbackInterface;
    }

    @Override
    protected String doInBackground(String... strings) {
        String name = strings[0];
        String nameData = "https://financialmodelingprep.com/api/company/price/" + name + "?datatype=json";

        String dataUrl = loadFromWeb(nameData);
        if(dataUrl != null){
            ArrayList<StockPriceUtility> stockData = parsedStockData(dataUrl);
            Log.d("TAG", "doInBackground: dataurl " + nameData);
            Log.d("TAG", "doInBackground: " + stockData.toString());
            return String.valueOf(stockData.get(0).getPrice());
        }
        else{
            return "Failed";
        }
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        Log.d("TAG", "onPostExecute: " + result);
        if(reporterInterface != null){
            reporterInterface.networkFetchDone(result);
        }
    }

    private ArrayList<StockPriceUtility> parsedStockData(String data){
        ArrayList<StockPriceUtility> stockData = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(data);
            Iterator iterator = jsonObject.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                JSONObject stock = jsonObject.getJSONObject(key);
                double stockPrice = stock.getDouble("price");
                StockPriceUtility stockPriceUtility = new StockPriceUtility(key, stockPrice);
                stockData.add(stockPriceUtility);
                Log.d("TAG", "parsedStockData: " + stockData.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stockData;
    }

    private String loadFromWeb(String urlStr){
        try{
            URL url = new URL(urlStr);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String htmlText = Utilities.fromStream(inputStream);
            return htmlText;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
