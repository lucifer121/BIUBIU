package com.android.biubiu.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class SharePreferanceUtils {
	public static String IS_FIRST_INSTALL = "is_first_install";
	public static String AGE_MAX = "age_max";
	public static String AGE_MIN = "age_min";
	public static String TOKEN="token";
	public static String DEVICE_ID="device_id";
	public static String USER_NAME="username";
	public static String USER_HEAD="userhead";
	public static String USER_CODE="usercode";

	public static SharePreferanceUtils shareUtils ;
	public static SharePreferanceUtils getInstance(){
		if(shareUtils == null){
			shareUtils = new SharePreferanceUtils();
		}
		return shareUtils;
	}
	/**
	 * 获取token
	 * @param context
	 * @param prefKey "token"
	 * @param defValue
	 * @return
	 */
	public String getToken(Context context,String prefKey,String defValue){
		return getShared(context, prefKey,defValue);
	}
	//获取设备编码
	public String getDeviceId(Context context,String prefKey,String defValue){
		if(getShared(context, prefKey,defValue).equals("")){
			putShared(context, DEVICE_ID, Utils.getDeviceID(context));
		}
		return getShared(context, prefKey,defValue);
	}
	//获取是否为第一次安装
	public boolean isFirstInstall(Context context,String prefKey,boolean defValue){
		return getShared(context, prefKey,defValue);
	}
	//获取设置保存最小年龄
	public int getMinAge(Context context,String prefKey,int defValue){
		return getShared(context, prefKey, defValue);
	}
	//获取设置保存最大年龄
	public int getMaxAge(Context context,String prefKey,int defValue){
		return getShared(context, prefKey, defValue);
	}
	//获取用户名
	public String getUserName(Context context,String prefKey,String defValue){
		return getShared(context, prefKey,defValue);
	}
	//获取用户头像
	public String getUserHead(Context context,String prefKey,String defValue){
		return getShared(context, prefKey,defValue);
	}
	//获取用户编码
	public String getUserCode(Context context,String prefKey,String defValue){
		return getShared(context, prefKey,defValue);
	}


	public  String getShared(Context ctx, String prefKey, String defValue) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getString(prefKey, defValue);
	}
	public  void putShared(Context ctx, String prefKey, String defValue) {
		PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(prefKey, defValue).commit();
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
	public  void putShared(Context ctx, String prefKey, float defValue) {
		PreferenceManager.getDefaultSharedPreferences(ctx).edit().putFloat(prefKey, defValue).commit();
	}

	public  long getShared(Context ctx, String prefKey, long defValue) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(prefKey, defValue);
	}
	public  void putShared(Context ctx, String prefKey, long defValue) {
		PreferenceManager.getDefaultSharedPreferences(ctx).edit().putLong(prefKey, defValue).commit();
	}
	public  int getShared(Context ctx, String prefKey, int defValue) {
		return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(prefKey, defValue);
	}
	public  void putShared(Context ctx, String prefKey, int defValue) {
		PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt(prefKey, defValue).commit();
	}
}
