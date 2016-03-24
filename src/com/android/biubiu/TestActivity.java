package com.android.biubiu;

import com.android.biubiu.view.HomeBgView;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TestActivity extends Activity{
	HomeBgView homeView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_test);
		homeView = (HomeBgView) findViewById(R.id.home_view);
		homeView.startAnim();
	}
}