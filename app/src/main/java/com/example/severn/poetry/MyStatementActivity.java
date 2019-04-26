package com.example.severn.poetry;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.severn.util.Constant;
import com.example.severn.util.RequestPost1;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyStatementActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyStatement";
    private TextView text_title;
    private Button button_backward;
    private RelativeLayout biaoti;
    private BarChart chart;
    private List<BarEntry> list_adapt=new ArrayList<>();
    private List<String> list_x=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_statement);
        initView();
        initTitle();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDatas();
                drawChart();
            }
        }).start();
    }

    /**
     * 绘制条形图
     */
    private void drawChart(){
        BarDataSet barDataSet=new BarDataSet(list_adapt,"");
        barDataSet.setColor(Color.BLACK);
        barDataSet.setBarSpacePercent(50);
        BarData barData=new BarData(list_x,barDataSet);
        chart.setData(barData);
        XAxis xAxis=chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        YAxis left=chart.getAxisLeft();
        left.setDrawGridLines(false);
        left.setDrawAxisLine(false);
        left.setDrawLabels(false);
        YAxis right=chart.getAxisRight();
        right.setDrawGridLines(false);
        right.setDrawLabels(false);
        right.setDrawAxisLine(false);
        chart.getLegend().setEnabled(false);
        chart.setDescription("");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chart.invalidate();
            }
        });

    }

    /**
     * 从服务器获取图标的内容
     */
    private void getDatas(){
        List<BarEntry> list=new ArrayList<>();
        List<String> list1=new ArrayList<>();
        String s= RequestPost1.Post("{\"username\":\"admin\"}", Constant.IP+"/gettime","MyStatement");
        try {
            JSONObject jsonObject=new JSONObject(s);
            s=jsonObject.getString("data");
            JSONArray jsonArray=new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                String dynasty=jsonObject.getString("dynasty");
                int times=jsonObject.getInt("time");
                list1.add(dynasty);
                list.add(new BarEntry(times,i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list_adapt=list;
        list_x=list1;
    }

    private void initTitle(){
        button_backward.setOnClickListener(this);
    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.text_title);
        button_backward = (Button) findViewById(R.id.button_backward);
        biaoti = (RelativeLayout) findViewById(R.id.biaoti);
        chart = (BarChart) findViewById(R.id.chart);

        button_backward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == button_backward) {
            finish();
        }
    }
}
