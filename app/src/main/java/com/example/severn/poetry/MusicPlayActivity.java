package com.example.severn.poetry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.severn.entity.PoemDao;
import com.example.severn.util.AudioUtils;
import com.example.severn.util.Constant;
import com.example.severn.util.JSONAnalysis;
import com.example.severn.util.RequestPost;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MusicPlayActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    int flag = 0;
    int playflag = 0;
    private ListView lv;
    private ImageButton btn_play_pause;
    private ImageButton btn_heart,btn_map;
    private Button back;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private TextView tv_time_start;
    private TextView tv_time_end;
    private TextView tv_poem_title;
    private List<PoemDao> poemDaos = new ArrayList<PoemDao>();

    private TextToSpeech tts;

    RequestPost requestPost = new RequestPost();
    JSONAnalysis jsonAnalysis = new JSONAnalysis();
    private Handler handler = new Handler();
    String message;
    String pomename;
    String username;
    String jsonData;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //判断如果滚动条数值大小大于或者等于最大值
            if(seekBar.getProgress()>=seekBar.getMax()){
                handler.removeCallbacks(runnable);
            }
            //调用线程
            handler.post(runnable);
            //给seekbar和timestart赋值
            int currentPostion = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(currentPostion);
            String time2 = timeParse(currentPostion);
            tv_time_start.setText(time2);
        }
    };

//    String poems[]={"床前明月光，","疑是地上霜。","举头望明月，","低头思故乡。"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music_play);

        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=5cabe307");

        lv = findViewById(R.id.lv_poem);
        seekBar = findViewById(R.id.seek_bar);
        tv_time_start = findViewById(R.id.time_start);
        tv_time_end = findViewById(R.id.time_end);
        btn_play_pause = findViewById(R.id.btn_play);
        btn_heart = findViewById(R.id.btn_heart);
        tv_poem_title = findViewById(R.id.tv_poem_title);
        back = findViewById(R.id.button_backward);
        btn_map=findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示地图
                Intent intent=new Intent(MusicPlayActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });



        Intent intent = getIntent();
        pomename = intent.getStringExtra("pomename");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //初始化歌词显示
        new Thread(){
            @Override
            public void run() {
                tv_poem_title.setText(pomename);
                SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                username = sharedPreferences.getString("username", "admin");
                jsonData = requestPost.Post("{\"username\":\"" + username + "\",\"poetryname\":\"" + pomename + "\"}", Constant.GETPOETRY, "MusicPlayActivity");
                Log.d("Music","{\"username\":\"" + username + "\",\"poetryname\":\"" + pomename + "\"}===============================");
                String author = jsonAnalysis.analysisData(jsonData, "", "author");
                String dynasty = jsonAnalysis.analysisData(jsonData, "", "dynasty");
                message = jsonAnalysis.analysisData(jsonData, "", "message");
                initList(message);
                Log.d("",message+"=========================================");
                Log.d("我是网络请求线程","initList();");
                new Thread(){
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lv.setAdapter(new MyAdapter());
                                Log.d("我是UI线程","lv.setAdapter(new MyAdapter());");
                            }
                        });
                    }
                }.start();
            }
        }.start();
        //SeekBar监听事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //设置音乐在Seekbar中滑动后的播放
                int currentPosition = seekBar.getProgress();
                mediaPlayer.seekTo(currentPosition);
            }
        });
        //播放按钮/1为播放，0为停止
        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 1){
                    AudioUtils.getInstance().init(MusicPlayActivity.this); //初始化语音对象
                    AudioUtils.getInstance().speakText(""); //播放语音
                    btn_play_pause.setBackgroundResource(R.mipmap.play_while);
                    flag = 0;
                }
                else{
                    AudioUtils.getInstance().init(MusicPlayActivity.this); //初始化语音对象
                    AudioUtils.getInstance().speakText(message); //播放语音
                    btn_play_pause.setBackgroundResource(R.mipmap.pause_while);
                    flag = 1;
                }
//                btn_play_pause = (ImageButton)view;
//                //第一次启动
//                if (mediaPlayer == null){
////                    //创建一个MeadiaPlay
//                    mediaPlayer = MediaPlayer.create(MusicPlayActivity.this,R.raw.ceshi);
////                    //开始播放
//                    mediaPlayer.start();
//                    //改变图标
//                    btn_play_pause.setBackgroundResource(R.mipmap.pause_while);
//                    //设置mediaplayer的监听 在调用mediaPlaye.start()后触发
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            long time = mediaPlayer.getDuration();
//                            seekBar.setMax((int)time);
//                            Log.i("tima",""+time);
//                            handler.post(runnable);
//                            //获取时间转换成分秒之后的值
//                            String time2=timeParse(time);
//                            tv_time_end.setText(time2);
//                        }
//                    });
//                    //如果音乐在播放
//                }else if(mediaPlayer.isPlaying()){
//                    //停止播放
//                    mediaPlayer.pause();
//                    btn_play_pause.setBackgroundResource(R.mipmap.play_while);
//                }else{
//                    //如果音乐停止过 则播放
//                    mediaPlayer.start();
//                    btn_play_pause.setBackgroundResource(R.mipmap.pause_while);
//                }

            }
        });
        //喜欢功能/1为不喜欢，0为喜欢
        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    btn_heart.setBackgroundResource(R.mipmap.heart_while);
                    flag = 0;
                }
                else{
                    btn_heart.setBackgroundResource(R.mipmap.heart_red);
                    flag = 1;
                }
            }
        });
    }

    private InitListener myInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("mySynthesiezer:", "InitListener init() code = " + code);
        }
    };



    //list_view适配器
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return poemDaos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(MusicPlayActivity.this,R.layout.poem_item,null);
            TextView tv;
            tv = view.findViewById(R.id.tv_poems);
            tv.setText(poemDaos.get(position).getPoemItem());
            return view;
        }
    }
    //集合初始化
    public void initList(String poems){
        String[] poem = poems.split("。");
        for (int i = 0;i<poem.length;i++){
            poemDaos.add(new PoemDao(poem[i]+"。"));
            Log.d("",poemDaos.get(i).getPoemItem()+"===================");
        }
    }
    //毫秒转换分秒
    public static  String timeParse(long duration){
        String time = "";
        long min = duration / 60000;
        long second = duration % 60000;
        long sec = Math.round((float)second/1000);
        if(min<10){
            time += "0";
        }
        time += min+":";
        if (sec<10){
            time += "0";
        }
        time += sec;
        return time;
    }



    @Override
    public void onInit(int status) {
        // 判断是否转化成功
        if (status == TextToSpeech.SUCCESS){
            //默认设定语言为中文，原生的android貌似不支持中文。
            int result = tts.setLanguage(Locale.CHINESE);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(MusicPlayActivity.this,"不支持", Toast.LENGTH_SHORT).show();
            }else{
                //不支持中文就将语言设置为英文
                tts.setLanguage(Locale.US);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
