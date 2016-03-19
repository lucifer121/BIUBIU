package com.android.biubiu.chat;

import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.common.Constant;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.Window;

public class ChatActivity extends EaseBaseActivity {
	private String userID;
	   public static ChatActivity activityInstance;
	    private EaseChatFragment chatFragment;
	    String toChatUsername;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
//		userID=getIntent().getStringExtra(Constant.EXTRA_USER_ID);
//		//new出EaseChatFragment或其子类的实例
//		 EaseChatFragment chatFragment = new EaseChatFragment();
//		 //传入参数
//		 Bundle args = new Bundle();
//		 args.putInt(EaseConstant.EXTRA_USER_ID, EaseConstant.CHATTYPE_GROUP);
//		 args.putString(EaseConstant.EXTRA_USER_ID, userID);
//		 chatFragment.setArguments(args);
		
	       activityInstance = this;
	        //聊天人或群id
	        toChatUsername = getIntent().getExtras().getString("userId");
	        //可以直接new EaseChatFratFragment使用
	        chatFragment = new ChatFragment();
	        //传入参数
	        chatFragment.setArguments(getIntent().getExtras());
		 getSupportFragmentManager().beginTransaction().add(R.id.container_chatActivity, chatFragment).commit();

	}
	
	

	

}
