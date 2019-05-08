package com.example.severn.poetry;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.severn.util.RequestPost1;

import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity {

    MapView mapView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        Intent intent=getIntent();
//        Bundle bundle=intent.getBundleExtra("map");
//        final String authorName=bundle.getString("author");
//        final String poetryName=bundle.getString("poetry");
//        Log.d("aaa", "onCreate: "+authorName);
        new Thread(new Runnable() {
            private static final String TAG = "MapActivity";

            @Override
            public void run() {
                String s=RequestPost1.get("/getadress?authorname="+"李白"+"&poetryname="+"静夜思");
                Log.d("aaa", "run: "+s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    s=jsonObject.getString("data");
                    jsonObject=new JSONObject(s);

                    final double x=jsonObject.getDouble("wtd");
                    final double y=jsonObject.getDouble("ltd");
                    Log.d(TAG, "当前的坐标"+x+"=========================="+y);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMap(savedInstanceState,x,y);//x:纬度 y:经度
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showMap(Bundle bundle,double x,double y){
        mapView.onCreate(bundle);
        AMap aMap=mapView.getMap();
        LatLng latLng=new LatLng(x,y);
        aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.point)))
        );
        CameraPosition cameraPosition= CameraPosition.fromLatLngZoom(latLng,13);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    private void initView() {
        mapView=findViewById(R.id.mapView);
    }
}
