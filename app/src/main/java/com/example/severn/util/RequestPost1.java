package com.example.severn.util;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * POST请求的工具类
 */
public class RequestPost1 {
    public static String Post(String POST,String URL,String TAG)//string POST参数,get 请求的URL地址,context 联系上下文
    {
        String html;
        try {
            String urldizhi = URL; //请求地址
            URL url=new URL(urldizhi);
            Log.d(TAG, "Post: "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setDoInput(true);//表示从服务器获取数据
            conn.setDoOutput(true);//表示向服务器写数据
            //获得上传信息的字节大小以及长度
            conn.setRequestMethod("POST");
            //是否使用缓存
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(POST);
            out.flush();
            out.close();
//            获取返回值代码
            int code = conn.getResponseCode();
            Log.d(TAG, "Post: "+code);
            if (code == 200){
            }
            InputStream inputStream=conn.getInputStream();
            byte[] data=StreamTools.read(inputStream);
            html = new String(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----"+e);
            String string2="{\"success\":-1}";
            return string2;
        }
        return html;
    }
}
