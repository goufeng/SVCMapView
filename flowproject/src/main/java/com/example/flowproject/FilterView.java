package com.example.flowproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jl on 2017-07-05.
 */
public class FilterView extends View {
    private Paint paint;
    private Bitmap bitmap;
    float progress = 0;

    public FilterView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xyjy2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#E0FFFF"));

//        paint.setMaskFilter(new BlurMaskFilter(80, BlurMaskFilter.Blur.SOLID));
//        paint.setMaskFilter(new EmbossMaskFilter(new float[]{1,0,0},0.9f,90,80));
//        ColorMatrix colorMartrix = new ColorMatrix(new float[]{
//                0.213f, 0.715f,0.072f,0,0,
//                0.213f, 0.715f,0.072f,0,0,
//                0.213f, 0.715f,0.072f,0,0,
//                0,0,0,1,0,
//        });

        // 反相效果 -- 底片效果
//       ColorMatrix colorMartrix = new ColorMatrix(new float[]{
//                -1, 0,0,0,255,
//                0,-1,0,0,255,
//                0,0,-1,0,255,
//                0,0,0,1,0,
//        });

        // 发色效果---（比如红色和绿色交换）
        /*ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                0,1,0,0,0,
                1,0,0,0,0,
                0,0,1,0,0,
                0,0,0,1,0,
        });*/

        // 复古效果
       /* ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                1/2f,1/2f,1/2f,0,0,
                1/3f, 1/3f,1/3f,0,0,
                1/4f,1/4f,1/4f,0,0,
                0,0,0,1,0,
        });*/

        /*ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                1/2f,1/2f,1/2f,0,0,
                1/4f, 1/4f,1/4f,0,0,
                1/8f,1/8f,1/8f,0,0,
                0,0,0,1,0,
        });*/

        // 颜色通道过滤
        /*ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                1,0,0,0,0,
                0,0,0,0,0,
                0,0,0,0,0,
                0,0,0,1,0,
        });*/

        ColorMatrix colorMartrix = new ColorMatrix();
//        colorMartrix.setScale(1.2f,1f,1.2f,1);
        colorMartrix.setSaturation(progress);
//        colorMartrix.setRotate(2,progress);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMartrix));
        canvas.drawBitmap(bitmap,0,0,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //progress += 1f;
                //progress += 20f;
                progress = 1;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                progress = 0;
                invalidate();
                break;
        }
//        progress += 10;
        invalidate();
        return true;
    }
}
