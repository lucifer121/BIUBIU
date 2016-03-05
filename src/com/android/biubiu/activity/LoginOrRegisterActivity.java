package com.android.biubiu.activity;

import com.biubiu.biubiu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginOrRegisterActivity extends BaseActivity implements OnClickListener{
	Button loginBtn;
	Button registerBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_or_register);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);
		registerBtn = (Button) findViewById(R.id.register_btn);
		registerBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			Intent loginIntent = new Intent(LoginOrRegisterActivity.this,LoginActivity.class);
			startActivity(loginIntent);
			overridePendingTransition(R.anim.up_in_anim,R.anim.no_anim); 
			break;
		case R.id.register_btn:
			Intent RegisterIntent = new Intent(LoginOrRegisterActivity.this,RegisterActivity.class);
			startActivity(RegisterIntent);
			overridePendingTransition(R.anim.up_in_anim,R.anim.no_anim); 
			break;
		default:
			break;
		}
	}
}
