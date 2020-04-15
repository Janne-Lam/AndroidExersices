package com.example.footballcompetitions;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static String TAG = "Parser";

    public static List<AreasClass> parsedAreas(JSONObject jsonObject){
        List<AreasClass> list = new ArrayList<>();
        try {
            JSONArray areas = jsonObject.getJSONArray("areas");
            for (int i = 0; i < areas.length(); i++) {
                String id = ((JSONObject) areas.get(i)).getString("id");
                String name = ((JSONObject) areas.get(i)).getString("name");
                AreasClass areasClass = new AreasClass(id, name);
                list.add(areasClass);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
            Log.d(TAG, "JSONException: " + e.toString());
        }
        return list;
    }

    public static List<String> parsedCompetitions(JSONObject jsonObject){
        List<String> list = new ArrayList<>();
        try{
            JSONArray comps = jsonObject.getJSONArray("competitions");
            for(int i = 0; i < comps.length(); i++){
                String name = ((JSONObject) comps.get(i)).getString("name");
                list.add(name);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
            Log.d(TAG, "JSONException: " + e.toString());
        }
        return list;
    }
}
