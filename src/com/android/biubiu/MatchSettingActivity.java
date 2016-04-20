package com.android.biubiu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.activity.mine.PersonalityTagActivity;
import com.android.biubiu.adapter.UserPagerTagAdapter;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.SettingBean;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.chat.MyHintDialog;
import com.android.biubiu.chat.MyHintDialog.OnDialogClick;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.android.biubiu.view.MyGridView;
import com.android.biubiu.view.RangeSeekBar;
import com.android.biubiu.view.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.avos.avoscloud.LogUtil.log;
import com.baidu.android.pushservice.PushManager;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MatchSettingActivity extends BaseActivity implements OnClickListener{
	private static final int PERSONAL_TAG = 1001;
	private static final int PERSONAL_TAG_MY=1002;
	private RelativeLayout backRl;
	private ImageView boyToggle;
	private ImageView girlToggle;
	private ImageView cityToggle;
	private ImageView unLimitToggle;
	private TextView ageMinTv;
	private TextView ageMaxTv;
	private RelativeLayout personalTagRl;
	private MyGridView tagGv;
	private ImageView newMsgToggle;
	private ImageView voiceToggle;
	private ImageView shockToggle;
	private RelativeLayout logoutRl;
	private LinearLayout seekLinear;
	private UserPagerTagAdapter setTagAdapter;
	private RelativeLayout boyLayout;
	private RelativeLayout girlLayout;
	private RelativeLayout cityLayout;
	private RelativeLayout unLimitLayout;
	private RelativeLayout msgLayout;
	private RelativeLayout voiceLayout;
	private RelativeLayout shockLayout;
	RangeSeekBar<Integer> seekBar;

	private boolean isSelBoy = true;
	private boolean isSameCity = true;
	private boolean isRecvMsg = true;
	private boolean isOpenVoice = true;
	private boolean isOpenShck = true;
	SettingBean setBean;
	
	/**
	 * 用来标识我自己有没有填 个性标签
	 */
	private boolean isCheckMyTags=false;
	
	private String sexMy;

	private String TAG ="MatchSettingActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_setting);
		initView();
		initlodo();
	}
	private void initView() {
		// TODO Auto-generated method stub
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);
		boyToggle = (ImageView) findViewById(R.id.boy_toggle);
		girlToggle = (ImageView) findViewById(R.id.girl_toggle);
		cityToggle = (ImageView) findViewById(R.id.city_toggle);
		unLimitToggle = (ImageView) findViewById(R.id.unlimit_toggle);
		ageMinTv = (TextView) findViewById(R.id.age_min_tv);
		ageMaxTv = (TextView) findViewById(R.id.age_max_tv);
		personalTagRl = (RelativeLayout) findViewById(R.id.personal_rl);
		personalTagRl.setOnClickListener(this);
		tagGv = (MyGridView) findViewById(R.id.interest_tag_gv);
		newMsgToggle = (ImageView) findViewById(R.id.newmsg_toggle);
		voiceToggle = (ImageView) findViewById(R.id.voice_toggle);
		shockToggle = (ImageView) findViewById(R.id.shock_toggle);
		logoutRl = (RelativeLayout) findViewById(R.id.logout_rl);
		logoutRl.setOnClickListener(this);
		seekLinear = (LinearLayout) findViewById(R.id.seek_linear);
		boyLayout = (RelativeLayout) findViewById(R.id.boy_layout);
		boyLayout.setOnClickListener(this);
		girlLayout = (RelativeLayout) findViewById(R.id.girl_layout);
		girlLayout.setOnClickListener(this);
		cityLayout = (RelativeLayout) findViewById(R.id.city_layout);
		cityLayout.setOnClickListener(this);
		unLimitLayout = (RelativeLayout) findViewById(R.id.unlimit_layout);
		unLimitLayout.setOnClickListener(this);
		msgLayout = (RelativeLayout) findViewById(R.id.msg_layout);
		msgLayout.setOnClickListener(this);
		voiceLayout = (RelativeLayout) findViewById(R.id.voice_layout);
		voiceLayout.setOnClickListener(this);
		shockLayout = (RelativeLayout) findViewById(R.id.shock_layout);
		shockLayout.setOnClickListener(this);

		seekBar = new RangeSeekBar<Integer>(16, 40, this);

		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Integer minValue, Integer maxValue) {
				ageMinTv.setText(minValue+"");
				ageMaxTv.setText(maxValue+"");
			}
		});       
		seekBar.setNotifyWhileDragging(true);
		seekLinear.addView(seekBar);

	}
	private void setTags(ArrayList<PersonalTagBean> tags){
		setTagAdapter = new UserPagerTagAdapter(getApplicationContext(), tags);
		tagGv.setAdapter(setTagAdapter);
	}
	private void setRangeAge() {
		if(setBean.getAgeDown() == 0 ){
			setBean.setAgeDown(16);
		}
		if(setBean.getAgeUp() == 0){
			setBean.setAgeUp(40);
		}
		ageMinTv.setText(""+setBean.getAgeDown());
		ageMaxTv.setText(""+setBean.getAgeUp());
		seekBar.setSelectedMaxValue(setBean.getAgeUp());
		seekBar.setSelectedMinValue(setBean.getAgeDown());
	}
	protected void setToggle() {
		// 1--选择男生 2--选择女生
		if(setBean.getSex().equals(Constants.SEX_MALE)){
			isSelBoy = true;
			boyToggle.setImageResource(R.drawable.setting_btn_no);
			girlToggle.setImageResource(R.drawable.setting_btn_yes);
		}else{
			isSelBoy = false;
			boyToggle.setImageResource(R.drawable.setting_btn_yes);;
			girlToggle.setImageResource(R.drawable.setting_btn_no);;
		}
		//1--同城 2--不限
		if(setBean.getCity().equals(Constants.SAME_CITY)){
			isSameCity = true;
			cityToggle.setImageResource(R.drawable.setting_btn_no);
			unLimitToggle.setImageResource(R.drawable.setting_btn_yes);
		}else{
			isSameCity = false;
			cityToggle.setImageResource(R.drawable.setting_btn_yes);
			unLimitToggle.setImageResource(R.drawable.setting_btn_no);
		}
		//声音 0--关闭 1--打开
		if(setBean.getSound() == 0){
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_OPEN_VOICE, false);
			log.d("SharePreferanceUtils"+SharePreferanceUtils.getInstance().isOpenVoice(getApplicationContext(), SharePreferanceUtils.IS_OPEN_VOICE, true));
			isOpenVoice = false;
			voiceToggle.setImageResource(R.drawable.setting_btn_yes);
		}else{
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_OPEN_VOICE, true);
			log.d("SharePreferanceUtils"+SharePreferanceUtils.getInstance().isOpenVoice(getApplicationContext(), SharePreferanceUtils.IS_OPEN_VOICE, false));
			isOpenVoice = true;
			voiceToggle.setImageResource(R.drawable.setting_btn_no);
		}
		//振动 0--关闭 1--打开
		if(setBean.getVibration() == 0){
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_SHOCK, false);
			isOpenShck = false;
			shockToggle.setImageResource(R.drawable.setting_btn_yes);
		}else{
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_SHOCK, true);
			isOpenShck = true;
			shockToggle.setImageResource(R.drawable.setting_btn_no);
		}
		//接收消息 0--关闭，不接收  1--打开，接收
		if(setBean.getMessage() == 0){
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_SHOCK, false);
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_OPEN_VOICE, false);
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_RECEIVE_MSG, false);
			isRecvMsg = false;
			newMsgToggle.setImageResource(R.drawable.setting_btn_yes);
			isOpenVoice = false;
			voiceToggle.setImageResource(R.drawable.setting_btn_yes);
			isOpenShck = false;
			shockToggle.setImageResource(R.drawable.setting_btn_yes);
		}else{
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_RECEIVE_MSG, true);
			isRecvMsg = true;
			newMsgToggle.setImageResource(R.drawable.setting_btn_no);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.boy_layout:
			if(isSelBoy){
				setBean.getPersonalTags().clear();
				setTags(setBean.getPersonalTags());
				isSelBoy = false;
				boyToggle.setImageResource(R.drawable.setting_btn_yes);
				girlToggle.setImageResource(R.drawable.setting_btn_no);
			}else{
				setBean.getPersonalTags().clear();
				setTags(setBean.getPersonalTags());
				isSelBoy = true;
				boyToggle.setImageResource(R.drawable.setting_btn_no);
				girlToggle.setImageResource(R.drawable.setting_btn_yes);
			}
			break;
		case R.id.girl_layout:
			if(isSelBoy){
				setBean.getPersonalTags().clear();
				setTags(setBean.getPersonalTags());
				isSelBoy = false;
				boyToggle.setImageResource(R.drawable.setting_btn_yes);
				girlToggle.setImageResource(R.drawable.setting_btn_no);
			}else{
				setBean.getPersonalTags().clear();
				setTags(setBean.getPersonalTags());
				isSelBoy = true;
				boyToggle.setImageResource(R.drawable.setting_btn_no);
				girlToggle.setImageResource(R.drawable.setting_btn_yes);
			}
			break;
		case R.id.city_layout:
			if(isSameCity){
				isSameCity = false;
				cityToggle.setImageResource(R.drawable.setting_btn_yes);
				unLimitToggle.setImageResource(R.drawable.setting_btn_no);
			}else{
				isSameCity = true;
				cityToggle.setImageResource(R.drawable.setting_btn_no);
				unLimitToggle.setImageResource(R.drawable.setting_btn_yes);
			}
			break;
		case R.id.unlimit_layout:
			if(isSameCity){
				isSameCity = false;
				cityToggle.setImageResource(R.drawable.setting_btn_yes);
				unLimitToggle.setImageResource(R.drawable.setting_btn_no);
			}else{
				isSameCity = true;
				cityToggle.setImageResource(R.drawable.setting_btn_no);
				unLimitToggle.setImageResource(R.drawable.setting_btn_yes);
			}
			break;
		case R.id.msg_layout:
			if(isRecvMsg){
				isRecvMsg = false;
				newMsgToggle.setImageResource(R.drawable.setting_btn_yes);
				isOpenVoice = false;
				voiceToggle.setImageResource(R.drawable.setting_btn_yes);
				isOpenShck = false;
				shockToggle.setImageResource(R.drawable.setting_btn_yes);
			}else{
				isRecvMsg = true;
				newMsgToggle.setImageResource(R.drawable.setting_btn_no);
			}
			break;
		case R.id.voice_layout:
			if(isOpenVoice){
				isOpenVoice = false;
				voiceToggle.setImageResource(R.drawable.setting_btn_yes);
			}else{
				isRecvMsg = true;
				newMsgToggle.setImageResource(R.drawable.setting_btn_no);
				isOpenVoice = true;
				voiceToggle.setImageResource(R.drawable.setting_btn_no);
			}
			break;
		case R.id.shock_layout:
			if(isOpenShck){
				isOpenShck = false;
				shockToggle.setImageResource(R.drawable.setting_btn_yes);
			}else{
				isRecvMsg = true;
				newMsgToggle.setImageResource(R.drawable.setting_btn_no);
				isOpenShck = true;
				shockToggle.setImageResource(R.drawable.setting_btn_no);
			}
			break;
		case R.id.back_rl:
			saveSetInfo();
			break;
		case R.id.personal_rl:
			//TODO 判断自己填没填
			
			if(isCheckMyTags==false){

				MyHintDialog.getDialog(this, "完善个性标签", "想让iU的恋爱公式发生作用么  要先完善你自己的个性标签哦", "完善个性标签", new OnDialogClick() {
					
					@Override
					public void onOK() {
						// TODO Auto-generated method stub
						UserInfoBean infoBean=new UserInfoBean();
						infoBean.setSex(setBean.getSex2());
						Intent intent = new Intent(MatchSettingActivity.this,PersonalityTagActivity.class);
						intent.putExtra("userInfoBean", (Serializable)infoBean);				
						startActivityForResult(intent, PERSONAL_TAG_MY);
					}
					
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						
					}
				});
			}else{
				Intent intent = new Intent(MatchSettingActivity.this,MatchSetTagActivity.class);
				intent.putExtra("personalTags", (Serializable)setBean.getPersonalTags());
				intent.putExtra("sex", isSelBoy);
				startActivityForResult(intent, PERSONAL_TAG);
			}
	
			break;
		case R.id.logout_rl:
			//退出
			exitApp();
			break;
		default:
			break;
		}
	}
	/**
	 * 加载数据
	 */
	public void initlodo(){
		showLoadingLayout(getResources().getString(R.string.loading));
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			dismissLoadingLayout();
			showErrorLayout(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismissErrorLayout();
					initlodo();
				}
			});
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GET_SETTING);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
		} catch (JSONException e) {

			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				showErrorLayout(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismissErrorLayout();
						initlodo();
					}
				});
				LogUtil.d(TAG, ""+arg0.getMessage());
				Toast.makeText(x.app(), arg0.getMessage(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				dismissLoadingLayout();
				dismissErrorLayout();
				LogUtil.d("mytest", "set--"+arg0);
				JSONObject jsons;
				try {
					jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					if(!code.equals("200")){
						if(code.equals("303")){
							toastShort("登录过期，请重新登录");
							exitHuanxin();
							return;
						}
//						showErrorLayout(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								dismissErrorLayout();
//								initlodo();
//							}
//						});
//						toastShort("获取设置信息失败");
						return;
					}
					JSONObject data = jsons.getJSONObject("data");
//					String token = data.getString("token");
//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					Gson gson=new Gson();
					SettingBean settingBean=gson.fromJson(data.toString(), SettingBean.class);
					if(settingBean == null){
						return;
					}
					setBean = settingBean;
					if(setBean.getPersonalityTags()>0){
						isCheckMyTags=true;
						
					}else{
						isCheckMyTags = false;
					}
					sexMy = setBean.getSex2();
					ArrayList<PersonalTagBean> list = new ArrayList<PersonalTagBean>();
					list.addAll(settingBean.getPersonalTags());
					setTags(list);
					setToggle();
					setRangeAge();
				} catch (JSONException e) {
					
					e.printStackTrace();
				}

			}
		});

	}
	/**
	 * 保存设置信息
	 */
	private void saveSetInfo() {
		if(setBean==null){
			finish();
			return;
		}
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			finish();
			return;
		}
		updateSetBean();
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_SETTING);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("sex", setBean.getSex());
			requestObject.put("city", setBean.getCity());
			requestObject.put("age_down", setBean.getAgeDown());
			requestObject.put("age_up", setBean.getAgeUp());
			ArrayList<PersonalTagBean> tags = setBean.getPersonalTags();
			StringBuffer perStr = new StringBuffer();
			if(null != tags && tags.size()>0){
				for(int i=0;i<tags.size();i++){
					if(i == tags.size()-1){
						perStr.append(tags.get(i).getCode());
						break;
					}
					perStr.append(tags.get(i).getCode()+",");
				}
			}
			requestObject.put("personalized_tags",perStr.toString());
			requestObject.put("message", setBean.getMessage());
			requestObject.put("sound", setBean.getSound());
			requestObject.put("vibration", setBean.getVibration());
			String paramStr = "sex,city,age_down,age_up,personalized_tags,message,sound,vibration";
			requestObject.put("parameters",paramStr);
		} catch (JSONException e) {

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
				toastShort("保存失败");
				LogUtil.d("mytest", ex.getMessage()+"");
				LogUtil.d("mytest", ex.getCause()+"");
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "updateset--"+arg0);
				try {
					JSONObject jsons = new JSONObject(arg0);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort("保存失败");
						return;
					}
					
					if(setBean.getSound() == 0){
						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_OPEN_VOICE, false);
						
					}else{
						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_OPEN_VOICE, true);
						
					}
					//振动 0--关闭 1--打开
					if(setBean.getVibration() == 0){
						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_SHOCK, false);
						
					}else{
						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_SHOCK, true);
											}
					//新消息通知 0--关闭 1--打开
					if(setBean.getMessage() == 0){
						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_RECEIVE_MSG, false);
						
					}else{
						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_RECEIVE_MSG, true);
											}
