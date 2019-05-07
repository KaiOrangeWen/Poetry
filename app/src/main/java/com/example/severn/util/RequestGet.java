package com.example.severn.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestGet {
    public static String Get (String urls)//string POST参数,get 请求的URL地址,context 联系上下文
    {
        String html = null;
        try {
            String urldizhi = Constant.IP+urls; //请求地址
            URL url=new URL(urldizhi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            //获得上传信息的字节大小以及长度
            conn.setRequestMethod("GET");
            //是否使用缓存
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200){
                InputStream inputStream=conn.getInputStream();
                byte[] data=StreamTools.read(inputStream);
                html = new String(data, "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----"+e);
            String string2="{\"success\":-1}";
            return string2;
        }
        return html;
    }
}
