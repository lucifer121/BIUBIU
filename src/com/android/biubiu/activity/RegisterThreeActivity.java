package com.android.biubiu.activity;

import com.biubiu.biubiu.R;

import android.os.Bundle;
import android.view.Window;

public class RegisterThreeActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerthree_layout);
	}

}
