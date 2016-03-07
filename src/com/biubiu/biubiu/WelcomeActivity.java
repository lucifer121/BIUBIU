package com.biubiu.biubiu;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.biubiu.R;
import com.android.biubiu.activity.GuildActivity;
import com.android.biubiu.activity.MainActivity;
import com.android.biubiu.utils.SharePreferanceUtils;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.test.suitebuilder.TestMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	private boolean isFirstInstall = false;
	//导入城市数据库到本地
	Handler handler ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		handler = new Handler();
		next();
	}

	private void next() {
		isFirstInstall = SharePreferanceUtils.getInstance().isFirstInstall(getApplicationContext(), SharePreferanceUtils.IS_FIRST_INSTALL, true);
		if(isFirstInstall){
			goIndex();
		}else{
			goHome();
		}
	}

	/**
	 * 首次进入app 进行的操作
	 */
	private void goIndex() {
		Intent intent = new Intent(WelcomeActivity.this,GuildActivity.class);
		startActivity(intent);
		finish();
	}

	private void goHome() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
					// 允许用户使用应用
					Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
			}
		}, 2000);
	}

}
