package com.android.biubiu.activity;



import com.android.biubiu.BaseActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.R;

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
	private static final int LOGIN_REQUEST = 1001;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_or_register);
		if(getIntent().getStringExtra("startActivity")!= null && getIntent().getStringExtra("startActivity").equals("login")){
			Intent intent = new Intent(LoginOrRegisterActivity.this,LoginActivity.class);
			startActivity(intent);
		}
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
				startActivityForResult(loginIntent, LOGIN_REQUEST);
			//	overridePendingTransition(R.anim.right_in_anim,R.anim.no_anim); 
			}
		});
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent RegisterIntent = new Intent(LoginOrRegisterActivity.this,RegisterOneActivity.class);
				startActivity(RegisterIntent);
			//	overridePendingTransition(R.anim.right_in_anim,R.anim.no_anim); 
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case LOGIN_REQUEST:
			if(resultCode == RESULT_OK){
				Intent intent=new Intent(LoginOrRegisterActivity.this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			break;

		default:
			break;
		}
	}
}
