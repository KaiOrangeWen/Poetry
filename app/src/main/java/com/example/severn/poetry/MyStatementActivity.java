package com.example.severn.poetry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.severn.frame.MyStatementFragment1;
import com.example.severn.frame.MyStatementFragment2;
import com.example.severn.frame.MyStatementFragment3;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class MyStatementActivity extends AppCompatActivity implements View.OnClickListener {

    private Fragment[] fragments = new Fragment[]{new MyStatementFragment1(), new MyStatementFragment2(), new MyStatementFragment3()};
    private static final String TAG = "MyStatement";
    private TextView text_title;
    private Button button_backward;
    private RelativeLayout biaoti;
    private BarChart chart;
    private List<BarEntry> list_adapt = new ArrayList<>();
    private List<String> list_x = new ArrayList<>();
    private Adapt_viewpager adapt_viewpager;
    private ViewPager viewPager;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView[] textViews;
    private TextView lastTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_statement);
        initView();
        initTitle();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                lastTextview.setBackgroundResource(R.drawable.statement_view_normal);
                textViews[i].setBackgroundResource(R.drawable.statement_view_selecter);
                lastTextview=textViews[i];
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initTitle() {
        button_backward.setOnClickListener(this);
    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.text_title);
        button_backward = (Button) findViewById(R.id.button_backward);
        biaoti = (RelativeLayout) findViewById(R.id.biaoti);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adapt_viewpager = new Adapt_viewpager(getSupportFragmentManager());
        viewPager.setAdapter(adapt_viewpager);
        button_backward.setOnClickListener(this);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        textViews = new TextView[]{tv1,tv2,tv3};
        lastTextview=tv1;
    }

    @Override
    public void onClick(View v) {
        if (v == button_backward) {
            finish();
        }
    }

    class Adapt_viewpager extends FragmentPagerAdapter {

        public Adapt_viewpager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

    }
}
