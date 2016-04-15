package com.android.biubiu.utils;

import android.content.Context;

public class LoginUtils {
	
	public static boolean isLogin(Context context){
		String token = SharePreferanceUtils.getInstance().getToken(context, SharePreferanceUtils.TOKEN, "");
		String userCode=SharePreferanceUtils.getInstance().getUserCode(context, SharePreferanceUtils.USER_CODE, "");
		if(null == token || token.equals("")||null == userCode || userCode.equals("")){
			return false;
		}else{
			return true;
		}
	}

}
