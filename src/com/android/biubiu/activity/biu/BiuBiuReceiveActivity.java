package com.android.biubiu.activity.biu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.R.menu;
import com.android.biubiu.activity.LoginActivity;
import com.android.biubiu.adapter.UserInterestAdapter;
import com.android.biubiu.adapter.UserPagerTagAdapter;
import com.android.biubiu.bean.BiuDetialBean;
import com.android.biubiu.bean.InterestByCateBean;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.TokenBean;
import com.android.biubiu.sqlite.SchoolDao;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BiuBiuReceiveActivity extends BaseActivity {
	private RelativeLayout backLayout;
	private GridView mGridViewTag, mGridViewInterestTag;
	private UserPagerTagAdapter personalAdapter;
	private UserInterestAdapter interestAdapter;
	ArrayList<PersonalTagBean> personalTagList = new ArrayList<PersonalTagBean>();
	ArrayList<InterestTagBean> interestTagList = new ArrayList<InterestTagBean>();
	private Button grabBT;
	private RelativeLayout neverGrab;
	private String TAG = "BiuBiuReceiveActivity";
	private String referenceId, userCode, chatId;

	private BiuDetialBean biuDEtialBean = new BiuDetialBean();

	private TextView userName, distance, matchingScore, timeBefore, sex, age,
			starsign, school, numberInTag, numberInInterestTag, description;
	private ImageView userPhoto;
	
	/**
	 * 表示 biu是否已经被抢了
	 */
	private Boolean isGrab=false;
	
	private int biubiuMoney=0;
	private int spentBiuMoney=0;
	
	private RelativeLayout noBiuMoneyLayout,isGrabLayout;
	private RelativeLayout chongzhiLayout,goSendBiuLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_receive);
		referenceId = getIntent().getStringExtra("referenceId");
		userCode = getIntent().getStringExtra("userCode");
		chatId = getIntent().getStringExtra("chatId");
		LogUtil.e(TAG, "referenceId==" + referenceId + "||userCode=="
				+ userCode + "||chatId==" + chatId);
		initView();
		initData();
		initAdapter();
	}

	private void initView() {
		// TODO Auto-generated method stub
		chongzhiLayout=(RelativeLayout) findViewById(R.id.receive_biu_duihuan_rl);
		goSendBiuLayout=(RelativeLayout) findViewById(R.id.receive_biu_go_sendbiu_rl);
		noBiuMoneyLayout=(RelativeLayout) findViewById(R.id.no_biuMoney_receive_biu_rl);
		isGrabLayout=(RelativeLayout) findViewById(R.id.grab_biu_receive_biu_rl);
		backLayout = (RelativeLayout) findViewById(R.id.back_receive_biu_mine_rl);
		mGridViewTag = (GridView) findViewById(R.id.gridview_receive_biubiu_tag);
		mGridViewInterestTag = (GridView) findViewById(R.id.gridview_receive_biubiu_interest_tag);
		grabBT = (Button) findViewById(R.id.grab_biu_receive_biu_bt);
		neverGrab = (RelativeLayout) findViewById(R.id.never_grag_biu_receive_biu_rl);
		userName = (TextView) findViewById(R.id.name_receive_biu_tv);
		distance = (TextView) findViewById(R.id.distanse_receive_biu_tv);
		matchingScore = (TextView) findViewById(R.id.matching_score_receive_biu_tv);
		timeBefore = (TextView) findViewById(R.id.time_receive_biu_tv);
		age = (TextView) findViewById(R.id.age_receive_biu_tv);
		sex = (TextView) findViewById(R.id.sex_receive_biu_tv);
		school = (TextView) findViewById(R.id.school_receive_biu_tv);
		starsign = (TextView) findViewById(R.id.starsign_receive_biu_tv);
		description = (TextView) findViewById(R.id.description_receive_biu_tv);
		numberInTag = (TextView) findViewById(R.id.number_in_personalTag_tv);
		numberInInterestTag = (TextView) findViewById(R.id.number_interestTag_receive_biu_tv);
		userPhoto = (ImageView) findViewById(R.id.photo_head_senbiu_img);

		neverGrab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				neverSee();
			}
		});
		grabBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "").equals("")){
					LogUtil.e(TAG, "未登录 去登陆");
					goLoginDialog();
				}else{
					grabBiu();
				}
				
			}
		});

		backLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();

			}
		});
		chongzhiLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toastShort("去充值界面");
			}
		});
		goSendBiuLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BiuBiuReceiveActivity.this,BiuBiuSendActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	private void initData() {
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			dismissLoadingLayout();
			showErrorLayout(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismissErrorLayout();
					initData();
				}
			});
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		showLoadingLayout(getResources().getString(R.string.loading));
		//初始化页面
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.BIU_DETIAL);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put(
					"token",
					SharePreferanceUtils.getInstance().getToken(
							getApplicationContext(),
							SharePreferanceUtils.TOKEN, ""));
			requestObject.put(
					"device_code",
					SharePreferanceUtils.getInstance().getDeviceId(
							getApplicationContext(),
							SharePreferanceUtils.DEVICE_ID, ""));

			requestObject.put("chat_id", chatId);
			requestObject.put("reference_id", referenceId);
			requestObject.put("user_code", userCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		params.addBodyParameter("data", requestObject.toString());
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
						initData();
					}
				});
				LogUtil.d(TAG, arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				dismissErrorLayout();
				LogUtil.e(TAG, arg0);
				JSONObject jsons;
				try {
					jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
						showErrorLayout(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dismissErrorLayout();
								initData();
							}
						});
						toastShort("获取biu信息失败");	
						return;
					}
					Gson gson = new Gson();
					biuDEtialBean = gson.fromJson(jsons.getJSONObject("data")
							.toString(), BiuDetialBean.class);

					personalTagList.clear();
					personalTagList.addAll(biuDEtialBean.getHit_tags());

					personalAdapter.notifyDataSetChanged();

					setInterestTags(biuDEtialBean.getInterested_tags());

					userName.setText("" + biuDEtialBean.getNickname());
					distance.setText(biuDEtialBean.getDistance() + "m");
					matchingScore.setText(""
							+ biuDEtialBean.getMatching_score() + "%");

					timeBefore.setText(biuDEtialBean.getTimebefore() + "分钟");



					numberInTag.setText("[" + biuDEtialBean.getHit_tags_num()
							+ "]");
					numberInInterestTag.setText("["
							+ biuDEtialBean.getInterested_tags_num() + "]");
					age.setText(biuDEtialBean.getAge() + "岁");
					description.setText(biuDEtialBean.getDescription());
					if (biuDEtialBean.getSex().equals("1")) {
						sex.setText("男生");
					} else {
						sex.setText("女生");
					}
					if (biuDEtialBean.getIsgraduated().equals("2")) {
						school.setText(biuDEtialBean.getCarrer() + "");
					} else {
						SchoolDao schoolDao = new SchoolDao();

						school.setText(schoolDao
								.getschoolName(biuDEtialBean.getSchool())
								.get(0).getUnivsNameString());
					}

					starsign.setText(biuDEtialBean.getStarsign() + "");
					x.image().bind(userPhoto,
							biuDEtialBean.getIcon_thumbnailUrl());
					biubiuMoney=biuDEtialBean.getHavevc();
					if(biubiuMoney==0){
						isGrabLayout.setVisibility(View.GONE);
						noBiuMoneyLayout.setVisibility(View.VISIBLE);	
					}else{
						isGrabLayout.setVisibility(View.VISIBLE);
						noBiuMoneyLayout.setVisibility(View.GONE);	
					}

				} catch (Exception e) {
					// TODO: handle exception
					log.e(TAG, e);
				}
			}

		});

	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		personalAdapter = new UserPagerTagAdapter(getApplicationContext(),
				personalTagList);
		mGridViewTag.setAdapter(personalAdapter);
		interestAdapter = new UserInterestAdapter(getApplicationContext(),
				interestTagList);
		mGridViewInterestTag.setAdapter(interestAdapter);

		setGridviewHight(mGridViewTag);
		setGridview2Hight(mGridViewInterestTag);
		mGridViewTag.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {

				toastShort(personalTagList.get(position).getName());
			}
		});
	}


	/**
	 * 设置 Gridview高度
	 */
	public void setGridviewHight(GridView mGridView) {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mGridView
				.getLayoutParams();
		int mHight;
		if (personalTagList.size() != 0 && (personalTagList.size()) % 4 == 0) {
			mHight = (((personalTagList.size()) / 4))
					* DensityUtil.dip2px(this, 37);
		} else if (personalTagList.size() == 0) {
			mHight = 0;
		} else {
			mHight = (((personalTagList.size()) / 4) + 1)
					* DensityUtil.dip2px(this, 37);
		}
		params.height = mHight;
		mGridView.setLayoutParams(params);

	}

	/**
	 * 设置 Gridview高度
	 */
	public void setGridview2Hight(GridView mGridView) {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mGridView
				.getLayoutParams();
		int mHight;
		if (interestTagList.size() != 0 && (interestTagList.size()) % 4 == 0) {
			mHight = (((interestTagList.size()) / 4))
					* DensityUtil.dip2px(this, 37);
		} else if (interestTagList.size() == 0) {
			mHight = 0;
		} else {
			mHight = (((interestTagList.size()) / 4) + 1)
					* DensityUtil.dip2px(this, 37);
		}
		params.height = mHight;
		mGridView.setLayoutParams(params);

	}

	/**
	 * 抢biu
	 */
	public void grabBiu(){
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		showLoadingLayout(getResources().getString(R.string.grabing));
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GRAB_BIU);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put(
					"token",
					SharePreferanceUtils.getInstance().getToken(
							getApplicationContext(),
							SharePreferanceUtils.TOKEN, ""));
			requestObject.put(
					"device_code",
					SharePreferanceUtils.getInstance().getDeviceId(
							getApplicationContext(),
							SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("chat_id", chatId);
			requestObject.put("user_code", userCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		params.addBodyParameter("data", requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				toastShort(arg0.getMessage());
				LogUtil.d(TAG, arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				Log.d(TAG, "result--"+arg0);
				JSONObject jsons;

				try {
					jsons = new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.d(TAG, "" + code);
					if (!code.equals("200")) {
						toastShort("" + jsons.getString("error"));
						return;
					}
					toastShort("抢中了啊");
					finish();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});

	}

	/**
	 * 兴趣标签
	 * 
	 * @param cates
	 */
	private void setInterestTags(ArrayList<InterestByCateBean> cates) {
		ArrayList<InterestTagBean> inters = new ArrayList<InterestTagBean>();
		if (cates != null && cates.size() > 0) {
			for (int i = 0; i < cates.size(); i++) {
				for (int j = 0; j < cates.get(i).getmInterestList().size(); j++) {
					InterestTagBean tagBean = cates.get(i).getmInterestList()
							.get(j);
					tagBean.setTagType(cates.get(i).getTypename());
					inters.add(tagBean);
				}
			}
		}
		interestTagList.clear();
		interestTagList.addAll(inters);
		interestAdapter = new UserInterestAdapter(getApplicationContext(),
				inters);
		mGridViewInterestTag.setAdapter(interestAdapter);
		setGridviewHight(mGridViewTag);
		setGridview2Hight(mGridViewInterestTag);
	}
	
	/**
	 * 提醒去登陆
	 */
	public void goLoginDialog(){
		final AlertDialog mAlertDialog= new AlertDialog.Builder(getApplicationContext()).create();
		mAlertDialog.show();
		Window window=mAlertDialog.getWindow();
		window.setContentView(R.layout.grab_biu_receive_no_login_dialog);
		RelativeLayout dismissLayout=(RelativeLayout) window.findViewById(R.id.dismiss_dialog_receive_biu_rl);
		RelativeLayout ogLoginLayout=(RelativeLayout) window.findViewById(R.id.goLogin_dialog_receive_biu_rl);
		dismissLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mAlertDialog.dismiss();
			}
		});
		ogLoginLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BiuBiuReceiveActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}
	/**
	 * 不想见到他
	 */
	public void neverSee(){
		showLoadingLayout("处理中");
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.NO_LONGER_MATCH);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put(
					"token",
					SharePreferanceUtils.getInstance().getToken(
							getApplicationContext(),
							SharePreferanceUtils.TOKEN, ""));
			requestObject.put(
					"device_code",
					SharePreferanceUtils.getInstance().getDeviceId(
							getApplicationContext(),
							SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("user_code", userCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		params.addBodyParameter("data", requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				toastShort(arg0.getMessage());		
				dismissLoadingLayout();
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub				
			}

			@Override
			public void onSuccess(String arg0) {
				dismissLoadingLayout();
				LogUtil.d(TAG, arg0);
				JSONObject jsons;
				
					try {
						jsons = new JSONObject(arg0);
						String code = jsons.getString("state");
						LogUtil.d(TAG, ""+code);
						if(!code.equals("200")){
							showErrorLayout(new OnClickListener() {								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dismissErrorLayout();
									neverSee();
								}
							});
							toastShort(""+jsons.getString("error"));	
							return;
						}
						String json=jsons.getString("data");
						Gson gson=new Gson();
						TokenBean tokenBean=gson.fromJson(json, TokenBean.class);
						if(!tokenBean.equals("")&&tokenBean!=null){
							SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, tokenBean.getToken());
						}	
						finish();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				
			}
		});
	}

}
