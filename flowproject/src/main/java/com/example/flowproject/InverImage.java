package com.example.flowproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * 倒影image
 * Created by jl on 2017-06-29.
 */
public class InverImage extends View{

    Bitmap mBitmap;
    int width;
    private Bitmap mScaleBitmap;
    LinearGradient linearGradient;
    Paint mPaint;
    Matrix matrix;
//    Bitmap finalBit;
    private Canvas mCanvas;
    private Bitmap inverBit;

    public InverImage(Context context) {
        super(context);
//        initView();
    }

    public InverImage(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initView();
    }

    private void initView(){
        mPaint= new Paint();
        matrix= new Matrix();
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xyjy2);
        width = getMeasuredWidth()/4;
        float scale = (float) width/mBitmap.getWidth();
        mScaleBitmap = Bitmap.createScaledBitmap(mBitmap,(int)(mBitmap.getWidth()*scale),(int)(mBitmap.getHeight()*scale),true);
        mBitmap.recycle();
        matrix.preScale(1,-1);

        inverBit = Bitmap.createBitmap(mScaleBitmap,0,mScaleBitmap.getHeight()/2,mScaleBitmap.getWidth(),mScaleBitmap.getHeight()/2,
                matrix,true);

//        finalBit = Bitmap.createBitmap(mScaleBitmap.getWidth(),mScaleBitmap.getHeight()+mScaleBitmap.getHeight()/2, Bitmap.Config.ARGB_8888);

        //创建线性渐变LinearGradient对象
        linearGradient = new LinearGradient(0, mScaleBitmap.getHeight(), 0, inverBit.getHeight() + 1, 0x70ffffff,
                0x00ffffff, Shader.TileMode.MIRROR);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        createReflectedImage(mScaleBitmap,canvas);
//        canvas.drawBitmap(mScaleBitmap, 0, 0, null);
//        canvas.save();
//
//        mPaint.reset();
//
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        mPaint.setShader(linearGradient);
//        mPaint.setColor(Color.RED);
//        //画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
//        canvas.drawRect(0, mScaleBitmap.getHeight() + 1, mScaleBitmap.getWidth(), inverBit.getHeight(), mPaint);
//        canvas.save();
//        //把倒影图片画到画布上
//        canvas.drawBitmap(inverBit, 0, mScaleBitmap.getHeight() + 1, null);
//        canvas.save();
    }

    public static Bitmap createReflectedImage(Bitmap originalImage,Canvas canvas)
    {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        // 实现图片翻转90度
        matrix.preScale(1, -1);
        // 创建倒影图片（是原始图片的一半大小）
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
        // 创建总图片（原图片 + 倒影图片）
        Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);
        // 创建画布
        canvas.drawBitmap(originalImage, 0, 0, null);
        //把倒影图片画到画布上
        canvas.drawBitmap(reflectionImage, 0, height + 1, null);
        Paint shaderPaint = new Paint();
//        shaderPaint.setAlpha(100);
        //创建线性渐变LinearGradient对象
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, finalReflection.getHeight() + 1, 0x70ffffff,
                0x00ffffff, Shader.TileMode.MIRROR);
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
        canvas.drawRect(0, height + 1, width, finalReflection.getHeight(), shaderPaint);
        return finalReflection;
    }
}
