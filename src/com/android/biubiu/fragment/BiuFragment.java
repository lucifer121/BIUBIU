package com.android.biubiu.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import cc.imeetu.iu.R;

import com.android.biubiu.MainActivity;
import com.android.biubiu.activity.LoginOrRegisterActivity;
import com.android.biubiu.activity.RegisterOneActivity;
import com.android.biubiu.activity.biu.BiuBiuReceiveActivity;
import com.android.biubiu.activity.biu.BiuBiuSendActivity;
import com.android.biubiu.activity.biu.BiuChargeActivity;
import com.android.biubiu.bean.DotBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.UserBean;
import com.android.biubiu.callback.BiuBooleanCallback;
import com.android.biubiu.chat.ChatActivity;
import com.android.biubiu.chat.Constant;
import com.android.biubiu.chat.MyHintDialog;
import com.android.biubiu.chat.MyHintDialog.OnDialogClick;
import com.android.biubiu.common.CommonDialog;
import com.android.biubiu.push.MyPushReceiver;
import com.android.biubiu.push.PushInterface;
import com.android.biubiu.sqlite.SchoolDao;
import com.android.biubiu.utils.BiuUtil;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.LoginUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.UploadImgUtils;
import com.android.biubiu.view.BiuView;
import com.android.biubiu.view.HomeBgView;
import com.android.biubiu.view.TaskView;
import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;

import android.R.integer;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.support.v4.app.Fragment;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("NewApi")
public class BiuFragment extends Fragment implements PushInterface{
	View view;
	//启动发biubiu页面的requestcode
	public static final int SEND_BIU_REQUEST = 1001;
	//屏幕宽高
	int width = 0;
	int height = 0;
	Handler taskHandler;
	Handler infoHandler;
	Handler removeHandler;
	//抢biubiu的用户，有则显示 没有则不显示
	UserBean userBiuBean;
	//圆心坐标
	float x0 = 0;
	float y0 = 0;
	float y1 = 0;
	//三个圆的半径
	float circleR1;
	float circleR2;
	float circleR3;
	//三个头像layout宽度
	float userD1;
	float userD2;
	float userD3;
	//三个圈放置view数
	int n1 = 5;
	int n2 = 8;
	int n3 = 12;
	//倒计时总时间和当前时间
	int totalTime = 90;
	int currentTime = 0;
	//放三个圆圈的分割点
	ArrayList<DotBean> c1DotList = new ArrayList<DotBean>();
	ArrayList<DotBean> c2DotList = new ArrayList<DotBean>();
	ArrayList<DotBean> c3DotList = new ArrayList<DotBean>();
	//存放三个圈上的用户列表
	ArrayList<UserBean> user1List = new ArrayList<UserBean>();
	ArrayList<UserBean> user2List = new ArrayList<UserBean>();
	ArrayList<UserBean> user3List = new ArrayList<UserBean>();
	//新增用户
	UserBean newUserBean ;
	//存放外圈临界角度
	ArrayList< Double> edgeAngleList = new ArrayList<Double>();
	//标识viewTag
	/*private String retivTag = "relative";
	private String tvTag = "textview";
	private String gifvTag = "gifview";
	private String imvHeadTag = "imvhead";
	private String imvDotTag = "imvdot";*/
	private int retivIdTag = 1;
	private int tvIdTag = 2;
	private int gifvIdTag = 3;
	private int imvHeadIdTag = 4;
	private int imvDotIdTag = 5;
	//线圈view
	BiuView biuView;
	//背景动画
	HomeBgView backgroundGif;
	//中间biubiu layout
	RelativeLayout biuLayout;
	//头像IMv
	ImageView userBiuImv;
	//倒计时view
	TaskView taskView;
	//放置接受用户信息layout
	AbsoluteLayout userGroupLayout;
	private TextView nameTv;
	private TextView sexTv;
	private TextView ageTv;
	private TextView starTv;
	private TextView schoolTv;
	private LinearLayout infoLayout;
	ImageOptions imageOptions;
	ImageView userBiuBg;
	Animation animationAlpha;
	Animation animationHide;
	Animation animationUserBg;

	SchoolDao schoolDao;

