package com.android.biubiu.view;

import java.util.ArrayList;

import com.android.biubiu.R;
import com.google.android.gms.internal.cv;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HomeBgView extends RelativeLayout{
	ImageView moovImv;
	ImageView sunImv;
	int width = 0;
	int moonX=0;
	int moonY=0;
	private ValueAnimator valueAnimator;

	public HomeBgView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initHomeView(context);
	}

	public HomeBgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initHomeView(context);
	}

	public HomeBgView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initHomeView(context);
	}
	public void setWidth(int width){
		this.width = width;
	}
	public void initHomeView(Context context){
		View view = LayoutInflater.from(context).inflate(R.layout.home_anmin_bg, null);
		addView(view);
		moovImv = (ImageView) view.findViewById(R.id.moon_imv);
		sunImv = (ImageView) view.findViewById(R.id.sun_imv);
		setAnimin();
	}
	public void setAnimin(){
		valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(), new PointF(600,1000),new PointF(600,500));
		valueAnimator.setDuration(2000);		
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				PointF pointF = (PointF)animation.getAnimatedValue();
				moovImv.setX(pointF.x);
				moovImv.setY(pointF.y);
			}
		});
		valueAnimator.setTarget(moovImv);
		valueAnimator.setRepeatCount(Integer.MAX_VALUE);
	}


	class BezierEvaluator implements TypeEvaluator<PointF>{

		@Override
		public PointF evaluate(float fraction, PointF startValue,
				PointF endValue) {
			final float t = fraction;
			float oneMinusT = 1.0f - t;
			PointF point = new PointF();

			PointF point0 = (PointF)startValue;

			PointF point1 = new PointF();
			point1.set(720,750);

			PointF point2 = new PointF();
			point2.set(300, 100);

			PointF point3 = new PointF();
			point3.set(0, 300);

			PointF point4 = (PointF)endValue;

			point.x = (float) (Math.pow(oneMinusT, 4)* (point0.x) 
					+ 4 * Math.pow(oneMinusT, 3) * t * (point1.x)
					+ 4 * Math.pow(oneMinusT, 2)* t * t * (point2.x)
					+ 4 * oneMinusT * (Math.pow(t, 3) * (point3.x)
					+ Math.pow(t, 4)* (point3.y)));	

			point.y = (float) (Math.pow(oneMinusT, 4)* (point0.y) 
					+ 4 * Math.pow(oneMinusT, 3) * t * (point1.y)
					+ 4 * Math.pow(oneMinusT, 2)* t * t * (point2.y)
					+ 4 * oneMinusT * (Math.pow(t, 3) * (point3.y)
					+ Math.pow(t, 4)* (point3.y)));			
					return point;
		}	
	}
	public void startAnim(){
		valueAnimator.start();
	}
}
