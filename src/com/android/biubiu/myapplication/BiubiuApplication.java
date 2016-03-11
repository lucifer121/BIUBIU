package com.android.biubiu.myapplication;

import org.xutils.BuildConfig;
import org.xutils.x;

import com.avos.avoscloud.AVOSCloud;
import com.hyphenate.easeui.controller.EaseUI;

import android.app.Application;

public class BiubiuApplication extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		x.Ext.init(this);
		x.Ext.setDebug(BuildConfig.DEBUG);
		//   EaseUI.getInstance().init(context, options);
		AVOSCloud.initialize(this,
				"tcd4rj3s3c54bdlkv1vfu5puvu9c2k96ur9kge3qvptqxp8p",
				"8fpp7j815746jg9x26f0d3c5p76xqkyqm586v2onvx3m2k7a");
	}
}
