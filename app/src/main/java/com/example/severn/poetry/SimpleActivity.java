package com.example.severn.poetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SimpleActivity extends AppCompatActivity {

    //    三秒到主页
    Timer timer;
    TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                SimpleActivity.this.finish();
            }
        };

        timer.schedule(task,1200);
    }
}
