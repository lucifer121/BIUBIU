package com.android.biubiu;



import com.android.biubiu.R;
import com.android.biubiu.fragment.BiuFragment;
import com.android.biubiu.fragment.MenuLeftFragment;
import com.android.biubiu.fragment.MenuRightFragment;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends SlidingFragmentActivity {
	public ImageView leftMenu;
	public ImageView rightMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initPageFragment();
		// 初始化SlideMenu
		initRightMenu();
		// 初始化ViewPager
		initViewPager();
		
		//注册一个监听连接状态的listener
		EMClient.getInstance().addConnectionListener(new MyConnectionListener());
		
	}
	private void initPageFragment() {
		// TODO Auto-generated method stub
		BiuFragment biuFragment = new BiuFragment();		
		getSupportFragmentManager().beginTransaction().add(R.id.page_layout, biuFragment)
		.commit();

	}
	private void initViewPager() {
		leftMenu=(ImageView) findViewById(R.id.id_iv_left);
		rightMenu=(ImageView) findViewById(R.id.id_iv_right);
		leftMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showLeftMenu();
			}
		});
		rightMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showRightMenu();
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
}

