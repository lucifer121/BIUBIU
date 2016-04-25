package com.android.biubiu.activity.biu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.activity.LoginActivity;
import com.android.biubiu.activity.mine.ScanUserHeadActivity;
import com.android.biubiu.activity.mine.SuperMainInfoActivity;
import com.android.biubiu.adapter.SeetingUserInterestAdapter;
import com.android.biubiu.adapter.SeetingUserPagerTagAdapter;
import com.android.biubiu.adapter.UserInterestAdapter;
import com.android.biubiu.adapter.UserPagerTagAdapter;
import com.android.biubiu.bean.BiuDetialBean;
import com.android.biubiu.bean.InterestByCateBean;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.TokenBean;
import com.android.biubiu.bean.UserFriends;
import com.android.biubiu.callback.BiuBooleanCallback;
import com.android.biubiu.chat.ChatActivity;
import com.android.biubiu.chat.Constant;
import com.android.biubiu.common.CommonDialog;
import com.android.biubiu.common.Umutils;
import com.android.biubiu.sqlite.SchoolDao;
import com.android.biubiu.sqlite.UserDao;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.UploadImgUtils;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;
import com.umeng.analytics.MobclickAgent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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
import android.widget.Toast;

public class BiuBiuReceiveActivity extends BaseActivity {
	private static final int GO_CHARGE = 1001;
	private RelativeLayout backLayout;
	private GridView mGridViewTag, mGridViewInterestTag;
	private SeetingUserPagerTagAdapter personalAdapter;
	private SeetingUserInterestAdapter interestAdapter;
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
	private ImageView superManiv;

	/**
	 * 表示 biu是否已经被抢了
	 */
	private Boolean isGrab=false;

	private int biubiuMoney=0;
	private int spentBiuMoney=0;

	private RelativeLayout noBiuMoneyLayout,isGrabLayout;
	private RelativeLayout chongzhiLayout,goSendBiuLayout;
	private UserDao userDao;
	private String userCodeString,userNameString,userUrlString;

