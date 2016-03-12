package com.android.biubiu.myapplication;

import org.xutils.BuildConfig;
import org.xutils.x;

import com.avos.avoscloud.AVOSCloud;
import com.android.biubiu.chat.DemoHelper;
import com.android.biubiu.utils.LogUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

import android.R.string;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class BiubiuApplication extends Application{
	private String TAG="BiubiuApplication";

	public static Context applicationContext;
	private static BiubiuApplication instance;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		applicationContext=this;
		instance = this;

		x.Ext.init(this);

        x.Ext.setDebug(BuildConfig.DEBUG);
        EMOptions options = new EMOptions();
     //   options.setAutoLogin(false);
        EaseUI.getInstance().init(applicationContext, options);
        
        LogUtil.e(TAG, "APPICATION  start");
     //   DemoHelper.getInstance().init(applicationContext);
        EMClient.getInstance().setDebugMode(true);
        
		//   EaseUI.getInstance().init(context, options);
		AVOSCloud.initialize(this,
				"tcd4rj3s3c54bdlkv1vfu5puvu9c2k96ur9kge3qvptqxp8p",
				"8fpp7j815746jg9x26f0d3c5p76xqkyqm586v2onvx3m2k7a");
	


	}
	public static BiubiuApplication getInstance() {
		return instance;
	}
}
