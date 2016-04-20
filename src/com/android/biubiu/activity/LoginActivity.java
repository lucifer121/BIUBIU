package com.android.biubiu.activity;



import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.UserAgreementActivity;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.avos.avoscloud.LogUtil.log;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.mob.tools.utils.SharePrefrenceHelper;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity{
	private String TAG="LoginActivity";
	private EditText phoneEt;
	private EditText passwordEt;
	private TextView forgetPsdTv;
	private TextView protocolTv;
	private Button loginBtn;
	private ImageView backImv;
	private RelativeLayout backLayout;
	/**
	 * 设备编码
	 */
	String deviceId = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, Utils.getDeviceID(getApplicationContext()));
		deviceId = SharePreferanceUtils.getInstance().getDeviceId(this, SharePreferanceUtils.DEVICE_ID, "");
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
		backLayout=(RelativeLayout) findViewById(R.id.title_left_rl);
		phoneEt.addTextChangedListener(watcher);
		passwordEt.addTextChangedListener(watcher);
		protocolTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,UserAgreementActivity.class);
				startActivity(intent);
			}
		});
	}
	/**
	 * 点击事件
	 */
	private void initClick() {
		// TODO Auto-generated method stub
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!NetUtils.isNetworkConnected(getApplicationContext())){
					toastShort(getResources().getString(R.string.net_error));
					return;
				}
				showLoadingLayout(getResources().getString(R.string.logining));
				login(phoneEt.getText().toString(),passwordEt.getText().toString());
				
			}
		});
		backLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(0,     
						R.anim.right_out_anim);
			}
		});
		forgetPsdTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
		//	changeNextBg();
			if(phoneEt.getText().length()>0&&passwordEt.getText().length()>0){
				loginBtn.setBackgroundResource(R.drawable.register_btn_normal);
				
			}else{

				loginBtn.setBackgroundResource(R.drawable.register_btn_disabled);	

			}
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
	
	
	//测试登录的方法
	protected void login(String uName,String uPassword) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.LOGIN);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("phone", uName);
			requestObject.put("password", uPassword);
			requestObject.put("device_code", deviceId);
			//requestObject.put("device_code", "xxxxxx");
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
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
				Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
				dismissLoadingLayout();
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				Log.d("mytest", "result--"+arg0);
				JSONObject jsons;
				try {
					jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
						toastShort(""+jsons.getString("error"));	
						return;
					}
					JSONObject obj = jsons.getJSONObject("data");
					String token = obj.getString("token");
					String hxName=obj.getString("username");
					String HxPassword=obj.getString("password");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.HX_USER_NAME, hxName);
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.HX_USER_PASSWORD, HxPassword);
					if(token!=null&&!token.equals("")){
						log.d("mytest", "toke--"+token);
						SharePreferanceUtils.getInstance().putShared(LoginActivity.this, SharePreferanceUtils.TOKEN, token);
					}
					
					String nickname = obj.getString("nickname");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_NAME, nickname);
					String userHead = obj.getString("icon_url");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_HEAD, userHead);
					String userCode = obj.getString("code");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_CODE, userCode);
					
					loginHuanXin(hxName,HxPassword);
					LogUtil.e(TAG, "hxName=="+hxName+"||"+"HxPassword=="+HxPassword);
					//统计登录用户
					MobclickAgent.onProfileSignIn(userCode);
					
					setResult(RESULT_OK);
					finish();
					/*Intent intent=new Intent(LoginActivity.this,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();*/
	
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
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 登录环信客户端 建立长连接
	 * @param username
	 * @param password
	 */
	public void loginHuanXin(String username,String password){
		EMClient.getInstance().login(username, password, new EMCallBack() {
			
			@Override
			public void onSuccess() {
				LogUtil.e(TAG, "登录成功环信");
				
			}
			
			@Override
			public void onProgress(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e(TAG, "登陆聊天服务器失败！");
			}
		});
		
	}
	
}
