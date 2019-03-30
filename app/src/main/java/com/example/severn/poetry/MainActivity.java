package com.example.severn.poetry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.severn.frame.PersonFragment;
import com.example.severn.frame.SearchFragment;
import com.example.severn.frame.StatFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private FragmentTransaction transaction;
    private PersonFragment personFragment;
    private SearchFragment searchFragment;
    private StatFragment statFragment;
    private Fragment mFragment;//当前显示的Fragment



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(searchFragment);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(statFragment);
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(personFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragmrnt();
    }

    //布局的初始化，默认是主页
    private  void initFragmrnt(){
        personFragment = new PersonFragment();
        statFragment = new StatFragment();
        searchFragment = new SearchFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout,searchFragment)
                .commit();
        mFragment = searchFragment;
    }

    //布局的切换
    private void switchFragment(Fragment fragment){
        //判断当前显示的Fragment是不是切换的Fragment
        if(mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.frameLayout,fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }
}
