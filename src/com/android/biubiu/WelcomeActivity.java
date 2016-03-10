package com.android.biubiu;




import com.android.biubiu.activity.BaseActivity;
import com.android.biubiu.activity.GuildActivity;
import com.android.biubiu.activity.LoginOrRegisterActivity;
import com.android.biubiu.activity.MainActivity;
import com.android.biubiu.activity.RegisterOneActivity;
import com.android.biubiu.activity.biu.MyPagerActivity;
import com.android.biubiu.activity.mine.ChangeCityActivity;
import com.android.biubiu.activity.mine.ChangeConstellationActivity;



import com.android.biubiu.activity.mine.InterestLabelActivity;
import com.android.biubiu.sqlite.DBManager;
import com.android.biubiu.sqlite.DBManagerCity;
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

public class WelcomeActivity extends BaseActivity {
	private boolean isFirstInstall = false;
	//导入城市数据库到本地
	
	private  DBManagerCity dbHelperCity;
	//导入学校数据库到本地
	private DBManager dbHelperSchool;
		
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
		// 导入数据库
		dbHelperCity = new DBManagerCity(this);
		dbHelperCity.openDatabase();
		dbHelperCity.closeDatabase();
		
		dbHelperSchool=new DBManager(this);
		dbHelperSchool.openDatabase();
		dbHelperSchool.closeDatabase();
		
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



		Intent intent = new Intent(WelcomeActivity.this,MyPagerActivity.class);


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
