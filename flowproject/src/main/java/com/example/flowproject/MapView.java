package com.example.flowproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.flowproject.model.ProvinceModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by jl on 2017-07-12.
 */
public class MapView extends View {
    private Context mContext;
    private ArrayList<ProvinceModel> mModelList = new ArrayList<>();
    private int[] colors = {Color.parseColor("#8A2BE2"),Color.parseColor("#00BFFF")
            ,Color.parseColor("#87CEEB"),Color.parseColor("#87CEFA"),Color.parseColor("#87CEEB")};
    private Paint mPaint;
    private int mTouchedX = 0;
    private int mTouchedY = 0;
    private Matrix mMatrix;
    private float scale = 1;
    private int mViewHeight;
    private int mViewWidth;
    private RectF mRectFMap;
    float tranX = 0f;
    float tranY = 0f;
    boolean draw = false;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    draw = true;
                    postInvalidate();
                    break;
            }
        }
    };
    public MapView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        MsgCodeThread.start();
    }

    private void init(){
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mMatrix = new Matrix();
        mPaint.setAntiAlias(true);
    }
    Thread MsgCodeThread = new Thread(){
        @Override
        public void run() {

            float left = 0;
            float top = 0;
            float right = 0;
            float bottom = 0;
            draw = false;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                InputStream is = mContext.getResources().openRawResource(R.raw.vector_map);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(is);
                Element rootElement = document.getDocumentElement();
                NodeList nodeList = rootElement.getElementsByTagName("path");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    ProvinceModel model = new ProvinceModel();
                    // Node转成Element
                    Element element = (Element) nodeList.item(i);
                    String pathData = element.getAttribute("d");
                    Path path = PathParser.createPathFromPathData(pathData);
                    RectF rectF=new RectF();
                    path.computeBounds(rectF,true);
                    left = left==0?rectF.left:Math.min(rectF.left,left);
                    top = top==0?rectF.top:Math.min(rectF.top,top);
                    right = right==0?rectF.right:Math.max(rectF.right,right);
                    bottom = bottom==0?rectF.bottom:Math.max(rectF.bottom,bottom);
                    model.setPath(path);
                    model.setColor(colors[i%colors.length]);
                    mModelList.add(model);
                }
                float width = right -left;
                float height = bottom-top;
                scale = mViewWidth/width;
                float scale2 = mViewHeight/height;
                scale = Math.min(scale,scale2);
                mRectFMap = new RectF(left,top,right,bottom);
                mHandler.sendEmptyMessage(1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        if(!draw){
            return;
        }
        canvas.save();
        mPaint.reset();
        ArrayList<ProvinceModel> models = (ArrayList<ProvinceModel>) mModelList.clone();
        canvas.drawColor(Color.parseColor("#AA00CED1"));

        mMatrix.reset();
        mMatrix.setScale(scale,scale);
        if(mRectFMap != null){
            float centerX = mRectFMap.centerX();
            float centerY = mRectFMap.centerY();
            tranX = mViewWidth/2-centerX*scale;
            tranY = mViewHeight/2-centerY*scale;
            mMatrix.postTranslate(tranX,tranY);//后乘可以理解为先画图形再平移
//            mMatrix.preTranslate(mViewWidth/2-centerX*scale,mViewHeight/2-centerY*scale);//前乘可以理解为先平移再画图形
        }
        canvas.setMatrix(mMatrix);

        if(models==null||models.size()==0){
            return;
        }
        for(ProvinceModel model:models){
            if(model.isSelected(mTouchedX,mTouchedY)){
                model.draw(canvas,mPaint,true);
                continue;
            }
            model.draw(canvas,mPaint,false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            mTouchedX = (int) ((event.getX()-tranX)/scale);
            mTouchedY = (int) ((event.getY()-tranY)/scale);
            invalidate();
        }
        return true;
    }

}