//					String token = (jsons.getJSONObject("data").getString("token"));
//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		finish();
	}
	private void updateSetBean() {
		// TODO Auto-generated method stub
		if(null == setBean){
			finish();
			return;
		}
		int minAge = 0;
		int maxAge = 40;
		if(ageMinTv.getText().toString().equals("")){
			minAge = 16;
		}else{
			minAge = Integer.parseInt(ageMinTv.getText().toString());
		}
		if(ageMaxTv.getText().toString().equals("")){
			maxAge = 40;
		}else{
			maxAge = Integer.parseInt(ageMaxTv.getText().toString());
		}
		setBean.setAgeDown(minAge);
		setBean.setAgeUp(maxAge);
		if(isSelBoy){
			setBean.setSex(Constants.SEX_MALE);
		}else{
			setBean.setSex(Constants.SEX_FAMALE);
		}
		if(isSameCity){
			setBean.setCity(Constants.SAME_CITY);
		}else{
			setBean.setCity(Constants.UN_LIMIT);
		}
		if(isOpenVoice){
			setBean.setSound(1);
		}else{
			setBean.setSound(0);
		}
		if(isRecvMsg){
			setBean.setMessage(1);
		}else{
			setBean.setMessage(0);
		}
		if(isOpenShck){
			setBean.setVibration(1);
		}else{
			setBean.setVibration(0);
		}
	}
	/**
	 * 退出登录
	 */
	private void exitApp() {
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		RequestParams params = new RequestParams(""+HttpContants.HTTP_ADDRESS+HttpContants.EXIT);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().getDeviceId(this, SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("user_code", SharePreferanceUtils.getInstance().getUserCode(getApplicationContext(), SharePreferanceUtils.USER_CODE, ""));
		} catch (JSONException e) {

			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(x.app(), arg0.getMessage(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				PushManager.stopWork(getApplicationContext());
				JSONObject jsons;
				try {
					jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
						String error=jsons.getString("error");
						toastShort(error);
						return;
					}
//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
					exitHuanxin();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}
	/**
	 * 退出环信登录
	 */
	public void exitHuanxin(){
		EMClient.getInstance().logout(true ,new EMCallBack() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, "环信退出成功");
				//清空本地token
				SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
				SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.HX_USER_NAME, "");
				SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.HX_USER_PASSWORD, "");
				SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_NAME, "");
				SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_HEAD, "");
				SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_CODE, "");
				LogUtil.d("mytest", "tok---"+SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
				finish();
			}

			@Override
			public void onProgress(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, "环信退出失败"+arg1);
			}
		});
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case PERSONAL_TAG:
			if(resultCode != RESULT_OK){
				return;
			}
			ArrayList<PersonalTagBean> list = (ArrayList<PersonalTagBean>) data.getSerializableExtra("personalTags");
			setTags(list);
			setBean.setPersonalTags(list);
			break;
		case PERSONAL_TAG_MY:
			
			if(resultCode==RESULT_OK){
				isCheckMyTags=true;
				LogUtil.d(TAG, "完成了设置个性标签");
			}
			
			break;

		default:
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			saveSetInfo();
		}
		return super.onKeyDown(keyCode, event);
	}
}
