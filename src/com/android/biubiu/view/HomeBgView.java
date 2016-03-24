package com.android.biubiu.view;

import com.android.biubiu.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HomeBgView extends RelativeLayout{
	ImageView moovImv;
	ImageView sunImv;
	AnimatorSet animSet;

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
	public void initHomeView(Context context){
		animSet =  new AnimatorSet(); 
		View view = LayoutInflater.from(context).inflate(R.layout.home_anmin_bg, null);
		addView(view);
		moovImv = (ImageView) view.findViewById(R.id.moon_imv);
		sunImv = (ImageView) view.findViewById(R.id.sun_imv);
		setAnimin();
	}
	public void setAnimin(){
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(moovImv, "x", (float)0.0,(float)100.0);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(moovImv, "y", (float)0.0,(float)150.0);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(moovImv, "x", (float)100.0,(float)200.0);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(moovImv, "y", (float)150.0,(float)300.0);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(moovImv, "x", (float)200.0,(float)300.0);
		ObjectAnimator anim6 = ObjectAnimator.ofFloat(moovImv, "y", (float)300.0,(float)450.0);
		ObjectAnimator anim7 = ObjectAnimator.ofFloat(moovImv, "x", (float)300.0,(float)0.0);
		ObjectAnimator anim8 = ObjectAnimator.ofFloat(moovImv, "y", (float)450.0,(float)0.0);
		animSet.play(anim1).with(anim2);  
		animSet.play(anim3).after(anim2);  
		animSet.play(anim3).with(anim4); 
		animSet.play(anim5).after(anim4);  
		animSet.play(anim5).with(anim6);
		animSet.play(anim7).after(anim6);  
		animSet.play(anim7).with(anim8);
		//animSet.play(anim1).after(anim8);  
		animSet.setDuration(1000*5);  
		animSet.start();  
		animSet.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				animSet.start();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
	}
	public void startAnim(){
		animSet.start();
	}
}
