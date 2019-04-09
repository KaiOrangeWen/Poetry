package com.example.severn.poetry;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity {

    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        videoView.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
        String path = "";
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( VideoPlayActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
