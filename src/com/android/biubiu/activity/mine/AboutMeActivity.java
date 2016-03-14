package com.android.biubiu.activity.mine;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.SharePreferanceUtils;

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
	private void updateInfo() {
		// TODO Auto-generated method stub
		RequestParams params = HttpUtils.getUpdateInfoParams(getApplicationContext(), infoBean,"aboutMe");
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort(jsons.getString("error"));
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
					String token = data.getString("token");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
					Intent intent = new Intent();
					intent.putExtra("userInfoBean", infoBean);
					setResult(RESULT_OK, intent);
					finish();
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
		case R.id.back_rl:
			finish();
			break;
		case R.id.complete_rl:
			if(null == aboutEt.getText() || aboutEt.getText().toString().equals("")){
				toastShort(getResources().getString(R.string.page_no_aboutme));
				return;
			}
			infoBean.setAboutMe(aboutEt.getText().toString());
			updateInfo();
			break;
		default:
			break;
		}
	}
}
