package com.android.biubiu.activity.mine;



import java.util.ArrayList;
import java.util.List;












import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.R;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.bean.Citybean;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.BaseCityActivity;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;
import com.android.biubiu.sqlite.CityDao;











import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeCityActivity extends BaseCityActivity implements
OnClickListener, OnWheelChangedListener{
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private TextView mBtnConfirm;
	private String city;
	private RelativeLayout wanchLayout, backLayout;

	private CityDao cityDao = new CityDao();

	// 控件相关
	private TextView cityName;
	UserInfoBean infoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_city_main);
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
		initView();
		city = super.getIntent().getStringExtra("hometown");
		setUpViews();
		setUpListener();
		setUpData();
		cityName.setText("" + mCurrentProviceName + mCurrentCityName
				);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		cityName = (TextView) super.findViewById(R.id.cityName_change_city_tv);
	}

	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (TextView) findViewById(R.id.city_selector_shengshiqu_tv);
		wanchLayout = (RelativeLayout) super
				.findViewById(R.id.city_selector_shengshiqu_rl);
		wanchLayout.setOnClickListener(this);
		backLayout = (RelativeLayout) super
				.findViewById(R.id.back_changecity_mine_rl);
		backLayout.setOnClickListener(this);
	}

	private void setUpListener() {
		// 添加change事件
		mViewProvince.addChangingListener(this);
		// ���change�¼�
		mViewCity.addChangingListener(this);
		// ���change�¼�
	//	mViewDistrict.addChangingListener(this);
		// ���onclick�¼�
		mBtnConfirm.setOnClickListener(this);
	}

	private void setUpData() {
		// initProvinceDatas();
		initProvinceDatasNews();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeCityActivity.this, mProvinceDatas));

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
			cityName.setText("" + mCurrentProviceName + mCurrentCityName
					);
		} else if (wheel == mViewCity) {
			updateAreas();
			cityName.setText("" + mCurrentProviceName + mCurrentCityName
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

			cityName.setText("" + mCurrentProviceName + mCurrentCityName
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
	private void updateInfo() {
		// TODO Auto-generated method stub
		RequestParams params = HttpUtils.getUpdateInfoParams(getApplicationContext(), infoBean,"nickname");
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "error--"+ex.getMessage());
				LogUtil.d("mytest", "error--"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "city=="+result);
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort(jsons.getString("error"));
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
					String token = data.getString("token");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					Intent intent = new Intent();
					intent.putExtra("userInfoBean", infoBean);
					setResult(RESULT_OK, intent);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.city_selector_shengshiqu_rl:
			if(null == cityName.getText() || cityName.getText().toString().equals("")){
				return;
			}
			String cityiId=cityDao.getID(mCurrentProviceName, mCurrentCityName).get(0).getId();
			infoBean.setCity(cityiId);
			updateInfo();
			break;
		case R.id.back_changecity_mine_rl:
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
		finish();
	}
}

//	/**
//	 * 上传 修改信息
//	 * 
//	 * @param user
//	 * @author lucifer
//	 * @date 2015-11-6
//	 */
//	public void completeInfo(final ObjUser user) {
//
//		String cityID = cityDao
//				.getID(mCurrentProviceName, mCurrentCityName,
//						mCurrentDistrictName).get(0).getId();
//		if (cityID != null) {
//			user.setHometown(cityID);
//
//			// 只上传信息
//			ObjUserWrap.completeUserInfo(user, new ObjFunBooleanCallback() {
//
//				@Override
//				public void callback(boolean result, AVException e) {
//					if (result) {
//						Toast.makeText(getApplicationContext(), "已保存", 1000)
//								.show();
//						Intent intent = new Intent();
//						intent.putExtra("city", mCurrentProviceName
//								+ mCurrentCityName + mCurrentDistrictName);
//						setResult(RESULT_OK, intent);
//						finish();
//						log.e("lucifer", "" + mCurrentProviceName
//								+ mCurrentCityName + mCurrentDistrictName);
//
//					} else {
//						Toast.makeText(getApplicationContext(), "保存失败", 1000)
//								.show();
//					}
//				}
//			});
//		} else {
//			Toast.makeText(getApplicationContext(), "保存失败", 1000).show();
//		}
//
//	}

	


