package com.android.biubiu.chat;

import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.biubiu.bean.UserFriends;
import com.android.biubiu.sqlite.UserDao;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.LogUtil.log;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseEmojiconInfoProvider;
import com.hyphenate.easeui.controller.EaseUI.EaseSettingsProvider;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.model.EaseNotifier.EaseNotificationInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;

public class DemoHelper {
    protected static final String TAG = "DemoHelper";
    private Context appContext;
    private EaseUI easeUI=null;
    private static DemoHelper instance = null;
    
    private UserDao userDao;
//    是否有新消息提示
    private boolean isShow=true;
  //是否有新消息提示 声音
    private boolean isMsgSoundAllowed=true;
  //是否有新消息提示 震动
    private boolean isMsgVibrateAllowed=true;
    
    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;
    
    public synchronized static DemoHelper getInstance() {
		if (instance == null) {
			instance = new DemoHelper();
		}
		return instance;
	}
	
	public void init(Context context) {
	 //   demoModel = new DemoModel(context);
		
	    EMOptions options = initChatOptions();
	    //options传null则使用默认的
		if (EaseUI.getInstance().init(context, options)) {
		    appContext = context;
		    
		    userDao=new UserDao(appContext);
		    //设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
		    EMClient.getInstance().setDebugMode(true);
		    //get easeui instance
		    easeUI = EaseUI.getInstance();
		    //调用easeui的api设置providers
		    setEaseUIProviders();
			//初始化PreferenceManager
		//	PreferenceManager.init(context);
			//初始化用户管理类
		//	getUserProfileManager().init(context);
			
			//设置全局监听
		//	setGlobalListeners();
		//	broadcastManager = LocalBroadcastManager.getInstance(appContext);
	     //   initDbDao();
		    //注册消息事件监听
	        registerEventListener();

		}
	}
	private EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");
        
        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        // 设置从db初始化加载时, 每个conversation需要加载msg的个数
        options.setNumberOfMessagesLoaded(1);
        
