package com.android.biubiu.activity;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ForgetPasswordActivity extends BaseActivity {
	private RelativeLayout backLayout,completelayout;
	private EditText uPhone,uPassword,uYanzhengma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		backLayout=(RelativeLayout) findViewById(R.id.back_forget_rl);
		completelayout=(RelativeLayout) findViewById(R.id.register_compl_forget_layout);
		uPhone=(EditText) findViewById(R.id.register_phone_forget_et);
		uYanzhengma=(EditText) findViewById(R.id.register_verify_forget_et);
		uPassword=(EditText) findViewById(R.id.register_psd_forget_et);
		
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		completelayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(uPhone.getText().length()>0&&uYanzhengma.getText().length()>0&&uPassword.getText().length()>0){
					toastShort("提交数据");
				}
				
			}
		});
	}

	

}
