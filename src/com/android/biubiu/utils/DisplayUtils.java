package com.android.biubiu.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取屏幕的尺寸
 * 
 * @author 张传强
 * 
 */
public class DisplayUtils {

	private static DisplayUtils instance;
	private Activity mActivity;

	private DisplayUtils(Activity mActivity) {
		this.mActivity = mActivity;
	}

	public static DisplayUtils getInstance(Activity mActivity) {
		if (instance == null) {
			instance = new DisplayUtils(mActivity);
		}
		return instance;
	}

	public final int[] getScreenSize() {
		int[] size = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		size[0] = dm.widthPixels;
		size[1] = dm.heightPixels;
		return size;
	}

	/**
	 * 得到屏幕的宽
	 * 
	 * @param mActivity
	 * @return
	 */

	public final static int getWindowWidth(Activity mActivity) {
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 得到屏幕的高
	 * 
	 * @param mActivity
	 * @return
	 */
	public final static int getWindowHeight(Activity mActivity) {
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 得到屏幕的宽
	 * 
	 * @param context
	 * @return
	 */
	public final static int getWindowWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 得到屏幕的高
	 * 
	 * @param context
	 * @return
	 */
	public final static int getWindowHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
}