        //使用gcm和mipush时，把里面的参数替换成自己app申请的
        //设置google推送，需要的GCM的app可以设置此参数
        options.setGCMNumber("242253255366");
        //在小米手机上当app被kill时使用小米推送进行消息提示，同GCM一样不是必须的
        options.setMipushConfig("2882303761517451967", "5541745124967");
        
//        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
//        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
//        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());
//        
        return options;
//        notifier.setNotificationInfoProvider(getNotificationListener());
    }
	
	   protected void setEaseUIProviders() {
	        //需要easeui库显示用户头像和昵称设置此provider
	        easeUI.setUserProfileProvider(new EaseUserProfileProvider() {
	            
	            @Override
	            public EaseUser getUser(String username) {
	                return getUserInfo(username);
	            }
	        });
	        
	        //不设置，则使用easeui默认的
	        easeUI.setSettingsProvider(new EaseSettingsProvider() {
	            
	            @Override
	            public boolean isSpeakerOpened() {
//	                return demoModel.getSettingMsgSpeaker();
	            	return true;
	            }
	            
	            @Override
	            public boolean isMsgVibrateAllowed(EMMessage message) {
//	                return demoModel.getSettingMsgVibrate();
	    	        isMsgVibrateAllowed=SharePreferanceUtils.getInstance().isShock(appContext, SharePreferanceUtils.IS_SHOCK, true);
	    	      log.e(TAG, "isMsgVibrateAllowed=="+isMsgVibrateAllowed);
	            	return isMsgVibrateAllowed;
	            }
	            
	            @Override
	            public boolean isMsgSoundAllowed(EMMessage message) {
//	                return demoModel.getSettingMsgSound();
	            	  isMsgSoundAllowed=SharePreferanceUtils.getInstance().isOpenVoice(appContext, SharePreferanceUtils.IS_OPEN_VOICE, true);
	            	  log.e(TAG, "isMsgSoundAllowed=="+isMsgSoundAllowed);
	            	return isMsgSoundAllowed;
	            }
	            
	            @Override
	            public boolean isMsgNotifyAllowed(EMMessage message) {
	                if(message == null){
//	                    return demoModel.getSettingMsgNotification();
	                	return false;
	                }
	                isShow = SharePreferanceUtils.getInstance().isReceiveMsg(appContext, SharePreferanceUtils.IS_RECEIVE_MSG, true);
	                if(!isShow){
	                	return false;
	                }
//	                if(!demoModel.getSettingMsgNotification()){
//	                    return false;
//	                }else{
	                    //如果允许新消息提示
	                    //屏蔽的用户和群组不提示用户
	                    String chatUsename = null;
	                    List<String> notNotifyIds = null;
	                    // 获取设置的不提示新消息的用户或者群组ids
//	                    if (message.getChatType() == ChatType.Chat) {
//	                        chatUsename = message.getFrom();
//	                        notNotifyIds = demoModel.getDisabledIds();
//	                    } else {
//	                        chatUsename = message.getTo();
//	                        notNotifyIds = demoModel.getDisabledGroups();
//	                    }
//
//	                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
//	                        return true;
//	                    } else {
//	                        return false;
//	                    }
	                    return true;
	                }
//	            }
	        });
	        //设置表情provider
	        easeUI.setEmojiconInfoProvider(new EaseEmojiconInfoProvider() {
	            
	            @Override
	            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
	                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
	                for(EaseEmojicon emojicon : data.getEmojiconList()){
	                    if(emojicon.getIdentityCode().equals(emojiconIdentityCode)){
	                        return emojicon;
	                    }
	                }
	                return null;
	            }

	            @Override
	            public Map<String, Object> getTextEmojiconMapping() {
	                //返回文字表情emoji文本和图片(resource id或者本地路径)的映射map
	                return null;
	            }
	        });
	        
	        //不设置，则使用easeui默认的
	        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotificationInfoProvider() {
	            
	            @Override
	            public String getTitle(EMMessage message) {
	              //修改标题,这里使用默认
	                return null;
	            }
	            
	            @Override
	            public int getSmallIcon(EMMessage message) {
	              //设置小图标，这里为默认
	            //	appContext.getResources().getDrawable(R.drawable.icon);
	            	
	                return 0;
	            }
	            
	            @Override
	            public String getDisplayedText(EMMessage message) {
	                // 设置状态栏的消息提示，可以根据message的类型做相应提示
	                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
	                if(message.getType() == Type.TXT){
	                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
	                }
	                //TODO 状态栏
	                EaseUser user = getUserInfo(message.getFrom());
	                if(user != null){
	                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
	                }else{
	                    return message.getFrom() + ": " + ticker;
	                }
	               
	            }
	            
	            @Override
	            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
	                return null;
	                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
	            }
	            
	            @Override
	            public Intent getLaunchIntent(EMMessage message) {
	                //设置点击通知栏跳转事件
	                Intent intent = new Intent(appContext.getApplicationContext(), ChatActivity.class);
	                LogUtil.e(TAG, "点击通知栏进入聊天activity");
	                
	                Intent intentRefresh = new Intent();  //Itent就是我们要发送的内容
	                
	                intentRefresh.setAction(Constants.FLAG_RECEIVE);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
	                appContext.sendBroadcast(intentRefresh);   //发送广播
	                
	                //有电话时优先跳转到通话页面
	                {
	                    ChatType chatType = message.getChatType();
	                    if (chatType == ChatType.Chat) { // 单聊信息
	                        intent.putExtra("userId", message.getFrom());
	                        intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
	                    } else { // 群聊信息
	                        // message.getTo()为群聊id
	                        intent.putExtra("userId", message.getTo());
	                        if(chatType == ChatType.GroupChat){
	                            intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
	                        }else{
	                            intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
	                        }
	                        
	                    }
	                }
	           
	                return intent;
	            }
	        });
	    }
	   
		private EaseUser getUserInfo(String username){
		    //获取user信息，demo是从内存的好友列表里获取，
	        //实际开发中，可能还需要从服务器获取用户信息,
	        //从服务器获取的数据，最好缓存起来，避免频繁的网络请求
	        EaseUser user = null;
//	        if(username.equals(EMClient.getInstance().getCurrentUser()))
//	            return getUserProfileManager().getCurrentUserInfo();
//	        user = getContactList().get(username);
//	        //TODO 获取不在好友列表里的群成员具体信息，即陌生人信息，demo未实现
//	        if(user == null && getRobotList() != null){
//	            user = getRobotList().get(username);
//	        }
	        user=new EaseUser(username);
	        

	       if(username.equals(SharePreferanceUtils.getInstance().getUserCode(appContext, SharePreferanceUtils.USER_CODE, ""))){
	    	   //是我自己
	    	   user.setAvatar(SharePreferanceUtils.getInstance().getUserHead(appContext, SharePreferanceUtils.USER_HEAD, ""));
	    	   user.setNick(SharePreferanceUtils.getInstance().getUserName(appContext, SharePreferanceUtils.USER_NAME, ""));
	       } else{
	           List<UserFriends>  list= userDao.queryUser(username);
		       
		        if (list!=null&&list.size()>0) {
		        	 user.setAvatar(list.get(0).getIcon_thumbnailUrl());
		 	         user.setNick(list.get(0).getNickname());
				}
	       }
	
	       
	        return user;
		}
		
		 /**
	     * 全局事件监听
	     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
	     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
	     */
	    protected void registerEventListener() {
	    	messageListener = new EMMessageListener() {
	            private BroadcastReceiver broadCastReceiver = null;
				
				@Override
				public void onMessageReceived(List<EMMessage> messages) {
				    for (EMMessage message : messages) {
				        EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
				        //应用在后台，不需要刷新UI,通知栏提示新消息
				        if(!easeUI.hasForegroundActivies()){
				            getNotifier().onNewMsg(message);
				        }
				    }
				}
				
				@Override
				public void onCmdMessageReceived(List<EMMessage> messages) {
				    for (EMMessage message : messages) {
	                    EMLog.d(TAG, "收到透传消息");
	                    //获取消息body
	                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
	                    final String action = cmdMsgBody.action();//获取自定义action

	                    //获取扩展属性 此处省略
	                    //message.getStringAttribute("");
	                    EMLog.d(TAG, String.format("透传消息：action:%s,message:%s", action,message.toString()));
	                    final String str = appContext.getString(R.string.receive_the_passthrough);

	                    final String CMD_TOAST_BROADCAST = "hyphenate.demo.cmd.toast";
	                    IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);

	                    if(broadCastReceiver == null){
	                        broadCastReceiver = new BroadcastReceiver(){

	                            @Override
	                            public void onReceive(Context context, Intent intent) {
	                                // TODO Auto-generated method stub
	                                Toast.makeText(appContext, intent.getStringExtra("cmd_value"), Toast.LENGTH_SHORT).show();
	                            }
	                        };

	                        //注册广播接收者
	                        appContext.registerReceiver(broadCastReceiver,cmdFilter);
	                    }

	                    Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
	                    broadcastIntent.putExtra("cmd_value", str+action);
	                    appContext.sendBroadcast(broadcastIntent, null);
	                }
				}

				@Override
				public void onMessageReadAckReceived(List<EMMessage> messages) {
				}
				
				@Override
				public void onMessageDeliveryAckReceived(List<EMMessage> message) {
				}
				
				@Override
				public void onMessageChanged(EMMessage message, Object change) {
					
				}
			};
			
	        EMClient.getInstance().chatManager().addMessageListener(messageListener);
	    }
	    
		/**
		 * 获取消息通知类
		 * @return
		 */
		public EaseNotifier getNotifier(){
		    return easeUI.getNotifier();
		}
		
		/**
		 * 是否登录成功过
		 * 
		 * @return
		 */
		public boolean isLoggedIn() {
			return EMClient.getInstance().isLoggedInBefore();
		}
		

	   

	

}
