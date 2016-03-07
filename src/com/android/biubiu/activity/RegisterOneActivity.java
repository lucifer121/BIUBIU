package com.android.biubiu.activity;

import com.android.biubiu.R;



import com.android.biubiu.common.PerfectInformation;
import com.android.biubiu.utils.DateUtils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterOneActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout backLayout,brithdayLayout,sexLayout, userHeadLayout,nextLayout;
	private TextView brithday,uSex;
	private EditText uName;
	private Long birthLong;
	private ImageView ivman_selector,ivwoman_selector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerone_layout);
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		
		nextLayout=(RelativeLayout) findViewById(R.id.next_registerone_rl);
		backLayout=(RelativeLayout) findViewById(R.id.back_registerone_rl);
		backLayout.setOnClickListener(this);
		brithday=(TextView) findViewById(R.id.brith_registerone_tv);
		brithdayLayout=(RelativeLayout) findViewById(R.id.registerone_center4_rl);
		brithdayLayout.setOnClickListener(this);
		
		sexLayout=(RelativeLayout) findViewById(R.id.registerone_center3_rl);
		
		uSex=(TextView) findViewById(R.id.sex_registerone_tv);
		uName=(EditText) findViewById(R.id.name_registerone_et);
		userHeadLayout=(RelativeLayout) findViewById(R.id.registerone_center1_rl);
		userHeadLayout.setOnClickListener(this);
		sexLayout.setOnClickListener(this);
		uName.addTextChangedListener(watcher);
		nextLayout.setOnClickListener(this);
	
	}
	
	/**
	 * Editview 输入框监听事件
	 */

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			changeNextBg();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};
	/**
	 * 改变下一步的背景
	 */
	private void changeNextBg(){
		if(uName.getText().length()>0&&uSex.getText().length()>0&&brithday.getText().length()>0){
			nextLayout.setBackgroundResource(R.drawable.main_btn_none);		
		}else{
			nextLayout.setBackgroundResource(R.drawable.main_btn_light);	
		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(0,R.anim.right_out_anim);
		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_registerone_rl:
			//返回
			finish();
			overridePendingTransition(0,R.anim.right_out_anim);
		case R.id.registerone_center4_rl:
			//生日点击
			showDialog(1);
			break;
		case R.id.registerone_center1_rl:
			//头像点击
			PerfectInformation.showDiolagPerfertInformation(this);
			break;
		case R.id.registerone_center3_rl:
			initPopupWindowSex();
			popupWindowSex.showAsDropDown(brithdayLayout, 0, 100);
			
			break;
		case R.id.next_registerone_rl:
			Intent intent=new Intent(this,RegisterTwoActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		Time time = new Time("GMT+8");
		time.setToNow();
		int year = time.year;
		int month = time.month;
		int day = time.monthDay;
		// TODO Auto-generated method stub

		return new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int month, int day) {
				// TODO Auto-generated method stub
				Log.e("lucifer", "DatePicker==" + arg0);
				brithday.setText(year + "-" + (month + 1) + "-" + day);
				changeNextBg();

				// 转成时间戳

				birthLong = DateUtils.getStringToDate(brithday.getText()
						.toString());
				Log.e("lucifer", "birthLong==" + birthLong);
			}
		}, year, month, day);

	}
	private PopupWindow popupWindowSex;

	private void initPopupWindowSex() {
		if (popupWindowSex == null) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.dialog_sex_selector, null);
			popupWindowSex = new PopupWindow(view,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			// 设置外观
			popupWindowSex.setFocusable(true);
			popupWindowSex.setOutsideTouchable(true);
			ColorDrawable colorDrawable = new ColorDrawable();
			popupWindowSex.setBackgroundDrawable(colorDrawable);
			// tvTitle=(TextView)view.findViewById(R.id.tvcolectList);
			ivman_selector = (ImageView) view
					.findViewById(R.id.iv_man_sexselector);
			ivwoman_selector = (ImageView) view
					.findViewById(R.id.iv_woman_sexselector);
			ivman_selector.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					uSex.setText("男");
					changeNextBg();
					popupWindowSex.dismiss();
				}
			});
			ivwoman_selector.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					uSex.setText("女");
					changeNextBg();
					popupWindowSex.dismiss();
				}
			});
		}
		

	}
}
