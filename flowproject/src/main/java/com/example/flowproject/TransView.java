package com.example.flowproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by jl on 2017-07-11.
 */
public class TransView extends View {
    Bitmap mBitmap;
    Paint mPaint;
    Matrix mMatrix;
    public TransView(Context context) {
        super(context);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xyjy2);
        mPaint = new Paint();
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        mMatrix.setScale(0.5f,0.5f);
        mMatrix.preTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight()/2);
        mMatrix.postTranslate(mBitmap.getWidth()/2,mBitmap.getHeight()/2);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
    }

}
