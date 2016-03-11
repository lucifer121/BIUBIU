package com.android.biubiu.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;

public class ScanUserHeadActivity extends BaseActivity implements OnClickListener{
	boolean isMyself = true;
	RelativeLayout titleRl;
	RelativeLayout backRl;
	RelativeLayout editRl;
	ImageView headImv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_userhead_layout);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		titleRl = (RelativeLayout) findViewById(R.id.title_rl);
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);
		editRl = (RelativeLayout) findViewById(R.id.edit_rl);
		editRl.setOnClickListener(this);
		headImv = (ImageView) findViewById(R.id.head_imv);
		
		if(isMyself){
			titleRl.setVisibility(View.VISIBLE);
		}else{
			titleRl.setVisibility(View.GONE);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_rl:
			finish();
			break;
		case R.id.edit_rl:
			break;
		default:
			break;
		}
	}

}
