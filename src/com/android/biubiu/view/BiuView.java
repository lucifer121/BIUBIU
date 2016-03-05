package com.android.biubiu.view;

import com.biubiu.biubiu.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BiuView extends View{
	private Paint mPaintCircle;
	//半径
	float circleR1 = 0;
    float circleR2 = 0;
    float circleR3 = 0;
    //圆心坐标
    float dotX = 0;
    float dotY = 0;
	public BiuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}
	public BiuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	public BiuView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void setCircleR(float r1,float r2,float r3){
		this.circleR1 = r1;
		this.circleR2 = r2;
		this.circleR3 = r3;
	}
	public void setDot(float x,float y){
		this.dotX = x;
		this.dotY = y;
	}
	private void init() {
		// TODO Auto-generated method stub
		mPaintCircle = new Paint();
        mPaintCircle.setColor(getResources().getColor(R.color.white));//设置颜色
        mPaintCircle.setStrokeWidth(2);//设置线宽
        mPaintCircle.setAntiAlias(true);//设置是否抗锯齿
        mPaintCircle.setStyle(Paint.Style.STROKE);//设置绘制风格
        
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
        //画出圆
        canvas.drawCircle(dotX, dotY, circleR1, mPaintCircle);
        canvas.drawCircle(dotX, dotY, circleR2, mPaintCircle);
        canvas.drawCircle(dotX, dotY, circleR3, mPaintCircle);
	}

}
