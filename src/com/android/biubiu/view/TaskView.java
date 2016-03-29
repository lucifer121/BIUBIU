package com.android.biubiu.view;

import cc.imeetu.iu.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TaskView extends View{
	// 画实心圆的画笔  
	private Paint mCirclePaint;  
	// 画圆环的画笔  
	private Paint mRingPaint;  
	// 画字体的画笔  
	private Paint mTextPaint;  
	// 半径  
	private float mRadius;  
	// 圆环半径  
	private float mRingRadius;  
	// 圆环宽度  
	private float mStrokeWidth;  
	// 圆心x坐标  
	private float mXCenter;  
	// 圆心y坐标  
	private float mYCenter;  
	// 字的长度  
	private float mTxtWidth;  
	// 字的高度  
	private float mTxtHeight;  
	// 总进度  
	private int mTotalProgress = 0;  
	// 当前进度  
	private int mProgress = 0;  
	public TaskView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public TaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public TaskView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	//设置内圆半径和边界宽度
	public void setRadius(float r1,float stroke){
		this.mRadius = r1;
		this.mStrokeWidth = stroke;
		mRingRadius = mRadius + mStrokeWidth / 2;
		initR();
	}
	//设置半径之后才能设置半径相关数据
	private void initR() {
		mRingPaint.setStrokeWidth(mStrokeWidth); 
		mTextPaint.setTextSize(mRadius / 2);  

		FontMetrics fm = mTextPaint.getFontMetrics();  
		mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent); 
	}

	//设置圆心
	public void setDot(float x,float y){
		this.mXCenter = x;
		this.mYCenter = y;
	}
	public void setTotal(int total){
		this.mTotalProgress = total;
	}

	private void init() {
		mCirclePaint = new Paint();  
		mCirclePaint.setAntiAlias(true);  
		mCirclePaint.setColor(getResources().getColor(R.color.circle));  
		mCirclePaint.setStyle(Paint.Style.FILL);  

		mRingPaint = new Paint();  
		mRingPaint.setAntiAlias(true);  
		mRingPaint.setColor(getResources().getColor(R.color.circle_strok));  
		mRingPaint.setStyle(Paint.Style.STROKE);  

		mTextPaint = new Paint();  
		mTextPaint.setAntiAlias(true);  
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setTextSize(18);
		mTextPaint.setColor(getResources().getColor(R.color.task_text));  
	}
	public void updeteTask(int current){
		mProgress = current;
		postInvalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (mProgress > 0 ) {
			canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
			RectF oval = new RectF();  
			oval.left = (mXCenter - mRingRadius);  
			oval.top = (mYCenter - mRingRadius);  
			oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);  
			oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
			canvas.drawArc(oval, -90, ((float)mProgress / mTotalProgress) * 360, false, mRingPaint); //  
			String txt = mProgress+"s";  
			mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());  
			canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);  
		}  
	}

}
