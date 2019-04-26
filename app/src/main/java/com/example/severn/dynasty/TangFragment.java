package com.example.severn.dynasty;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.severn.Adapter.VideoAdapter;
import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.R;
import com.example.severn.util.Constant;
import com.example.severn.util.StreamTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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


    private static final String TAG ="TangFragment" ;
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
            "八十",
            "咏菊",
            "小池",
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;


    //    所有的古诗词list
    private List<VideoDao> videoDaoList = new ArrayList<>();
    private RecyclerView recyclerView;

    private String videoNmae;
    private String videoAuthor;
    private String videoImgUrl;

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
        final int initalDelay = 0;
        final int delay = 5;
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), initalDelay, delay, TimeUnit.SECONDS);
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
        Constant.DYNASTY = "唐";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tang, container, false);
        setView();
//        古诗词列表
        recyclerView = mView.findViewById(R.id.tanglist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        initVideos();
        return mView;
    }
    //    古诗词列表的初始化
    private void initVideos(){
        new Thread(){
            @Override
            public void run() {
                String data = "{\"dynasty\":\"唐\"}";
                String post = Post(data, Constant.getbspoetry, getActivity());
                Log.d(TAG,post+"===============================");
                String videoList =  paresVideoJSON(post);
                if (videoList!=null){
                    videoJSON(videoList);
                }else {
                }
            }
        }.start();
//        VideoDao tang0 = new VideoDao("静夜思",R.drawable.tang1,"张集","唐");
//        videoDaoList.add(tang0);
    }
    public  String Post(String string,String post,Context context)//string POST参数,get 请求的URL地址,context 联系上下文
    {
        String html;
        try {
            String urldizhi=post; //请求地址
            URL url=new URL(urldizhi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setDoInput(true);//表示从服务器获取数据
            conn.setDoOutput(true);//表示向服务器写数据
            //获得上传信息的字节大小以及长度
            conn.setRequestMethod("POST");
            //是否使用缓存
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(string);
            out.flush();
            out.close();
//            获取返回值代码
//            int code = conn.getResponseCode();
//            Log.d(TAG, "Post: "+code);
//            if (code != 200){
//            }
            InputStream inputStream=conn.getInputStream();
            byte[] data=StreamTools.read(inputStream);
            html = new String(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----"+e);
            String string2="{\"success\":-1}";
            return string2;
        }
        return html;
    }
    private String paresVideoJSON (String jsonData){
        String videoInfo = null;
        jsonData = "["+jsonData+"]";
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                videoInfo = jsonObject.getString("data");
                Log.d(TAG,videoInfo+"====================");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videoInfo;
    }
    private void videoJSON (String jsonData){
        try {
            JSONArray jsonArray  = new JSONArray(jsonData);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject  jsonObject = jsonArray.getJSONObject(i);
                videoNmae = jsonObject.getString("name");
                videoAuthor = jsonObject.getString("author");
                videoImgUrl = jsonObject.getString("image");
                Log.d(TAG,videoNmae+"======"+videoAuthor+"======="+videoImgUrl);
                videoDaoList.add(new VideoDao(videoNmae,videoAuthor,Constant.IP +"/"+videoImgUrl));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        VideoAdapter adapter = new VideoAdapter(videoDaoList);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
