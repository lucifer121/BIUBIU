package com.android.biubiu.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import com.android.biubiu.R;
import com.android.biubiu.activity.mine.UserPhotoScanActivity;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.DisplayUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class UserPagerPhotoAdapter extends PagerAdapter{

	Context context;
	ArrayList<String> photos;
	ImageOptions options;
	ArrayList<View> viewList ;
	public UserPagerPhotoAdapter(Context context,ArrayList<String> photos,ImageOptions options,ArrayList<View> viewList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.photos = photos;
		this.options = options;
		this.viewList = viewList;
		
	}
	@Override
	public float getPageWidth(int position) {
		float pagerWidth = DisplayUtils.getWindowWidth(context)-DensityUtil.dip2px(context, 100);
		float scale = DensityUtil.dip2px(context, 70)/pagerWidth;
		return  scale;
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
		View itemView = viewList.get(position);
		container.addView(itemView);
		ImageView imv = (ImageView) itemView.findViewById(R.id.userphoto_imv);
		x.image().bind(imv, photos.get(position), options);
		imv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,UserPhotoScanActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		return itemView;
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}