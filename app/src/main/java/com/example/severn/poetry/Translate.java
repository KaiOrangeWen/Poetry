package com.example.severn.poetry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.severn.util.RequestGet;

public class Translate extends AppCompatActivity {

    private String username;
    private String poetryname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        new Thread(){
            @Override
            public void run() {
                String get = RequestGet.Get("/getpoetry?poetryname=将进酒&username=admin");

            }
        }.start();
    }

}
