package com.android.biubiu.activity;



import com.android.biubiu.R;
import com.android.biubiu.bean.UserInfoBean;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
	private ImageView userheadImv;
	//倒计时相关
	private Handler handler = new Handler();
	private int totalTime = 60;
	private int currentTime = 0;

	UserInfoBean userBean = new UserInfoBean();
	Bitmap userheadBitmp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerthree_layout);
		getIntentInfo();
		initView();
	}
	private void getIntentInfo() {
		// TODO Auto-generated method stub
		UserInfoBean bean = (UserInfoBean) getIntent().getSerializableExtra("infoBean");
		Bitmap bitmp = getIntent().getParcelableExtra("userhead");
		userBean = bean;
		userheadBitmp = bitmp;
	}
	private void initView() {
		// TODO Auto-generated method stub
		backImv = (ImageView) findViewById(R.id.title_left_imv);
		backImv.setOnClickListener(this);
		registerPhoneEt = (EditText) findViewById(R.id.register_phone_et);
		verifyCodeEt = (EditText) findViewById(R.id.register_verify_et);
		passwordEt = (EditText) findViewById(R.id.register_psd_et);
		sendVerifyTv = (TextView) findViewById(R.id.register_get_verify_tv);
		sendVerifyTv.setOnClickListener(this);
		compLayout = (RelativeLayout) findViewById(R.id.register_compl_layout);
		compLayout.setOnClickListener(this);
		userheadImv = (ImageView) findViewById(R.id.userhead_imv);
		userheadImv.setImageBitmap(userheadBitmp);
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
			if(currentTime>0){
				return ;
			}
			currentTime = totalTime;
			handler.post(r);
			break;
		case R.id.register_compl_layout:
			register();
			break;
		default:
			break;
		}
	}
	//倒计时线程
	Runnable r=new Runnable() {

		@Override
		public void run() {
			sendVerifyTv.setText("重新发送("+(currentTime--)+")");
			if(currentTime<=0){
				sendVerifyTv.setText(getResources().getString(R.string.register_three_send_verify));
				currentTime = 0;
				handler.removeCallbacks(r);
				return;
			}
			handler.postDelayed(r, 1000);
		}
	};
	private void register() {
		// TODO Auto-generated method stub
		if(null == registerPhoneEt.getText() || registerPhoneEt.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_phone));
			return;
		}
		if(null == verifyCodeEt.getText() || verifyCodeEt.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_verify));
			return;
		}
		if(null == passwordEt.getText() || passwordEt.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_password));
			return;
		}
		Intent intent = new Intent(RegisterThreeActivity.this,MainActivity.class);
		startActivity(intent);
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
