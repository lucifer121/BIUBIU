package com.android.biubiu.biu.activity;

import android.os.Bundle;
import android.view.Window;

import com.android.biubiu.activity.BaseActivity;
import com.biubiu.biubiu.R;

public class BiuSetActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biu_set_layout);
	}

}
