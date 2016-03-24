package com.android.biubiu;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.android.biubiu.R;
import com.android.biubiu.activity.LoginActivity;
import com.android.biubiu.chat.DemoHelper;
import com.android.biubiu.fragment.BiuFragment;
import com.android.biubiu.fragment.MenuLeftFragment;
import com.android.biubiu.fragment.MenuRightFragment;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LocationUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.LoginUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.LogUtil.log;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SlidingFragmentActivity implements AMapLocationListener{
	public RelativeLayout leftRl;
	public RelativeLayout rightRl;
	public static TextView biuCoinTv;
	//定位相关
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	private String TAG="MainActivity";
	private ImageView newMessage;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		if(!com.android.biubiu.utils.NetUtils.isNetworkConnected(getApplicationContext())){
			Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
		}
		biuCoinTv = (TextView) findViewById(R.id.biu_coin_tv);
		initPageFragment();
		// 初始化SlideMenu
		initRightMenu();
		// 初始化ViewPager
		initViewPager();
		//注册一个监听连接状态的listener
	//	EMClient.getInstance().addConnectionListener(new MyConnectionListener());
		if(DemoHelper.getInstance().isLoggedIn()==false){
			LogUtil.e(TAG, "未登录环信");			
			if(!SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "").equals("")){
				LogUtil.e(TAG, "有token");
				loginHuanXin(SharePreferanceUtils.getInstance().getHxUserName(getApplicationContext(), SharePreferanceUtils.HX_USER_NAME, ""),
						SharePreferanceUtils.getInstance().getHxUserName(getApplicationContext(), SharePreferanceUtils.HX_USER_PASSWORD, ""));
			}
		}else{
			log.e(TAG, "注册接收消息监听");
			EMClient.getInstance().chatManager().addMessageListener(msgListener);
	

	
			
		}
		
	//	newMessage.setVisibility(View.VISIBLE);
		location();
		log.e("Token", SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_APP_OPEN, true);
	}
	private void location() {
		// TODO Auto-generated method stub
		locationClient = new AMapLocationClient(this.getApplicationContext());
		locationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式
		locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		// 设置定位监听
		locationClient.setLocationListener(this);
		//设置定位参数相关
		locationOption.setOnceLocation(false);
		locationOption.setInterval(Long.valueOf(1000*60*10));
		// 设置定位参数
		locationClient.setLocationOption(locationOption);
		// 启动定位
		locationClient.startLocation();
	}

	Handler mHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			//开始定位
			case LocationUtils.MSG_LOCATION_START:
				break;
				// 定位完成
			case LocationUtils.MSG_LOCATION_FINISH:
				AMapLocation loc = (AMapLocation) msg.obj;
				String result = LocationUtils.getLocationStr(loc);
				String[] ss = result.split(",");
				if(ss.length==2){
					double longitude = Double.parseDouble(ss[0]);
					double latitide = Double.parseDouble(ss[1]);
					updateLocation(longitude,latitide);
					LogUtil.d("mytest", "gaode"+result);
				}
				break;
				//停止定位
			case LocationUtils.MSG_LOCATION_STOP:
				break;
			default:
				break;
			}
		};
	};

	private void initPageFragment() {
		// TODO Auto-generated method stub
		BiuFragment biuFragment = new BiuFragment();		
		getSupportFragmentManager().beginTransaction().add(R.id.page_layout, biuFragment)
		.commit();

	}
	//更新位置信息
	protected void updateLocation(double lontitide,double latitude) {
		if(!LoginUtils.isLogin(getApplicationContext())){
			return;
		}
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_LACATION);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("device_code",SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("token",SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
			requestObject.put("longitude",lontitide);
			requestObject.put("dimension",latitude);
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
				LogUtil.d("mytest", "location--"+result);
				JSONObject jsons;
				try {
					jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						return;
					}
					JSONObject data = jsons.getJSONObject("data");
					String token = data.getString("token");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	private void initViewPager() {
		newMessage=(ImageView) findViewById(R.id.new_message_main_img);
		leftRl = (RelativeLayout) findViewById(R.id.title_left_rl);
		leftRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showLeftMenu();
			}
		});
		rightRl = (RelativeLayout) findViewById(R.id.title_right_rl);
		rightRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showRightMenu();
	//			newMessage.setVisibility(View.GONE);
			}
		});

	}
	private void initRightMenu()
	{
		Fragment leftMenuFragment = new MenuLeftFragment();
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		//	menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		//		menu.setBehindWidth()
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		// menu.setBehindScrollScale(1.0f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);
		//设置右边（二级）侧滑菜单
		menu.setSecondaryMenu(R.layout.right_menu_frame);
		Fragment rightMenuFragment = new MenuRightFragment();
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.id_right_menu_frame, rightMenuFragment).commit();
	}

	public void showLeftMenu()
	{
		getSlidingMenu().showMenu();

	}

	public void showRightMenu()
	{
		getSlidingMenu().showSecondaryMenu();
	}
	public void closeMenu(){
		getSlidingMenu().showContent();
	}

	/**
	 * 实现环信 ConnectionListener接口
	 * @author lucifer
	 *
	 */
	private class MyConnectionListener implements EMConnectionListener {
		@Override
		public void onConnected() {
		}
		@Override
		public void onDisconnected(final int error) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if(error == EMError.USER_REMOVED){
						// 显示帐号已经被移除
					}else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
						// 显示帐号在其他设备登陆
					} else {
						if (NetUtils.hasNetwork(MainActivity.this)){
							//连接不到聊天服务器
							Toast.makeText(getApplicationContext(), "连接不到聊天服务器", Toast.LENGTH_SHORT).show();
						}
						else{
							//当前网络不可用，请检查网络设置
							Toast.makeText(getApplicationContext(), "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
						}			
					}
				}
			});
		}
	}

	@Override
	public void onLocationChanged(AMapLocation loc) {
		// TODO Auto-generated method stub
		if (null != loc) {
			Message msg = mHandler.obtainMessage();
			msg.obj = loc;
			msg.what = LocationUtils.MSG_LOCATION_FINISH;
			mHandler.sendMessage(msg);
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != locationClient) {
			SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.IS_APP_OPEN, false);
			// 停止定位
			locationClient.stopLocation();
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}
	
	/**
	 * 登录环信客户端 建立长连接
	 * @param username
	 * @param password
	 */
	public void loginHuanXin(String username,String password){
		if(password.equals("")||username.equals("")){
			return;			
		}
		EMClient.getInstance().login(username, password, new EMCallBack() {
			
			@Override
			public void onSuccess() {
				LogUtil.e(TAG, "登录成功环信");				
				Intent intent=new Intent(MainActivity.this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
			
			@Override
			public void onProgress(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e(TAG, "登陆环信失败！");
			}
		});
		
	}
	/**
	 * 会话消息监听
	 */
	EMMessageListener msgListener = new EMMessageListener() {
		
		 
		@Override
		public void onMessageReceived(List<EMMessage> messages) {
			//收到消息
			newMessage.setVisibility(View.VISIBLE);
			log.e(TAG, "收到消息");
		}
	 
		@Override
		public void onCmdMessageReceived(List<EMMessage> messages) {
			//收到透传消息
			//收到消息
			newMessage.setVisibility(View.VISIBLE);
			log.e(TAG, "收到透传消息");
		}
	 
		@Override
		public void onMessageReadAckReceived(List<EMMessage> messages) {
			//收到已读回执
		}
	 
		@Override
		public void onMessageDeliveryAckReceived(List<EMMessage> message) {
			//收到已送达回执
		}
	 
		@Override
		public void onMessageChanged(EMMessage message, Object change) {
			//消息状态变动
		}
	};
	 
//	记得在不需要的时候移除listener，如在activity的onDestroy()时
//	EMClient.getInstance().chatManager().removeMessageListener(msgListener);
//	监听
	
}

