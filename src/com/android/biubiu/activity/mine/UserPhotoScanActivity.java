package com.android.biubiu.activity.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.biubiu.R;
import com.android.biubiu.activity.BaseActivity;

public class UserPhotoScanActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout backRl;
	private TextView indexTv;
	private RelativeLayout deleteRl;
	private ViewPager photoPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userphoto_scan_layout);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		backRl = (RelativeLayout) findViewById(R.id.title_back_rl);
		backRl.setOnClickListener(this);
		indexTv = (TextView) findViewById(R.id.photo_index_tv);
		deleteRl = (RelativeLayout) findViewById(R.id.delete_rl);
		deleteRl.setOnClickListener(this);
		photoPager = (ViewPager) findViewById(R.id.userphoto_scan_pager);
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
