package com.example.severn.frame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.severn.poetry.R;
import com.example.severn.util.RequestPost1;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MyStatementFragment3 extends Fragment {
    private PieChart chart;
    private List<Entry> list_adapt=new ArrayList<>();
    private List<String> list_x=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_statement_fragment3, container, false);
        initView(view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDatas();
                drawChart();
            }
        }).start();
        return view;
    }

    /**
     * 饼图
     */
    private void drawChart(){
        PieDataSet pieDataSet=new PieDataSet(list_adapt,"");
        pieDataSet.setColors(new int[]{Color.RED,Color.GREEN,Color.BLACK});
        PieData pieData=new PieData(list_x,pieDataSet);
        chart.setData(pieData);
        chart.getLegend().setEnabled(false);
        chart.setDescription("");
        if (getActivity()==null)
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chart.setTouchEnabled(false);
                chart.invalidate();
            }
        });
    }

    /**
     * 从服务器获取图标的内容
     */
    private void getDatas(){
        List<Entry> list=new ArrayList<>();
        List<String> list1=new ArrayList<>();
        String s= RequestPost1.get( "/gettime?username=admin");
        Log.d(TAG, "getDatas: "+s);
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

    private void initView(View view) {
        chart = (PieChart) view.findViewById(R.id.chart);
    }
}
