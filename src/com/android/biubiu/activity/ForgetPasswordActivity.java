package com.android.biubiu.activity;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;








import com.avos.avoscloud.LogUtil.log;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForgetPasswordActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout backLayout,completelayout;
	private EditText uPhone,uPassword,uYanzhengma;
	private String TAG="ForgetPasswordActivity";
	//
	//倒计时相关
	private Handler handler = new Handler();
	private int totalTime = 60;
	private int currentTime = 0;
	String deviceId = "";
	String phoneNum = "";
	private TextView sendVerifyTv;
	

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
		sendVerifyTv=(TextView) findViewById(R.id.forget_get_verify_tv);
		sendVerifyTv.setOnClickListener(this);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		completelayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updatePasswordReady();
				
			}
		});
		uPhone.addTextChangedListener(watcher);
		uYanzhengma.addTextChangedListener(watcher);
		uPassword.addTextChangedListener(watcher);
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
		if(uPhone.getText().length()>0&&uYanzhengma.getText().length()>0&&uPassword.getText().length()>0){
			completelayout.setBackgroundResource(R.drawable.register_btn_normal);		
		}else{

			completelayout.setBackgroundResource(R.drawable.register_btn_disabled);	

		}

	}
	//检测手机号是否已注册
		private void queryIsHad() {
			// TODO Auto-generated method stub
			if(null == uPhone.getText()||uPhone.getText().toString().equals("")){
				toastShort(getResources().getString(R.string.reg_three_no_phone));
				return;
			}
			RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.IS_REGISTERED);
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("phone", uPhone.getText().toString());
				phoneNum = uPhone.getText().toString();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.addBodyParameter("data",jsonObject.toString());
			x.http().post(params, new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError(Throwable ex, boolean arg1) {
					// TODO Auto-generated method stub
					Log.d("mytest", "error--"+ex.getMessage());
					Log.d("mytest", "error--"+ex.getCause());
				}

				@Override
				public void onFinished() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(String arg0) {
					// TODO Auto-generated method stub
					log.d("mytest", arg0);
					try {
						JSONObject  jsonObject = new JSONObject(arg0);
						JSONObject obj = new JSONObject(jsonObject.getJSONObject("data").toString());
						String result = obj.getString("result");
						if(result.equals("0")){
							toastShort("该手机号未注册");
						}else{
							sendSms();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	//发送验证码
	private void sendSms() {
		if(currentTime>0){
			return ;
		}
		if(null == uPhone.getText()||uPhone.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_phone));
			return;
		}
		currentTime = totalTime;
		handler.post(r);
		
		AVOSCloud.requestSMSCodeInBackground(uPhone.getText().toString(), "biubiu", "重置密码", 1,
				new RequestMobileCodeCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					LogUtil.e(TAG, "发送验证码成功");
				} else {
					LogUtil.e(TAG, "发送验证码失败");
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
	
	private void updatePasswordReady() {
		// TODO Auto-generated method stub
		if(null == uPhone.getText() || uPhone.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_phone));
			return;
		}
		if(null == uYanzhengma.getText() || uYanzhengma.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_verify));
			return;
		}
		if(null == uPassword.getText() || uPassword.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_password));
			return;
		}
		AVOSCloud.verifySMSCodeInBackground(uYanzhengma.getText().toString(), uPhone.getText().toString(),
				new AVMobilePhoneVerifyCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					UpdatePassword(uPassword.getText().toString());
				} else {
					toastShort(getResources().getString(R.string.reg_three_error_verify));
				}
			}
		});
	}
	/**
	 * 重置密码
	 */
	public void UpdatePassword(String passwprd){
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_PASSWORD);
		deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		JSONObject requestObject=new JSONObject();
		try {
			requestObject.put("password", passwprd);
			requestObject.put("phone", phoneNum);
			
		} catch (Exception e) {
			// TODO: handle exception
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
				LogUtil.d("mytest", "forgete"+arg0.getMessage());
				LogUtil.d("mytest", "forgete"+arg0.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				String ss = arg0;
				LogUtil.d("mytest", "forget"+arg0);
				try {
					JSONObject jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					if(!code.equals("200")){
						if(code.equals("300")){
							String error=jsons.getString("error");
							toastShort(error);
						}
						return;
					}
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		//点击获取验证码
		case R.id.forget_get_verify_tv:
			queryIsHad();
			break;

		default:
			break;
		}
		
	}

	

}
