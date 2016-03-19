package com.android.biubiu.chat;

import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.fragment.FriendsListFragment;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.Window;

public class UserListActivity extends FragmentActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_list);
		//new出EaseChatFragment或其子类的实例
		EaseContactListFragment contastListFragment = new EaseContactListFragment();
		
		FriendsListFragment userfragment=new FriendsListFragment();
		 //传入参数
//		 Bundle args = new Bundle();
//		 args.putInt(EaseConstant.EXTRA_USER_ID, EaseConstant.CHATTYPE_GROUP);
//		 args.putString(EaseConstant.EXTRA_USER_ID, userID);
//		 contastListFragment.setArguments(args);		 
		 getSupportFragmentManager().beginTransaction().add(R.id.container_user_list, userfragment).commit();
	
		
	}
	

	

}
