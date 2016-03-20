package com.android.biubiu;

import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class BaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_APP_OPEN, true);
		//启动百度云推送
		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"v3FkYC4w53w46uuvw9L6qBF1");
	}
	/**
	 * 显示Toast长通知
	 * 
	 * @param msg
	 */
	public void toastLong(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示Toast短通知
	 * 
	 * @param msg
	 */
	public void toastShort(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
