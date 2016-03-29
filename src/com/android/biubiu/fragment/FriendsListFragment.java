package com.android.biubiu.fragment;

import cc.imeetu.iu.R;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseContactList;

public class FriendsListFragment extends EaseContactListFragment{

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		
		contactListLayout=(EaseContactList) getView().findViewById(R.id.contact_list);
		//初始化时需要传入联系人list
//		EaseUser item=new EaseUser("10035");
//		contactList.add(item);
//		contactListLayout.init(contactList);
		//刷新列表
		contactListLayout.refresh();
		
	}

	@Override
	protected void setUpView() {
		// TODO Auto-generated method stub
		super.setUpView();
		titleBar.setBackgroundColor(getResources().getColor(R.color.main_green));
	}
	

	
	
	

}
