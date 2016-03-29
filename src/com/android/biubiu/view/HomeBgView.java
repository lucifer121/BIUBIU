package com.android.biubiu.view;


import cc.imeetu.iu.R;

import com.android.biubiu.utils.LogUtil;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HomeBgView extends RelativeLayout{
	ImageView moovImv;
	ImageView sunImv;
	ImageView dot1;
	ImageView dot2;
	ImageView dot3;
	ImageView dot4;
	ImageView dot5;
	ImageView dot6;
	ImageView star1;
	ImageView star2;
	ImageView star3;
	ImageView star4;
	ImageView star5;
	ImageView cloud1;
	ImageView cloud2;
	ImageView cloud3;
	ImageView cloud4;
	ImageView cloud5;
	int width = 0;
	AnimatorSet animMoonSet;
	AnimatorSet animSunSet;
	AnimatorSet animStarSet;
	AnimatorSet animDotSet;
	AnimatorSet animCloud1Set;
	AnimatorSet animCloud2Set;
	AnimatorSet animCloud3Set;
	AnimatorSet animCloud4Set;
	AnimatorSet animCloud5Set;
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
	public void setWidth(int w){
		this.width = w;
		setMoonAnimin();
		setSunAnimin();
		setDotAnimin();
		setStarAnimin();
		setCloudAnimin();
	}
	public void initHomeView(Context context){
		animMoonSet = new AnimatorSet();
		animDotSet = new AnimatorSet();
		animStarSet = new AnimatorSet();
		animSunSet = new AnimatorSet();
		animCloud1Set = new AnimatorSet();
		animCloud2Set = new AnimatorSet();
		animCloud3Set = new AnimatorSet();
		animCloud4Set = new AnimatorSet();
		animCloud5Set = new AnimatorSet();
		View view = LayoutInflater.from(context).inflate(R.layout.home_anmin_bg, null);
		addView(view);
		moovImv = (ImageView) view.findViewById(R.id.moon_imv);
		sunImv = (ImageView) view.findViewById(R.id.sun_imv);
		dot1 = (ImageView) view.findViewById(R.id.dot1);
		dot2 = (ImageView) view.findViewById(R.id.dot2);
		dot3 = (ImageView) view.findViewById(R.id.dot3);
		dot4 = (ImageView) view.findViewById(R.id.dot4);
		dot5 = (ImageView) view.findViewById(R.id.dot5);
		dot6 = (ImageView) view.findViewById(R.id.dot6);
		star1 = (ImageView) view.findViewById(R.id.star1);
		star2 = (ImageView) view.findViewById(R.id.star2);
		star3 = (ImageView) view.findViewById(R.id.star3);
		star4 = (ImageView) view.findViewById(R.id.star4);
		star5 = (ImageView) view.findViewById(R.id.star5);
		cloud1 = (ImageView) view.findViewById(R.id.cloud1);
		cloud2 = (ImageView) view.findViewById(R.id.cloud2);
		cloud3 = (ImageView) view.findViewById(R.id.cloud3);
		cloud4 = (ImageView) view.findViewById(R.id.cloud4);
		cloud5 = (ImageView) view.findViewById(R.id.cloud5);
	}
	public void setMoonAnimin(){
		ObjectAnimator anim7 = ObjectAnimator.ofFloat(moovImv, "x", width*298/360,width*120/360);
		ObjectAnimator anim8 = ObjectAnimator.ofFloat(moovImv, "y",width*18/360,width*23/360);
		ObjectAnimator anim9 = ObjectAnimator.ofFloat(moovImv, "x", width*120/360,width*33/360);
		ObjectAnimator anim10 = ObjectAnimator.ofFloat(moovImv, "y", width*23/360,width*224/360);
		ObjectAnimator anim11= ObjectAnimator.ofFloat(moovImv, "x", width*33/360,width*10/360);
		ObjectAnimator anim12 = ObjectAnimator.ofFloat(moovImv, "y", width*224/360,width*554/360);
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(moovImv, "x",width*10/360, width*192/360);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(moovImv, "y", width*510/360,width*350/360);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(moovImv, "x", width*192/360,width*298/360);
		ObjectAnimator anim6 = ObjectAnimator.ofFloat(moovImv, "y", width*350/360,width*18/360);
		anim1.setDuration(1000*20);
		anim2.setDuration(1000*20);
		anim5.setDuration(1000*35);
		anim6.setDuration(1000*35);
		anim7.setDuration(1000*10);
		anim8.setDuration(1000*10);
		anim9.setDuration(1000*25);
		anim10.setDuration(1000*25);
		anim11.setDuration(1000*30);
		anim12.setDuration(1000*30);
		animMoonSet.play(anim7).with(anim8);
		animMoonSet.play(anim9).after(anim7);
		animMoonSet.play(anim9).with(anim10);
		animMoonSet.play(anim11).after(anim9);
		animMoonSet.play(anim11).with(anim12);
		animMoonSet.play(anim1).after(anim11);
		animMoonSet.play(anim1).with(anim2);
		animMoonSet.play(anim5).after(anim1);
		animMoonSet.play(anim5).with(anim6);
		animMoonSet.addListener(new AnimatorListener() {

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
				animMoonSet.start();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void setSunAnimin() {
		// TODO Auto-generated method stub
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(sunImv, "x",width*144/360, width*320/360);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(sunImv, "y", width*500/360,width*484/360);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(sunImv, "x", width*320/360,width*280/360);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(sunImv, "y", width*484/360,width*266/360);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(sunImv, "x", width*280/360,width*344/360);
		ObjectAnimator anim6 = ObjectAnimator.ofFloat(sunImv, "y",width*266/360,width*126/360);
		ObjectAnimator anim7 = ObjectAnimator.ofFloat(sunImv, "x", width*344/360,width*285/360);
		ObjectAnimator anim8 = ObjectAnimator.ofFloat(sunImv, "y", width*126/360,width*41/360);
		ObjectAnimator anim9 = ObjectAnimator.ofFloat(sunImv, "x", width*285/360,width*77/360);
		ObjectAnimator anim10 = ObjectAnimator.ofFloat(sunImv, "y", width*41/360,width*8/360);
		ObjectAnimator anim11 = ObjectAnimator.ofFloat(sunImv, "x", width*77/360,width*29/360);
		ObjectAnimator anim12 = ObjectAnimator.ofFloat(sunImv, "y", width*8/360,width*240/360);
		ObjectAnimator anim13 = ObjectAnimator.ofFloat(sunImv, "x", width*29/360,width*144/360);
		ObjectAnimator anim14 = ObjectAnimator.ofFloat(sunImv, "y", width*240/360,width*500/360);
		anim1.setDuration(1000*10);
		anim2.setDuration(1000*10);
		anim3.setDuration(1000*15);
		anim4.setDuration(1000*15);
		anim5.setDuration(1000*15);
		anim6.setDuration(1000*15);
		anim7.setDuration(1000*15);
		anim8.setDuration(1000*15);
		anim9.setDuration(1000*20);
		anim10.setDuration(1000*20);
		anim11.setDuration(1000*15);
		anim12.setDuration(1000*15);
		anim13.setDuration(1000*30);
		anim14.setDuration(1000*30);
		animSunSet.play(anim1).with(anim2);
		animSunSet.play(anim3).after(anim1);
		animSunSet.play(anim3).with(anim4);
		animSunSet.play(anim5).after(anim3);
		animSunSet.play(anim5).with(anim6);
		animSunSet.play(anim7).after(anim5);
		animSunSet.play(anim7).with(anim8);
		animSunSet.play(anim9).after(anim7);
		animSunSet.play(anim9).with(anim10);
		animSunSet.play(anim11).after(anim9);
		animSunSet.play(anim11).with(anim12);
		animSunSet.play(anim13).after(anim11);
		animSunSet.play(anim13).with(anim14);
		animSunSet.addListener(new AnimatorListener() {

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
				animSunSet.start();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setDotAnimin() {
		// TODO Auto-generated method stub
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(dot1, "alpha", 0.1f,0.8f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(dot2, "alpha", 0.1f,0.8f);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(dot3, "alpha", 0.0f,1.0f);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(dot4, "alpha", 0.0f,1.0f);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(dot5, "alpha", 0.0f,1.0f);
		ObjectAnimator anim6 = ObjectAnimator.ofFloat(dot6, "alpha", 0.1f,0.8f);
		anim1.setDuration(8000);
		anim2.setDuration(6000);
		anim3.setDuration(10000);
		anim4.setDuration(4000);
		anim5.setDuration(6000);
		anim6.setDuration(12000);
		anim1.setRepeatCount(Integer.MAX_VALUE);
		anim2.setRepeatCount(Integer.MAX_VALUE);
		anim3.setRepeatCount(Integer.MAX_VALUE);
		anim4.setRepeatCount(Integer.MAX_VALUE);
		anim5.setRepeatCount(Integer.MAX_VALUE);
		anim6.setRepeatCount(Integer.MAX_VALUE);
		animDotSet.playTogether(anim1,anim2,anim3,anim4,anim5,anim6);
		/*animDotSet.addListener(new AnimatorListener() {
			
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
				animDotSet.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}

	private void setStarAnimin() {
		// TODO Auto-generated method stub
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(star1, "alpha", 0.1f,0.8f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(star2, "alpha", 0.0f,1.0f);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(star3, "alpha", 0.1f,0.8f);
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(star4, "alpha", 0.1f,0.8f);
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(star5, "alpha", 0.0f,1.0f);
		anim1.setDuration(9000);
		anim2.setDuration(7000);
		anim3.setDuration(11000);
		anim4.setDuration(5000);
		anim5.setDuration(7000);
		animStarSet.playTogether(anim1,anim2,anim3,anim4,anim5);
		anim1.setRepeatCount(Integer.MAX_VALUE);
		anim2.setRepeatCount(Integer.MAX_VALUE);
		anim3.setRepeatCount(Integer.MAX_VALUE);
		anim4.setRepeatCount(Integer.MAX_VALUE);
		anim5.setRepeatCount(Integer.MAX_VALUE);
		/*animStarSet.addListener(new AnimatorListener() {
			
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
				animStarSet.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}

	private void setCloudAnimin() {
		// TODO Auto-generated method stub
		cloudAnimin1();
		cloudAnimin2();
		cloudAnimin3();
		cloudAnimin4();
		cloudAnimin5();
	}
	private void cloudAnimin1(){
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(cloud1, "x",width*366/360, -width*20/360);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(cloud1, "x", -width*20/360,width*366/360);
		animCloud1Set.setDuration(40*1000);
		animCloud1Set.playSequentially(anim1,anim2);
		animCloud1Set.addListener(new AnimatorListener() {
			
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
				animCloud1Set.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void cloudAnimin2(){
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(cloud2, "x",width*350/360, width*276/360);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(cloud2, "x", width*276/360,width*350/360);
		animCloud2Set.setDuration(56*1000);
		animCloud2Set.playSequentially(anim1,anim2);
		animCloud2Set.addListener(new AnimatorListener() {
			
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
				animCloud2Set.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void cloudAnimin3(){
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(cloud3, "x",-width*42/360, width*102/360);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(cloud3, "x",  width*102/360,-width*42/360);
		animCloud3Set.setDuration(74*1000);
		animCloud3Set.playSequentially(anim1,anim2);
		animCloud3Set.addListener(new AnimatorListener() {
			
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
				animCloud3Set.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void cloudAnimin4(){
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(cloud4, "x",width*100/360, width*322/360);
		anim1.setDuration(14*1000);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(cloud4, "x", width*322/360,0);
		anim2.setDuration(30*1000);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(cloud4, "x", 0,width*100/360);
		anim3.setDuration(14*1000);
		animCloud4Set.playSequentially(anim1,anim2,anim3);
		animCloud4Set.addListener(new AnimatorListener() {
			
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
				animCloud4Set.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void cloudAnimin5(){
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(cloud5, "x",0, width*336/360);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(cloud5, "x", width*336/360,0);
		animCloud5Set.setDuration(48*1000);
		animCloud5Set.playSequentially(anim1,anim2);
		animCloud5Set.addListener(new AnimatorListener() {
			
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
				animCloud5Set.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void startAnim(){
		animMoonSet.start();
		animSunSet.start();
		animDotSet.start();
		animStarSet.start();
		animCloud1Set.start();
		animCloud2Set.start();
		animCloud3Set.start();
		animCloud4Set.start();
		animCloud5Set.start();
	}
}
