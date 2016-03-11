package com.android.biubiu.activity.mine;

import com.android.biubiu.R;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.activity.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_name);
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
				toastShort("wancheng");
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
