package com.example.flowproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义放大镜
 * Created by jl on 2017-06-29.
 */
public class ZoomView extends View {

    //放大倍数
    private static final int FACTOR = 2;
    //放大镜的半径
    private static final int RADIUS  = 100;
    // 原图
    private Bitmap mBitmap;
    // 放大后的图
    private Bitmap mBitmapScale;
    // 制作的圆形的图片（放大的局部），盖在Canvas上面
    private ShapeDrawable mShapeDrawable;

    private Matrix mMatrix;

    public ZoomView(Context context) {
        super(context);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xyjy3);
        mBitmapScale = Bitmap.createScaledBitmap(mBitmap,mBitmap.getWidth()*FACTOR,mBitmap.getHeight()*FACTOR,true);
        BitmapShader bitmapShader = new BitmapShader(mBitmapScale, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mShapeDrawable = new ShapeDrawable();
        mShapeDrawable.setShape(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        mShapeDrawable.setBounds(0,0,RADIUS*2,RADIUS*2);
        mMatrix = new Matrix();
    }

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xyjy3);
        mBitmapScale = Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth()*FACTOR,mBitmap.getHeight()*FACTOR);
        BitmapShader bitmapShader = new BitmapShader(mBitmapScale, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mShapeDrawable = new ShapeDrawable();
        mShapeDrawable.setShape(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        mShapeDrawable.setBounds(0,0,RADIUS*2,RADIUS*2);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,0,0,null);
        canvas.save();
        mShapeDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        mMatrix.setTranslate(-x*FACTOR+RADIUS,-y*FACTOR+RADIUS);
        mShapeDrawable.getPaint().getShader().setLocalMatrix(mMatrix);
//        mShapeDrawable.setBounds(new Rect());
        mShapeDrawable.setBounds(x-RADIUS,y - RADIUS, x + RADIUS, y + RADIUS);
        invalidate();
        return true;
    }
}
