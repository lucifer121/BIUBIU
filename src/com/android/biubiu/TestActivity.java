package com.android.biubiu;

import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.view.HomeBgView;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TestActivity extends Activity{
	HomeBgView homeView;
	//屏幕宽高
	int width = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_test);
		width = getWindowManager().getDefaultDisplay().getWidth();
		LogUtil.d("mytest", "w--"+width);
		homeView = (HomeBgView) findViewById(R.id.home_view);
		homeView.setWidth(width);
		homeView.startAnim();
	}
}