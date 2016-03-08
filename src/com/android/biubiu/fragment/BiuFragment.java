package com.android.biubiu.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.android.biubiu.R;
import com.android.biubiu.activity.biu.BiuBiuSendActivity;
import com.android.biubiu.bean.DotBean;
import com.android.biubiu.bean.UserBean;
import com.android.biubiu.utils.BiuUtil;
import com.android.biubiu.view.BiuView;
import com.android.biubiu.view.TaskView;
import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;




import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

@SuppressLint("NewApi")
public class BiuFragment extends Fragment {
	View view;
	//启动发biubiu页面的requestcode
	public static final int SEND_BIU_REQUEST = 1001;
	//屏幕宽高
	int width = 0;
	int height = 0;
	Handler handler;
	//圆心坐标
	float x0 = 0;
	float y0 = 0;
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
	int n3 = 10;
	//倒计时总时间和当前时间
	int totalTime = 60;
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

	//线圈view
	BiuView biuView;
	//背景动画
	GifView backgroundGif;
	//中间biubiu layout
	RelativeLayout biuLayout;
	//biubiu动画
	GifView userGif;
	//头像IMv
	ImageView userBiuImv;
	//倒计时view
	TaskView taskView;
	//放置接受用户信息layout
	AbsoluteLayout userGroupLayout;

