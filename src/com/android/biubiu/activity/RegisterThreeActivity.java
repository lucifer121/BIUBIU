package com.android.biubiu.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

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
import com.android.biubiu.R;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
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
	private ImageView userheadImv;
	//倒计时相关
	private Handler handler = new Handler();
	private int totalTime = 60;
	private int currentTime = 0;

	UserInfoBean userBean;
	Bitmap userheadBitmp = null;
	String deviceId = "";
	String headPath;
	//上传文件相关
	String accessKeyId = "";
	String accessKeySecret = "";
	String securityToken = "";
	String expiration = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerthree_layout);
		deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		getIntentInfo();
		initView();
	}
	private void getIntentInfo() {
		// TODO Auto-generated method stub
		userBean = (UserInfoBean) getIntent().getSerializableExtra("infoBean");
		//System.out.println(userBean.getNickname());
		Bitmap bitmp = getIntent().getParcelableExtra("userhead");
		headPath = getIntent().getStringExtra("headPath");
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
						//sendSms();
					}else{
						toastShort("该手机号已注册,请直接登录");
						//启动登录页，因堆栈问题，启动登录注册页
						Intent intent = new Intent(RegisterThreeActivity.this,LoginOrRegisterActivity.class);
						intent.putExtra("startActivity", "login");
						startActivity(intent);
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
		//验证  验证码
		AVOSCloud.verifySMSCodeInBackground(verifyCodeEt.getText().toString(), registerPhoneEt.getText().toString(),
				new AVMobilePhoneVerifyCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					getOssToken();
				} else {
					toastShort(getResources().getString(R.string.reg_three_error_verify));
				}
			}
		});
	}
	//鉴权
	public void getOssToken(){
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REGISTER_OSS);
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "error--"+ex.getMessage());
				LogUtil.d("mytest", "error--"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "ret=="+arg0);
				try {
					JSONObject jsonObjs = new JSONObject(arg0);
					JSONObject obj = jsonObjs.getJSONObject("data");
					//JSONObject obj = new JSONObject(jsonObjs.getString("data"));
					accessKeyId = obj.getString("accessKeyId");
					accessKeySecret = obj.getString("accessKeySecret");
					securityToken = obj.getString("securityToken");
					expiration = obj.getString("expiration");
					asyncPutObjectFromLocalFile();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	// 从本地文件上传，使用非阻塞的异步接口
	public void asyncPutObjectFromLocalFile() {
		String endpoint = HttpContants.A_LI_YUN;
		//OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("XWp6VLND94vZ8WNJ", "DSi9RRCv4bCmJQZOOlnEqCefW4l1eP");
		OSSCredentialProvider credetialProvider = new OSSFederationCredentialProvider() {
			@Override
			public OSSFederationToken getFederationToken() {

				return new OSSFederationToken(accessKeyId, accessKeySecret, securityToken, expiration);
			}
		};
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		OSSLog.enableLog();
		OSS oss = new OSSClient(getApplicationContext(), endpoint, credetialProvider, conf);
		final String fileName = "profile/"+System.currentTimeMillis()+deviceId+".png";
		// 构造上传请求
		PutObjectRequest put = new PutObjectRequest("protect-app",fileName, headPath);

		// 异步上传时可以设置进度回调
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
			}
		});
		OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				Log.d("PutObject", "UploadSuccess");
				Log.d("ETag", result.getETag());
				Log.d("RequestId", result.getRequestId());
				userBean.setIconOrign(fileName);
				registerRequest();
			}
			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				// 请求异常
				if (clientExcepion != null) {
					// 本地异常如网络异常等
					clientExcepion.printStackTrace();
				}
				if (serviceException != null) {
					// 服务异常
					Log.e("ErrorCode", serviceException.getErrorCode());
					Log.e("RequestId", serviceException.getRequestId());
					Log.e("HostId", serviceException.getHostId());
					Log.e("RawMessage", serviceException.getRawMessage());
				}
			}
		});
	}
	//注册
	private void registerRequest() {
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REGISTER_METHOD);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("nickname",userBean.getNickname());
			requestObject.put("sex", userBean.getSex());
			requestObject.put("birth_date", userBean.getBirthday());
			requestObject.put("isgraduated", userBean.getIsStudent());
			requestObject.put("school", userBean.getSchool());
			requestObject.put("city", userBean.getCity());
			requestObject.put("career", userBean.getCareer());
			requestObject.put("phone", registerPhoneEt.getText().toString());
			requestObject.put("device_name", "");
			requestObject.put("device_code", deviceId);
			requestObject.put("password", passwordEt.getText().toString());
			requestObject.put("icon_url", userBean.getIconOrign());
			requestObject.put("original_icon_url", userBean.getIconOrign());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogUtil.d("mytest", userBean.getCity()+",");
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				Log.d("mytest", "error--pp"+ex.getMessage());
				Log.d("mytest", "error--pp"+ex.getCause());
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
					LogUtil.e(TAG, code);
					if(code.equals("200")==false){
						if(code.equals("300")==true){
							String error=jsons.getString("error");
							LogUtil.e(TAG, error);
						}
						toastShort("注册失败");
						return;
					}
					JSONObject obj = jsons.getJSONObject("data");
					String username = obj.getString("username");
					String passwprd = obj.getString("password");
					String token = obj.getString("token");
					String nickname = obj.getString("nickname");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_NAME, nickname);
					String userHead = obj.getString("icon_url");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_HEAD, userHead);
					String userCode = obj.getString("code");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_CODE, code);

					LogUtil.e(TAG, "username=="+username+"||||passwprd=="+passwprd);

					loginHuanXin(username,passwprd,token);

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

	/**
	 * 登录环信客户端 建立长连接
	 * @param username
	 * @param password
	 */
	public void loginHuanXin(String username,String password,final String token){
		EMClient.getInstance().login(username, password, new EMCallBack() {

			@Override
			public void onSuccess() {

				//	Toast.makeText(TAG, "注册成功", Toast.LENGTH_SHORT).show();
				LogUtil.e(TAG, "登录成功环信");
				//把token 存在本地
				SharePreferanceUtils.getInstance().putShared(RegisterThreeActivity.this, SharePreferanceUtils.TOKEN, token);
				Intent intent=new Intent(RegisterThreeActivity.this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

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
