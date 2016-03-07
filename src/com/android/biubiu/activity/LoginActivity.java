package com.android.biubiu.activity;



import com.android.biubiu.R;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BaseActivity{
	private EditText phoneEt;
	private EditText passwordEt;
	private TextView forgetPsdTv;
	private TextView protocolTv;
	private Button loginBtn;
	private ImageView backImv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
		initClick();
	}
	private void initView() {
		// TODO Auto-generated method stub
		phoneEt = (EditText) findViewById(R.id.phone_tv);
		passwordEt = (EditText) findViewById(R.id.password_tv);
		forgetPsdTv = (TextView) findViewById(R.id.forget_psd_tv);
		protocolTv = (TextView) findViewById(R.id.protocol_tv);
		protocolTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		loginBtn = (Button) findViewById(R.id.login_btn);
		backImv = (ImageView) findViewById(R.id.title_left_imv);
	}
	private void initClick() {
		// TODO Auto-generated method stub
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		backImv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,     
						R.anim.right_out_anim);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(0,     
					R.anim.right_out_anim);
		}
		return super.onKeyDown(keyCode, event);
	}
}