	//测试按钮
	Button testBtn;
	//是否一次性全部加上
	boolean isAddAll = true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.biu_fragment_layout, null);
		init();
		drawBiuView();
		setBiuLayout();
		initTestBtn();
		initUserGroup();
		return view;
	}
	private void init() {
		// TODO Auto-generated method stub
		width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		handler = new Handler();
		x0 = width / 2;
		y0 = width *3/ 5;

		biuView = (BiuView) view.findViewById(R.id.biu_view);
		biuLayout = (RelativeLayout) view.findViewById(R.id.biu_layout);
		userGif = (GifView) view.findViewById(R.id.user_receive_anim);
		userBiuImv = (ImageView) view.findViewById(R.id.user_biu);
		backgroundGif = (GifView) view.findViewById(R.id.background_gif);
		taskView = (TaskView) view.findViewById(R.id.task_view);
		userGroupLayout = (AbsoluteLayout) view.findViewById(R.id.user_group_layout);

		backgroundGif.setGifImage(R.drawable.background);
		backgroundGif.setShowDimension(width, height);
		backgroundGif.setGifImageType(GifImageType.COVER);

		testBtn = (Button) view.findViewById(R.id.test_btn);
	}
	private void drawBiuView() {
		// TODO Auto-generated method stub
		circleR1 = width/5;
		circleR2 = width*2/5;
		circleR3 = width*11/20;
		//设置半径
		biuView.setCircleR(circleR1, circleR2, circleR3);
		//设置圆心
		biuView.setDot(x0, y0); 
	}
	private void setBiuLayout() {
		/**
		 * 外层动画
		 */
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width/5, width/5);
		params.leftMargin = width *2/5 ;
		params.topMargin = width / 2;
		biuLayout.setLayoutParams(params);
		RelativeLayout.LayoutParams animParams = new RelativeLayout.LayoutParams(width/5, width/5);
		userGif.setLayoutParams(animParams);
		//设置闪烁动画相关
		userGif.setGifImage(R.drawable.anim);
		userGif.setShowDimension(width/5, width/5);
		userGif.setGifImageType(GifImageType.COVER);
		userGif.setVisibility(View.GONE);
		/***
		 * 头像及倒计时
		 */
		RelativeLayout.LayoutParams imvParams = new RelativeLayout.LayoutParams(width*3/20, width*3/20);
		imvParams.leftMargin = width / 40 ;
		imvParams.topMargin = width / 40;
		userBiuImv.setLayoutParams(imvParams);
		//倒计时view
		drawTaskView();
		/**
		 * 外层biubiu点击
		 * */
		biuLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentTime > 0){
					return;
				}
				//启动发送biubiu界面
				Intent intent = new Intent(getActivity(),BiuBiuSendActivity.class);
				startActivityForResult(intent, SEND_BIU_REQUEST);
			}
		});
	}
	//设置倒计时按钮
	private void drawTaskView() {
		// TODO Auto-generated method stub
		taskView.setDot(width / 2, width *3/ 5);
		taskView.setRadius(width *5/80, width/80);
		taskView.setTotal(totalTime);
	}
	//倒计时线程
	Runnable r=new Runnable() {

		@Override
		public void run() {
			taskView.updeteTask(currentTime--);
			if(currentTime <= 0){
				taskView.setVisibility(View.GONE);
				userBiuImv.setVisibility(View.VISIBLE);
				currentTime = 0;
				handler.removeCallbacks(r);
				return;
			}
			handler.postDelayed(r, 1000);
		}
	};
	//放置接收到的用户
	private void initUserGroup() {
		//给三个头像layout赋值
		userD1 = width/8;
		userD2 = width/10;
		userD3 = width*3/40;
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
	//往第一个圈上放view
	private void addCircle1View(UserBean userBean) {
		boolean haveSpace = false;
		for(int i=0;i<n1;i++){
			DotBean bean = c1DotList.get(i);
			if(!bean.isAdd()){
				//添加区域标记
				userBean.setIndex(i);
				haveSpace = true;
				//计算放置位置
				double randomAngle = BiuUtil.getRandomAngle(n1, i, userD1, circleR1);
				int xLocation =  BiuUtil.getLocationX(randomAngle, userD1, circleR1, x0);
				int yLocation = BiuUtil.getLocationY(randomAngle, userD1, circleR1, y0);
				userBean.setX(xLocation);
				userBean.setY(yLocation);
				createCir1NewView(xLocation, yLocation, (int)userD1, (int)userD1, userBean);
				c1DotList.get(i).setAdd(true);
				user1List.add(userBean);
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
			RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewById(Integer.valueOf(1+delBean.getId()));
			userGroupLayout.removeView(rl);
			//改变空闲标记，第二圈移到外圈
			c3DotList.get(delBean.getIndex()).setAdd(false);
			user3List.remove(0);
			moveTwoToThree(oneUserBean,twoUserBean);
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
	//创建第一圈上新的view
	private void createCir1NewView(int xLocation,int yLocation,int lWidth,int lHeight,UserBean bean){
		final RelativeLayout rl = new RelativeLayout(getActivity());
		rl.setId(Integer.valueOf(1+bean.getId()));
		AbsoluteLayout.LayoutParams llParams = new AbsoluteLayout.LayoutParams(
				lWidth,
				lHeight, xLocation, yLocation);
		final GifView gifView = new GifView(getActivity());
		RelativeLayout.LayoutParams gifP = new RelativeLayout.LayoutParams(
				lWidth,
				lHeight);
		gifView.setGifImage(R.drawable.anim);
		gifView.setShowDimension(lWidth, lHeight);
		gifView.setGifImageType(GifImageType.COVER);
		rl.addView(gifView, gifP);
		if(lWidth != userD1){
			gifView.setVisibility(View.GONE);
		}
		ImageView imageView = new ImageView(getActivity());
		int margin = 35;
		if(lWidth != userD1){
			margin = (int) (margin*lWidth/userD1);
		}
		RelativeLayout.LayoutParams imageP = new RelativeLayout.LayoutParams(
				lWidth-margin,
				lHeight-margin);
		imageView.setId(Integer.valueOf(2+bean.getId()));
		imageP.addRule(RelativeLayout.CENTER_IN_PARENT); 
		imageView.setImageResource(R.drawable.chat_img_profiles_default);
		rl.addView(imageView, imageP);

		final ImageView imageViewL = new ImageView(getActivity());
		int dotD = 20;
		if(lWidth != userD1){
			dotD = (int) (dotD*lWidth/userD1);
		}
		RelativeLayout.LayoutParams imagePL = new RelativeLayout.LayoutParams(dotD,dotD);
		imagePL.leftMargin = dotD/4;
		imageViewL.setId(Integer.valueOf(4+bean.getId()));
		imagePL.addRule(RelativeLayout.ALIGN_RIGHT,imageView.getId());
		imagePL.addRule(RelativeLayout.ALIGN_BOTTOM,imageView.getId());
		imageViewL.setImageResource(R.drawable.new_dot);
		rl.addView(imageViewL, imagePL);

		TextView tv = new TextView(getActivity());
		RelativeLayout.LayoutParams tvP = new RelativeLayout.LayoutParams(LayoutParams .WRAP_CONTENT,LayoutParams .WRAP_CONTENT);
		tvP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); 
		tvP.addRule(RelativeLayout.CENTER_HORIZONTAL); 
		tv.setText("11%");
		tv.setMaxLines(1);
		tv.setTextSize(8);
		tv.setGravity(Gravity.CENTER); 
		tv.setTextColor(getResources().getColor(R.color.white));
		tv.setId(Integer.valueOf(3+bean.getId()));
		rl.addView(tv, tvP);
		userGroupLayout.addView(rl, llParams);
		if(lWidth!=userD1){
			tv.setVisibility(View.GONE);
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				rl.removeView(gifView);
			}
		}, 1000);
		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imageViewL.setVisibility(View.GONE);
			}
		});
	}
	//移动view
	public void moveUserView(double startX,double startY,double endX,double endY,UserBean userBean,float viewD,float viewD1,float viewD2){
		final RelativeLayout rl = (RelativeLayout) userGroupLayout.findViewById(Integer.valueOf(1+userBean.getId()));
		float scale = 0;
		if(rl.getWidth() == viewD2){
			scale = viewD/viewD2;
		}else{
			scale = viewD/viewD1;
		}
		float d = viewD*(1-scale)/2;
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(rl, "x", (float)startX,(float)(endX-d));
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(rl, "y", (float)startY,(float)(endY-d));
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(rl, "scaleX",  1.0f, scale);  
		ObjectAnimator anim4 = ObjectAnimator.ofFloat(rl, "scaleY",   1.0f, scale);  
		AnimatorSet animSet = new AnimatorSet();
		animSet.setDuration(500);
		animSet.playTogether(anim1, anim2,anim3,anim4);
		animSet.start();
		TextView tv = (TextView) rl.findViewById(Integer.valueOf(3+userBean.getId()));
		tv.setVisibility(View.GONE);
	}
	private void initTestBtn() {
		// TODO Auto-generated method stub
		testBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*if(currentTime > 0){
					taskView.setVisibility(View.GONE);
					userBiuImv.setVisibility(View.VISIBLE);
					userGif.setVisibility(View.VISIBLE);
					currentTime = 0;
					handler.removeCallbacks(r);
					return;
				}
				if(userGif.getVisibility() == View.GONE){
					userGif.setVisibility(View.VISIBLE);
				}else{
					userGif.setVisibility(View.GONE);
				}
				 */
				if(isAddAll){
					ArrayList<UserBean> list = new ArrayList<UserBean>();
					for(int i=0;i<15;i++){
						UserBean bean = new UserBean();
						Random random = new Random();
						int idRandom = random.nextInt(10000);
						bean.setId(String.valueOf(idRandom));
						bean.setTime(System.currentTimeMillis());
						list.add(bean);
					}
					addAllView(list);
					isAddAll = false;
				}else{
					newUserBean = new UserBean();
					Random random = new Random();
					int idRandom = random.nextInt(10000);
					newUserBean.setId(String.valueOf(idRandom));
					newUserBean.setTime(System.currentTimeMillis());
					addCircle1View(newUserBean);
				}
			}
		});
	}
	protected void addAllView(ArrayList<UserBean> list) {
		for(int i=0;i<list.size();i++){
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
					createCir1NewView(xLocation, yLocation, (int)userD1, (int)userD1, userBean);
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
					createCir1NewView(x2, y2, (int)userD2, (int)userD2, userBean);
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
					createCir1NewView(x3, y3, (int)userD3, (int)userD3, userBean);
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
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		handler.removeCallbacks(r);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SEND_BIU_REQUEST:
			//开始倒计时
			currentTime = totalTime;
			taskView.setVisibility(View.VISIBLE);
			userBiuImv.setVisibility(View.GONE);
			taskView.updeteTask(currentTime);
			handler.post(r);
			break;

		default:
			break;
		}
	}
}
