package com.example.severn.frame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.severn.poetry.MainActivity;
import com.example.severn.poetry.PoetryTestActivity;
import com.example.severn.poetry.R;
import com.example.severn.util.Constant;

public class PersonFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private RelativeLayout test;
    private RelativeLayout exit;




    private TextView name;


    public PersonFragment() {
    }
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        test = view.findViewById(R.id.mytest);
        name = view.findViewById(R.id.userneme);
        exit = view.findViewById(R.id.exit);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","请登录");
        name.setText(username);
//        String password = sharedPreferences.getString("password","");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().commit();
//                onHiddenChanged(true);
                name.setText("请登录");
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PoetryTestActivity.class));
            }
        });
        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (hidden){
//            SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
//            name.setText(sharedPreferences2.getString("username","请登录"));
//        }else {
//
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserVisibleHint(true);
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        name.setText(sharedPreferences2.getString("username","请登录"));
    }
}
