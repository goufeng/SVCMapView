package com.example.flowproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义挂挂卡效果
 * Created by jl on 2017-07-03.
 */
public class GGCardView extends View {
    Bitmap mBitmapText;
    Bitmap mBitmapCover;
    int textWidth=0;
    int textHeight=0;
    int coverWidth = 0;
    int coverHeight=0;
    int startX = 0;
    int startY = 0;
    int viewWidth = 0;
    int viewHeight = 0;
    Paint mPaint;
    float scale =0;
    Path path;
    int dx = 100;
    int dy = 100;
    int nextX = 0;
    int nextY = 0;
    private Bitmap mBitmapDST;
    private float mPreX,mPreY;

    public GGCardView(Context context) {
        super(context);
    }

    public GGCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        initView();
    }

    private void initView(){
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(45);

        path= new Path();
        mBitmapText = BitmapFactory.decodeResource(getResources(),R.drawable.guaguaka_text1);
        mBitmapCover = BitmapFactory.decodeResource(getResources(),R.drawable.guaguaka);
        mBitmapDST = Bitmap.createBitmap(mBitmapCover.getWidth(),mBitmapCover.getHeight(), Bitmap.Config.ARGB_8888);

        textWidth = mBitmapText.getWidth();
        textHeight = mBitmapText.getHeight();
        coverWidth = mBitmapCover.getWidth();
        coverHeight = mBitmapCover.getHeight();
        startX = viewWidth/2-coverWidth/2;
        startY = viewHeight/2-coverHeight/2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapText,0,0,mPaint);
        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);

        Canvas canvas1 = new Canvas(mBitmapDST);
        canvas1.drawPath(path,mPaint);

        canvas.drawBitmap(mBitmapDST,0,0,mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mBitmapCover,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                path.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}
