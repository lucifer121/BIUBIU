package com.android.biubiu.activity.biu;

import android.os.Bundle;
import android.view.Window;

import com.android.biubiu.R;
import com.android.biubiu.activity.BaseActivity;

public class MyPagerActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pager_layout);
	}

}
