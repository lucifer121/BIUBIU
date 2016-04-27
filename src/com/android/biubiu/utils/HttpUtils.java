package com.android.biubiu.utils;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.util.Log;

import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.UserInfoBean;

public class HttpUtils {
	
	public static void commitChannelId(final Context context) {
		// TODO Auto-generated method stub
		if(!LoginUtils.isLogin(context)){
			SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.IS_COMMIT_CHANNEL, false);
			return;
		}
		String channelId = SharePreferanceUtils.getInstance().getChannelId(context, SharePreferanceUtils.CHANNEL_ID, "");
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_CHANNEL);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("device_code",SharePreferanceUtils.getInstance().getDeviceId(context, SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("token",SharePreferanceUtils.getInstance().getToken(context, SharePreferanceUtils.TOKEN, ""));
			requestObject.put("channelId", channelId);
			requestObject.put("deviceType", "3");
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "channel--"+result);
				JSONObject jsons;
				try {
					jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.IS_COMMIT_CHANNEL, false);
						return;
					}
					JSONObject data = jsons.getJSONObject("data");
//					String token = data.getString("token");
//					SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.TOKEN, token);
					SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.IS_COMMIT_CHANNEL, true);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	public static void commitIconState(final Context context) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_HEAD_STATE);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("device_code",SharePreferanceUtils.getInstance().getDeviceId(context, SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("token",SharePreferanceUtils.getInstance().getToken(context, SharePreferanceUtils.TOKEN, ""));
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "headState--"+result);
				JSONObject jsons;
				try {
					jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						return;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}