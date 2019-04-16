package com.example.severn.frame;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.severn.Adapter.DiscoverAdapter;
import com.example.severn.entity.DiscoverDao;
import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.R;
import com.example.severn.util.Constant;
import com.example.severn.util.JSONAnalysis;
import com.example.severn.util.RequestPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewDiscover;
    private List<DiscoverDao> discoverDaoList = new ArrayList<>();

    public SearchFragment() {
    }
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.discover2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        // 设置滚动方向水平
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discoverDaoList);
        recyclerView.setAdapter(discoverAdapter);
        recyclerViewDiscover = view.findViewById(R.id.discover);
        LinearLayoutManager linearLayoutManagerDis = new LinearLayoutManager(this.getActivity());
        //设置滚动方向水平
        linearLayoutManagerDis.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDiscover.setLayoutManager(linearLayoutManagerDis);
        DiscoverAdapter discoverAdaptergf = new DiscoverAdapter(discoverDaoList);
        recyclerViewDiscover.setAdapter(discoverAdaptergf);
        return view;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    private void initList(){
//        final DiscoverDao discoverDao = new DiscoverDao("静夜思",Constant.IP+"/images/libai.jpg","");
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);
//        discoverDaoList.add(discoverDao);


        new Thread(){
            @Override
            public void run() {
                String data = "{\"type\":\"古风\"}";
                String url= Constant.IP+"/getvideo";
                RequestPost requestPost = new RequestPost();
                String post = requestPost.Post(data, url, "gfSearch");
                Log.d("============",post);
                post = "["+post+"]";
                try {
                    JSONArray jsonArray = new JSONArray(post);
                    String data1 = jsonArray.getJSONObject(0).getString("data");
                    JSONArray jsonArray2 = new JSONArray(data1);
                    for (int i = 0;i<jsonArray2.length();i++){
                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String videopath = jsonObject.getString("video");
                        String imagepath = jsonObject.getString("image");
                        discoverDaoList.add(new DiscoverDao(name,Constant.IP+"/"+imagepath,Constant.IP+"/"+videopath));
                        Log.d("============",name+Constant.IP+"/"+imagepath+videopath);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                JSONAnalysis jsonAnalysis = new JSONAnalysis();
//                String data1 = jsonAnalysis.analysisData(post, "", "data");
//                String name = jsonAnalysis.JSON(data1, "", "name");
//                String videopath = jsonAnalysis.JSON(data1, "", "video");
//                String imagepath = jsonAnalysis.JSON(data1, "", "image");
//                DiscoverDao discoverDao1 = new DiscoverDao(name,imagepath,videopath);
//                discoverGuFeng.add(discoverDao);
            }
        }.start();
    }
}
