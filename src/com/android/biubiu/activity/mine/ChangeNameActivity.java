package com.android.biubiu.activity.mine;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.SharePreferanceUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChangeNameActivity extends BaseActivity {
	private RelativeLayout backLayout,completeLayout;
	private EditText nameEt;
	private TextView numberTv;
	private UserInfoBean infoBean ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_name);
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		completeLayout=(RelativeLayout) findViewById(R.id.mine_changename_wancheng_rl);
		nameEt=(EditText) findViewById(R.id.name_changName_et);
		nameEt.addTextChangedListener(textWatcher);
		backLayout=(RelativeLayout) findViewById(R.id.back_changename_mine_rl);
		numberTv=(TextView) findViewById(R.id.textSi_change_name_tv);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		completeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(null == nameEt.getText() || nameEt.getText().toString().equals("")){
					toastShort(getResources().getString(R.string.reg_one_no_nickname));
					return;
				}
				infoBean.setAboutMe(nameEt.getText().toString());
				updateInfo();
			}
		});
	}
	
	protected void updateInfo() {
		// TODO Auto-generated method stub
		RequestParams params = HttpUtils.getUpdateInfoParams(getApplicationContext(), infoBean,"nickname");
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

	public TextWatcher textWatcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			numberTv.setText(""+arg0.toString().length());
		}
	};

	

}
