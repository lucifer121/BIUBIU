package com.android.biubiu;

import cc.imeetu.iu.R;
import cc.imeetu.iu.R.layout;
import cc.imeetu.iu.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.RelativeLayout;

public class UserAgreementActivity extends BaseActivity {
	private RelativeLayout backLayout;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_agreement);
		backLayout=(RelativeLayout) findViewById(R.id.back_agreement_rl);
		mWebView=(WebView) findViewById(R.id.webview_user_agreement);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mWebView.loadUrl("http://protect-app.oss-cn-beijing.aliyuncs.com/app-resources/html/protocol/UserProtocol.html");
	}

	

}