	//中间是否为biubiu可发送状态
	boolean isBiuState = true;
	private static final int SELECT_PHOTO = 1002;
	private static final int CROUP_PHOTO = 1003;
	Bitmap userheadBitmap = null;
	String headPath = "";
	LinearLayout loadingLayout;
	GifView loadGif;
	TextView loadTv;
	public static boolean isUploadingPhoto = false;
	RemoveReceiver removeReceiver;
	//头像审核状态标记
	String headFlag = "";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.biu_fragment_layout, null);
		schoolDao=new SchoolDao();
		initReceiver();
		init();
		drawBiuView();
		setBiuLayout();
		return view;
	}
	private void initReceiver() {
		// TODO Auto-generated method stub
		removeReceiver = new RemoveReceiver();
		IntentFilter filter = new IntentFilter(Constants.GRAB_BIU);
		getActivity().registerReceiver(removeReceiver, filter);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//接口通信赋值
		MyPushReceiver.setUpdateBean(this);
		//检查是否提交了channelID
		if(!SharePreferanceUtils.getInstance().getShared(getActivity(), SharePreferanceUtils.IS_COMMIT_CHANNEL, false)){
			HttpUtils.commitChannelId(getActivity());
		}
		initUserGroup();
		if(LoginUtils.isLogin(getActivity())){
			//执行加载页面所有信息的请求
			getAllUser();
			MainActivity.biuCoinLayout.setVisibility(View.VISIBLE);
		}else{
			//获取未登录时的biubiu列表
			getBiuListUnlogin();
			MainActivity.biuCoinLayout.setVisibility(View.GONE);
		}
	}
	private void init() {
		// TODO Auto-generated method stub
		width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		loadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout);
		loadGif = (GifView) view.findViewById(R.id.load_gif);
		loadTv = (TextView) view.findViewById(R.id.loading_tv);
		taskHandler = new Handler();
		infoHandler = new Handler();
		removeHandler = new Handler();
		x0 = width / 2;
		y0 = width *417/720;

		biuView = (BiuView) view.findViewById(R.id.biu_view);
		biuLayout = (RelativeLayout) view.findViewById(R.id.biu_layout);
		userBiuImv = (ImageView) view.findViewById(R.id.user_biu);
		backgroundGif = (HomeBgView) view.findViewById(R.id.home_view);
		backgroundGif.setWidth(width);
		backgroundGif.startAnim();
		taskView = (TaskView) view.findViewById(R.id.task_view);
		userGroupLayout = (AbsoluteLayout) view.findViewById(R.id.user_group_layout);
		nameTv = (TextView) view.findViewById(R.id.name_tv);
		sexTv = (TextView) view.findViewById(R.id.sex_tv);
		ageTv = (TextView) view.findViewById(R.id.age_tv);
		starTv = (TextView) view.findViewById(R.id.star_tv);
		schoolTv = (TextView) view.findViewById(R.id.school_tv);
		infoLayout = (LinearLayout) view.findViewById(R.id.info_layout);
		userBiuBg = (ImageView) view.findViewById(R.id.user_biu_bg);
		animationAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha);
		animationHide = AnimationUtils.loadAnimation(getActivity(), R.anim.hide_anim);
		animationUserBg = AnimationUtils.loadAnimation(getActivity(), R.anim.user_gif_alpha);

		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.photo_fail)
		.setFailureDrawableId(R.drawable.photo_fail)
		.setIgnoreGif(true)
		.build();
	}
	private void drawBiuView() {
		// TODO Auto-generated method stub
		circleR1 = width*111/360;
		circleR2 = width*81/180;
		circleR3 = width*49/90;
		//设置半径
		biuView.setCircleR(circleR1, circleR2, circleR3);
		//设置圆心
		biuView.setDot(x0, y0); 
	}
	private void setBiuLayout() {
		/**
		 * 外层动画
		 */
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width*125/360, width*125/360);
		params.leftMargin = width *235/720 ;
		params.topMargin = width*146/360;
		biuLayout.setLayoutParams(params);
		RelativeLayout.LayoutParams animParams = new RelativeLayout.LayoutParams(width*125/360, width*125/360);
		userBiuBg.setLayoutParams(animParams);
		/***
		 * 头像及倒计时
		 */
		RelativeLayout.LayoutParams imvParams = new RelativeLayout.LayoutParams(width*65/360, width*65/360);
		imvParams.leftMargin = width*30/360 ;
		imvParams.topMargin = width*30/360;
		userBiuImv.setLayoutParams(imvParams);
		userBiuBg.startAnimation(animationAlpha);
		//倒计时view
		drawTaskView();
		/**
		 * 外层biubiu点击
		 * */
		biuLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int flag = 0;
				if(!LoginUtils.isLogin(getActivity())){
					Intent intent = new Intent(getActivity(),LoginOrRegisterActivity.class);
					startActivity(intent);
					return;
				}
				if(currentTime > 0){
					return;
				}
				if(isBiuState){
					String sendTimeStr = SharePreferanceUtils.getInstance().getBiuTime(getActivity(), SharePreferanceUtils.SEND_BIU_TIME, "");
					if(!sendTimeStr.equals("")){
						long time = System.currentTimeMillis() - Long.parseLong(sendTimeStr);
						if(time/1000 > 90){
							//启动发送biubiu界面
							if(null != headFlag && !"".equals(headFlag)){
								switch (Integer.parseInt(headFlag)) {
								case 2:
								case 4:
								case 6:
									showShenHeDaiog("send",Integer.parseInt(headFlag));
									break;
								default:
									Intent intent = new Intent(getActivity(),BiuBiuSendActivity.class);
									startActivityForResult(intent, SEND_BIU_REQUEST);
									break;
								}
							}else{
								Intent intent = new Intent(getActivity(),BiuBiuSendActivity.class);
								startActivityForResult(intent, SEND_BIU_REQUEST);
							}
						}else{
							Toast.makeText(getActivity(), "距离上次发biu还不到90秒哦！", 1000).show();
						}	
					}else{
						//启动发送biubiu界面
						if(null != headFlag && !"".equals(headFlag)){
							switch (Integer.parseInt(headFlag)) {
							case 2:
							case 4:
							case 6:
								showShenHeDaiog("send",Integer.parseInt(headFlag));
								break;
							default:
								Intent intent = new Intent(getActivity(),BiuBiuSendActivity.class);
								startActivityForResult(intent, SEND_BIU_REQUEST);
								break;
							}
						}else{
							Intent intent = new Intent(getActivity(),BiuBiuSendActivity.class);
							startActivityForResult(intent, SEND_BIU_REQUEST);
						}
					}
				}else{
					isBiuState = true;
					userBiuImv.setImageResource(R.drawable.biu_btn_biu);
					//标记抢我biubiu的人为已读
					updateBiuState();
					//进入聊天界面
					Intent intent=new Intent(getActivity(),ChatActivity.class);
					intent.putExtra(Constant.EXTRA_USER_ID, userBiuBean.getId());
					startActivity(intent);
				}
			}
		});
	}
	private void showShenHeDaiog(final String actyStr,final int flag){
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
		HttpUtils.commitIconState(getActivity());
		if(flag == 2){
			CommonDialog.singleBtnDialog(getActivity(), title, msg, strBtn1, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					if(actyStr.equals("send")){
						Intent intent = new Intent(getActivity(),BiuBiuSendActivity.class);
						startActivityForResult(intent, SEND_BIU_REQUEST);
					}else{
						((MainActivity)getActivity()).showRightMenu();
						MainActivity.newMessage.setVisibility(View.GONE);
					}
				}
			});
		}else{
			CommonDialog.doubleBtnDialog(getActivity(), title, msg, strBtn1, strBtn2, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					if(actyStr.equals("send")){
						Intent intent = new Intent(getActivity(),BiuBiuSendActivity.class);
						startActivityForResult(intent, SEND_BIU_REQUEST);
					}else{
						((MainActivity)getActivity()).showRightMenu();
						MainActivity.newMessage.setVisibility(View.GONE);
					}
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
		CommonDialog.headDialog(getActivity(), new DialogInterface.OnClickListener() {

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
	//设置倒计时按钮
	private void drawTaskView() {
		// TODO Auto-generated method stub
		taskView.setDot(x0, y0);
		taskView.setRadius(width*65/720, 2);
		taskView.setTotal(totalTime);
	}
	//倒计时线程
	Runnable taskR=new Runnable() {

		@Override
		public void run() {
			taskView.updeteTask(currentTime--);
			if(currentTime <= 0){
				if(getActivity() != null){
					Toast.makeText(getActivity().getApplicationContext(), "你的biubiu暂时无人应答，请重新发送", Toast.LENGTH_SHORT).show();
				}
				isBiuState = true;
				taskView.setVisibility(View.GONE);
				userBiuImv.setImageResource(R.drawable.biu_btn_biu);
				userBiuImv.setVisibility(View.VISIBLE);
				currentTime = 0;
				taskHandler.removeCallbacks(taskR);
				return;
			}
			taskHandler.postDelayed(taskR, 1000);
		}
	};
	//放置接收到的用户
	private void initUserGroup() {
		//给三个头像layout赋值
		userD1 = width*58/360;
		userD2 = width*34/360;
		userD3 = width*25/360;
		//计算分割区域
		c1DotList.clear();
		c1DotList.addAll(BiuUtil.caculateCircle(n1, circleR1, x0, y0)) ;
		c2DotList.clear();
		c2DotList.addAll(BiuUtil.caculateCircle(n2, circleR2, x0, y0));
		edgeAngleList.clear();
		edgeAngleList.addAll(BiuUtil.getEdgeAngle(width, userD3, circleR3, x0, y0));
		c3DotList.clear();
		//将角度按照从小到大排列
		Collections.sort(edgeAngleList, new SorByAngle());
		c3DotList.addAll(BiuUtil.caculateCircleBig(edgeAngleList, userD3, circleR3, x0, y0, n3));
	}
	//按照角度
	class SorByAngle implements Comparator {
		public int compare(Object o1, Object o2) {
			Double s1 = (Double) o1;
			Double s2 = (Double) o2;
			if (s1 < s2)
				return 1;
			return 0;
		}
	}
	//判断是否存在
	private boolean isOnCircle(UserBean userBean){
		if(user3List.size()>0){
			for(int i3=0;i3<user3List.size();i3++){
				if(userBean.getId().equals(user3List.get(i3).getId())){
					return true;
				}
			}
		}
		if(user2List.size()>0){
			for(int i2=0;i2<user2List.size();i2++){
				if(userBean.getId().equals(user2List.get(i2).getId())){
					return true;
				}
			}
		}
		if(user1List.size()>0){
			for(int i1=0;i1<user1List.size();i1++){
				if(userBean.getId().equals(user1List.get(i1).getId())){
					return true;
				}
			}
		}
		return false;
	}
	//往第一个圈上放view
	private void addCircle1View(UserBean userBean) {
		if(isOnCircle(userBean)){
			return;
		}
		boolean haveSpace = false;
		for(int i=0;i<n1;i++){
			DotBean bean = c1DotList.get(i);
			if(!bean.isAdd()){
				c1DotList.get(i).setAdd(true);
				//添加区域标记
				userBean.setIndex(i);
				haveSpace = true;
				//计算放置位置
				double randomAngle = BiuUtil.getRandomAngle(n1, i, userD1, circleR1);
				int xLocation =  BiuUtil.getLocationX(randomAngle, userD1, circleR1, x0);
				int yLocation = BiuUtil.getLocationY(randomAngle, userD1, circleR1, y0);
				userBean.setX(xLocation);
				userBean.setY(yLocation);
				user1List.add(userBean);
				createCir1NewView(xLocation, yLocation, (int)userD1, (int)userD1, userBean,false);
				showInfoLayout(userBean);
				break ;
			}
		}
		if(!haveSpace){
			//将内圈中的用户按照时间顺序排列
			Collections.sort(user1List,new SorByTime());
			UserBean moveBean = user1List.get(0);
			user1List.remove(0);
			moveOneToTwo(moveBean);
		}
	}
	//从第1个圈移到第2个圈
	private void moveOneToTwo(UserBean oneUserBean) {
		boolean haveSpace = false;
		for(int i=0;i<n2;i++){
			DotBean dotBean = c2DotList.get(i);
			if(!dotBean.isAdd()){
				haveSpace = true;
				//内圈最早的移到第二圈,计算移到第二圈的位置x2,y2
				double randomAngle = BiuUtil.getRandomAngle(n2, i, userD2, circleR2);
				int x2 = BiuUtil.getLocationX(randomAngle, userD2, circleR2, x0);
				int y2 = BiuUtil.getLocationY(randomAngle, userD2, circleR2, y0);
				moveUserView(oneUserBean.getX(), oneUserBean.getY(),
						x2, y2,oneUserBean,userD2,userD1,userD2);
				oneUserBean.setX(x2);
				oneUserBean.setY(y2);
				c2DotList.get(i).setAdd(true);
				//改变空闲标记，将最新插入内圈
				c1DotList.get(oneUserBean.getIndex()).setAdd(false);
				//移动后改变区域标记
				oneUserBean.setIndex(i);
				user2List.add(oneUserBean);
				addCircle1View(newUserBean);
				break;
			}
		}
		if(!haveSpace){
			Collections.sort(user2List,new SorByTime());
			UserBean twoUserBean = user2List.get(0);
			user2List.remove(0);
			moveTwoToThree(oneUserBean,twoUserBean);
		}
	}
	//从第2个圈移到第3个圈
	private void moveTwoToThree(UserBean oneUserBean,UserBean twoUserBean) {
		boolean haveSpace = false;
		for(int i=0;i<n3;i++){
			DotBean dotBean = c3DotList.get(i);
			if(!dotBean.isAdd()){
				haveSpace = true;
				//从第2个圈移到第3个圈,计算移到第二圈的位置x3,y3
				//将角度按照从小到大排列
				Collections.sort(edgeAngleList, new SorByAngle());
				double randomAngle = BiuUtil.getRandomAngleBig(edgeAngleList, n3, i, userD1, circleR3);
				int x3 = BiuUtil.getLocationX(randomAngle, userD3, circleR3, x0);
				int y3 = BiuUtil.getLocationY(randomAngle, userD3, circleR3, y0);
				moveUserView(twoUserBean.getX(), twoUserBean.getY(),
						x3, y3,twoUserBean,userD3,userD1,userD2);
				twoUserBean.setX(x3);
				twoUserBean.setY(y3);
				c3DotList.get(i).setAdd(true);
				//改变空闲标记，内圈移到第二圈
				c2DotList.get(twoUserBean.getIndex()).setAdd(false);
				//移动后改变区域标记
				twoUserBean.setIndex(i);
				user3List.add(twoUserBean);
				moveOneToTwo(oneUserBean);
				break;
			}
		}
		if(!haveSpace){
			//移除外圈最早的
			Collections.sort(user3List,new SorByTime());
			UserBean delBean = user3List.get(0);
			//RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewWithTag(retivTag+delBean.getId());
			RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewById(retivIdTag+Integer.parseInt(delBean.getId()));
			userGroupLayout.removeView(rl);
			//改变空闲标记，第二圈移到外圈
			c3DotList.get(delBean.getIndex()).setAdd(false);
			user3List.remove(0);
			moveTwoToThree(oneUserBean,twoUserBean);
		}
	}
	//biubiu被抢后显示view
	private void updateBiuView(UserBean bean){
		currentTime = 0;
		taskHandler.removeCallbacks(taskR);
		taskView.setVisibility(View.GONE);
		userBiuImv.setImageResource(R.drawable.photo_fail);
		userBiuImv.setVisibility(View.VISIBLE);
		x.image().bind(userBiuImv, bean.getUserHead(), imageOptions);
	}
	//显示底部的信息view
	private void showInfoLayout(UserBean bean) {
		// TODO Auto-generated method stub
		infoHandler.removeCallbacks(infoR);
		infoLayout.setVisibility(View.VISIBLE);
		nameTv.setText(bean.getNickname());
		sexTv.setText(bean.getSex());
		if (bean.getSex().equals(Constants.SEX_MALE)) {
			sexTv.setText("男生");
		} else {
			sexTv.setText("女生");
		}
		ageTv.setText(bean.getAge()+"岁");
		starTv.setText(bean.getStar());
		LogUtil.e("503 行", ""+bean.getIsStudent()+"||"+bean.getSchool()+"||"+bean.getCareer());
		if(bean.getIsStudent().equals(Constants.IS_STUDENT_FLAG)){

			if(bean.getSchool()!=null&&!bean.getSchool().equals("")){
				if(schoolDao.getschoolName(bean.getSchool())!=null){
					schoolTv.setText(schoolDao.getschoolName(bean.getSchool()).get(0).getUnivsNameString());
				}

			}
		}else{
			schoolTv.setText(bean.getCareer());
		}
		infoHandler.postDelayed(infoR, 5000);
	}
	Runnable infoR =new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			infoLayout.setVisibility(View.GONE);
		}
	};
	
	class RemoveReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(null != intent && null != intent.getStringExtra("user_code")){
				String userCode = intent.getStringExtra("user_code");
				removeView(userCode);
			}
		}
		
	}
	//删除被抢的view
	private void removeView(String userCode){
		if(user3List.size()>0){
			for(int i=0;i<user3List.size();i++){
				if(userCode.equals(user3List.get(i).getId())){
					//RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewWithTag(retivTag+userCode);
					RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewById(retivIdTag+Integer.parseInt(userCode));
					userGroupLayout.removeView(rl);
					c1DotList.get(user3List.get(i).getIndex()).setAdd(false);
					user3List.remove(i);
					return;
				}
			}
		}
		if(user2List.size()>0){
			for(int i=0;i<user2List.size();i++){
				if(userCode.equals(user2List.get(i).getId())){
					//RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewWithTag(retivTag+userCode);
					RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewById(retivIdTag+Integer.parseInt(userCode));
					userGroupLayout.removeView(rl);
					c2DotList.get(user2List.get(i).getIndex()).setAdd(false);
					user2List.remove(i);
					return;
				}
			}
		}
		if(user1List.size()>0){
			for(int i=0;i<user1List.size();i++){
				if(userCode.equals(user1List.get(i).getId())){
					//RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewWithTag(retivTag+userCode);
					RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewById(retivIdTag+Integer.parseInt(userCode));
					userGroupLayout.removeView(rl);
					c1DotList.get(user1List.get(i).getIndex()).setAdd(false);
					user1List.remove(i);
					return;
				}
			}
		}
	}
	//按照时间排序
	class SorByTime implements Comparator {
		public int compare(Object o1, Object o2) {
			UserBean s1 = (UserBean) o1;
			UserBean s2 = (UserBean) o2;
			if (s1.getTime() < s2.getTime())
				return 1;
			return 0;
		}
	}
	//创建第一圈上新的view,宽高为要创建view的宽高
	private void createCir1NewView(int xLocation,int yLocation,int lWidth,int lHeight,final UserBean bean,boolean isFirst){
		LogUtil.d("mytest", "收到新消息type0");
		final RelativeLayout rl = new RelativeLayout(getActivity());
		//rl.setTag(retivTag+bean.getId());
		rl.setId(retivIdTag+Integer.parseInt(bean.getId()));
		AbsoluteLayout.LayoutParams llParams = new AbsoluteLayout.LayoutParams(
				lWidth,
				lHeight, xLocation, yLocation);
		rl.setBackgroundResource(R.drawable.photo_fail);
		userGroupLayout.addView(rl, llParams);
		final ImageView gifIv = new ImageView(getActivity());
		RelativeLayout.LayoutParams gifP = new RelativeLayout.LayoutParams(
				lWidth,
				lHeight);
		rl.addView(gifIv, gifP);
		//layout与头像宽高差 像素
		int margin = DensityUtil.dip2px(getActivity(), (58-46));
		if(lWidth != userD1){
			margin = (int) (margin*lWidth/userD1);
		}
		ImageView imageViewbg = new ImageView(getActivity());
		RelativeLayout.LayoutParams imagebg = new RelativeLayout.LayoutParams(
				lWidth-margin,
				lHeight-margin);
		imageViewbg.setId(Integer.parseInt(bean.getId()));

		//imageViewbg.setTag(imvHeadTag+bean.getId());
		imageViewbg.setId(imvHeadIdTag+Integer.parseInt(bean.getId()));

		imagebg.addRule(RelativeLayout.CENTER_IN_PARENT); 
		rl.addView(imageViewbg, imagebg);
		if(lWidth != userD1){
			margin = margin+DensityUtil.dip2px(getActivity(), 2);
		}else{
			margin = margin+DensityUtil.dip2px(getActivity(), 4);
		}
		ImageView imageView = new ImageView(getActivity());
		RelativeLayout.LayoutParams imageP = new RelativeLayout.LayoutParams(
				lWidth-margin,
				lHeight-margin);
		imageView.setId(imvHeadIdTag+Integer.parseInt(bean.getId()));
		//imageView.setTag(imvHeadTag+bean.getId());
		imageP.addRule(RelativeLayout.CENTER_IN_PARENT); 
		rl.addView(imageView, imageP);
		final ImageView imageViewL = new ImageView(getActivity());
		//红点layout直径
		int dotD = DensityUtil.dip2px(getActivity(), 8);
		if(lWidth != userD1){
			dotD = (int) (dotD*lWidth/userD1);
		}
		RelativeLayout.LayoutParams imagePL = new RelativeLayout.LayoutParams(dotD,dotD);
		//基于头像底部 右侧 偏移d/4
		//imagePL.leftMargin = dotD/4;
		imagePL.rightMargin = DensityUtil.dip2px(getActivity(), 0);
		imagePL.bottomMargin = DensityUtil.dip2px(getActivity(), 2);
		//imageViewL.setTag(imvDotTag+bean.getId());
		imageViewL.setId(imvDotIdTag+Integer.parseInt(bean.getId()));
		imagePL.addRule(RelativeLayout.ALIGN_BOTTOM,imageView.getId());
		imagePL.addRule(RelativeLayout.ALIGN_RIGHT,imageViewbg.getId());
		rl.addView(imageViewL, imagePL);
		/*TextView tv = new TextView(getActivity());
		RelativeLayout.LayoutParams tvP = new RelativeLayout.LayoutParams(LayoutParams .WRAP_CONTENT,LayoutParams .WRAP_CONTENT);
		tvP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); 
		tvP.addRule(RelativeLayout.CENTER_HORIZONTAL); 
		tv.setText("11%");
		tv.setMaxLines(1);
		tv.setTextSize(8);
		tv.setGravity(Gravity.CENTER); 
		tv.setTextColor(getResources().getColor(R.color.white));
		tv.setTag(tvTag+bean.getId());
		rl.addView(tv, tvP);
		if(lWidth!=userD1){
			tv.setVisibility(View.GONE);
		}*/
		imageViewbg.setImageResource(R.drawable.biu_imageview_photo_s);
		imageView.setImageResource(R.drawable.photo_fail);
		x.image().bind(imageView, bean.getUserHead(), imageOptions);
		gifIv.setImageResource(R.drawable.biu_imageview_photo_circle);
		gifIv.startAnimation(animationUserBg);
		if(isFirst){
			gifIv.setVisibility(View.GONE);
		}
		imageViewL.setImageResource(R.drawable.biu_imageview_photo_news_s);
		if(isFirst && bean.getAlreadSeen().equals(Constants.ALREADY_SEEN)){
			imageViewL.setVisibility(View.GONE);
		}else{
			imageViewL.setVisibility(View.VISIBLE);
		}
		rl.setBackgroundResource(0);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				gifIv.startAnimation(animationHide);
				gifIv.setVisibility(View.GONE);
			}
		}, 1000);
		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(LoginUtils.isLogin(getActivity())){
					imageViewL.setVisibility(View.GONE);
					Intent intent = new Intent(getActivity(),BiuBiuReceiveActivity.class);
					intent.putExtra("referenceId", bean.getReferenceId());
					intent.putExtra("userCode", bean.getId());
					intent.putExtra("chatId", bean.getChatId());
					startActivity(intent);
				}else{
					Intent intent = new Intent(getActivity(),LoginOrRegisterActivity.class);
					startActivity(intent);
				}
			}
		});
	}
	//移动view
	public void moveUserView(double startX,double startY,double endX,double endY,UserBean userBean,float viewD,float viewD1,float viewD2){
		//final RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewWithTag(retivTag+userBean.getId());
		final RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewById(retivIdTag+Integer.parseInt(userBean.getId()));
		float scale = 0;
		float d = 0;
		if(rl.getWidth() == viewD2){
			scale = viewD2/viewD2;
			d = viewD*(1-scale)/2;
		}else{
			scale = viewD/viewD1;
			d = viewD1*(1-scale)/2;
		}
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(rl, "x", (float)startX,(float)(endX-d));
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(rl, "y", (float)startY,(float)(endY-d));
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(rl, "scaleX",  1.0f, scale);  
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(rl, "scaleY",   1.0f, scale);  
		ObjectAnimator anim5 = ObjectAnimator.ofFloat(rl, "alpha", 1.0f,0.8f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(500);
		animSet.playTogether(anim1, anim2,anim3,anim4,anim5);
		animSet.start();
		/*TextView tv = (TextView) rl.findViewWithTag(tvTag+userBean.getId());
		tv.setVisibility(View.GONE);*/
	}
	//加入所有list中的view
	protected void addAllView(ArrayList<UserBean> list,boolean isLogin) {
		int length = 0;
		if(list.size()>25){
			length = 25;
		}else{
			length = list.size();
		}
		for(int i=0;i<length;i++){
			UserBean userBean = list.get(i);
			boolean haveOneSpace = false;
			boolean haveTwoSpace = false;
			for(int j=i;j<n1;j++){
				DotBean bean = c1DotList.get(j);
				if(!bean.isAdd()){
					haveOneSpace = true;
					//添加区域标记
					userBean.setIndex(j);
					//计算放置位置
					double randomAngle = BiuUtil.getRandomAngle(n1, j, userD1, circleR1);
					int xLocation =  BiuUtil.getLocationX(randomAngle, userD1, circleR1, x0);
					int yLocation = BiuUtil.getLocationY(randomAngle, userD1, circleR1, y0);
					userBean.setX(xLocation);
					userBean.setY(yLocation);
					createCir1NewView(xLocation, yLocation, (int)userD1, (int)userD1, userBean,true);
					c1DotList.get(i).setAdd(true);
					user1List.add(userBean);
					break;
				}
			}
			if(haveOneSpace){
				continue;
			}
			for(int k = i-n1;k<n2;k++){
				DotBean dotBean = c2DotList.get(k);
				if(!dotBean.isAdd()){
					haveTwoSpace = true;
					//计算第二圈的位置x2,y2
					double randomAngle = BiuUtil.getRandomAngle(n2, k, userD2, circleR2);
					int x2 = BiuUtil.getLocationX(randomAngle, userD2, circleR2, x0);
					int y2 = BiuUtil.getLocationY(randomAngle, userD2, circleR2, y0);
					userBean.setX(x2);
					userBean.setY(y2);
					createCir1NewView(x2, y2, (int)userD2, (int)userD2, userBean,true);
					userBean.setIndex(k);
					c2DotList.get(k).setAdd(true);
					user2List.add(userBean);
					break;
				}
			}
			if(haveTwoSpace){
				continue;
			}
			for(int l = i-n1-n2;l<n3;l++){
				DotBean dotBean = c3DotList.get(l);
				if(!dotBean.isAdd()){
					//计算第3圈的位置x3,y3
					//将角度按照从小到大排列
					Collections.sort(edgeAngleList, new SorByAngle());
					double randomAngle = BiuUtil.getRandomAngleBig(edgeAngleList, n3, l, userD1, circleR3);
					int x3 = BiuUtil.getLocationX(randomAngle, userD3, circleR3, x0);
					int y3 = BiuUtil.getLocationY(randomAngle, userD3, circleR3, y0);
					createCir1NewView(x3, y3, (int)userD3, (int)userD3, userBean,true);
					userBean.setX(x3);
					userBean.setY(y3);
					c3DotList.get(l).setAdd(true);
					//移动后改变区域标记
					userBean.setIndex(l);
					user3List.add(userBean);
					break;
				}

			}
		}
		if(isLogin){
			removeHandler.post(removeR);
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		removeHandler.removeCallbacks(removeR);
		userGroupLayout.removeAllViews();
		user1List.clear();
		user2List.clear();
		user3List.clear();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SEND_BIU_REQUEST:
			if(resultCode == BiuBiuSendActivity.RESULT_OK){
				//开始倒计时
				currentTime = totalTime;
				SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.SEND_BIU_TIME, System.currentTimeMillis()+"");
				taskView.setVisibility(View.VISIBLE);
				userBiuImv.setVisibility(View.GONE);
				taskView.updeteTask(currentTime);
				taskHandler.post(taskR);
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
		UploadImgUtils.uploadPhoto(getActivity(), headPath, new BiuBooleanCallback() {

			@Override
			public void callback(boolean result) {
				// TODO Auto-generated method stub
				isUploadingPhoto = false;
				if(result){
					dismissLoadingLayout();
				}else{
					Toast.makeText(getActivity(), "上传照片失败", 1000).show();
					dismissLoadingLayout();
				}
			}
		});
	}
	public void showLoadingLayout(String loadingStr){
		if(loadingLayout == null){
			loadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout);
		}
		if(loadGif == null){
			loadGif = (GifView) view.findViewById(R.id.load_gif);
			loadGif.setGifImage(R.drawable.loadingbbbb);
			loadGif.setShowDimension(DensityUtil.dip2px(getActivity(), 30), DensityUtil.dip2px(getActivity(), 30));
			loadGif.setGifImageType(GifImageType.COVER);
		}
		if(loadTv == null){
			loadTv = (TextView) view.findViewById(R.id.loading_tv);
		}
		loadTv.setText(loadingStr);
		loadGif.setVisibility(View.VISIBLE);
		loadingLayout.setVisibility(View.VISIBLE);
	}
	//加载完毕隐藏
	public void dismissLoadingLayout(){
		if(loadingLayout == null){
			loadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout);
		}
		loadingLayout.setVisibility(View.GONE);
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
	//获取biubiu列表
	private void getAllUser(){
		user1List.clear();
		user2List.clear();
		user3List.clear();
		userGroupLayout.removeAllViews();
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GET_BIU_LIST);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("device_code",SharePreferanceUtils.getInstance().getDeviceId(getActivity(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("token",SharePreferanceUtils.getInstance().getToken(getActivity(), SharePreferanceUtils.TOKEN, ""));
			log.d("mytest","request tok=="+SharePreferanceUtils.getInstance().getToken(getActivity(), SharePreferanceUtils.TOKEN, ""));
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

			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "getall--"+result);
				JSONObject jsons;
				try {
					jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(state.equals("303")){
						Toast.makeText(getActivity(),"登录过期，请重新登录",1000).show();
						SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.TOKEN, "");
						SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.USER_NAME, "");
						SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.USER_HEAD, "");
						SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.USER_CODE, "");
						LogUtil.d("mytest", "tok---"+SharePreferanceUtils.getInstance().getToken(getActivity(), SharePreferanceUtils.TOKEN, ""));
						exitHuanxin();
						return;
					}
					if(!state.equals("200")){
						return;
					}
					JSONObject data = jsons.getJSONObject("data");
					String token = data.getString("token");
					if(token!=null&&token.length()>0){
						SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.TOKEN, token);
					}

					JSONArray userArray = data.getJSONArray("users");
					int biuCoin = data.getInt("virtual_currency");
					initBiuView(biuCoin);
					headFlag = data.getString("iconStatus");
					if(null != headFlag && !"".equals(headFlag)){
						initMsgView(Integer.parseInt(headFlag));
					}
					Gson gson = new Gson();
					JSONObject biuObject = data.optJSONObject("mylatestbiu");
					if(biuObject!=null){
						UserBean bean = gson.fromJson(biuObject.toString(), UserBean.class);
						if(null != bean){
							userBiuBean = bean;
							if(userBiuBean.getAlreadSeen().equals(Constants.UN_SEEN)){
								isBiuState = false;
								x.image().bind(userBiuImv, bean.getUserHead(), imageOptions);
							}else{
								isBiuState = true;
							}
						}
					}
					ArrayList<UserBean> list = gson.fromJson(userArray.toString(), new TypeToken<List<UserBean>>(){}.getType());
					if(null != list && list.size()>0){
						addAllView(list,true);
					}
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
				//清空本地token
				SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.HX_USER_NAME, "");
				SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.HX_USER_PASSWORD, "");
			}

			@Override
			public void onProgress(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
			}
		});

	}
	//设置消息按钮点击
	protected void initMsgView(final int flag) {
		// 头像审核flag：0：待审核  1：审核中 2：审核成功未读 3：审核成功已读  4:审核失败（第一次）未读 5：审核失败已读 6：审核失败 (未回滚)
		MainActivity.rightRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch (flag) {
				case 2:
				case 4:
				case 6:
					showShenHeDaiog("msg",Integer.parseInt(headFlag));
					break;
				default:
					((MainActivity)getActivity()).showRightMenu();
					MainActivity.newMessage.setVisibility(View.GONE);
					break;
				}
			}
		});
	}
	//设置biubiu币相关
	protected void initBiuView(final int biuCoin) {
		// TODO Auto-generated method stub
		MainActivity.biuCoinTv.setText(""+biuCoin);
		MainActivity.biuCoinLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),BiuChargeActivity.class);
				intent.putExtra("coin", biuCoin);
				startActivity(intent);
			}
		});
	}
	//标记抢我biubiu币的人为已读
	protected void updateBiuState() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_BIU_STATE);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("device_code",SharePreferanceUtils.getInstance().getDeviceId(getActivity(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("token",SharePreferanceUtils.getInstance().getToken(getActivity(), SharePreferanceUtils.TOKEN, ""));
			LogUtil.d("mytest", "biubean--"+userBiuBean.getChatId());
			requestObject.put("chat_id",userBiuBean.getChatId());
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

			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String result) {
				LogUtil.d("mytest", "updatebiu--"+result);
				JSONObject jsons;
				try {
					jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						return;
					}
					JSONObject data = jsons.getJSONObject("data");
					//					String token = data.getString("token");
					//					SharePreferanceUtils.getInstance().putShared(getActivity(), SharePreferanceUtils.TOKEN, token);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	//获取未登录时的biubiu列表
	private void getBiuListUnlogin() {
		// TODO Auto-generated method stub
		user1List.clear();
		user2List.clear();
		user3List.clear();
		userGroupLayout.removeAllViews();
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GET_UNLOGIN_BIU_LIST);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("device_code",SharePreferanceUtils.getInstance().getDeviceId(getActivity(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("token",SharePreferanceUtils.getInstance().getToken(getActivity(), SharePreferanceUtils.TOKEN, ""));
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

			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "getall--"+result);
				JSONObject jsons;
				try {
					jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						return;
					}
					JSONObject data = jsons.getJSONObject("data");
					JSONArray userArray = data.getJSONArray("users");
					Gson gson = new Gson();
					ArrayList<UserBean> list = gson.fromJson(userArray.toString(), new TypeToken<List<UserBean>>(){}.getType());
					if(null != list && list.size()>0){
						addAllView(list,false);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	Runnable removeR = new Runnable() {

		@Override
		public void run() {
			long current = System.currentTimeMillis();
			if(null != user1List && user1List.size()>0){
				Collections.sort(user1List,new SorByTime());
				UserBean bean = user1List.get(0);
				if((current - bean.getTime())>1000*60*60){
					removeView(bean.getId());
				}
			}
			if(null != user2List && user2List.size()>0){
				Collections.sort(user2List,new SorByTime());
				UserBean bean = user2List.get(0);
				if((current - bean.getTime())>1000*60*60){
					removeView(bean.getId());
				}
			}
			if(null != user3List && user3List.size()>0){
				Collections.sort(user3List,new SorByTime());
				UserBean bean = user3List.get(0);
				if((current - bean.getTime())>1000*60*60){
					removeView(bean.getId());
				}
				removeHandler.removeCallbacks(removeR);
			}
			removeHandler.postDelayed(removeR, 1000);
		}
	};
	@Override
	public void updateView(UserBean userBean,int type) {
		// TODO Auto-generated method stub
		switch (type) {
		case 0:
			//有新的匹配消息
			newUserBean = userBean;
			addCircle1View(userBean);
			break;
		case 1:
			//biubiu被抢啦
			isBiuState = false;
			userBiuBean = userBean;
			updateBiuView(userBean);
			break;
		case 2:
			//匹配的人被抢啦
			String userCode = userBean.getId();
			removeView(userCode);
			break;
		default:
			break;
		}
	}
}
