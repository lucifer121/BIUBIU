package com.android.biubiu.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import cc.imeetu.iu.R;

import com.android.biubiu.bean.UserPhotoBean;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScanPagerAdapter extends PagerAdapter{
	Context context;
	ArrayList<UserPhotoBean> photos;
	ImageOptions options;
	public ScanPagerAdapter(Context context,ArrayList<UserPhotoBean> photos,ImageOptions options) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.photos = photos;
		this.options = options;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return photos.size();
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View)object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		UserPhotoBean bean = photos.get(position);
		View view = LayoutInflater.from(context).inflate(R.layout.scanpager_photo_item, null);
		container.addView(view);
		ImageView imv = (ImageView) view.findViewById(R.id.scan_pager_imv);
		x.image().bind(imv, bean.getPhotoOrigin(), options);
		return view;
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
