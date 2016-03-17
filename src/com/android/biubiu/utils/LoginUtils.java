package com.android.biubiu.utils;

import android.content.Context;

public class LoginUtils {
	
	public static boolean isLogin(Context context){
		String token = SharePreferanceUtils.getInstance().getToken(context, SharePreferanceUtils.TOKEN, "");
		if(null == token || token.equals("")){
			return false;
		}else{
			return true;
		}
	}

}
