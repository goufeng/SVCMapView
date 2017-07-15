package com.example.flowproject;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    RadarView view;

    private ImageView mImageView;
    ListView mListView;
    float degree=0;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    degree+=3;
                    view.rotate(degree);
                    sendEmptyMessageDelayed(0,10);
                    if(degree>=360){
                        degree = 0;
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ZoomView view = new ZoomView(this);
//        view = new RadarView(this);
//        setContentView(view);
//        WaterWave view = new WaterWave(this);
//        MapView view = new MapView(this);
//        setContentView(view);
//        mHandler.sendEmptyMessageDelayed(0,1000);
        setContentView(R.layout.activity_main);
//        mImageView = (ImageView) findViewById(R.id.anima_image);
//        mImageView.setImageResource(R.drawable.vector_anima);
//        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                anim(mImageView);
//            }
//        });
    }

    public void anim(View view){
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        ((Animatable)drawable).start();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
