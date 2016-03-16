package com.android.biubiu.activity;

import org.xutils.x;
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








import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
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
//				if(uPhone.getText().length()>0&&uYanzhengma.getText().length()>0&&uPassword.getText().length()>0){
//					toastShort("提交数据");
//				}
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
		/*AVOSCloud.verifySMSCodeInBackground(uYanzhengma.getText().toString(), uPhone.getText().toString(),
				new AVMobilePhoneVerifyCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					UpdatePassword(uPassword.getText().toString(),deviceId);
				} else {
					toastShort(getResources().getString(R.string.reg_three_error_verify));
				}
			}
		});*/
		UpdatePassword(uPassword.getText().toString());
	}
	/**
	 * 重置密码
	 */
	public void UpdatePassword(String passwprd){
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_PASSWORD);
		deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		JSONObject requestObject=new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
			requestObject.put("password", passwprd);
			requestObject.put("device_code", deviceId);
			
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
				LogUtil.d("mytest", "ss---"+ss);
				LogUtil.d("mytest", "forget"+arg0);
				/*try {
					JSONObject jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					if(!code.equals("200")){
						if(code.equals("300")){
							String error=jsons.getString("error");
							toastShort(error);
						}
						return;
					}
					JSONObject obj = jsons.getJSONObject("data");
					String token = obj.getString("token");
					SharePreferanceUtils.getInstance().putShared(ForgetPasswordActivity.this, SharePreferanceUtils.TOKEN, token);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		//点击获取验证码
		case R.id.forget_get_verify_tv:
			sendSms();
			break;

		default:
			break;
		}
		
	}

	

}
