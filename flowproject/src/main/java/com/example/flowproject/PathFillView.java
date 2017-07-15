package com.example.flowproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by John on 2017/5/22.
 */

public class PathFillView extends View{

    private Path mPath;
    private Paint mPaint;

    public PathFillView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // WINDING 模式 --- 取Path所有所在的区域 -- 默认的模式
//        drawWindingType(canvas);

        // EVEN_ODD 模式 --- 取Path所在不相交的区域
//        drawEvenOddType(canvas);

        // INVERSE_WINDING 模式 -- 取path所有未占的区域
        drawInverseWinding(canvas);

        // INVERSE_EVEN_ODD 模式 --- 取path所有未占和相交的区域
//        drawInverseEvenOdd(canvas);

    }

    private void drawInverseEvenOdd(Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPath = new Path();
        mPath.offset(100,100);
        mPath.addCircle(200, 200, 100, Path.Direction.CW);
        mPath.addCircle(300, 300, 100, Path.Direction.CW);
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawPath(mPath,mPaint);
    }

    private void drawInverseWinding(Canvas canvas) {
        mPath = new Path();
        mPath.offset(100,100);
        mPath.addCircle(200, 200, 100, Path.Direction.CW);
        mPath.addCircle(300, 300, 100, Path.Direction.CW);
        mPath.setFillType(Path.FillType.INVERSE_WINDING);
        mPaint.setColor(Color.RED);
        canvas.drawPath(mPath,mPaint);
    }

    private void drawEvenOddType(Canvas canvas) {
        mPath = new Path();
        mPath.offset(100,100);
        mPath.addCircle(200, 200, 100, Path.Direction.CW);
        mPath.addCircle(300, 300, 100, Path.Direction.CW);
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPaint.setColor(Color.RED);
        canvas.drawPath(mPath,mPaint);
    }

    private void drawWindingType(Canvas canvas) {
        mPath = new Path();
        mPath.offset(100,100);
        mPath.addCircle(200, 200, 100, Path.Direction.CW);
        mPath.addCircle(300, 300, 100, Path.Direction.CW);
        mPath.setFillType(Path.FillType.WINDING);
        mPaint.setColor(Color.RED);
        canvas.drawPath(mPath,mPaint);
    }
}
