package com.android.biubiu.chat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.bean.UserFriends;
import com.android.biubiu.sqlite.UserDao;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.util.Log;

public class LoadUserFriend {

	private static String TAG="LoadUserFriend";
	private static UserDao userDao;
	public static void getUserFriends(final Context context){
		
		userDao=new UserDao(context);
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GET_FRIDENS_LIST);
		JSONObject object=new JSONObject();
		try {
			object.put("token", SharePreferanceUtils.getInstance().
					getToken(context.getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
			object.put("device_code", SharePreferanceUtils.getInstance().
					getDeviceId(context.getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data", object.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
			//	toastShort(arg0.getMessage());
			//	Log.e(TAG, arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, arg0);
				JSONObject jsons;			
					try {
						jsons = new JSONObject(arg0);					
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
					//	toastShort(""+jsons.getString("error"));	
						return;
					}
					JSONObject obj = jsons.getJSONObject("data");
//					String token=obj.getString("token");
//					if(!token.equals("")&&token!=null){
//						SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.TOKEN, token);
//					}	
					Gson gson=new Gson();
					
					List<UserFriends> userFriendsList=gson.fromJson(obj.getString("users").toString(),
							new TypeToken<List<UserFriends>>() {}.getType());
					
					List<UserFriends> mData=new ArrayList<UserFriends>();
					mData.clear();
					mData.addAll(userFriendsList);
					int number=mData.size();
					Log.e(TAG, ""+number);
					if(number!=0){
						userDao.deleteAllUser();
						for(int i=0;i<mData.size();i++){
							userDao.insertOrReplaceUser(mData.get(i));
						}						
					log.e(TAG, userDao.queryUserAll().size()+"");
					}			
				//	LogUtil.e(TAG, ""+userFriendsList.size()+" ||"+mData.size());							
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		});
		
		
	}
	

}
