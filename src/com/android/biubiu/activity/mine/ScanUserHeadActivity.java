package com.android.biubiu.activity.mine;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;

public class ScanUserHeadActivity extends BaseActivity implements OnClickListener{
	boolean isMyself = true;
	RelativeLayout scanLayout;
	RelativeLayout editRl;
	ImageView headImv;
	String userHeadStr = "";
	ImageOptions imageOptions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_userhead_layout);
		getIntentInfo();
		initView();
	}
	private void getIntentInfo() {
		// TODO Auto-generated method stub
		userHeadStr = getIntent().getStringExtra("userhead");
	}
	private void initView() {
		// TODO Auto-generated method stub
		scanLayout = (RelativeLayout) findViewById(R.id.scan_linear);
		scanLayout.setOnClickListener(this);
		editRl = (RelativeLayout) findViewById(R.id.edit_rl);
		editRl.setOnClickListener(this);
		headImv = (ImageView) findViewById(R.id.head_imv);
		
		if(isMyself){
			editRl.setVisibility(View.VISIBLE);
		}else{
			editRl.setVisibility(View.GONE);
		}
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.ic_launcher)
		.setFailureDrawableId(R.drawable.ic_launcher)
		.build();
		x.image().bind(headImv, userHeadStr, imageOptions);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scan_linear:
			finish();
			break;
		case R.id.edit_rl:
			break;
		default:
			break;
		}
	}

}
