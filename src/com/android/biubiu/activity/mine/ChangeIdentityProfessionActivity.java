package com.android.biubiu.activity.mine;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChangeIdentityProfessionActivity extends BaseActivity implements
		OnWheelChangedListener {
	private WheelView mViewConstellation;
	private RelativeLayout backLayout, completeLayout;
	/**
	 * 所有身份职业
	 */
	private String mIdentity[] = { "学生", "媒体/公关", "金融", "法律", "销售", "咨询",
			"IT/互联网/通信", "文化/艺术", "影视/娱乐", "教育/科研", "医疗/健康", "房地产/建筑", "政府机构" };
	private TextView identity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_identity_profession);
		initView();
		setUpListener();
		setUpData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		identity = (TextView) findViewById(R.id.identity_profession_change_city_tv);
		mViewConstellation = (WheelView) findViewById(R.id.id_identify_profession);
		backLayout = (RelativeLayout) findViewById(R.id.back_changeIdentity_mine_rl);
		completeLayout = (RelativeLayout) findViewById(R.id.mine_identity_profession_wancheng_rl);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
		completeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toastShort("wancheng");
				if(identity.getText().equals("学生")){
					Intent intent=new Intent(ChangeIdentityProfessionActivity.this,ChangeSchoolActivity.class);
					startActivityForResult(intent, 10);
				}
				
			}
		});
	}

	private void setUpListener() {
		// TODO Auto-generated method stub
		mViewConstellation.addChangingListener(this);
	}

	private void setUpData() {
		// TODO Auto-generated method stub
		// initProvinceDatas();
		identity.setText(mIdentity[0]);
		mViewConstellation.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeIdentityProfessionActivity.this, mIdentity));

		// Log.e("lucifer", "mProvinceDatas.length==" + mProvinceDatas.length);
		// 设置可见条目数量
		mViewConstellation.setVisibleItems(7);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewConstellation) {

			int pCurrent = mViewConstellation.getCurrentItem();
			identity.setText(mIdentity[pCurrent]);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		switch (requestCode) {
		case 10:
			if(resultCode == this.RESULT_OK){
				finish();
				
			}
			break;
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	

}
