package com.android.biubiu.activity;



import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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
	private EditText phoneEt;
	private EditText passwordEt;
	private TextView forgetPsdTv;
	private TextView protocolTv;
	private Button loginBtn;
	private ImageView backImv;
	private RelativeLayout backLayout;
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
		backLayout=(RelativeLayout) findViewById(R.id.title_left_rl);
	}
	private void initClick() {
		// TODO Auto-generated method stub
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
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
	//测试登录的方法
	protected void login() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("http://123.56.193.210:8080/meetu_maven/app/auth/login");
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("phone", "12365478968");
			requestObject.put("password", "123456");
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
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				Log.d("mytest", "error--"+ex.getMessage());
				Log.d("mytest", "error--"+ex.getCause());
				Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				Log.d("mytest", "result--"+arg0);
				Toast.makeText(x.app(), arg0, Toast.LENGTH_LONG).show();
				JSONObject jsons;
				try {
					jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					JSONObject obj = jsons.getJSONObject("data");
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
			overridePendingTransition(0,     
					R.anim.right_out_anim);
		}
		return super.onKeyDown(keyCode, event);
	}
}
