package com.example.severn.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON数据解析类
 */
public class JSONAnalysis {
    /**
     * json数据的解析,单个数据，数据不带[]
     * @param jsonData json数据，不能包含中括号
     * @param TAG 日志输出TAG
     * @param jsonName 数据键的名字
     */
    public String analysisData (String jsonData,String TAG,String jsonName){
        String userInfo = "";
        jsonData = "["+jsonData+"]";
        System.out.println(jsonData);
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userInfo = jsonObject.getString(jsonName);
                Log.d(TAG,userInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * 自带[]的json数据的解析
     * @param jsonData
     * @param TAG
     * @param jsonName
     * @return
     */
    public String JSON (String jsonData,String TAG,String jsonName){
        String userInfo = "";
        System.out.println(jsonData);
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userInfo = jsonObject.getString(jsonName);
                Log.d(TAG,userInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

}
