package com.android.biubiu.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import com.android.biubiu.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
		return viewList.size();
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(viewList.get(position));
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View view = viewList.get(position);
		container.addView(view);
		ImageView imv = (ImageView) view.findViewById(R.id.scan_pager_imv);
		x.image().bind(imv, photos.get(position), options);
		return view;
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
