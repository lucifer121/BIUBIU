package com.android.biubiu.activity;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.android.biubiu.R;


import com.android.biubiu.activity.mine.ChangeIdentityProfessionActivity;
import com.android.biubiu.activity.mine.ChangeSchoolActivity;
import com.android.biubiu.bean.Citybean;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.BaseCityActivity;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;


import com.android.biubiu.sqlite.CityDao;


import com.android.biubiu.utils.Constants;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterTwoActivity extends BaseCityActivity implements OnClickListener,OnWheelChangedListener{
	private static final int SELECT_SCHOOL = 1001;
	private RelativeLayout nextLayout;
	private RelativeLayout cityLayout,schoolLayout;
	private WheelView	mViewProvince,mViewCity,mViewDistrict,mViewProfesstion;
	private TextView mBtnConfirm;
	private CityDao cityDao = new CityDao();
	private TextView cityTextView;
	private TextView schoolTv;
	private ImageView isStudentImv;
	private ImageView graduateImv;
	private ImageView userheadImv;
	private LinearLayout isStudentLinear;
	private LinearLayout graduateLinear;
	private RelativeLayout backRl;
	boolean isStudent = true;
	UserInfoBean userBean = new UserInfoBean();
	Bitmap userheadBitmp;
	String headPath;
	/**
	 * 所有身份职业
	 */
	private String mIdentity[]={
			"媒体/公关",
			"金融",
			"法律",
			"销售",
			"咨询",
			"IT/互联网/通信",
			"文化/艺术",
			"影视/娱乐",
			"教育/科研",
			"医疗/健康",
			"房地产/建筑",
			"政府机构"
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registertwo_layout);
		getinentInfo();
		initView();
	}
	private void getinentInfo() {
		// TODO Auto-generated method stub
		UserInfoBean bean = (UserInfoBean) getIntent().getSerializableExtra("infoBean");
		Bitmap bitmp = getIntent().getParcelableExtra("userhead");
		headPath = getIntent().getStringExtra("headPath");
		userBean.setSex(bean.getSex());
		userBean.setBirthday(bean.getBirthday());
		userBean.setSex(bean.getSex());
		userheadBitmp = bitmp;
	}
	private void initView() {
		// TODO Auto-generated method stub
		cityLayout=(RelativeLayout) findViewById(R.id.registertwo_center4_rl);
		cityLayout.setOnClickListener(this);
		nextLayout=(RelativeLayout) findViewById(R.id.next_registertwo_rl);
		nextLayout.setOnClickListener(this);
		mBtnConfirm=(TextView) findViewById(R.id.city_selector_shengshiqu_tv);
		cityTextView=(TextView) findViewById(R.id.city_registertwo_tv);
		schoolTv = (TextView) findViewById(R.id.school_registertwo_tv);
		schoolLayout=(RelativeLayout) findViewById(R.id.registertwo_center3_rl);
		schoolLayout.setOnClickListener(this);
		isStudentImv = (ImageView) findViewById(R.id.in_school_imv);
		graduateImv = (ImageView) findViewById(R.id.out_school_imv);
		isStudentLinear = (LinearLayout) findViewById(R.id.is_student_linear);
		isStudentLinear.setOnClickListener(this);
		graduateLinear = (LinearLayout) findViewById(R.id.graduate_linear);
		graduateLinear.setOnClickListener(this);
		userheadImv = (ImageView) findViewById(R.id.userhead_imv);
		userheadImv.setImageBitmap(userheadBitmp);
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);
		
		
	}
	private PopupWindow popWindowProfession;
	private void initPopupWindowProfession() {
		if (popWindowProfession == null) {
			View view = LayoutInflater.from(this).inflate(R.layout.professtion_popwindow,
					null);
			popWindowProfession = new PopupWindow(view,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			// 设置外观
			popWindowProfession.setFocusable(true);
			popWindowProfession.setOutsideTouchable(true);
			ColorDrawable colorDrawable = new ColorDrawable();
			popWindowProfession.setBackgroundDrawable(colorDrawable);
			// tvTitle=(TextView)view.findViewById(R.id.tvcolectList);

			mViewProfesstion = (WheelView) view.findViewById(R.id.id_professtion_wheelView);
			mViewProfesstion.addChangingListener(RegisterTwoActivity.this);
			TextView complete=(TextView) view.findViewById(R.id.professtion_selector_tv);

			complete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					popWindowProfession.dismiss();
				}
			});
			setUpDataProfesstion();
		}

	}

	private void setUpDataProfesstion() {
		// TODO Auto-generated method stub
		mViewProfesstion.setViewAdapter(new ArrayWheelAdapter<String>(
				RegisterTwoActivity.this, mIdentity));

		//	Log.e("lucifer", "mProvinceDatas.length==" + mProvinceDatas.length);
		// 设置可见条目数量
		mViewProfesstion.setVisibleItems(7);
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
		String[] citys = new String[cityList.size()];

		for (int i = 0; i < cityList.size(); i++) {
			citys[i] = cityList.get(i).getCity();
		}
		mCurrentCityName = cityList.get(pCurrent).getCity();
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
	/**
	 * 改变下一步的背景
	 */
	private void changeNextBg(){
		if(schoolTv.getText().length()>0&&cityTextView.getText().length()>0){
			nextLayout.setBackgroundResource(R.drawable.register_btn_normal);		
		}else{
			nextLayout.setBackgroundResource(R.drawable.register_btn_clk);	
		}

	}
	private void nextStep() {
		// TODO Auto-generated method stub
		if(null == schoolTv.getText() || schoolTv.getText().toString().equals("")){
			if(isStudent){
				toastShort(getResources().getString(R.string.reg_two_no_school));
			}else{
				toastShort(getResources().getString(R.string.reg_two_no_job));
			}
			return;
		}
		if(null == cityTextView.getText() || cityTextView.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_two_no_city));
			return;
		}
		if(isStudent){
			userBean.setIsStudent(Constants.IS_STUDENT_FLAG);
			userBean.setSchool(schoolTv.getText().toString());
			userBean.setJob("");
		}else{
			userBean.setIsStudent(Constants.HAS_GRADUATE);
			userBean.setJob(schoolTv.getText().toString());
			userBean.setSchool("");
		}
		userBean.setCity(cityTextView.getText().toString());
		Intent nextIntent=new Intent(this,RegisterThreeActivity.class);
		nextIntent.putExtra("infoBean", userBean);
		nextIntent.putExtra("userhead", userheadBitmp);
		nextIntent.putExtra("headPath", headPath);
		startActivity(nextIntent);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_rl:
			finish();
			break;
		case R.id.registertwo_center4_rl:
			//选择城市
			cityTextView.setText("北京 东城区");
			initPopupWindowCity();
			popupWindowCity.showAsDropDown(cityTextView, 0, 200);
			break;
		case R.id.city_selector_shengshiqu_tv:
			popupWindowCity.dismiss();
			break;
		case R.id.registertwo_center3_rl:
			if(isStudent){
				Intent intent=new Intent(this,ChangeSchoolActivity.class);
				startActivityForResult(intent, SELECT_SCHOOL);
			}else{
				//选择职业
				initPopupWindowProfession();
				popWindowProfession.showAsDropDown(cityTextView, 0, 200);
			}
			break;
		case R.id.next_registertwo_rl:
			nextStep();
			break;
		case R.id.is_student_linear:
			if(!isStudent){
				isStudent = true;
				schoolTv.setText("");
				schoolTv.setHint(getResources().getString(R.string.register_two_selecter_school));
				isStudentImv.setImageResource(R.drawable.register_shenfen_imageview_btn_light);
				graduateImv.setImageResource(R.drawable.register_shenfen_imageview_normal);
			}
			break;
		case R.id.graduate_linear:
			if(isStudent){
				isStudent = false;
				schoolTv.setText("");
				schoolTv.setHint(getResources().getString(R.string.register_two_selecter_job));
				isStudentImv.setImageResource(R.drawable.register_shenfen_imageview_normal);
				graduateImv.setImageResource(R.drawable.register_shenfen_imageview_btn_light);
			}
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
			cityTextView.setText("" + mCurrentProviceName +" "+ mCurrentCityName
					);
			changeNextBg();
		} else if (wheel == mViewCity) {
			updateAreas();
			cityTextView.setText("" + mCurrentProviceName +" "+ mCurrentCityName
					);
			changeNextBg();
		} else if (wheel == mViewDistrict) {
			int pCurrent = mViewDistrict.getCurrentItem();
			List<Citybean> townList = new ArrayList<Citybean>();
			townList = cityDao
					.getAllTown(mCurrentProviceName, mCurrentCityName);
			String[] towns = new String[townList.size()];
			for (int i = 0; i < townList.size(); i++) {
				towns[i] = townList.get(i).getTown();
			}
			mCurrentDistrictName = towns[pCurrent];
			cityTextView.setText("" + mCurrentProviceName+" " + mCurrentCityName
					);
			changeNextBg();
		}else if(wheel == mViewProfesstion){

			
			int pCurrent = mViewProfesstion.getCurrentItem();
			
			schoolTv.setText(mIdentity[pCurrent]);
		} 

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECT_SCHOOL:
			String schoolName = data.getStringExtra("school");
			schoolTv.setText(schoolName);
			changeNextBg();
			break;

		default:
			break;
		}
	}
}