	private static final int SELECT_PHOTO = 1002;
	private static final int CROUP_PHOTO = 1003;
	Bitmap userheadBitmap = null;
	String headPath = "";
	private boolean isUploadingPhoto = false;
	String headFlag = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_receive);
		referenceId = getIntent().getStringExtra("referenceId");
		userCode = getIntent().getStringExtra("userCode");
		chatId = getIntent().getStringExtra("chatId");
		headFlag = getIntent().getStringExtra("headFlag");
		//		LogUtil.e(TAG, "referenceId==" + referenceId + "||userCode=="
		//				+ userCode + "||chatId==" + chatId);
		userDao=new UserDao(this);
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
		superManiv = (ImageView) findViewById(R.id.super_man_iv);

		userPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent headIntent = new Intent(BiuBiuReceiveActivity.this,ScanUserHeadActivity.class);
				headIntent.putExtra("userhead", biuDEtialBean.getIconOrigin());
				headIntent.putExtra("isMyself", false);
				startActivity(headIntent);
			}
		});
		neverGrab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showNeverDialog();
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
					if(null != headFlag && !"".equals(headFlag)){
						switch (Integer.parseInt(headFlag)) {
						case 2:
						case 4:
						case 6:
							showShenHeDaiog(Integer.parseInt(headFlag));
							break;

						default:
							grabBiu();
							Umutils.count(BiuBiuReceiveActivity.this, Umutils.RECEIVE_BIU_TOTAL);
							break;
						}
					}else{
						grabBiu();
						Umutils.count(BiuBiuReceiveActivity.this, Umutils.RECEIVE_BIU_TOTAL);
					}
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
				Intent intent = new Intent(BiuBiuReceiveActivity.this,BiuChargeActivity.class);
				intent.putExtra("coin", biubiuMoney);
				startActivityForResult(intent, GO_CHARGE);
			}
		});
		goSendBiuLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BiuBiuReceiveActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		superManiv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent superIntent = new Intent(BiuBiuReceiveActivity.this,SuperMainInfoActivity.class);
				startActivity(superIntent);
			}
		});
	}
	protected void showNeverDialog() {
		// TODO Auto-generated method stub
		CommonDialog.doubleBtnDialog(BiuBiuReceiveActivity.this, "提示", "确定屏蔽他/她？", "取消", "确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				neverSee();
			}
		});
	}

	private void showShenHeDaiog(final int flag){
		String title = "";
		String msg = "";
		String strBtn1 = "";
		String strBtn2 = "";
		switch (flag) {
		case 2:
			title = getResources().getString(R.string.head_egis);
			msg = getResources().getString(R.string.head_egis_info);
			strBtn1 = "我知道了";
			break;
		case 4:
			title = getResources().getString(R.string.head_no_egis);
			msg = getResources().getString(R.string.head_no_egis_info1);
			strBtn1 = "取消";
			strBtn2 = "重新上传";
			break;
		case 6:
			title = getResources().getString(R.string.head_no_egis);
			msg = getResources().getString(R.string.head_no_egis_info2);
			strBtn1 = "取消";
			strBtn2 = "重新上传";
			break;
		default:
			break;
		}
		if(flag == 2){
			CommonDialog.singleBtnDialog(BiuBiuReceiveActivity.this, title, msg, strBtn1, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					HttpUtils.commitIconState(BiuBiuReceiveActivity.this);
					dialog.dismiss();
				}
			});
		}else{
			CommonDialog.doubleBtnDialog(BiuBiuReceiveActivity.this, title, msg, strBtn1, strBtn2, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					HttpUtils.commitIconState(BiuBiuReceiveActivity.this);
					dialog.dismiss();
				}
			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (flag) {
					case 4:
						showHeadDialog();
						break;
					case 6:
						showHeadDialog();
						break;
					default:
						break;
					}
					dialog.dismiss();
				}
			});
		}
	}
	public void showHeadDialog() {
		CommonDialog.headDialog(BiuBiuReceiveActivity.this, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent ;
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
		            intent = new Intent(Intent.ACTION_GET_CONTENT);
		            intent.setType("image/*");
		        } else {
		            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		        }
				/*Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");*/
				startActivityForResult(intent, SELECT_PHOTO);
				dialog.dismiss();
			}
		},new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	/**
	 * 初始化数据
	 */
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
					if(biuDEtialBean.getIsGrabed()==1){
						grabBT.setText("该biu已经被抢了");
						grabBT.setBackgroundResource(R.drawable.biu_btn_disabled);
					}else{
						grabBT.setText(getResources().getString(R.string.receive_biu_grab));
						grabBT.setBackgroundResource(R.drawable.biu_btn_normal);
					}

					personalTagList.clear();
					personalTagList.addAll(biuDEtialBean.getHit_tags());

					personalAdapter.notifyDataSetChanged();

					setInterestTags(biuDEtialBean.getInterested_tags());

					userName.setText("" + biuDEtialBean.getNickname());
					//		distance.setText(biuDEtialBean.getDistance() + "m");
					matchingScore.setText(""
							+ biuDEtialBean.getMatching_score() + "%");

					//		timeBefore.setText(biuDEtialBean.getTimebefore() + "分钟");


					if(biuDEtialBean.getDistance()>1000){
						distance.setText(Math.round(biuDEtialBean.getDistance()/1000)/10.0+"km");
					}else{
						distance.setText(biuDEtialBean.getDistance()+"m");
					}

					if(biuDEtialBean.getTimebefore()>(24*60*60)){
						timeBefore.setText(biuDEtialBean.getTimebefore()+"day");
					}else if(biuDEtialBean.getTimebefore()>(60*60)){
						timeBefore.setText(biuDEtialBean.getTimebefore()+"h");
					}else{
						timeBefore.setText(biuDEtialBean.getTimebefore()+"min");
					}


					numberInTag.setText("[" + biuDEtialBean.getHit_tags_num()
							+ "]");
					numberInInterestTag.setText("["
							+ biuDEtialBean.getInterested_tags_num() + "]");
					age.setText(biuDEtialBean.getAge() + "岁");
					description.setText(biuDEtialBean.getChat_tags());
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
					spentBiuMoney=biuDEtialBean.getNeedvc();
					if((biubiuMoney-spentBiuMoney)<0){
						isGrabLayout.setVisibility(View.GONE);
						noBiuMoneyLayout.setVisibility(View.VISIBLE);	
					}else{
						isGrabLayout.setVisibility(View.VISIBLE);
						noBiuMoneyLayout.setVisibility(View.GONE);	
					}
					userCodeString=biuDEtialBean.getUser_code();
					userNameString=biuDEtialBean.getNickname();
					userUrlString=biuDEtialBean.getIcon_thumbnailUrl();
					switch (biuDEtialBean.getSuperMan()) {
					case 0:
						superManiv.setVisibility(View.GONE);
						break;
					case 1:
						superManiv.setImageResource(R.drawable.special_label_icon_pink);
						superManiv.setVisibility(View.VISIBLE);
						break;
					case 2:
						superManiv.setImageResource(R.drawable.special_label_icon_yellow);
						superManiv.setVisibility(View.VISIBLE);
						break;
					case 3:
						superManiv.setImageResource(R.drawable.special_label_icon_blue);
						superManiv.setVisibility(View.VISIBLE);
						break;
					default:
						break;
					}
					saveUserFriend(userCodeString,userNameString,userUrlString);
					//					if(biuDEtialBean.getToken()!=null&&!biuDEtialBean.getToken().equals("")){
					//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, biuDEtialBean.getToken());
					//					}


				} catch (Exception e) {
					// TODO: handle exception
					log.e(TAG, e);
				}
			}

		});

	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		personalAdapter = new SeetingUserPagerTagAdapter(getApplicationContext(),
				personalTagList);
		mGridViewTag.setAdapter(personalAdapter);
		interestAdapter = new SeetingUserInterestAdapter(getApplicationContext(),
				interestTagList);
		mGridViewInterestTag.setAdapter(interestAdapter);

		setGridviewHight(mGridViewTag);
		setGridview2Hight(mGridViewInterestTag);
		//		mGridViewTag.setOnItemClickListener(new OnItemClickListener() {
		//
		//			@Override
		//			public void onItemClick(AdapterView<?> arg0, View view,
		//					int position, long id) {
		//
		//				toastShort(personalTagList.get(position).getName());
		//			}
		//		});
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
		if(biuDEtialBean.getIsGrabed()==1){
			toastShort("该biu已经被抢了");
			return;
		}
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
					JSONObject obj = jsons.getJSONObject("data");
					//					String token=obj.getString("token");
					//					if(token!=null&&!token.equals("")){
					//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					//					}
					//					saveUserFriend(userCodeString,userNameString,userUrlString);
					String message=obj.getString("message");
					if(message.equals("0")){
						toastShort("biu币不足");
					}
					else if(message.equals("1")){
						toastShort("抢中了啊");	
						Intent broadIntent = new Intent();
						broadIntent.putExtra("user_code",userCodeString);
						sendBroadcast(broadIntent,Constants.GRAB_BIU);
						Umutils.count(BiuBiuReceiveActivity.this, Umutils.GRAB_BIU_SUCCESS);
						Intent intent=new Intent(BiuBiuReceiveActivity.this,ChatActivity.class);
						intent.putExtra(Constant.EXTRA_USER_ID, userCodeString);
						startActivity(intent);
						finish();
					}else{
						toastShort("该biu已被抢");
					}

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
		interestAdapter = new SeetingUserInterestAdapter(getApplicationContext(),
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
					//						TokenBean tokenBean=gson.fromJson(json, TokenBean.class);
					//						if(!tokenBean.equals("")||tokenBean!=null){
					//							SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, tokenBean.getToken());
					//						}	
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
		});
	}

	public void saveUserFriend(String code,String name, String url){
		UserFriends item=new UserFriends();
		item.setUserCode(code);
		item.setIcon_thumbnailUrl(url);
		item.setNickname(name);
		userDao.insertOrReplaceUser(item);

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case GO_CHARGE:
			if(resultCode == RESULT_OK){
				noBiuMoneyLayout.setVisibility(View.GONE);
				isGrabLayout.setVisibility(View.VISIBLE);
			}
			break;
		case CROUP_PHOTO:
			try {
				if (data != null) {
					Bundle extras = data.getExtras();
					userheadBitmap = extras.getParcelable("data");
					if(userheadBitmap != null){
						headPath = saveHeadImg(userheadBitmap);
						uploadPhoto(headPath);
					}
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			break;
		case SELECT_PHOTO:
			if(data != null){
				cropPhoto(data.getData());// 裁剪图片
			}
			break;
		default:
			break;
		}
	}
	private void uploadPhoto(String headPath) {
		// TODO Auto-generated method stub
		isUploadingPhoto = true;
		showLoadingLayout("正在上传……");
		UploadImgUtils.uploadPhoto(BiuBiuReceiveActivity.this, headPath, new BiuBooleanCallback() {

			@Override
			public void callback(boolean result) {
				// TODO Auto-generated method stub
				isUploadingPhoto = false;
				if(result){
					dismissLoadingLayout();
				}else{
					Toast.makeText(getApplicationContext(), "上传照片失败", 1000).show();
					dismissLoadingLayout();
				}
			}
		});
	}
	/**
	 * 调用系统的裁剪功能
	 * 
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		// 调用拍照的裁剪功能
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽和搞的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// // outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROUP_PHOTO);
	}
	public String saveHeadImg(Bitmap head) {
		FileOutputStream fos = null;
		String path = "";
		path = Environment.getExternalStorageDirectory()
				+ "/biubiu/"+System.currentTimeMillis()+".png";
		File file = new File(path);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			head.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(isUploadingPhoto){
			return;
		}
		finish();
	}
}
