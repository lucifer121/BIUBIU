package com.android.biubiu.activity.mine;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import android.content.Intent;
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
import com.android.biubiu.bean.UserPhotoBean;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;

public class UserPhotoScanActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout backRl;
	private TextView indexTv;
	private RelativeLayout deleteRl;
	private ViewPager photoPager;
	private ArrayList<UserPhotoBean> photoList = new ArrayList<UserPhotoBean>();
	private int currentIndex = 0;
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
		ArrayList<UserPhotoBean> list = (ArrayList<UserPhotoBean>) getIntent().getSerializableExtra("photolist");
		if(list != null && list.size()>0){
			photoList.addAll(list);
		}
		currentIndex = getIntent().getIntExtra("photoindex", 0);
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
		indexTv.setText((currentIndex+1)+"/"+photoList.size());
		photoViews.clear();
		for(int i=0;i<photoList.size();i++){
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.scanpager_photo_item, null);
			photoViews.add(view);
		}
		photoPager.setOffscreenPageLimit(3);
		scanAdapter = new ScanPagerAdapter(getApplicationContext(), photoList, imageOptions, photoViews);
		photoPager.setAdapter(scanAdapter);
		photoPager.setCurrentItem(currentIndex);
		photoPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				currentIndex = arg0;
				indexTv.setText((currentIndex+1)+"/"+photoList.size());
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
	private void deletePhoto() {
		// TODO Auto-generated method stub
		String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		String fileCode = photoList.get(currentIndex).getPhotoCode();
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.DELETE_PHOTO);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token",token);
			requestObject.put("device_code", deviceId);
			requestObject.put("photo_code", fileCode);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "error--"+ex.getMessage());
				LogUtil.d("mytest", "error--"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "deleteph=="+result);
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort(jsons.getString("error"));
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
					String token = data.getString("token");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					photoList.remove(currentIndex);
					scanAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			deletePhoto();
			break;
		default:
			break;
		}
	}
}
