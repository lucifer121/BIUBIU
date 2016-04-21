package com.android.biubiu.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;




import cc.imeetu.iu.R;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.biubiu.BaseActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.activity.biu.BiuBiuReceiveActivity;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.Umutils;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.LogUtil.log;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.umeng.analytics.MobclickAgent;

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
import android.widget.Toast;

public class RegisterThreeActivity extends BaseActivity implements OnClickListener{
	ImageView backImv;
	private String TAG="RegisterThreeActivity";
	private EditText registerPhoneEt;
	private EditText verifyCodeEt;
	private TextView sendVerifyTv;
	private EditText passwordEt;
	private RelativeLayout compLayout;
	//倒计时相关
	private Handler handler = new Handler();
	private int totalTime = 60;
	private int currentTime = 0;

	String headPath;
	String cityCode="";
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
		//System.out.println(userBean.getNickname());
		cityCode=getIntent().getStringExtra("cityf");
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

			compLayout.setBackgroundResource(R.drawable.register_btn_disabled);	

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
			if(!NetUtils.isNetworkConnected(getApplicationContext())){
				toastShort(getResources().getString(R.string.net_error));
				return;
			}
			queryIsHad();
			break;
		case R.id.register_compl_layout:
			registerReady();
			break;
		default:
			break;
		}
	}
	//检测手机号是否已注册
	private void queryIsHad() {
		// TODO Auto-generated method stub
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			toastShort(getResources().getString(R.string.net_error));
		}
		if(null == registerPhoneEt.getText()||registerPhoneEt.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_three_no_phone));
			return;
		}
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.IS_REGISTERED);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("phone", registerPhoneEt.getText().toString());
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
				toastShort("请求发送验证码失败");
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
					String state = jsonObject.getString("state");
					if(!state.equals("200")){
						if(jsonObject.optString("error")!= null && jsonObject.optString("error").contains("phone")){
							toastShort("请求发送验证码失败,请检查手机号");
						}else{
							toastShort("请求发送验证码失败");
						}
						return;
					}
					JSONObject obj = new JSONObject(jsonObject.getJSONObject("data").toString());
					String result = obj.getString("result");
					if(result.equals("0")){
						sendSms();
					}else{
						toastShort("该手机号已注册,请直接登录");
						//启动登录页，因堆栈问题，启动登录注册页
						setResult(RESULT_OK);
						finish();
						/*Intent intent=new Intent(RegisterThreeActivity.this,LoginActivity.class);
						startActivity(intent);
						finish();*/
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
		currentTime = totalTime;
		handler.post(r);
		Umutils.count(RegisterThreeActivity.this, Umutils.REGISTER_SMS_SEND);
		AVOSCloud.requestSMSCodeInBackground(registerPhoneEt.getText().toString(), "biubiu", "注册", 10,
				new RequestMobileCodeCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					Umutils.count(RegisterThreeActivity.this, Umutils.REGISTER_SMS_SEND_SUCCESS);
				} else {

				}
			}
		});
	}
	//倒计时线程
	Runnable r=new Runnable() {

		@Override
		public void run() {
			sendVerifyTv.setBackgroundResource(R.drawable.register_phone_btn_disabled);
			sendVerifyTv.setText("重新发送("+(currentTime--)+")");
			if(currentTime<=0){
				sendVerifyTv.setBackgroundResource(R.drawable.register_phone_btn_normal);
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
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		//验证  验证码
		AVOSCloud.verifySMSCodeInBackground(verifyCodeEt.getText().toString(), registerPhoneEt.getText().toString(),
				new AVMobilePhoneVerifyCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					Umutils.count(RegisterThreeActivity.this, Umutils.REGISTER_SMS_VERIFY);
					Intent intent = new Intent(RegisterThreeActivity.this,RegisterOneActivity.class);
					intent.putExtra("phone", registerPhoneEt.getText().toString());
					intent.putExtra("password", passwordEt.getText().toString());
					startActivity(intent);
				} else {
					toastShort(getResources().getString(R.string.reg_three_error_verify));
				}
			}
		});
		/*Intent intent = new Intent(RegisterThreeActivity.this,RegisterOneActivity.class);
		intent.putExtra("phone", registerPhoneEt.getText().toString());
		intent.putExtra("password", passwordEt.getText().toString());
		startActivity(intent);*/
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
