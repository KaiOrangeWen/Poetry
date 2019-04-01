package com.example.severn.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.severn.entity.VideoDao;
import com.example.severn.poetry.R;

import java.util.List;

/**
 * RecyclerView 的适配器
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoDao> mVideoList;



    static class ViewHolder extends RecyclerView.ViewHolder{

        View videoView;

        ImageView videoImg;
        TextView videoName;
        TextView videoAuthor;
        TextView videoTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView;

            videoImg = itemView.findViewById(R.id.video_img);
            videoName = itemView.findViewById(R.id.video_name);
            videoAuthor = itemView.findViewById(R.id.video_author);
            videoTime = itemView.findViewById(R.id.video_time);
        }
    }
    public VideoAdapter(List<VideoDao> videoList) {
        mVideoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                VideoDao videoDao = mVideoList.get(postion);
                Toast.makeText(v.getContext(), videoDao.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        VideoDao videoDao = mVideoList.get(i);
        viewHolder.videoImg.setImageResource(videoDao.getImageId());
        viewHolder.videoTime.setText(videoDao.getTime());
        viewHolder.videoName.setText(videoDao.getName());
        viewHolder.videoAuthor.setText(videoDao.getAuthor());
    }
    @Override
    public int getItemCount() {
        System.out.println(mVideoList.size()+"==================================================================");
        return mVideoList.size();

    }
}
