package com.example.severn.dynasty;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.severn.Adapter.VideoAdapter;
import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TangFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;



    private View mView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.tang1,
            R.drawable.tang2,
            R.drawable.tang3,
    };
    //存放图片的标题
    private String[] titles = new String[]{
            "轮播1",
            "轮播2",
            "轮播3",
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;


    //    所有的古诗词list
    private List<VideoDao> videoDaoList = new ArrayList<>();
    private RecyclerView recyclerView;


    private void setView(){
        mViewPaper = (ViewPager)mView.findViewById(R.id.vp);

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));


        title = (TextView) mView.findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.mipmap.dot_yes);
                dots.get(oldPosition).setBackgroundResource(R.mipmap.dot_no);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(images.get(position));
        }
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }
    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };


    public TangFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tang, container, false);
        setView();
        initVideos();

//        古诗词列表
        recyclerView = mView.findViewById(R.id.tanglist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        VideoAdapter adapter = new VideoAdapter(videoDaoList);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    //    古诗词列表的初始化
    private void initVideos(){
        VideoDao tang0 = new VideoDao("静夜思",R.drawable.tang1,"张集","唐");
        VideoDao tang1 = new VideoDao("将进酒",R.drawable.tang2,"张集","唐");
        VideoDao tang2 = new VideoDao("留别妻",R.drawable.tang3,"张集","唐");
        VideoDao tang3 = new VideoDao("静夜思",R.drawable.tang2,"张集","唐");
        VideoDao tang4 = new VideoDao("静夜思",R.drawable.tang2,"张集","唐");
        VideoDao tang5 = new VideoDao("静夜思",R.drawable.tang2,"张集","唐");
        VideoDao tang6 = new VideoDao("静夜思",R.drawable.tang2,"张集","唐");
        VideoDao tang7 = new VideoDao("静夜思",R.drawable.tang2,"张集","唐");
        VideoDao tang8 = new VideoDao("静夜思",R.drawable.tang2,"张集","唐");
        videoDaoList.add(tang0);
        videoDaoList.add(tang1);
        videoDaoList.add(tang2);
        videoDaoList.add(tang3);
        videoDaoList.add(tang4);
        videoDaoList.add(tang5);
        videoDaoList.add(tang6);
        videoDaoList.add(tang7);
        videoDaoList.add(tang8);
        videoDaoList.add(tang0);
        videoDaoList.add(tang1);
        videoDaoList.add(tang2);
        videoDaoList.add(tang3);
        videoDaoList.add(tang4);
        videoDaoList.add(tang5);
        videoDaoList.add(tang6);
        videoDaoList.add(tang7);
        videoDaoList.add(tang8);
        videoDaoList.add(tang0);
        videoDaoList.add(tang1);
        videoDaoList.add(tang2);
        videoDaoList.add(tang3);
        videoDaoList.add(tang4);
        videoDaoList.add(tang5);
        videoDaoList.add(tang6);
        videoDaoList.add(tang7);
        videoDaoList.add(tang8);
        videoDaoList.add(tang0);
        videoDaoList.add(tang1);
        videoDaoList.add(tang2);
        videoDaoList.add(tang3);
        videoDaoList.add(tang4);
        videoDaoList.add(tang5);
        videoDaoList.add(tang6);
        videoDaoList.add(tang7);
        videoDaoList.add(tang8);
        videoDaoList.add(tang0);
        videoDaoList.add(tang1);
        videoDaoList.add(tang2);
        videoDaoList.add(tang3);
        videoDaoList.add(tang4);
        videoDaoList.add(tang5);
        videoDaoList.add(tang6);
        videoDaoList.add(tang7);
        videoDaoList.add(tang8);
        videoDaoList.add(tang0);
        videoDaoList.add(tang1);
        videoDaoList.add(tang2);
        videoDaoList.add(tang3);
        videoDaoList.add(tang4);
        videoDaoList.add(tang5);
        videoDaoList.add(tang6);
        videoDaoList.add(tang7);
        videoDaoList.add(tang8);
        videoDaoList.add(tang0);
        videoDaoList.add(tang1);
        videoDaoList.add(tang2);
        videoDaoList.add(tang3);
        videoDaoList.add(tang4);
        videoDaoList.add(tang5);
        videoDaoList.add(tang6);
        videoDaoList.add(tang7);
        videoDaoList.add(tang8);
    }

























    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
