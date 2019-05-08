package com.example.severn.poetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.severn.util.JSONAnalysis;
import com.example.severn.util.RequestGet;

public class Translate extends AppCompatActivity {

    private String username;
    private String poetryname;
    private TextView translation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        poetryname = intent.getStringExtra("poetryname");
        translation = findViewById(R.id.translation);
        new Thread(){
            @Override
            public void run() {
                String get = RequestGet.Get("/getpoetry?poetryname="+poetryname+"&username="+username);
                JSONAnalysis jsonAnalysis = new JSONAnalysis();
                String data = jsonAnalysis.analysisData(get, "", "data");
                final String poteTran = jsonAnalysis.analysisData(data, "", "translation");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        translation.setText(poteTran);
                    }
                });
            }
        }.start();
    }

}
