package com.android.biubiu.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class SharePreferanceUtils {
	public static String IS_FIRST_INSTALL = "is_first_install";

	public static SharePreferanceUtils shareUtils ;
	public static SharePreferanceUtils getInstance(){
		if(shareUtils == null){
			shareUtils = new SharePreferanceUtils();
		}
		return shareUtils;
	}
	//获取是否为第一次安装
	public boolean isFirstInstall(Context context,String prefKey,boolean defValue){
		return getShared(context, prefKey,defValue);
	}
	public  String getShared(Context ctx, String prefKey, String defValue) {
		if(ctx != null){
			return PreferenceManager.getDefaultSharedPreferences(ctx).getString(prefKey, defValue);
		}else{
			return "";
		}
	}

	public  boolean getShared(Context ctx, String prefKey, boolean defValue) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(prefKey, defValue);
	}
	public  void putShared(Context ctx, String prefKey, boolean defValue) {
		 PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean(prefKey, defValue).commit();
	}

	public  float getShared(Context ctx, String prefKey, float defValue) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getFloat(prefKey, defValue);
	}

	public  long getShared(Context ctx, String prefKey, long defValue) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(prefKey, defValue);
	}

	public  int getShared(Context ctx, String prefKey, int defValue) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(prefKey, defValue);
	}
}
