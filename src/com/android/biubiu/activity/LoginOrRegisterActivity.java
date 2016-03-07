package com.android.biubiu.activity;

import com.biubiu.biubiu.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class LoginOrRegisterActivity extends BaseActivity{
	private Button loginBtn;
	private Button registerBtn;
	private LinearLayout backLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_or_register);
		initView();
		initClick();
	}
	private void initView() {
		// TODO Auto-generated method stub
		loginBtn = (Button) findViewById(R.id.login_btn);
		registerBtn = (Button) findViewById(R.id.register_btn);
		backLayout = (LinearLayout) findViewById(R.id.back_layout);
	}
	private void initClick() {
		// TODO Auto-generated method stub
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loginIntent = new Intent(LoginOrRegisterActivity.this,LoginActivity.class);
				startActivity(loginIntent);
				overridePendingTransition(R.anim.right_in_anim,R.anim.no_anim); 
			}
		});
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent RegisterIntent = new Intent(LoginOrRegisterActivity.this,RegisterOneActivity.class);
				startActivity(RegisterIntent);
				overridePendingTransition(R.anim.right_in_anim,R.anim.no_anim); 
			}
		});
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
