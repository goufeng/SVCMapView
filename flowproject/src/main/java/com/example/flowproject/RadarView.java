package com.example.flowproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View{
    private int[] mColors = {Color.argb(80,0,55,255),Color.argb(200,0,55,255),Color.argb(200,255,0,0)};
    Paint mPaint;
    SweepGradient mSweepGradient;
    float[] pts = {300,0,300,600,0,300,600,300};
    Matrix matrix;
    public RadarView(Context context) {
        super(context);
        init();
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mSweepGradient = new SweepGradient(300, 300, mColors, null);
        mPaint = new Paint();
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setShader(mSweepGradient);
        canvas.drawCircle(300, 300, 300, mPaint);
        canvas.save();
        mPaint.reset();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(300, 300,60,mPaint);
        canvas.drawCircle(300, 300,120,mPaint);
        canvas.drawCircle(300, 300,180,mPaint);
        canvas.drawCircle(300, 300,240,mPaint);
        canvas.drawCircle(300, 300,300,mPaint);
        canvas.save();
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.YELLOW);
        canvas.drawLines(pts,mPaint);
        canvas.save();
    }

    public void rotate(float degree){
        matrix.setRotate(degree,300, 300);
        mSweepGradient.setLocalMatrix(matrix);
        postInvalidate();
    }
}
