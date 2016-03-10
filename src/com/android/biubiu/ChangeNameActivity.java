package com.android.biubiu;

import com.android.biubiu.activity.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ChangeNameActivity extends BaseActivity {
	private RelativeLayout backLayout,completeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_name);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		completeLayout=(RelativeLayout) findViewById(R.id.mine_changename_wancheng_rl);
		
		backLayout=(RelativeLayout) findViewById(R.id.back_changename_mine_rl);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		completeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toastShort("wancheng");
			}
		});
	}

	

}
