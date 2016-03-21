package com.android.biubiu.activity.biu;

import java.util.ArrayList;
import java.util.List;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.R.menu;
import com.android.biubiu.adapter.UserInterestAdapter;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.utils.DensityUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BiuBiuReceiveActivity extends BaseActivity {
	private RelativeLayout backLayout;
	private GridView mGridViewTag,mGridViewInterestTag;
	private UserInterestAdapter interestAdapter;
	private ArrayList<InterestTagBean> mList=new ArrayList<InterestTagBean>();
	private Button grabBT;
	private RelativeLayout neverGrab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_receive);
		initView();
		initData();
		initAdapter();
	}

	private void initData() {
		InterestTagBean item=new InterestTagBean();
		item.setName("美丽");
		item.setCode("1001");
		InterestTagBean item2=new InterestTagBean();
		item2.setName("漂亮");
		item2.setCode("1002");
		for(int i=0;i<5;i++){
			mList.add(item);
			mList.add(item2);
		}
		
	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		interestAdapter = new UserInterestAdapter(getApplicationContext(), mList);
		mGridViewTag.setAdapter(interestAdapter);
		interestAdapter = new UserInterestAdapter(getApplicationContext(), mList);
		mGridViewInterestTag.setAdapter(interestAdapter);
		
		setGridviewHight(mGridViewTag);
		setGridviewHight(mGridViewInterestTag);
		mGridViewTag.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				
				toastShort(mList.get(position).getName());
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		backLayout=(RelativeLayout) findViewById(R.id.back_receive_biu_mine_rl);
		mGridViewTag=(GridView) findViewById(R.id.gridview_receive_biubiu_tag);
		mGridViewInterestTag=(GridView) findViewById(R.id.gridview_receive_biubiu_interest_tag);
		grabBT=(Button)findViewById(R.id.grab_biu_receive_biu_bt);
		neverGrab=(RelativeLayout) findViewById(R.id.never_grag_biu_receive_biu_rl);
		neverGrab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toastShort("不见你");
			}
		});
		grabBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			toastShort("抢中了啊");
			}
		});
		
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
	}	
	/**
	 * 设置 Gridview高度
	 */
	public void setGridviewHight(GridView mGridView) {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mGridView
				.getLayoutParams();
		int mHight;
		if (mList.size() != 0 && (mList.size()) % 4 == 0) {
			mHight = (((mList.size()) / 4)) * DensityUtil.dip2px(this, 37);
		} else {
			mHight = (((mList.size()) / 4) + 1) * DensityUtil.dip2px(this, 37);
		}
		params.height = mHight;
		mGridView.setLayoutParams(params);

	}
	/**
	 * 抢biu
	 */
	public void grabBiu(){
		
	}

	

}
