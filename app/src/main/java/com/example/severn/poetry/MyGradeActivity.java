package com.example.severn.poetry;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.severn.util.MyOpenHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MyGradeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text_title;
    private Button button_backward;
    private RelativeLayout biaoti;
    private LineChart chart;
    private List<Entry> list_adapt=new ArrayList<>();
    private List<String> list_x=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grade);
        initView();
        getData();
        drawChart();
        initTitle();
    }

    private void initTitle(){
        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 折线图
     */
    private void drawChart(){
        LineDataSet lineDataSet=new LineDataSet(list_adapt,"");
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setCircleSize(5);
        LineData lineData=new LineData(list_x,lineDataSet);
        chart.setData(lineData);
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
        chart.setExtraRightOffset(30);
        chart.setExtraLeftOffset(30);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chart.setTouchEnabled(false);
                chart.invalidate();
            }
        });
    }

    /**
     * 从数据库中获得数据
     */
    private void getData(){
        MyOpenHelper myOpenHelper=new MyOpenHelper(this,null,null,0);
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query("Grade",null,null,null,null,null,null);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM.dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        List<Entry> list=new ArrayList<>();
        List<String> list1=new ArrayList<>();
        int index=0;
        while (cursor.moveToNext()) {
            float grade=cursor.getFloat(cursor.getColumnIndex("grade"));
            long l=cursor.getLong(cursor.getColumnIndex("time"));
            String time=simpleDateFormat.format(new Date(l));
            list.add(new Entry(grade,index++));
            list1.add(time);
        }
        list_adapt=list;
        list_x=list1;
        Log.d("aaa", "getData: "+list_adapt.size()+"---"+list_x.size());
    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.text_title);
        button_backward = (Button) findViewById(R.id.button_backward);
        biaoti = (RelativeLayout) findViewById(R.id.biaoti);
        chart = (LineChart) findViewById(R.id.chart);

        button_backward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_backward:

                break;
        }
    }
}
