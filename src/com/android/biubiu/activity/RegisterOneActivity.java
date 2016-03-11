package com.android.biubiu.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import com.android.biubiu.R;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.PerfectInformation;
import com.android.biubiu.utils.BitmapUtils;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DateUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
	private TextView birthTv,uSexTv;
	private EditText uNameEt;
	private Long birthLong;
	private ImageView ivman_selector,ivwoman_selector;
	private TextView addHeadTv;
	private TextView verifyTv;
	private ImageView userHeadImv;

	private static final int SELECT_PHOTO = 1001;
	private static final int CROUP_PHOTO = 1002;
	Bitmap userheadBitmap = null;

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
		birthTv=(TextView) findViewById(R.id.brith_registerone_tv);
		brithdayLayout=(RelativeLayout) findViewById(R.id.registerone_center4_rl);
		brithdayLayout.setOnClickListener(this);

		sexLayout=(RelativeLayout) findViewById(R.id.registerone_center3_rl);

		uSexTv=(TextView) findViewById(R.id.sex_registerone_tv);
		uNameEt=(EditText) findViewById(R.id.name_registerone_et);
		userHeadLayout=(RelativeLayout) findViewById(R.id.registerone_center1_rl);
		userHeadLayout.setOnClickListener(this);
		sexLayout.setOnClickListener(this);
		uNameEt.addTextChangedListener(watcher);
		nextLayout.setOnClickListener(this);
		addHeadTv = (TextView) findViewById(R.id.add_userhead_tv);
		verifyTv = (TextView) findViewById(R.id.virify_tv);
		userHeadImv = (ImageView) findViewById(R.id.userhead_imv);

		addHeadTv.setVisibility(View.VISIBLE);
		verifyTv.setVisibility(View.GONE);

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
		if(uNameEt.getText().length()>0&&uSexTv.getText().length()>0&&birthTv.getText().length()>0){
			nextLayout.setBackgroundResource(R.drawable.register_btn_normal);		
		}else{
			nextLayout.setBackgroundResource(R.drawable.register_btn_clk);	
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
			showHeadDialog();
			break;
		case R.id.registerone_center3_rl:
			initPopupWindowSex();
			popupWindowSex.showAsDropDown(brithdayLayout, 0, 100);

			break;
		case R.id.next_registerone_rl:
			nextStep();
			break;
		default:
			break;
		}

	}
	private void nextStep() {
		// TODO Auto-generated method stub
		if(userheadBitmap == null){
			toastShort(getResources().getString(R.string.reg_one_no_userhead));
			return;
		}
		if(null == uNameEt.getText().toString() || uNameEt.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_one_no_nickname));
			return;
		}
		if(null == uSexTv.getText().toString() || uSexTv.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_one_no_sex));
			return;
		}
		if(null == birthTv.getText().toString() || birthTv.getText().toString().equals("")){
			toastShort(getResources().getString(R.string.reg_one_no_birth));
			return;
		}
		UserInfoBean bean = new UserInfoBean();
		bean.setBirthday(birthTv.getText().toString());
		bean.setNickname(uNameEt.getText().toString());
		if(uSexTv.getText().toString().equals("男")){
			bean.setSex(Constants.SEX_MALE);
		}else{
			bean.setSex(Constants.SEX_FAMALE);
		}
		Intent intent=new Intent(this,RegisterTwoActivity.class);
		intent.putExtra("infoBean", bean);
		intent.putExtra("userhead", userheadBitmap);
		startActivity(intent);
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
				birthTv.setText(year + "-" + (month + 1) + "-" + day);
				changeNextBg();
				// 转成时间戳
				birthLong = DateUtils.getStringToDate(birthTv.getText()
						.toString());
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
					uSexTv.setText("男");
					changeNextBg();
					popupWindowSex.dismiss();
				}
			});
			ivwoman_selector.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					uSexTv.setText("女");
					changeNextBg();
					popupWindowSex.dismiss();
				}
			});
		}
	}
	public void showHeadDialog() {
		 PerfectInformation.headDialog(RegisterOneActivity.this, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");
				startActivityForResult(intent, SELECT_PHOTO);
				dialog.dismiss();
			}
		},new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	/**
	 * 调用系统的裁剪功能
	 * 
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		// 调用拍照的裁剪功能
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽和搞的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// // outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROUP_PHOTO);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				cropPhoto(data.getData());// 裁剪图片
			}
			break;
		case CROUP_PHOTO:
			if (data != null) {
				Bundle extras = data.getExtras();
				userheadBitmap = extras.getParcelable("data");
				userHeadImv.setImageBitmap(userheadBitmap);
				addHeadTv.setVisibility(View.GONE);
				verifyTv.setBackgroundResource(R.drawable.register_imageview_photo_bg);
				verifyTv.setText("待审核");
				verifyTv.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}
}
