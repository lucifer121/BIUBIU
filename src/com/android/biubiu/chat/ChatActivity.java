package com.android.biubiu.chat;


import com.android.biubiu.MainActivity;

import cc.imeetu.iu.R;



import com.android.biubiu.common.Constant;
import com.avos.avoscloud.LogUtil.log;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

		
	       activityInstance = this;
	        //聊天人或群id
	        toChatUsername = getIntent().getExtras().getString("userId");
	        //可以直接new EaseChatFratFragment使用
	        chatFragment = new ChatFragment();
	        //传入参数
	        chatFragment.setArguments(getIntent().getExtras());
		 getSupportFragmentManager().beginTransaction().add(R.id.container_chatActivity, chatFragment).commit();

	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        log.e("chat", ""+username);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
	
	

	

}
