package com.android.biubiu.activity.mine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.bean.Citybean;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;
import com.android.biubiu.utils.HttpContants;
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

public class ChangeConstellationActivity extends BaseActivity implements
		OnWheelChangedListener {
	private WheelView mViewConstellation;
	private RelativeLayout backLayout,completeLayout;
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
	UserInfoBean infoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_constellation);
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
		initView();
		setUpListener();
		setUpData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mViewConstellation = (WheelView) findViewById(R.id.id_constellation);
		constellation=(TextView) findViewById(R.id.constellationName_change_city_tv);
		constellation.setText(infoBean.getStar());
		backLayout=(RelativeLayout) findViewById(R.id.back_changexingzuo_mine_rl);
		completeLayout=(RelativeLayout) findViewById(R.id.mine_changexingzuo_wancheng_rl);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		completeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(null == constellation.getText() || constellation.getText().toString().equals("")){
					return;
				}
				infoBean.setStar(constellation.getText().toString());
				updateInfo();
			}
		});

	}

	protected void updateInfo() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_USETINFO);
		String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", token);
			requestObject.put("device_code", deviceId);
			requestObject.put("starsign",infoBean.getStar());
			requestObject.put("parameters", "starsign");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
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
				LogUtil.d("mytest", "star=="+result);
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
	
		mViewConstellation.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeConstellationActivity.this, mProvinceDatas));

	//	Log.e("lucifer", "mProvinceDatas.length==" + mProvinceDatas.length);
		// 设置可见条目数量
		mViewConstellation.setVisibleItems(7);

	}

	


}
