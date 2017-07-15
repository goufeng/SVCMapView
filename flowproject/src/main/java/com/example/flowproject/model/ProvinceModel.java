package com.example.flowproject.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Created by jl on 2017-07-13.
 */
public class ProvinceModel {
    private int color;
    private Path path;

    public int getColor() {
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setPath(Path path){
        this.path = path;
    }

    public void draw(Canvas canvas, Paint paint,boolean isSelected){
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        if(isSelected){
            paint.setStrokeWidth(10);
            paint.setColor(Color.GRAY);
        }else{
            paint.setStrokeWidth(8);
            paint.setColor(Color.WHITE);
        }

        paint.setShadowLayer(10,0,0,Color.WHITE);
        canvas.drawPath(path,paint);

        paint.clearShadowLayer();
        if(isSelected){
            paint.setColor(Color.parseColor("#CCFF0000"));
        }else{
            paint.setColor(color);
        }
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(8);
        canvas.drawPath(path,paint);
    }

    public boolean isSelected(int x,int y){
        Region region = new Region();
        RectF rectF = new RectF();
        path.computeBounds(rectF,true);
        region.setPath(path,new Region((int)rectF.left,(int)rectF.top,(int)rectF.right,(int)rectF.bottom));
        return region.contains(x,y);
    }

}
