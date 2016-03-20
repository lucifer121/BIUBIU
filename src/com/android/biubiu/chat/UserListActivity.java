package com.android.biubiu.chat;

import java.util.List;

import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.fragment.FriendsListFragment;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class UserListActivity extends FragmentActivity {
	private RelativeLayout backLayout;
	private ListView mListView;
	private List<String> mData;
	private UserListAdapter mAdapter;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_list);
		//new出EaseChatFragment或其子类的实例
//		EaseContactListFragment contastListFragment = new EaseContactListFragment();
//		
//		FriendsListFragment userfragment=new FriendsListFragment();
//		 //传入参数
////		 Bundle args = new Bundle();
////		 args.putInt(EaseConstant.EXTRA_USER_ID, EaseConstant.CHATTYPE_GROUP);
////		 args.putString(EaseConstant.EXTRA_USER_ID, userID);
////		 contastListFragment.setArguments(args);		 
//		 getSupportFragmentManager().beginTransaction().add(R.id.container_user_list, userfragment).commit();
	
		initView();
		initData();
		initAdapter();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		backLayout=(RelativeLayout) findViewById(R.id.back_chat_userList_rl);
		mListView=(ListView) findViewById(R.id.chat_user_list_listView);
		
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}



	private void initAdapter() {
		// TODO Auto-generated method stub
		mAdapter=new UserListAdapter(this, mData);
		mListView.setAdapter(mAdapter);
	}



	private void initData() {
		// TODO Auto-generated method stub
		
		try {
			List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
			mData.addAll(usernames);
		} catch (HyphenateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	
	

	

}
