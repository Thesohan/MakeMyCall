package com.example.makemycall.Utill;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SaveAndLoadContact {
    private Context context;
    private static final String SHARED_PREFERENCE_NAME="com.example.makemycall.MyContacts";
    private SharedPreferences sharedPreferences;
    public SaveAndLoadContact(Context context) {

        this.context=context;
        this.sharedPreferences=context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
    }

    public void clearSharedPreferencs(){
        sharedPreferences.edit().clear().apply();
    }
    public void saveMap(Map<String,String> inputMap){
        if (sharedPreferences != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("My_map").apply();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
    }

    public Map<String,String> loadMap(){
        Map<String,String> outputMap = new HashMap<String,String>();
        try{
            if (sharedPreferences != null){
                String jsonString = sharedPreferences.getString("My_map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }

}
