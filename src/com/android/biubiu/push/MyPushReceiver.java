package com.android.biubiu.push;

import java.util.List;
import java.util.Random;

import org.xutils.x;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.android.biubiu.MainActivity;
import com.android.biubiu.R;
import com.android.biubiu.bean.UserBean;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.baidu.android.pushservice.PushMessageReceiver;

public class MyPushReceiver extends PushMessageReceiver{

	PushInterface updateBean;
	public MyPushReceiver(Context context) {
		// TODO Auto-generated constructor stub
	}
	public void setUpdateBean(PushInterface updateBean) {
		this.updateBean = updateBean;
	}
	@Override
	public void onBind(Context context, int errorCode, String appid,
            String userId, String channelId, String requestId) {
		// TODO Auto-generated method stub
		String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
		LogUtil.d("mytest", responseString);
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
        String messageString = "透传消息 message=\"" + message
                + "\" customContentString=" + customContentString;
        LogUtil.d("mytest", messageString);
		boolean isOpen = SharePreferanceUtils.getInstance().isAppOpen(context, SharePreferanceUtils.IS_APP_OPEN, false);
		if(isOpen){
			playSound(context);
			UserBean newUserBean = new UserBean();
			Random random = new Random();
			int idRandom = random.nextInt(10000);
			newUserBean.setId(String.valueOf(idRandom));
			newUserBean.setTime(System.currentTimeMillis());
			updateBean.updateView(newUserBean);
		}else{
			showNotification(context);
		}
	}

	@Override
	public void onNotificationArrived(Context arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
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
	private void showNotification(Context context){
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
				.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
				.setSmallIcon(com.android.biubiu.R.drawable.biu_btn_biu);
		playSound(context);
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
}
