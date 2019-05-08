package com.example.severn.poetry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.severn.util.Constant;
import com.example.severn.util.StreamTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity" ;
    private Activity activity=LoginActivity.this;
    private Button returnButton;
    private Button login;
    private EditText et_name;
    private EditText et_pass;
    private TextView regrdit;


    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        returnButton = findViewById(R.id.button_mereturn);
        login = findViewById(R.id.login);
        et_name = findViewById(R.id.uselogin);
        et_pass = findViewById(R.id.usepass);
        regrdit = findViewById(R.id.register_home);
        regrdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SlignActivity.class));
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        final String name = et_name.getText().toString();
                        String pass = et_pass.getText().toString();
//                      String data  = "name=" + URLEncoder.encode(name, "utf-8") + "&pass=" + URLEncoder.encode(pass, "utf-8") + "";
                        String data = "{\"name\":\""+name+"\",\"password\":\""+pass+"\"}";
                        final String post = Post(data, Constant.LOGIN, getApplicationContext());
                        paresUserIbfoJSON(post);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra("name",name);
                                setResult(2,intent);
                                finish();
                            }
                        });
                    }
                }.start();
            }
        });


    }

    public  String Post(String string,String post,Context context)//string POST参数,get 请求的URL地址,context 联系上下文
    {
        String html;
        try {
            String urldizhi=post; //请求地址
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
            out.write(string);
            out.flush();
            out.close();
//            获取返回值代码
            int code = conn.getResponseCode();
            Log.d(TAG, "Post: "+code);
            if (code == 200){
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(activity, "登陆成功", Toast.LENGTH_SHORT).show();
                       //将用户名密码保存到SP文件中
                       SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("username",username);
                       editor.putString("password",password);
                       editor.commit();
                   }
               });

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
    private void paresUserIbfoJSON (String jsonData){
        String userInfo;
        jsonData = "["+jsonData+"]";
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject  jsonObject = jsonArray.getJSONObject(i);
                userInfo = jsonObject.getString("userInfo");
                Log.d(TAG,userInfo);
                userJSON(userInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void userJSON (String jsonData){
        JSONArray jsonArray ;
        jsonData = "["+jsonData+"]";
        try {
            jsonArray = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject  jsonObject = jsonArray.getJSONObject(i);
                username  = jsonObject.getString("name");
                password = jsonObject.getString("passward");
                Log.d(TAG,username);
                Log.d(TAG,password);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
