package com.android.biubiu.activity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.R;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.Utils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.LogUtil.log;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

	UserInfoBean userBean;
	Bitmap userheadBitmp = null;
	String deviceId = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerthree_layout);
		deviceId = Utils.getDeviceID(RegisterThreeActivity.this);
		getIntentInfo();
		initView();
	}
	private void getIntentInfo() {
		// TODO Auto-generated method stub
		userBean = (UserInfoBean) getIntent().getSerializableExtra("infoBean");
		Bitmap bitmp = getIntent().getParcelableExtra("userhead");
		userheadBitmp = bitmp;
	}
	private void initView() {
		// TODO Auto-generated method stub
		backImv = (ImageView) findViewById(R.id.title_left_imv);
		backImv.setOnClickListener(this);
		registerPhoneEt = (EditText) findViewById(R.id.register_phone_et);
		registerPhoneEt.addTextChangedListener(watcher);
		verifyCodeEt = (EditText) findViewById(R.id.register_verify_et);
		verifyCodeEt.addTextChangedListener(watcher);
		passwordEt = (EditText) findViewById(R.id.register_psd_et);
		passwordEt.addTextChangedListener(watcher);
		sendVerifyTv = (TextView) findViewById(R.id.register_get_verify_tv);
		sendVerifyTv.setOnClickListener(this);
		compLayout = (RelativeLayout) findViewById(R.id.register_compl_layout);
		compLayout.setOnClickListener(this);
		userheadImv = (ImageView) findViewById(R.id.userhead_imv);
		userheadImv.setImageBitmap(userheadBitmp);
	}
	/**
	 * Editview 输入框监听事件
	 */

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			changeNextBg();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};
	/**
	 * 改变下一步的背景
	 */
	private void changeNextBg(){
		if(registerPhoneEt.getText().length()>0&&verifyCodeEt.getText().length()>0&&verifyCodeEt.getText().length()>0){
			compLayout.setBackgroundResource(R.drawable.register_btn_normal);		
		}else{
			compLayout.setBackgroundResource(R.drawable.register_btn_clk);	
		}

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
			sendSms();
			break;
		case R.id.register_compl_layout:
			registerReady();
			break;
		default:
			break;
		}
	}
	//发送验证码
	private void sendSms() {
		if(currentTime>0){
			return ;
		}
		if(null == registerPhoneEt.getText()||registerPhoneEt.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_phone));
			return;
		}
		currentTime = totalTime;
		handler.post(r);
		AVOSCloud.requestSMSCodeInBackground(registerPhoneEt.getText().toString(), "biubiu", "注册", 1,
				new RequestMobileCodeCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {

				} else {

				}
			}
		});
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
	private void registerReady() {
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
		AVOSCloud.verifySMSCodeInBackground(verifyCodeEt.getText().toString(), registerPhoneEt.getText().toString(),
				new AVMobilePhoneVerifyCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					upLoadPic();
				} else {
					toastShort(getResources().getString(R.string.reg_three_error_verify));
				}
			}
		});
	}
	//上传头像
	protected void upLoadPic() {
		// TODO Auto-generated method stub
		userBean.setUserHead("http://iconurl");
		registerRequest();
	}
	//注册
	private void registerRequest() {
		RequestParams params = new RequestParams("http://123.56.193.210:8080/meetu_maven/app/auth/register");
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("nickname",userBean.getNickname());
			requestObject.put("sex", userBean.getSexFlag());
			requestObject.put("birth_date", userBean.getBirthday());
			requestObject.put("isgraduated", userBean.getIsStudent());
			requestObject.put("school", userBean.getSchool());
			requestObject.put("career", userBean.getJob());
			requestObject.put("phone", registerPhoneEt.getText().toString());
			requestObject.put("device_code", deviceId);
			requestObject.put("password", passwordEt.getText().toString());
			requestObject.put("icon_url", userBean.getUserHead());
			requestObject.put("original_icon_url", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					JSONObject obj = jsons.getJSONObject("data");
					String username = obj.getString("username");
					String passwprd = obj.getString("password");
					String token = obj.getString("token");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
