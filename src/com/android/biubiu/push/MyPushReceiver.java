package com.android.biubiu.push;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import android.widget.Toast;
import cc.imeetu.iu.R;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.biubiu.MainActivity;
import com.android.biubiu.activity.biu.BiuBiuReceiveActivity;
import com.android.biubiu.bean.UserBean;
import com.android.biubiu.bean.UserFriends;
import com.android.biubiu.sqlite.PushMatchDao;
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
	private PushMatchDao pushDao;

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
		pushDao=new PushMatchDao(context);
		Log.d("mytest", "透传消息");
		String messageString = "透传消息 message=\"" + message
				+ "\" customContentString=" + customContentString;
		Log.d("mytest", messageString);
		boolean isOpen = SharePreferanceUtils.getInstance().isAppOpen(context, SharePreferanceUtils.IS_APP_OPEN, true);
		boolean isOpenVoice = SharePreferanceUtils.getInstance().isOpenVoice(context, SharePreferanceUtils.IS_OPEN_VOICE, true);
		boolean isShock = SharePreferanceUtils.getInstance().isOpenVoice(context, SharePreferanceUtils.IS_SHOCK, true);
		String lastSound = SharePreferanceUtils.getInstance().getBiuSoundTime(context, SharePreferanceUtils.BIU_SOUND_TIME, "0");
		boolean isPlaySound = true;
		if((System.currentTimeMillis() - Long.parseLong(lastSound))<5*1000){
			isPlaySound = false;
		}else{
			isPlaySound = true;
		}
		LogUtil.e(TAG, isOpen+"");
		UserBean newUserBean = new UserBean();
		String msgType = "";
		try {
			JSONObject jsons;
			jsons = JSONObject.parseObject(URLDecoder.decode(message, "utf-8"));
			msgType = jsons.getString("messageType");
			newUserBean.setTime(Long.parseLong(jsons.getString("time")));
			newUserBean.setChatId(jsons.getString("chat_id"));
			newUserBean.setId(jsons.getString("user_code"));
			newUserBean.setAlreadSeen(Constants.UN_SEEN);

			if(msgType.equals(Constants.MSG_TYPE_MATCH)){
				newUserBean.setNickname(jsons.getString("nickname"));
				newUserBean.setUserHead(jsons.getString("icon_thumbnailUrl"));
				newUserBean.setAge(jsons.getString("age"));
				newUserBean.setSex(jsons.getString("sex"));
				newUserBean.setStar(jsons.getString("starsign"));
				newUserBean.setIsStudent(jsons.getString("isgraduated"));
				newUserBean.setSchool(jsons.getString("school"));
				newUserBean.setCareer(jsons.getString("career"));
				newUserBean.setReferenceId("reference_id");
			}else if(msgType.equals(Constants.MSG_TYPE_GRAB)){
				newUserBean.setId(jsons.getString("user_code"));
				newUserBean.setUserHead(jsons.getString("icon_thumbnailUrl"));
				saveUserFriend(jsons.getString("user_code"),jsons.getString("nickname"),jsons.getString("icon_thumbnailUrl"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		if(isOpen){
			if(msgType.equals(Constants.MSG_TYPE_MATCH)){
				if(updateface != null){
					pushDao.insertOrReplacePush(newUserBean);
					if(isPlaySound){
						SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.BIU_SOUND_TIME, String.valueOf(System.currentTimeMillis()));
						if(isOpenVoice){
							playSound(context);
						}
						if(isShock){
							shock(context);
						}
					}
					updateface.updateView(newUserBean,0);
				}
			}else if(msgType.equals(Constants.MSG_TYPE_GRAB)){
				if(updateface != null){
					if(isOpenVoice){
						playSound(context);
					}
					if(isShock){
						shock(context);
					}
					updateface.updateView(newUserBean,1);
				}
				saveUserFriend(newUserBean.getId(),newUserBean.getNickname(),newUserBean.getUserHead());
			}else{
				if(updateface != null){
					if(pushDao.queryIsSameTime(newUserBean.getId(), System.currentTimeMillis())){
						Toast.makeText(context, "biubiu已经被抢啦", 1000).show();
					}
					pushDao.deleteByType(newUserBean.getId());
					updateface.updateView(newUserBean,2);
				}
			}
		}else{
			if(msgType.equals(Constants.MSG_TYPE_MATCH)){
				showNotification(context,isShock,isOpenVoice,newUserBean,msgType);
			}
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
	private void showNotification(Context context,boolean isShock,boolean isOpenVoice,UserBean bean,String type){
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle("你收到一条biubiu")
		.setTicker("你收到一条biubiu")//通知首次出现在通知栏，带上升动画效果的
		.setAutoCancel(true)
		.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
		.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
		//.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
		//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
		.setSmallIcon(cc.imeetu.iu.R.drawable.icon);
		try{
		if(type.equals(Constants.MSG_TYPE_MATCH)){
			String info = ""+bean.getNickname()+" ";
			if(bean.getSex().equals(Constants.SEX_MALE)){
				info = info+"男  ";
			}else{
				info = info+"女  ";
			}
			info = info+bean.getAge()+"岁";
			mBuilder.setContentText(info);
		}else{
			mBuilder.setContentText("你的biubiu被人抢啦");
		}
		}catch(NullPointerException e){
			// TODO: handle exception
		}
		if(isShock){
			mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
		}
		if(isOpenVoice){
			playSound(context);
		}
		Intent resultIntent;
		if((System.currentTimeMillis()-bean.getTime())<59*60*1000){
			resultIntent= new Intent(context.getApplicationContext(), BiuBiuReceiveActivity.class);
			resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			resultIntent.putExtra("referenceId", bean.getReferenceId());
			resultIntent.putExtra("userCode", bean.getId());
			LogUtil.d("mytest", "code--"+bean.getId());
			resultIntent.putExtra("chatId", bean.getChatId());
			LogUtil.e(TAG, "userCode=="+bean.getId());
		}else{
			resultIntent= new Intent(context.getApplicationContext(), MainActivity.class);
		}
		PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(0, mBuilder.build());
	}
	//播放自定义的声音  
	public void playSound(Context context) {  
		Log.d("mytest", "播放自己的提示音");
		String uri = "android.resource://" + context.getPackageName() + "/"+R.raw.yaho;  //自己把铃声放在raw文件夹下就行了
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
		LogUtil.e(TAG, code+"||"+name+"||"+url);
		log.e("保存用户信息");
		UserFriends item=new UserFriends();
		item.setUserCode(code);
		item.setIcon_thumbnailUrl(url);
		item.setNickname(name);
		userDao.insertOrReplaceUser(item);

	}
}
