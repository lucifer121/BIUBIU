package com.android.biubiu.activity;

import com.biubiu.biubiu.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class RegisterTwoActivity extends BaseActivity{
	Button nextBtn;
	ImageView backImv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registertwo_layout);
		initView();
		initClick();
	}
	private void initView() {
		// TODO Auto-generated method stub
		nextBtn = (Button) findViewById(R.id.register_next_btn);
		backImv = (ImageView) findViewById(R.id.title_left_imv);
	}
	private void initClick() {
		// TODO Auto-generated method stub
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterTwoActivity.this,RegisterThreeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in_anim,R.anim.no_anim);
			}
		});
		backImv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,R.anim.right_out_anim);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(0,R.anim.right_out_anim);
		}
		return super.onKeyDown(keyCode, event);
	}
}
