package com.example.severn.poetry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.severn.util.Constant;
import com.example.severn.util.RequestPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SlignActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText password1;
    private Button reg;
    private String TAG = "SlignActivity";
    private String code ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slign);
        username = findViewById(R.id.usernamezh);
        password = findViewById(R.id.password);
        password1 = findViewById(R.id.passwordqr);
        reg = findViewById(R.id.zcqueren);



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = username.getText().toString();
                final String pass = password.getText().toString();
                new Thread() {
                    @Override
                    public void run() {
                        String post = "{\"name\":\""+name+"\",\"password\":\""+pass+"\"}";
                        String jsonData = new RequestPost().Post(post, Constant.REGISTER,"");
                        paresRegJSON(jsonData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SlignActivity.this, code, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }.start();
            }
        });

    }
    private void paresRegJSON (String jsonData){
        String regJSON;
        jsonData = "["+jsonData+"]";
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                regJSON = jsonObject.getString("result");
                if (regJSON != null){
                    paresMsgJSON(regJSON);
                }
                Log.d(TAG,regJSON);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void paresMsgJSON (String jsonData){
        jsonData = "["+jsonData+"]";
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                code = jsonObject.getString("msg");
                Log.d(TAG,code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
