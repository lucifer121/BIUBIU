package com.android.biubiu;

import cc.imeetu.iu.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutOurActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout versionLayout;
	private RelativeLayout commentLayout;
	private TextView versionTv;
	private TextView phoneTv;
	private TextView weixinTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_our);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		versionLayout = (RelativeLayout) findViewById(R.id.version_layout);
		commentLayout = (RelativeLayout) findViewById(R.id.comment_layout);
		versionTv = (TextView) findViewById(R.id.version_tv);
		phoneTv = (TextView) findViewById(R.id.phone_tv);
		weixinTv = (TextView) findViewById(R.id.weixin_tv);
		
		versionLayout.setOnClickListener(this);
		commentLayout.setOnClickListener(this);
		phoneTv.setOnClickListener(this);
		weixinTv.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.version_layout:

			break;
		case R.id.comment_layout:

			break;
		case R.id.weixin_tv:

			break;
		case R.id.phone_tv:

			break;

		default:
			break;
		}
	}
}
