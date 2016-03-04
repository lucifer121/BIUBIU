package com.android.biubiu.activity;

import com.android.biubiu.fragment.BiuFragment;
import com.android.biubiu.fragment.MenuLeftFragment;
import com.android.biubiu.fragment.MenuRightFragment;
import com.biubiu.biubiu.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends SlidingFragmentActivity {
	private ImageView leftMenu,rightMenu;

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
}