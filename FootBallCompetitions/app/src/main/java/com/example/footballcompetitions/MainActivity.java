package com.example.footballcompetitions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static String TAG = "MainActivity";
    private ListView countryListView;
    private ArrayAdapter<String> adapter;
    private List<String> areaNames;
    private RequestQueue queue;
    private HashMap<String, String> nameIdHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryListView = findViewById(R.id.listView);

        nameIdHashMap = new HashMap<>();
        queue = Volley.newRequestQueue(MainActivity.this);
        areaNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, areaNames);
        countryListView.setAdapter(adapter);

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String areaName = areaNames.get(position);
                String countryId = nameIdHashMap.get(areaName);
                Intent i = new Intent(MainActivity.this, CompetitionsActivity.class);
                i.putExtra("id", countryId);
                startActivity(i);
            }
        });
        getListView();
    }
    private void getListView () {

        String url = "https://api.football-data.org/v2/areas";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<AreasClass> areaList = Parser.parsedAreas(response);
                List<String> areaNamesList = new ArrayList<>();
                for (AreasClass areasClass : areaList) {
                    nameIdHashMap.put(areasClass.getArea(), areasClass.getId());
                    areaNamesList.add(areasClass.getArea());
                }
                areaNames.removeAll(areaNames);
                areaNames.addAll(areaNamesList);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(TAG, "Error Response: " + error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }
}
