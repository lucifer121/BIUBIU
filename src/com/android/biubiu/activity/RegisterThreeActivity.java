package com.android.biubiu.activity;



import com.android.biubiu.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterThreeActivity extends BaseActivity implements OnClickListener{
	ImageView backImv;
	private EditText registerPhoneEt;
	private EditText verifyCodeEt;
	private TextView sendVerifyTv;
	private EditText passwordEt;
	private RelativeLayout compLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerthree_layout);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		backImv = (ImageView) findViewById(R.id.title_left_imv);
		backImv.setOnClickListener(this);
		registerPhoneEt = (EditText) findViewById(R.id.register_phone_et);
		verifyCodeEt = (EditText) findViewById(R.id.verify_colplete_tv);
		passwordEt = (EditText) findViewById(R.id.register_psd_et);
		sendVerifyTv = (TextView) findViewById(R.id.register_get_verify_tv);
		sendVerifyTv.setOnClickListener(this);
		compLayout = (RelativeLayout) findViewById(R.id.register_compl_layout);
		compLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_left_imv:
			finish();
			overridePendingTransition(0,R.anim.right_out_anim);
			break;
		case R.id.register_get_verify_tv:
			
			break;
		case R.id.register_compl_layout:
			
			break;
		default:
			break;
		}
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
