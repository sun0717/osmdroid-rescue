package com.sun.myapplication1.utils;

import android.text.TextUtils;
import android.util.Log;

import com.sun.myapplication1.FragmentReceived;
import com.sun.myapplication1.model.Position;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /*
    解析和处理服务器返回的数据
     */
    public static boolean handleGeopointResponse(String response, int control) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allPositions = new JSONArray(response) ;
                for (int i = 0 ; i < allPositions.length() ;i++) {
                    JSONObject positionObject = allPositions.getJSONObject(i);
                    double la = positionObject.getDouble("la");
                    double lon = positionObject.getDouble("lon");
                    Log.d("MainActivity","第"+i+1+"个");
                    Log.d("MainActivity","纬度:"+la);
                    Log.d("MainActivity","纬度:"+lon);
                    if(FragmentReceived.control == 1) {
                        Position position = new Position();
                        position.setLatitude(positionObject.getDouble("la"));
                        position.setLongitude(positionObject.getDouble("lon"));
                        position.save();
                    }
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
