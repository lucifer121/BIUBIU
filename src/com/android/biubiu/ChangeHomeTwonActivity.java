package com.android.biubiu;

import java.util.ArrayList;
import java.util.List;

import com.android.biubiu.activity.BaseActivity;
import com.android.biubiu.activity.mine.ChangeCityActivity;
import com.android.biubiu.bean.Citybean;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.BaseCityActivity;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;
import com.android.biubiu.sqlite.CityDao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChangeHomeTwonActivity extends BaseCityActivity implements
OnClickListener, OnWheelChangedListener{
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private TextView mBtnConfirm;
	private String city;
	private RelativeLayout wanchLayout, backLayout;

	private CityDao cityDao = new CityDao();

	// 控件相关
	private TextView homeName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_home_twon);
		initView();
		city = super.getIntent().getStringExtra("hometown");

	

		setUpViews();
		setUpListener();
		setUpData();
		homeName.setText("" + mCurrentProviceName + mCurrentCityName
				);
	}

	private void initView() {
		// TODO Auto-generated method stub
		homeName = (TextView) super.findViewById(R.id.hometwonName_change_city_tv);
	}

	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (TextView) findViewById(R.id.city_selector_shengshiqu_tv);
		wanchLayout = (RelativeLayout) super
				.findViewById(R.id.hometwon_selector_shengshiqu_rl);
		wanchLayout.setOnClickListener(this);
		backLayout = (RelativeLayout) super
				.findViewById(R.id.back_changehometwon_mine_rl);
		backLayout.setOnClickListener(this);
	}

	private void setUpListener() {
		// 添加change事件
		mViewProvince.addChangingListener(this);
		// ���change�¼�
		mViewCity.addChangingListener(this);

		mBtnConfirm.setOnClickListener(this);
	}

	private void setUpData() {
		// initProvinceDatas();
		initProvinceDatasNews();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeHomeTwonActivity.this, mProvinceDatas));

		Log.e("lucifer", "mProvinceDatas.length==" + mProvinceDatas.length);
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
	//	mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
																																																																																																																																																																																																																																													updateCities();
			homeName.setText("" + mCurrentProviceName + mCurrentCityName
					);
		} else if (wheel == mViewCity) {
			updateAreas();
			homeName.setText("" + mCurrentProviceName + mCurrentCityName
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

			homeName.setText("" + mCurrentProviceName + mCurrentCityName
					+ mCurrentDistrictName);
		}
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
//		// mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
//
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.city_selector_shengshiqu_rl:
	//		completeInfo(user);

			break;
		case R.id.back_changecity_mine_rl:
			Intent intent2 = new Intent();
			intent2.putExtra("city", city);
			ChangeHomeTwonActivity.this.setResult(RESULT_CANCELED, intent2);
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 设置点击返回键的状态
	 */
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("city", city);
		ChangeHomeTwonActivity.this.setResult(RESULT_CANCELED, intent);
		finish();
	}
}
