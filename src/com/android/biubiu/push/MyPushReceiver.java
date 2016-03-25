package com.android.biubiu.push;

import java.util.List;
import java.util.Random;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.biubiu.MainActivity;
import com.android.biubiu.R;
import com.android.biubiu.bean.UserBean;
import com.android.biubiu.bean.UserFriends;
import com.android.biubiu.sqlite.UserDao;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.LoginUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.avos.avoscloud.LogUtil.log;
import com.baidu.android.pushservice.PushMessageReceiver;

public class MyPushReceiver extends PushMessageReceiver{

	static PushInterface updateface;
	private UserDao userDao;
	
	public static void setUpdateBean(PushInterface updateBean) {
		updateface = updateBean;
	}
	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		// TODO Auto-generated method stub
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		Log.d("mytest", responseString);
		if(null != channelId && !channelId.equals("")){
			SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.CHANNEL_ID, channelId);
			HttpUtils.commitChannelId(context);
		}
	}

	@Override
	public void onDelTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onListTags(Context arg0, int arg1, List<String> arg2,
			String arg3) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		userDao=new UserDao(context);
		Log.d("mytest", "透传消息");
		String messageString = "透传消息 message=\"" + message
				+ "\" customContentString=" + customContentString;
		Log.d("mytest", messageString);
		boolean isOpen = SharePreferanceUtils.getInstance().isAppOpen(context, SharePreferanceUtils.IS_APP_OPEN, true);
		boolean isOpenVoice = SharePreferanceUtils.getInstance().isOpenVoice(context, SharePreferanceUtils.IS_OPEN_VOICE, true);
		boolean isShock = SharePreferanceUtils.getInstance().isOpenVoice(context, SharePreferanceUtils.IS_SHOCK, true);
		if(isOpen){ 
			if(isOpenVoice){
				playSound(context);
			}
			if(isShock){
				shock(context);
			}
			try {
				JSONObject jsons = JSONObject.parseObject(message);
				String msgType = jsons.getString("messageType");
				UserBean newUserBean = new UserBean();
				newUserBean.setTime(Long.parseLong(jsons.getString("time")));
				newUserBean.setChatId(jsons.getString("chat_id"));
				newUserBean.setAlreadSeen(Constants.UN_SEEN);
				
				if(msgType.equals(Constants.MSG_TYPE_MATCH)){
					newUserBean.setId(jsons.getString("user_code"));
					newUserBean.setNickname(jsons.getString("nickname"));
					newUserBean.setUserHead(jsons.getString("icon_thumbnailUrl"));
					newUserBean.setAge(jsons.getString("age"));
					newUserBean.setSex(jsons.getString("sex"));
					newUserBean.setStar(jsons.getString("starsign"));
					newUserBean.setIsStudent(jsons.getString("isgraduated"));
					newUserBean.setSchool(jsons.getString("isgraduated"));
					newUserBean.setCareer(jsons.getString("career"));
					newUserBean.setReferenceId("reference_id");
					updateface.updateView(newUserBean,0);
				}else if(msgType.equals(Constants.MSG_TYPE_GRAB)){
					newUserBean.setId(jsons.getString("user_code"));
					newUserBean.setUserHead(jsons.getString("icon_thumbnailUrl"));
					updateface.updateView(newUserBean,1);
					saveUserFriend(jsons.getString("user_code"),jsons.getString("nickname"),jsons.getString("icon_thumbnailUrl"));
				}else{
					updateface.updateView(newUserBean,2);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			showNotification(context,isShock,isOpenVoice);
		}
	}

	@Override
	public void onNotificationArrived(Context context, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		Log.d("mytest", "通知消息");
	}

	@Override
	public void onNotificationClicked(Context arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSetTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnbind(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub

	}
	private void showNotification(Context context,boolean isShock,boolean isOpenVoice){
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle("测试标题")
		.setContentText("测试内容")
		.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
		.setAutoCancel(true)
		.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
		.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
		//.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
		//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
		.setSmallIcon(com.android.biubiu.R.drawable.biu_btn_biu);
		if(isShock){
			mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
		}
		if(isOpenVoice){
			playSound(context);
		}
		Intent resultIntent = new Intent(context, MainActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(0, mBuilder.build());
	}
	//播放自定义的声音  
	public void playSound(Context context) {  
		Log.d("mytest", "播放自己的提示音");
		String uri = "android.resource://" + context.getPackageName() + "/"+R.raw.duolaam;  //自己把铃声放在raw文件夹下就行了
		Uri no=Uri.parse(uri);  
		Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(),  
				no);  
		r.play();  
	}  
	public void shock(Context context){
		Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
	}
	/**
	 * 保存用户信息
	 * @param code
	 * @param name
	 * @param url
	 */
	public void saveUserFriend(String code,String name, String url){
		log.e("保存用户信息");
		UserFriends item=new UserFriends();
		item.setUserCode(code);
		item.setIcon_thumbnailUrl(url);
		item.setNickname(name);
		userDao.insertOrReplaceUser(item);
		
	}
}
