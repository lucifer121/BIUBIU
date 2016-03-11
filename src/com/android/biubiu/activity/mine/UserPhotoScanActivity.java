package com.android.biubiu.activity.mine;

import java.util.ArrayList;

import org.xutils.image.ImageOptions;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.adapter.ScanPagerAdapter;

public class UserPhotoScanActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout backRl;
	private TextView indexTv;
	private RelativeLayout deleteRl;
	private ViewPager photoPager;
	private ArrayList<String> photoList = new ArrayList<String>();
	private int index = 0;
	private ScanPagerAdapter scanAdapter;
	ImageOptions imageOptions;
	ArrayList<View> photoViews = new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userphoto_scan_layout);
		getIntentData();
		initView();
		setPager();
	}
	private void getIntentData() {
		// TODO Auto-generated method stub
		ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("photolist");
		if(list != null && list.size()>0){
			photoList.addAll(list);
		}
		index = getIntent().getIntExtra("photoindex", 0);
	}
	private void initView() {
		// TODO Auto-generated method stub
		backRl = (RelativeLayout) findViewById(R.id.title_back_rl);
		backRl.setOnClickListener(this);
		indexTv = (TextView) findViewById(R.id.photo_index_tv);
		deleteRl = (RelativeLayout) findViewById(R.id.delete_rl);
		deleteRl.setOnClickListener(this);
		photoPager = (ViewPager) findViewById(R.id.userphoto_scan_pager);
		
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.ic_launcher)
		.setFailureDrawableId(R.drawable.ic_launcher)
		.build();
	}
	private void setPager() {
		indexTv.setText((index+1)+"/"+photoList.size());
		photoViews.clear();
		for(int i=0;i<photoList.size();i++){
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.scanpager_photo_item, null);
			photoViews.add(view);
		}
		photoPager.setOffscreenPageLimit(3);
		scanAdapter = new ScanPagerAdapter(getApplicationContext(), photoList, imageOptions, photoViews);
		photoPager.setAdapter(scanAdapter);
		photoPager.setCurrentItem(index);
		photoPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				indexTv.setText((arg0+1)+"/"+photoList.size());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back_rl:
			finish();
			break;
		case R.id.delete_rl:
			break;
		default:
			break;
		}
	}

}
