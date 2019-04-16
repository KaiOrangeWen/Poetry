package com.example.severn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.severn.entity.DiscoverDao;
import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.R;
import com.example.severn.poetry.VideoPlayActivity;
import com.example.severn.util.MyImageView;

import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {
    private List<DiscoverDao> mVideoList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View videoView;
        MyImageView videoImg;
        TextView videoName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView;
            videoImg = itemView.findViewById(R.id.discover_img);
            videoName = itemView.findViewById(R.id.discover_video);
        }
    }
    public DiscoverAdapter(List<DiscoverDao> videoList) {
        mVideoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discover_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                DiscoverDao discoverDao = mVideoList.get(postion);
                Toast.makeText(v.getContext(),discoverDao.getName() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),VideoPlayActivity.class);
                intent.putExtra("imgurl",discoverDao.getImageId());
                intent.putExtra("videourl",discoverDao.getVideoPath());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DiscoverDao discoverDao = mVideoList.get(i);
        viewHolder.videoImg.setImageURL(discoverDao.getImageId());
        viewHolder.videoName.setText(discoverDao.getName());
    }
    @Override
    public int getItemCount() {
        System.out.println("视频的的个数"+mVideoList.size());
        return mVideoList.size();
    }
}
