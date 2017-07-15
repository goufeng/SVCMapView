package com.example.flowproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by John on 2017/5/18.
 */

public class Controller1 extends BaseController {
    private static final String TAG = "Controller1";

    private String mColor = "#4CAF50";

    private int mCircleX;// 圆心

    private int mCircleY;

    private int mRadius;// 圆半径

    /**
     * 圆Rect区域
     */
    private RectF mRectF;

    private int mDeltaD = 15;

    public Controller1() {
        mRectF = new RectF();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawColor(Color.parseColor(mColor));
        switch (mState) {
            case STATE_ANIM_NONE:
                drawNormalView(paint, canvas);
                break;
            case STATE_ANIM_START:
                drawStartAnimView(paint, canvas);
                break;
            case STATE_ANIM_STOP:
                drawNormalView(paint, canvas);
                break;
        }
    }

    private void drawStopAnimView(Paint paint, Canvas canvas) {
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        //0~1
        if(mpro<=0.5f){
//			canvas.drawArc(
            //			r,
            //			startAngle, //起始角度，相对X轴正方向
            //			sweepAngle, //画多少角度的弧度
            //			useCenter, //boolean, false：只有一个纯弧线；true：闭合的边
            //			paint)；
//			canvas.drawArc(r, 0, 90, true, paint);//顺时针旋转90度
            /**
             * -360 ~ 0 需要变换的范围
             * 	 0  ~ 0.5 mpro实际的变化范围
             * 转换公式：360*(mpro*2-1),
             */
            //绘制圆和把手
            canvas.drawArc(
                    mRectF,
                    45,
                    360*(mpro*2-1),
                    false,
                    paint);
            canvas.drawLine(
                    mRectF.right - mDeltaD,
                    mRectF.bottom - mDeltaD,
                    mRectF.right+ mRadius -mDeltaD,
                    mRectF.bottom+ mRadius -mDeltaD,
                    paint);

            /*canvas.save();
            float circleX = mRectF.left + mRadius;
            float circleY = mRectF.top + mRadius;
            canvas.rotate(45, circleX, circleY);
            canvas.drawLine(circleX + mRadius, circleY, circleX + mRadius *2 + mDeltaD, circleY, paint);
            canvas.drawArc(
                    mRectF,
                    0,
                    360*(mpro*2-1),
                    false,
                    paint);
            canvas.restore();*/
        }else {
            /**
             *   0    ~ 1 需要变换的范围
             * 	 0.5  ~ 1 mpro实际的变化范围
             * 转换公式：(mpro*2-1),
             */
            //绘制把手
            canvas.drawLine(
                    mRectF.right - mDeltaD + mRadius *(mpro*2-1),
                    mRectF.bottom - mDeltaD + mRadius *(mpro*2-1),
                    mRectF.right - mDeltaD + mRadius,
                    mRectF.bottom+ mRadius - mDeltaD,
                    paint);
        }
        //绘制下面的横线
        canvas.drawLine(
                (mRectF.right - mDeltaD + mRadius)*(1-mpro*0.8f),
                mRectF.bottom+ mRadius - mDeltaD,
                mRectF.right - mDeltaD + mRadius,
                mRectF.bottom+ mRadius - mDeltaD,
                paint);
        canvas.restore();

        mRectF.left = mCircleX - mRadius + mpro*250;
        mRectF.right = mCircleX + mRadius +mpro*250;
        mRectF.top = mCircleY - mRadius;
        mRectF.bottom = mCircleY + mRadius;

    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        mRadius = getWidth()/20;
        Log.d(TAG, "mRadius = " + mRadius);
        mCircleX = getWidth()/2;
        mCircleY = getHeight()/2;

        mRectF.left = mCircleX - mRadius;
        mRectF.right= mCircleX + mRadius;
        mRectF.top = mCircleY - mRadius;
        mRectF.bottom = mCircleY + mRadius;
//		canvas.drawArc(
//		r,
//		startAngle, //起始角度，相对X轴正方向
//		sweepAngle, //画多少角度的弧度
//		useCenter, //boolean, false：只有一个纯弧线；true：闭合的边
//		paint)；
//		canvas.drawArc(r, 0, 90, true, paint);//顺时针旋转90度

        canvas.save();
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        canvas.rotate(45, mCircleX, mCircleY);
        canvas.drawLine(mCircleX + mRadius, mCircleY, mCircleX + mRadius *2, mCircleY, paint);
        canvas.drawArc(
                mRectF,
                0,
                360,
                false,
                paint);
        canvas.restore();
    }

    @Override
    public void startAnim() {
        super.startAnim();
        mState = STATE_ANIM_START;
        startViewAnimation();
    }

    @Override
    public void resetAnim() {
        super.resetAnim();
        mState = STATE_ANIM_STOP;
        startViewAnimation();
    }
}
