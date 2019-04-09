package com.example.severn.frame;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.severn.Adapter.DiscoverAdapter;
import com.example.severn.entity.DiscoverDao;
import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.R;
import com.example.severn.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private RecyclerView recyclerView;
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
        recyclerView = view.findViewById(R.id.discover);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
//        设置滚动方向水平
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discoverDaoList);
        recyclerView.setAdapter(discoverAdapter);
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
        DiscoverDao discoverDao = new DiscoverDao("静夜思",Constant.IP+"/images/libai.jpg");
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
        discoverDaoList.add(discoverDao);
    }
}
