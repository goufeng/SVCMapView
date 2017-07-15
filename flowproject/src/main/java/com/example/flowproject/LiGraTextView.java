package com.example.flowproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jl on 2017-06-29.
 */
public class LiGraTextView extends TextView {
    private TextPaint mPaint;

    private LinearGradient mLinearGradient ;
    private Matrix mMatrix;

    private float mTranslate;
    private float DELTAX = 20;
    float viewWidth;

    public LiGraTextView(Context context) {
        super(context);
    }

    public LiGraTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = getPaint();
        String text = getText().toString();
        float size = mPaint.measureText(text);
        float perTextSize = size/text.length();
        int gradientSize = (int) (perTextSize*3);
//        mLinearGradient = new LinearGradient(-gradientSize,0,size,0);
        mLinearGradient = new LinearGradient(-gradientSize,0,0,0,new int[]{
                0x22ffffff, 0xffffffff, 0x22ffffff},null, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMatrix = new Matrix();
        mTranslate += DELTAX;
        float width = getPaint().measureText(getText().toString());
        if(viewWidth<width){
            if(mTranslate > viewWidth+1||mTranslate<1){
                DELTAX = -DELTAX;
            }
        }else{
            if(mTranslate > width+1||mTranslate<1){
                DELTAX = -DELTAX;
            }
        }

        float height = width/getText().toString().length();
        mMatrix.setTranslate(mTranslate,height);
        mLinearGradient.setLocalMatrix(mMatrix);
        postInvalidateDelayed(50);
    }
}
