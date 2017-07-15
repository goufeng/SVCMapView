package com.example.flowproject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jl on 2017-06-27.
 */
public class FlowLayout extends ViewGroup {
    private ArrayList<ViewModel> models = new ArrayList<>();

    /**
     * 用来保存每行views的列表
     */
    private List<List<View>> mViewLinesList = new ArrayList<>();
    /**
     * 用来保存行高的列表
     */
    private List<Integer> mLineHeights = new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public LayoutParams getLayoutParams() {
        return super.getLayoutParams();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHight = 0;
        int rowLWidth = 0;
        int rowLHeight = 0;
        int line=1;
        models.clear();
        int childCount = getChildCount();
        List<View> viewList = new ArrayList<>();
        if(specWidthMode==MeasureSpec.EXACTLY&&specHeightMode==MeasureSpec.EXACTLY){
            measuredWidth = specWidth;
            measuredHight = specHeight;
        }else{
            for(int i=0;i<childCount;i++){
                ViewModel model = new ViewModel();
                model.curLine = line;
                View childView = getChildAt(i);
                measureChild(childView,widthMeasureSpec,heightMeasureSpec);
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                int childWidth = params.leftMargin+childView.getMeasuredWidth()+params.rightMargin;
                int childHeight = params.topMargin+childView.getMeasuredHeight()+params.bottomMargin;
                model.width=childWidth;
                model.height=childHeight;
                model.view = childView;
                if(rowLWidth + childWidth>specWidth){//换行
                    line++;
                    model.curLine = line;
                    measuredWidth = Math.max(rowLWidth,measuredWidth);
                    measuredHight+=rowLHeight;

                    mViewLinesList.add(viewList);
                    mLineHeights.add(rowLHeight);
                    model.rowHeight = rowLHeight;
                    rowLWidth = childWidth;
                    rowLHeight = childHeight;

                    // 2、新建一行的viewlist，添加新一行的view
                    viewList = new ArrayList<View>();
                    viewList.add(childView);

                    Log.e("onMeasure","onMeasure line "+line+" line width " + rowLWidth);
                }else{
                    rowLWidth+=childWidth;
                    rowLHeight = Math.max(rowLHeight,childHeight);
                    // 2、添加至当前行的viewList中
                    viewList.add(childView);
                }

                models.add(model);

                /*****3、如果正好是最后一行需要换行**********/
                if(i == childCount - 1){
                    //1、记录当前行的最大宽度，高度累加
                    measuredWidth = Math.max(measuredWidth,rowLWidth);
                    measuredHight += rowLHeight;

                    //2、将当前行的viewList添加至总的mViewsList，将行高添加至总的行高List
                    mViewLinesList.add(viewList);
                    mLineHeights.add(rowLHeight);
                }
            }

        }
        setMeasuredDimension(measuredWidth,measuredHight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layout2();
    }

    private void layout2(){
        int left=0,top=0,right=0,bottom=0;
        int curTop = 0;
        int curLeft = 0;

        int curLine = 1;
        for(int i=0;i<models.size();i++){
            ViewModel model = models.get(i);
            View childView = model.view;
            int line = model.curLine;
            if(curLine != line){
                curLeft = 0;
                curTop += model.rowHeight;
            }
            curLine = line;
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

            int vWidth = childView.getMeasuredWidth();
            int vHeight = childView.getMeasuredHeight();

            left = curLeft+params.leftMargin;
            top = curTop + params.topMargin;
            right = left+vWidth;
            bottom = top+vHeight;
            childView.layout(left,top,right,bottom);
            curLeft= curLeft+vWidth+params.leftMargin+params.rightMargin;
        }
    }
    private void layout(){
        int left=0,top=0,right=0,bottom=0;
        int curTop = 0;
        int curLeft = 0;
        int lineCount = mViewLinesList.size();
        for(int i=0;i<lineCount;i++){
            List<View> viewList = mViewLinesList.get(i);
            for(int j=0;j<viewList.size();j++){
                View childView= viewList.get(j);
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                int viewWidth = childView.getMeasuredWidth()+params.leftMargin+params.rightMargin;
                int viewHeight = childView.getMeasuredHeight()+params.topMargin+params.bottomMargin;

                left = curLeft + params.leftMargin;
                top = curTop + params.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();

                childView.layout(left,top,right,bottom);

                curLeft += viewWidth;

            }
            curLeft = 0;
            curTop += mLineHeights.get(i);
        }
        mViewLinesList.clear();
        mLineHeights.clear();
    }
    public class ViewModel{
        public int width;
        public int height;
        public int curLine=1;
        public View view;
        public int rowHeight;
    }
}
