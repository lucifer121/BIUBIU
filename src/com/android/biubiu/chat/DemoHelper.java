package com.android.biubiu.chat;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMessage.Type;

import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseEmojiconInfoProvider;
import com.hyphenate.easeui.controller.EaseUI.EaseSettingsProvider;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier.EaseNotificationInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;

public class DemoHelper {
    protected static final String TAG = "DemoHelper";
    private Context appContext;
    private EaseUI easeUI=null;
    private static DemoHelper instance = null;
    
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
        options.setGCMNumber("324169311137");
        //在小米手机上当app被kill时使用小米推送进行消息提示，同GCM一样不是必须的
        options.setMipushConfig("2882303761517426801", "5381742660801");
        
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
	        
//	        //不设置，则使用easeui默认的
//	        easeUI.setSettingsProvider(new EaseSettingsProvider() {
//	            
//	            @Override
//	            public boolean isSpeakerOpened() {
//	                return demoModel.getSettingMsgSpeaker();
//	            }
//	            
//	            @Override
//	            public boolean isMsgVibrateAllowed(EMMessage message) {
//	                return demoModel.getSettingMsgVibrate();
//	            }
//	            
//	            @Override
//	            public boolean isMsgSoundAllowed(EMMessage message) {
//	                return demoModel.getSettingMsgSound();
//	            }
//	            
//	            @Override
//	            public boolean isMsgNotifyAllowed(EMMessage message) {
//	                if(message == null){
//	                    return demoModel.getSettingMsgNotification();
//	                }
//	                if(!demoModel.getSettingMsgNotification()){
//	                    return false;
//	                }else{
//	                    //如果允许新消息提示
//	                    //屏蔽的用户和群组不提示用户
//	                    String chatUsename = null;
//	                    List<String> notNotifyIds = null;
//	                    // 获取设置的不提示新消息的用户或者群组ids
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
//	                }
//	            }
//	        });
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
//	                EaseUser user = getUserInfo(message.getFrom());
//	                (user != null){
////	                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
//	                }else{
//	                    return message.getFrom() + ": " + ticker;
//	                }
	                return message.getFrom() + ": " + ticker;
	            }
	            
	            @Override
	            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
	                return null;
	                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
	            }
	            
	            @Override
	            public Intent getLaunchIntent(EMMessage message) {
	                //设置点击通知栏跳转事件
	                Intent intent = new Intent(appContext, ChatActivity.class);
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
	        return user;
		}
		

	   

	

}
