package com.android.biubiu.adapter;

import java.util.ArrayList;

import org.xutils.image.ImageOptions;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class ScanPagerAdapter extends PagerAdapter{
	Context context;
	ArrayList<String> photos;
	ImageOptions options;
	ArrayList<View> viewList ;
	public ScanPagerAdapter(Context context,ArrayList<String> photos,ImageOptions options,ArrayList<View> viewList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.photos = photos;
		this.options = options;
		this.viewList = viewList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
