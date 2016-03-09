package com.android.biubiu.activity;



import java.util.ArrayList;
import java.util.List;

import com.android.biubiu.R;


import com.android.biubiu.activity.mine.ChangeSchoolActivity;
import com.android.biubiu.bean.Citybean;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.BaseCityActivity;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;


import com.android.biubiu.sqlite.CityDao;




import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterTwoActivity extends BaseCityActivity implements OnClickListener,OnWheelChangedListener{
	private RelativeLayout nextLayout;
	private RelativeLayout cityLayout,schoolLayout;
	private WheelView	mViewProvince,mViewCity,mViewDistrict;
	private TextView mBtnConfirm;
	private CityDao cityDao = new CityDao();
	private TextView cityTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registertwo_layout);
		initView();
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		cityLayout=(RelativeLayout) findViewById(R.id.registertwo_center4_rl);
		cityLayout.setOnClickListener(this);
		nextLayout=(RelativeLayout) findViewById(R.id.next_registertwo_rl);
		mBtnConfirm=(TextView) findViewById(R.id.city_selector_shengshiqu_tv);
		cityTextView=(TextView) findViewById(R.id.job_registertwo_tv);
		schoolLayout=(RelativeLayout) findViewById(R.id.registertwo_center3_rl);
		
		schoolLayout.setOnClickListener(this);
		nextLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(RegisterTwoActivity.this,RegisterThreeActivity.class);
				startActivity(intent);
			}
		});
	
	}
	
	private PopupWindow popupWindowCity;

	private void initPopupWindowCity() {
		if (popupWindowCity == null) {
			View view = LayoutInflater.from(this).inflate(R.layout.shengshiqu,
					null);
			popupWindowCity = new PopupWindow(view,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			// 设置外观
			popupWindowCity.setFocusable(true);
			popupWindowCity.setOutsideTouchable(true);
			ColorDrawable colorDrawable = new ColorDrawable();
			popupWindowCity.setBackgroundDrawable(colorDrawable);
			// tvTitle=(TextView)view.findViewById(R.id.tvcolectList);

			mViewProvince = (WheelView) view.findViewById(R.id.id_province);
			mViewCity = (WheelView) view.findViewById(R.id.id_city);
			mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
			mBtnConfirm = (TextView) view
					.findViewById(R.id.city_selector_shengshiqu_tv);

			// 添加change事件
			mViewProvince.addChangingListener(RegisterTwoActivity.this);
			mViewProvince
					.addChangingListener(RegisterTwoActivity.this);
			// 添加change事件
			mViewCity.addChangingListener(RegisterTwoActivity.this);
			// 添加change事件
			mViewDistrict
					.addChangingListener(RegisterTwoActivity.this);
			// 添加onclick事件
			mBtnConfirm.setOnClickListener(this);
			setUpData();
		}

	}
	
	private void setUpData() {
		// initProvinceDatas();
		initProvinceDatasNews();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				RegisterTwoActivity.this, mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	@SuppressWarnings("unused")
	private void updateAreas() {
		Citybean city = new Citybean();
		List<Citybean> provinceList = new ArrayList<Citybean>();
		List<Citybean> cityList = new ArrayList<Citybean>();
		List<Citybean> townList = new ArrayList<Citybean>();

		int pCurrent = mViewCity.getCurrentItem();
		cityList = cityDao.getAllCity(mCurrentProviceName);
		// cityList=cityDao.getAllCity(mCurrentProviceName);
		String[] citys = new String[cityList.size()];

		for (int i = 0; i < cityList.size(); i++) {
			citys[i] = cityList.get(i).getCity();
		}
		mCurrentCityName = cityList.get(pCurrent).getCity();
		// mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];

//		townList = cityDao.getAllTown(mCurrentProviceName, mCurrentCityName);
//		Log.e("lucifer", "townList==" + townList.size());
//		String[] areas = new String[townList.size()];
//
//		for (int i = 0; i < townList.size(); i++) {
//			areas[i] = townList.get(i).getTown();
//		}
//
//		// String[] areas = mDistrictDatasMap.get(mCurrentCityName);
//
//		if (areas == null) {
//			areas = new String[] { "" };
//		}
//		mViewDistrict
//				.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
//		mViewDistrict.setCurrentItem(0);
//		mCurrentDistrictName = areas[0];
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	@SuppressWarnings("unused")
	private void updateCities() {
		Citybean city = new Citybean();
		List<Citybean> provinceList = new ArrayList<Citybean>();
		List<Citybean> cityList = new ArrayList<Citybean>();
		List<Citybean> townList = new ArrayList<Citybean>();

		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		Log.e("lucifer", "mCurrentProviceName==" + mCurrentProviceName);

		cityList = cityDao.getAllCity(mCurrentProviceName);
		String[] cities = new String[cityList.size()];
		for (int i = 0; i < cityList.size(); i++) {
			cities[i] = cityList.get(i).getCity();
		}
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(0,R.anim.right_out_anim);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registertwo_center4_rl:
			//选择城市
			initPopupWindowCity();
			popupWindowCity.showAsDropDown(cityTextView, 0, 100);
			break;
		case R.id.registertwo_center3_rl:
			Intent intent=new Intent(this,ChangeSchoolActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
			cityTextView.setText("" + mCurrentProviceName + mCurrentCityName
					);
		} else if (wheel == mViewCity) {
			updateAreas();
			cityTextView.setText("" + mCurrentProviceName + mCurrentCityName
					);
		} else if (wheel == mViewDistrict) {
			// mCurrentDistrictName =
			// mDistrictDatasMap.get(mCurrentCityName)[newValue];
			// mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			int pCurrent = mViewDistrict.getCurrentItem();
			List<Citybean> townList = new ArrayList<Citybean>();
			townList = cityDao
					.getAllTown(mCurrentProviceName, mCurrentCityName);
			String[] towns = new String[townList.size()];
			for (int i = 0; i < townList.size(); i++) {
				towns[i] = townList.get(i).getTown();
			}
			mCurrentDistrictName = towns[pCurrent];

			cityTextView.setText("" + mCurrentProviceName + mCurrentCityName
					);
		}
		
	}
}
