package com.android.biubiu.activity;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;







import cc.imeetu.iu.R;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.biubiu.MainActivity;


import com.android.biubiu.activity.biu.BiuBiuReceiveActivity;
import com.android.biubiu.activity.mine.ChangeIdentityProfessionActivity;
import com.android.biubiu.activity.mine.ChangeSchoolActivity;
import com.android.biubiu.bean.Citybean;
import com.android.biubiu.bean.Schools;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.Umutils;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.BaseCityActivity;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.OnWheelChangedListener2;
import com.android.biubiu.common.city.WheelView2;


import com.android.biubiu.sqlite.CityDao;


import com.android.biubiu.utils.CaculateDateUtils;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.umeng.analytics.MobclickAgent;

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

public class RegisterTwoActivity extends BaseCityActivity implements OnClickListener,OnWheelChangedListener2{
	private static final int SELECT_SCHOOL = 1001;
	private String TAG="RegisterTwoActivity";
	private RelativeLayout nextLayout;
	private RelativeLayout cityLayout,schoolLayout;
	private WheelView2	mViewProvince,mViewCity,mViewDistrict,mViewProfesstion;
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
	String phoneNum = "";
	String password = "";
	String deviceId = "";
	private String schoolCode="";
	private String cityiId="";//默认的北京市东城区id
	private String cityCode="";
	//上传文件相关
	String accessKeyId = "";
	String accessKeySecret = "";
	String securityToken = "";
	String expiration = "";
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
		deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		getinentInfo();
		initView();
	}
	private void getinentInfo() {
		// TODO Auto-generated method stub
		UserInfoBean bean = (UserInfoBean) getIntent().getSerializableExtra("infoBean");
		Bitmap bitmp = getIntent().getParcelableExtra("userhead");
		headPath = getIntent().getStringExtra("headPath");
		phoneNum = getIntent().getStringExtra("phone");
		password = getIntent().getStringExtra("password");
		userBean.setNickname(bean.getNickname());
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

			mViewProfesstion = (WheelView2) view.findViewById(R.id.id_professtion_wheelView);
			mViewProfesstion.addChangingListener(RegisterTwoActivity.this);
			TextView complete=(TextView) view.findViewById(R.id.professtion_selector_tv);

			complete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					changeNextBg();
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

			mViewProvince = (WheelView2) view.findViewById(R.id.id_province);
			mViewCity = (WheelView2) view.findViewById(R.id.id_city);
			mViewDistrict = (WheelView2) view.findViewById(R.id.id_district);
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
		cityTextView.setText("" + mCurrentProviceName +" "+ mCurrentCityName);
		changeNextBg();
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

			nextLayout.setBackgroundResource(R.drawable.register_btn_disabled);	

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
			LogUtil.d("mytest", "school"+schoolCode);
			userBean.setSchool(schoolCode);
			userBean.setCareer("");
		}else{
			userBean.setIsStudent(Constants.HAS_GRADUATE);
			userBean.setCareer(schoolTv.getText().toString());
			userBean.setSchool("");
		}
		try {
			Umutils.count(RegisterTwoActivity.this, Umutils.REGISTER_BEFORE);
			String cityiId=cityDao.getID(mCurrentProviceName, mCurrentCityName).get(0).getId();
			cityCode=cityDao.getID(mCurrentProviceName, mCurrentCityName).get(0).getCity_num();
			LogUtil.d("mytest", cityiId);
			userBean.setCity(cityiId);
			LogUtil.d("mytest", "city1--"+userBean.getCity());
			/*Intent nextIntent=new Intent(this,RegisterThreeActivity.class);
			nextIntent.putExtra("infoBean", (Serializable)userBean);
			nextIntent.putExtra("userhead", userheadBitmp);
			nextIntent.putExtra("headPath", headPath);
			nextIntent.putExtra("cityf", cityCode);
			startActivity(nextIntent);*/
			getOssToken();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_rl:
			finish();
			break;
		case R.id.registertwo_center4_rl:
			//选择城市

			changeNextBg();
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
	public void onChanged(WheelView2 wheel, int oldValue, int newValue) {
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
			if(resultCode == RESULT_OK){
				Schools school = (Schools) data.getSerializableExtra("school");
				schoolTv.setText(school.getUnivsNameString());
				schoolCode = school.getUnivsId();
				changeNextBg();
			}
			break;

		default:
			break;
		}
	}
	//鉴权
	public void getOssToken(){
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		showLoadingLayout(getResources().getString(R.string.register));
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REGISTER_OSS);
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				toastShort("注册失败");
				LogUtil.d("mytest", "error--"+ex.getMessage());
				LogUtil.d("mytest", "error--"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "ret=="+arg0);
				try {
					JSONObject jsonObjs = new JSONObject(arg0);
					String  state = jsonObjs.getString("state");
					if(!state.equals("200")){
						dismissLoadingLayout();
						toastShort("注册失败");
						return;
					}
					JSONObject obj = jsonObjs.getJSONObject("data");
					//JSONObject obj = new JSONObject(jsonObjs.getString("data"));
					accessKeyId = obj.getString("accessKeyId");
					accessKeySecret = obj.getString("accessKeySecret");
					securityToken = obj.getString("securityToken");
					expiration = obj.getString("expiration");
					asyncPutObjectFromLocalFile();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	// 从本地文件上传，使用非阻塞的异步接口
	public void asyncPutObjectFromLocalFile() {
		String endpoint = HttpContants.A_LI_YUN;
		//OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("XWp6VLND94vZ8WNJ", "DSi9RRCv4bCmJQZOOlnEqCefW4l1eP");
		OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {
			@Override
			public OSSFederationToken getFederationToken() {

				return new OSSFederationToken(accessKeyId, accessKeySecret, securityToken, expiration);
			}
		};
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		OSSLog.enableLog();
		OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
		final String fileName = "profile/"+System.currentTimeMillis()+deviceId+".jpeg";
		// 构造上传请求
		PutObjectRequest put = new PutObjectRequest("protect-app",fileName, headPath);

		// 异步上传时可以设置进度回调
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
			}
		});
		OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				Log.d("PutObject", "UploadSuccess");
				Log.d("ETag", result.getETag());
				Log.d("RequestId", result.getRequestId());
				userBean.setIconOrign(fileName);
				registerRequest();
			}
			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				dismissLoadingLayout();
				toastShort("注册失败");
				// 请求异常
				if (clientExcepion != null) {
					// 本地异常如网络异常等
					clientExcepion.printStackTrace();
				}
				if (serviceException != null) {
					// 服务异常
					Log.e("ErrorCode", serviceException.getErrorCode());
					Log.e("RequestId", serviceException.getRequestId());
					Log.e("HostId", serviceException.getHostId());
					Log.e("RawMessage", serviceException.getRawMessage());
				}
			}
		});
	}
	//注册
	private void registerRequest() {
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REGISTER_METHOD);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("nickname",userBean.getNickname());
			requestObject.put("sex", userBean.getSex());
			requestObject.put("birth_date", userBean.getBirthday());
			requestObject.put("isgraduated", userBean.getIsStudent());
			requestObject.put("school", userBean.getSchool());
			requestObject.put("city", userBean.getCity());
			requestObject.put("career", userBean.getCareer());
			requestObject.put("phone", phoneNum);
			requestObject.put("device_name", "");
			requestObject.put("device_code", deviceId);
			requestObject.put("password", password);
			requestObject.put("icon_url", userBean.getIconOrign());
			requestObject.put("original_icon_url", userBean.getIconOrign());
			requestObject.put("cityf", cityCode);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogUtil.d("mytest", userBean.getCity()+",");
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				toastShort("注册失败");
				Log.d("mytest", "error--pp"+ex.getMessage());
				Log.d("mytest", "error--pp"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				try {
					JSONObject jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.e(TAG, code);
					if(!code.equals("200")){
						toastShort("注册失败");
						return;
					}
					toastShort("注册成功");
					JSONObject obj = jsons.getJSONObject("data");
					String username = obj.getString("username");
					String passwprd = obj.getString("password");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.HX_USER_NAME, username);
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.HX_USER_PASSWORD, passwprd);
					String token = obj.getString("token");
					String nickname = obj.getString("nickname");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_NAME, nickname);
					String userHead = obj.getString("icon_thumbnailUrl");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_HEAD, userHead);
					String userCode = obj.getString("code");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_CODE, userCode);

					LogUtil.e(TAG, "username=="+username+"||||passwprd=="+passwprd);

					loginHuanXin(username,passwprd,token);   
					MobclickAgent.onProfileSignIn(userCode);
					//把token 存在本地
					if(token!=null&&token.length()>0){
						SharePreferanceUtils.getInstance().putShared(RegisterTwoActivity.this, SharePreferanceUtils.TOKEN, token);
					}


					Umutils.count(RegisterTwoActivity.this, Umutils.RIGISTER_SUCCESS);
					Intent intent=new Intent(RegisterTwoActivity.this,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 登录环信客户端 建立长连接
	 * @param username
	 * @param password
	 */
	public void loginHuanXin(String username,String password,final String token){
		EMClient.getInstance().login(username, password, new EMCallBack() {

			@Override
			public void onSuccess() {

				//	Toast.makeText(TAG, "注册成功", Toast.LENGTH_SHORT).show();
				//				LogUtil.e(TAG, "登录成功环信");
				//				//把token 存在本地
				//				SharePreferanceUtils.getInstance().putShared(RegisterTwoActivity.this, SharePreferanceUtils.TOKEN, token);
			}

			@Override
			public void onProgress(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e(TAG, "登陆聊天服务器失败！");
			}
		});

	}
}
