package com.android.biubiu.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.bean.UserInfoBean;

public class AboutMeActivity extends BaseActivity implements OnClickListener{
	RelativeLayout backRl;
	EditText aboutEt;
	RelativeLayout completeRl;
	UserInfoBean infoBean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_me_layout);
		getIngentInfo();
		initView();
	}
	private void getIngentInfo() {
		// TODO Auto-generated method stub
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
	}
	private void initView() {
		// TODO Auto-generated method stub
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);
		aboutEt = (EditText) findViewById(R.id.about_me_tv);
		completeRl = (RelativeLayout) findViewById(R.id.complete_rl);
		completeRl.setOnClickListener(this);
		aboutEt.setText(infoBean.getAboutMe());
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_rl:
			finish();
			break;
		case R.id.complete_rl:
			if(null == aboutEt.getText() || aboutEt.getText().toString().equals("")){
				toastShort(getResources().getString(R.string.page_no_aboutme));
				return;
			}
			infoBean.setAboutMe(aboutEt.getText().toString());
			Intent intent = new Intent();
			intent.putExtra("userInfoBean", infoBean);
			Log.d("mytest", "intent"+intent);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}
}
