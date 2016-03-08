/*package com.android.biubiu.common.city;



import com.android.biubiu.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShengshiquActivity extends BaseActivity implements
		OnClickListener, OnWheelChangedListener {
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private TextView mBtnConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shengshiqu);
		setUpViews();
		setUpListener();
		setUpData();
	}

	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (TextView) findViewById(R.id.city_selector_shengshiqu_tv);
	}

	private void setUpListener() {
		// ���change�¼�
		mViewProvince.addChangingListener(this);
		// ���change�¼�
		mViewCity.addChangingListener(this);
		// ���change�¼�
		mViewDistrict.addChangingListener(this);
		// ���onclick�¼�
		mBtnConfirm.setOnClickListener(this);
	}

	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				ShengshiquActivity.this, mProvinceDatas));
		// ���ÿɼ���Ŀ����
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	*//**
	 * ��ݵ�ǰ���У�������WheelView����Ϣ
	 *//*
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict
				.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	*//**
	 * ��ݵ�ǰ��ʡ��������WheelView����Ϣ
	 *//*
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
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
		case R.id.city_selector_shengshiqu_tv:
			showSelectedResult();
			break;
		default:
			break;
		}
	}

	private void showSelectedResult() {
		Toast.makeText(
				ShengshiquActivity.this,
				"你选择了:" + mCurrentProviceName + "," + mCurrentCityName + ","
						+ mCurrentDistrictName + "," + mCurrentZipCode,
				Toast.LENGTH_SHORT).show();
	}
}
*/