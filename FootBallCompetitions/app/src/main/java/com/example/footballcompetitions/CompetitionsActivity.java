package com.example.footballcompetitions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CompetitionsActivity extends AppCompatActivity {

    private static String TAG = "CompetitionsActivity";
    private List<String> competitions;
    private ListView competitionsListView;
    private ArrayAdapter<String> adapter;
    private String areaId;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);
        competitionsListView = findViewById(R.id.compListView);
        Intent i = getIntent();
        areaId = i.getStringExtra("id");
        queue = Volley.newRequestQueue(CompetitionsActivity.this);
        competitions = new ArrayList<>();
        adapter = new ArrayAdapter<>(CompetitionsActivity.this, android.R.layout.simple_list_item_1, competitions);
        competitionsListView.setAdapter(adapter);
        getListView();
    }

    private void getListView(){
        String url = "https://api.football-data.org/v2/competitions?areas=" + areaId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> list = Parser.parsedCompetitions(response);
                competitions.removeAll(competitions);
                competitions.addAll(list);
                if (competitions.size() == 0) {
                    competitions.add("No competitions in this area");
                }
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
