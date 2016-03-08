package com.android.biubiu.activity.mine;

import java.util.ArrayList;
import java.util.List;

import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.activity.BaseActivity;
import com.android.biubiu.bean.Citybean;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ChangeConstellationActivity extends BaseActivity implements
		OnWheelChangedListener {
	private WheelView mViewConstellation;
	/**
	 * 所有星座
	 */
	private String mProvinceDatas[]={
			"水瓶座","双鱼座","牧羊座",
			"金牛座","双子座","巨蟹座" ,
			"狮子座","处女座","天秤座",
			"天蝎座","射手座","摩羯座"
			};
	private TextView constellation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_constellation);
		initView();
		setUpListener();
		setUpData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mViewConstellation = (WheelView) findViewById(R.id.id_constellation);
		constellation=(TextView) findViewById(R.id.constellationName_change_city_tv);

	}

	private void setUpListener() {
		// 添加change事件
		mViewConstellation.addChangingListener(this);

	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewConstellation) {

			int pCurrent = mViewConstellation.getCurrentItem();
			constellation.setText(mProvinceDatas[pCurrent]);
		} 

	}
	
	private void setUpData() {
		// initProvinceDatas();
		constellation.setText(mProvinceDatas[0]);
		mViewConstellation.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeConstellationActivity.this, mProvinceDatas));

	//	Log.e("lucifer", "mProvinceDatas.length==" + mProvinceDatas.length);
		// 设置可见条目数量
		mViewConstellation.setVisibleItems(7);

	}

	


}
