package com.android.biubiu.activity.mine;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.imeetu.iu.R;

import com.android.biubiu.AboutOurActivity;
import com.android.biubiu.BaseActivity;

public class SuperMainInfoActivity extends BaseActivity{
	LinearLayout weixinLayout;
	TextView weixinTv;
	RelativeLayout backRl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.superman_info_layout);
		weixinLayout = (LinearLayout) findViewById(R.id.weixin_layout);
		weixinTv = (TextView) findViewById(R.id.weixin_tv);
		weixinLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ClipboardManager copy = (ClipboardManager) SuperMainInfoActivity.this  
						.getSystemService(Context.CLIPBOARD_SERVICE);  
						copy.setText(weixinTv.getText().toString());
						toastShort("已复制");
			}
		});
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
