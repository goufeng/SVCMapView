package com.example.flowproject;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class DrawView extends View {
    private float round;
    private Path path;
    private Paint paint = null;
    private int VIEW_WIDTH = 800;
    private int VIEW_HEIGHT = 600;
    Bitmap cacheBitmap = null;
    //定义缓冲区Cache的Canvas对象
    Canvas cacheCanvas = null;
    private RectF mRectF;
    public float mpro = -1;
    float length;
    float lineLen;
    PointF pointF;
    int radius = 50;
    int lLength = LINE_LENGTH;
    private static final int LINE_LENGTH = 80;
    private ValueAnimator valueAnimator;

    public DrawView(Context context) {
        this(context,null);
        pointF = new PointF(100,800);
        mRectF = new RectF(pointF.x-radius,pointF.y-radius,pointF.x+radius,pointF.y+radius);
        mpro= 0;
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //创建一个与该VIew相同大小的缓冲区
        cacheBitmap = Bitmap.createBitmap(VIEW_WIDTH,VIEW_HEIGHT,Bitmap.Config.ARGB_8888);
        //创建缓冲区Cache的Canvas对象
        cacheCanvas = new Canvas();
        path = new Path();
        //设置cacheCanvas将会绘制到内存的bitmap上
        cacheCanvas.setBitmap(cacheBitmap);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setFlags(Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);
        paint.setDither(true);

        round = (float) (2*Math.PI*radius);
        length = round+lLength;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        //mpro为圆的角度的进度，当mpro==0时，则整个圆绘制完成
        if(mpro>0){
            path.addArc(mRectF,45,-360*mpro);
        }
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPath(path,paint);
        canvas.save();
        canvas.drawLine(
                pointF.x+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH))-(float)(Math.sin(2*Math.PI/360*45)*lLength),
                pointF.y+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH))-(float)(Math.sin(2*Math.PI/360*45)*lLength),
                pointF.x+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH)),
                pointF.y+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH)),
                paint);

        canvas.drawLine(pointF.x+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH))-lineLen,
                pointF.y+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH)),
                pointF.x+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH)),
                pointF.y+(float)(Math.sin(2*Math.PI/360*45)*(radius+LINE_LENGTH)),
                paint);
    }

    public void startViewAnimation(){
        if(valueAnimator!=null&&valueAnimator.isRunning()){
            return;
        }
        valueAnimator = ValueAnimator.ofFloat(0,length);
        valueAnimator.setDuration((long) (2000));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float  curLen = (float) animation.getAnimatedValue();
                float lingTh = curLen -round;
                pointF.x+=2;
                mRectF = new RectF(pointF.x-radius,pointF.y-radius,pointF.x+radius,pointF.y+radius);
                if(lingTh>0){
                    lLength = (int) (80-lingTh);
                }else{
                    if(curLen<=round){
                        mpro= 1-curLen/round - 0.009f;
                    }
                }
                lineLen = curLen;
              invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                lLength = 80;
                pointF.x = 100;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                endViewAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    public void endViewAnimation(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(length,0);
        valueAnimator.setDuration((long) (2000));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float  curLen = (float) animation.getAnimatedValue();
                float lingTh = curLen -round;
                pointF.x-=2;
                mRectF = new RectF(pointF.x-radius,pointF.y-radius,pointF.x+radius,pointF.y+radius);
                if(lingTh>0){
                    lLength = (int) (80-lingTh);
                }else{
                    if(curLen<=round){
                        mpro= 1-curLen/round;
                    }
                }
                lineLen = curLen;
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startViewAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startViewAnimation();
                break;
        }
        return true;
    }
}
