package com.android.biubiu.activity.mine;



import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.activity.RegisterOneActivity;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.MyDatePicker;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;
import com.android.biubiu.utils.CaculateDateUtils;
import com.android.biubiu.utils.DateUtils;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.okhttp.Callback;
import com.avos.avoscloud.okhttp.Request;
import com.avos.avoscloud.okhttp.Response;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeBrithdayActivity extends BaseActivity implements OnClickListener,OnWheelChangedListener{
	// 控件相关
	private TextView birthday;
	private TextView queding;
	private String birth;
	private ImageView backImageView;
	private RelativeLayout backlLayout, quedingLayout;
	private long birthLong;
	private UserInfoBean infoBean;
	private WheelView mViewYear;
	private WheelView mViewMonth;
	private WheelView mViewDay;
	private String[] years,months,days;
	String currentYear="",currentMonth="",currentDay="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_brithday);
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
		birth = infoBean.getBirthday();
		intiView();
		setYearData();
	}
	private void intiView() {
		birthday = (TextView) super.findViewById(R.id.name_changbirth_et);
		birthday.setText(birth);
		birthday.setOnClickListener(this);
		queding = (TextView) super
				.findViewById(R.id.mine_changebirth_wancheng_bt);
		// queding.setOnClickListener(this);
		backImageView = (ImageView) super
				.findViewById(R.id.back_changebirth_mine);
		// backImageView.setOnClickListener(this);
		backlLayout = (RelativeLayout) super
				.findViewById(R.id.back_changebirth_mine_rl);
		backlLayout.setOnClickListener(this);
		quedingLayout = (RelativeLayout) super
				.findViewById(R.id.mine_changebirth_wancheng_rl);
		quedingLayout.setOnClickListener(this);
		mViewYear = (WheelView) findViewById(R.id.id_year);
		mViewMonth = (WheelView) findViewById(R.id.id_month);
		mViewDay = (WheelView) findViewById(R.id.id_day);
		// 添加change事件
		mViewYear.addChangingListener(ChangeBrithdayActivity.this);
		// 添加change事件
		mViewMonth.addChangingListener(ChangeBrithdayActivity.this);
		// 添加change事件
		mViewDay.addChangingListener(ChangeBrithdayActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 点击 完成 上传时间
		case R.id.mine_changebirth_wancheng_rl:
			// 转成时间戳
			birthLong = DateUtils.getStringToDate(birthday.getText()
					.toString());
			if(birthLong>System.currentTimeMillis()){
				toastShort("请选择有效日期");	
				return;
			}
			if(null == birthday.getText() || birthday.getText().toString().equals("")){
				toastShort(getResources().getString(R.string.reg_one_no_birth));
				return;
			}
			infoBean.setBirthday(birthday.getText().toString());
			updateInfo();
			break;
			// 返回
		case R.id.back_changebirth_mine_rl:
			finish();
			break;
		default:
			break;
		}

	}
	private void setYearData() {
		// TODO Auto-generated method stub
		years = CaculateDateUtils.getInstance().getYearSs(1975, 2100);
		mViewYear.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeBrithdayActivity.this, years));
		// 设置可见条目数量
		mViewYear.setVisibleItems(7);
		mViewMonth.setVisibleItems(7);
		mViewDay.setVisibleItems(7);
		if(currentYear.equals("")){
			currentYear = years[0];
		}
		updateMonthData();
	}

	private void updateMonthData() {
		// TODO Auto-generated method stub
		months = CaculateDateUtils.getInstance().getMonthSs();
		mViewMonth.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeBrithdayActivity.this, months));
		if(currentMonth.equals("")){
			mViewMonth.setCurrentItem(0);
			currentMonth = months[0];
		}
		updateDayData();
	}
	private void updateDayData() {
		// TODO Auto-generated method stub
		int currentY = Integer.parseInt(currentYear.replace("年", ""));
		int currentM = Integer.parseInt(currentMonth.replace("月", ""));
		days = CaculateDateUtils.getInstance().getDaySs(currentY, currentM);
		mViewDay.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeBrithdayActivity.this, days));
		if(currentDay.equals("")){
			mViewDay.setCurrentItem(0);
			currentDay = days[0];
		}
	}
	private void setBirthData(String yearStr,String monthStr,String dayStr) {
		int year=Integer.parseInt(yearStr.replace("年", ""));
		int month=Integer.parseInt(monthStr.replace("月", ""));
		int day=Integer.parseInt(dayStr.replace("日", ""));
		String dateStr = year + "-" + month + "-" + day;	
		birthday.setText(dateStr);
	}
	private void updateInfo() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_USETINFO);
		String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", token);
			requestObject.put("device_code", deviceId);
			requestObject.put("birth_date",infoBean.getBirthday());
			requestObject.put("parameters", "birth_date");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data", requestObject.toString());
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
				LogUtil.d("mytest", "birthday=="+result);
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort("修改信息失败");
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
					//					String token = data.getString("token");
					//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
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
	/**
	 * 设置点击返回键的状态
	 */
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//		Intent intent = new Intent();
		//		intent.putExtra("birthday", birth);
		//		ChangeBirthdayActivity.this.setResult(RESULT_CANCELED, intent);
		finish();
	}
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if(wheel == mViewYear){
			int index = mViewYear.getCurrentItem();
			currentYear = years[index];
			updateMonthData();
			setBirthData(currentYear, currentMonth, currentDay);
		}else if(wheel == mViewMonth){
			int index = mViewMonth.getCurrentItem();
			currentMonth = months[index];
			updateDayData();
			setBirthData(currentYear, currentMonth, currentDay);
		}else if(wheel == mViewDay){
			int index = mViewDay.getCurrentItem();
			currentDay = days[index];
			setBirthData(currentYear, currentMonth, currentDay);
		}
	}



}
