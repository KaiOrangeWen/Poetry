package com.example.severn.frame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.severn.Adapter.VideoAdapter;
import com.example.severn.dynasty.MingFragment;
import com.example.severn.dynasty.QingFragment;
import com.example.severn.dynasty.SongFragment;
import com.example.severn.dynasty.TangFragment;
import com.example.severn.dynasty.YuanFragment;
import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.LoginActivity;
import com.example.severn.poetry.R;
import com.example.severn.tabview.TabTopAutoLayout;

import java.util.ArrayList;
import java.util.List;
public class StatFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private TabTopAutoLayout id_titleLayout;
    private ArrayList<CharSequence> tabTitleList = new ArrayList<CharSequence>();
    /**保存的选项卡的下标值*/
    private int savdCheckedIndex = 0;
    /**当前的选项卡的下标值*/
    private int mCurrentIndex = -1;
    private TextView tv_show;//显示选中的选项卡的文本

    private FragmentTransaction transaction;
    private SongFragment songFragment;
    private TangFragment tangFragment;
    private YuanFragment yuanFragment;
    private MingFragment mingFragment;
    private QingFragment qingFragment;
    private Fragment mFragment;//当前显示的Fragment

//    登陆按钮
    private Button longinButton;

    public StatFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    /**初始化数据*/
    private void initData() {
        tabTitleList.add("唐代");
        tabTitleList.add("宋代");
        tabTitleList.add("元代");
        tabTitleList.add("明代");
        tabTitleList.add("清代");
        id_titleLayout.setTabList(tabTitleList);
    }
    /**
     * 初始化点击事件
     * */
    private void initEvent(){
        //每一个选项卡的点击事件
        id_titleLayout.setOnTopTabSelectedListener(new TabTopAutoLayout.OnTopTabUnderLineSelectListener() {
            @Override
            public void onTopTabSelected(int index) {
                ShowFragment(index);//独立出来，用于OnResume的时候初始化展现相应的Fragment
            }
        });
    }
    /**控制切换选项卡*/
    public void SwitchTab(int checkedIndex){
        if(id_titleLayout != null){
            id_titleLayout.setTabsDisplay(checkedIndex);
        }
    }
    /**
     * 显示选项卡对应的Fragment*/
    public void ShowFragment(int checkedIndex) {
        if (mCurrentIndex == checkedIndex) {
            return;
        }
        //隐藏全部碎片
        hideFragments();
        //=============To Do根据实际情况显示不同的区域=============
        switch (tabTitleList.get(checkedIndex).toString()) {
            case "明代":
                switchFragment(mingFragment);
                System.out.println(checkedIndex+"==============================="+"明代选项卡");
                return;
            case "清代":
                switchFragment(qingFragment);
                System.out.println(checkedIndex+"==============================="+"清代选项卡");
                return;
            case "元代":
                switchFragment(yuanFragment);
                System.out.println(checkedIndex+"==============================="+"元代选项卡");
                return;
            case "唐代":
                switchFragment(tangFragment);
                System.out.println(checkedIndex+"==============================="+"唐代选项卡");
                return;
            case "宋代":
                switchFragment(songFragment);
                System.out.println(checkedIndex+"==============================="+"宋代选项卡");
                return;
        }
        savdCheckedIndex = checkedIndex;
        mCurrentIndex = checkedIndex;
    }
    /**隐藏全部碎片
     * 需要注意：不要在OnResume方法中实例化碎片，因为先添加、显示，才可以隐藏。否则会出现碎片无法显示的问题*/
    private void hideFragments() {
        //=============根据实际情况进行处理=============
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);
        id_titleLayout = view.findViewById(R.id.id_titleLayout);
        longinButton = view.findViewById(R.id.bstat_login);
        //初始化数据
        initData();
        //初始化控件的点击事件
        initEvent();
//        初始化Fragmrnt
        initFragmrnt();
//        选项卡默认选中唐代
        SwitchTab(0);
        longinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(getActivity(),LoginActivity.class),1);
            }
        });
        return view;
        //初始化控件
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    //布局的初始化，默认是唐代
    private  void initFragmrnt(){
        mingFragment = new MingFragment();
        qingFragment = new QingFragment();
        songFragment = new SongFragment();
        tangFragment = new TangFragment();
        yuanFragment = new YuanFragment();
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.dynasyfrag,tangFragment)
                .commit();
        mFragment = tangFragment;
    }
    //布局的切换
    private void switchFragment(Fragment fragment){
        //判断当前显示的Fragment是不是切换的Fragment
        if(mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.dynasyfrag,fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == 2){
                    String returnUsername = data.getStringExtra("name");
                    longinButton.setText(returnUsername);
                }
        }
    }
}
