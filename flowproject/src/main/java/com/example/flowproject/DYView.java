package com.example.flowproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

/**
 * 自定义倒影图片
 * Created by jl on 2017-07-05.
 */
public class DYView extends View {

    private Bitmap mBitmap;
    private  Bitmap mSRCBit;
    private Bitmap mDSTBit;
    private Paint mPaint;
    private Bitmap cBitmap;
    public DYView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xyjy2);
        Matrix matrix = new Matrix();
        matrix.setScale(1,-1);
        mSRCBit = Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getHeight(),matrix,true);
        mDSTBit = BitmapFactory.decodeResource(getResources(), R.drawable.invert_shade);
        mDSTBit = Bitmap.createBitmap(mDSTBit,0,0,mBitmap.getWidth(),mBitmap.getHeight());
        cBitmap = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        Canvas canvas1 = new Canvas(cBitmap);
        RectF rectF = new RectF(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        LinearGradient linearGradient = new LinearGradient(0,0,0,mBitmap.getHeight(), 0xFF000000,0x00000000, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas1.drawRect(rectF,mPaint);

        canvas.drawBitmap(mBitmap,0,0,mPaint);
        int layerId = canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.translate(0,mBitmap.getHeight()+5);
        float ske = (float) (Math.sin(60)/Math.cos(60));
        canvas.skew(ske,0);
        canvas.drawBitmap(cBitmap,0,0,mPaint);
//        canvas.drawBitmap(mDSTBit,0,mBitmap.getHeight()+5,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mSRCBit,0,0,mPaint);
        mPaint.setXfermode(null);

    }
}
