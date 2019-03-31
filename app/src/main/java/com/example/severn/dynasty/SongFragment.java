package com.example.severn.dynasty;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.severn.Adapter.VideoAdapter;
import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.R;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    //    所有的古诗词list
    private List<VideoDao> videoDaoList = new ArrayList<>();
    private RecyclerView recyclerView;


    public SongFragment() {

    }

    public static SongFragment newInstance(String param1, String param2) {
        SongFragment fragment = new SongFragment();
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
        initVideos();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
//        首先进行集合的初始化
        recyclerView = view.findViewById(R.id.song_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
//        设置滚动方向垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        VideoAdapter adapter = new VideoAdapter(videoDaoList);
        recyclerView.setAdapter(adapter);
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
}
