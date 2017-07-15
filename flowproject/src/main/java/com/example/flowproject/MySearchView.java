package com.example.flowproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by John on 2017/5/18.
 */
public class MySearchView extends View {

	private Paint mPaint;
	private BaseController mController;

	public MySearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStrokeWidth(5);
		
	}
	
	public void setController(BaseController controller){
		this.mController = controller;
		mController.setSearchView(this);
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mController.draw(canvas, mPaint);
	}
	
	public void startAnimation(){
		if(mController!=null){
			mController.startAnim();
		}
	}

	public void resetAnimation(){
		if(mController!=null){
			mController.resetAnim();
		}
	}
}
