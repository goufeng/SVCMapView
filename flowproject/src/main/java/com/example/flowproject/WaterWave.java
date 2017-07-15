package com.example.flowproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jl on 2017-06-29.
 */
public class WaterWave extends View {
    RadialGradient radialGradient;
    private int [] colors = {Color.parseColor("#E0FFFF"),Color.WHITE};
    private float [] stops = {(float) 0.5,1};
    private int radius = 400;
    private int cx = 500;
    private int cy = 500;
    private Paint mPaint;
    private float waveStep = 20;
    private Canvas mCanvas;

    public WaterWave(Context context) {
        super(context);
        initView();
    }

    public WaterWave(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        radialGradient = new RadialGradient(cx,cx,radius,colors,stops, Shader.TileMode.REPEAT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float ra = 10;
        while (ra<getWidth()/2){
            mPaint.reset();
            canvas.save();
            radialGradient = new RadialGradient(cx,cy,ra,colors,stops, Shader.TileMode.REPEAT);
            mPaint.setShader(radialGradient);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(cx,cy,ra,mPaint);
            ra+=waveStep;
        }
    }

    private void drawWave(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                float ra = 10;
                while (ra<getWidth()/2){
                    mPaint.reset();
                    mCanvas.save();
                    radialGradient = new RadialGradient(cx,cy,ra,colors,stops, Shader.TileMode.REPEAT);
                    mPaint.setShader(radialGradient);
                    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    mCanvas.drawCircle(cx,cy,ra,mPaint);
                    ra+=waveStep;
                    postInvalidateDelayed(1000);
                }
//            }
//        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        cx = (int) event.getX();
        cy = (int) event.getY();
        drawWave();
        return true;
    }
}
