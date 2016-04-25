package com.android.biubiu.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.CommonDialog;
import com.android.biubiu.common.Umutils;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.OnWheelChangedListener2;
import com.android.biubiu.common.city.WheelView2;
import com.android.biubiu.utils.BitmapUtils;
import com.android.biubiu.utils.CaculateDateUtils;
import com.android.biubiu.utils.CloseJianpan;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DateUtils;
import com.android.biubiu.utils.LogUtil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterOneActivity extends BaseActivity implements OnClickListener,OnWheelChangedListener2{
	private RelativeLayout backLayout,brithdayLayout,sexLayout, userHeadLayout,nextLayout;
	private TextView birthTv,uSexTv;
	private EditText uNameEt;
	private Long birthLong;
	private ImageView ivman_selector,ivwoman_selector;
	private TextView addHeadTv;
	private TextView verifyTv;
	private ImageView userHeadImv;
	private WheelView2	mViewYear,mViewMonth,mViewDay;
	private String[] years,months,days;
	String currentYear="",currentMonth="",currentDay="";
	private TextView dateComplete;

	private static final int SELECT_PHOTO = 1001;
	private static final int CROUP_PHOTO = 1002;
	Bitmap userheadBitmap = null;
	String headPath = "";

	private RelativeLayout sexDiolagLayout;

	private Boolean isSex=false;//是否点击了性别选择器
	String phontNum = "";
	String password = "";
	Uri croupUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registerone_layout);
		getIntentInfo();
		initView();
		Umutils.count(this, Umutils.IN_REGISTER_ACTIVITY);
	}

	private void getIntentInfo() {
		// TODO Auto-generated method stub
		phontNum = getIntent().getStringExtra("phone");
		password = getIntent().getStringExtra("password");
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
		sexDiolagLayout=(RelativeLayout) findViewById(R.id.sex_dialog_sex_selector_rl);

		ivman_selector = 
				(ImageView) findViewById(R.id.iv_man_sexselector);
		ivwoman_selector = (ImageView) 
				findViewById(R.id.iv_woman_sexselector);
		ivman_selector.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				uSexTv.setText("男");
				changeNextBg();
				sexDiolagLayout.setVisibility(View.GONE);
			}
		});
		ivwoman_selector.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				uSexTv.setText("女");
				changeNextBg();

				sexDiolagLayout.setVisibility(View.GONE);
			}
		});
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

			nextLayout.setBackgroundResource(R.drawable.register_btn_disabled);	

		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			//overridePendingTransition(0,R.anim.right_out_anim);
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
			break;
		case R.id.registerone_center4_rl:
			//生日点击
			initPhotoWindowDate();
			datePop.showAsDropDown(brithdayLayout, 0, 100);
			sexDiolagLayout.setVisibility(View.GONE);
			break;
		case R.id.registerone_center1_rl:
			//头像点击
			showHeadDialog();
			sexDiolagLayout.setVisibility(View.GONE);
			break;
		case R.id.registerone_center3_rl:
			//			initPopupWindowSex();
			//			popupWindowSex.showAsDropDown(brithdayLayout, 0, 100);

			if(isSex==false){
				isSex=true;
				CloseJianpan.closeKeyboard(this, uNameEt);
				sexDiolagLayout.setVisibility(View.VISIBLE);
			}else{
				isSex=false;

				sexDiolagLayout.setVisibility(View.GONE);
			}
			break;
		case R.id.next_registerone_rl:
			nextStep();
			sexDiolagLayout.setVisibility(View.GONE);
			break;
		default:
			break;
		}

	}
	private PopupWindow datePop;
	private void initPhotoWindowDate(){
		if (datePop == null) {
			View view = LayoutInflater.from(this).inflate(R.layout.date_wheel_view,
					null);
			datePop = new PopupWindow(view,
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			// 设置外观
			datePop.setFocusable(true);
			datePop.setOutsideTouchable(true);
			ColorDrawable colorDrawable = new ColorDrawable();
			datePop.setBackgroundDrawable(colorDrawable);
			// tvTitle=(TextView)view.findViewById(R.id.tvcolectList);

			mViewYear = (WheelView2) view.findViewById(R.id.id_year);
			mViewMonth = (WheelView2) view.findViewById(R.id.id_month);
			mViewDay = (WheelView2) view.findViewById(R.id.id_day);
			dateComplete = (TextView) view
					.findViewById(R.id.date_complete_tv);

			// 添加change事件
			mViewYear.addChangingListener(RegisterOneActivity.this);
			// 添加change事件
			mViewMonth.addChangingListener(RegisterOneActivity.this);
			// 添加change事件
			mViewDay
			.addChangingListener(RegisterOneActivity.this);
			// 添加onclick事件
			dateComplete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					setBirthData(currentYear, currentMonth, currentDay);
					datePop.dismiss();
				}
			});
			setYearData();
		}
	}

	private void setYearData() {
		// TODO Auto-generated method stub
		years = CaculateDateUtils.getInstance().getYearSs(1975, 2100);
		mViewYear.setViewAdapter(new ArrayWheelAdapter<String>(
				RegisterOneActivity.this, years));
		// 设置可见条目数量
		mViewYear.setVisibleItems(7);
		mViewMonth.setVisibleItems(7);
		mViewDay.setVisibleItems(7);
		if(currentYear.equals("")){
			currentYear = years[0];
		}
		updateMonthData();
	}

	private void updateMonthData() {
		// TODO Auto-generated method stub
		months = CaculateDateUtils.getInstance().getMonthSs();
		mViewMonth.setViewAdapter(new ArrayWheelAdapter<String>(
				RegisterOneActivity.this, months));
		mViewMonth.setCurrentItem(0);
		if(currentMonth.equals("")){
			currentMonth = months[0];
		}
		updateDayData();
	}
	private void updateDayData() {
		// TODO Auto-generated method stub
		int currentY = Integer.parseInt(currentYear.replace("年", ""));
		int currentM = Integer.parseInt(currentMonth.replace("月", ""));
		days = CaculateDateUtils.getInstance().getDaySs(currentY, currentM);
		mViewDay.setViewAdapter(new ArrayWheelAdapter<String>(
				RegisterOneActivity.this, days));
		mViewDay.setCurrentItem(0);
		if(currentDay.equals("")){
			currentDay = days[0];
		}
	}
	private void setBirthData(String yearStr,String monthStr,String dayStr) {
		int year=Integer.parseInt(yearStr.replace("年", ""));
		int month=Integer.parseInt(monthStr.replace("月", ""));
		int day=Integer.parseInt(dayStr.replace("日", ""));
		String dateStr = year + "-" + month + "-" + day;				
		// 转成时间戳
		birthLong = DateUtils.getStringToDate(birthTv.getText()
				.toString());
		if(birthLong>System.currentTimeMillis()){
			toastShort("请选择有效日期");	
			birthTv.setText("");
		}else{
			birthTv.setText(year + "-" + month + "-" + day);
		}
		changeNextBg();
	}

	private void nextStep() {
		// TODO Auto-generated method stub
		if(userheadBitmap == null){
			toastShort(getResources().getString(R.string.reg_one_no_userhead));
			return;
		}
		if(headPath.equals("")){
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
		bean.setSex(bean.getSexFlag(uSexTv.getText().toString()));
		Intent intent=new Intent(this,RegisterTwoActivity.class);
		intent.putExtra("infoBean", (Serializable)bean);
		intent.putExtra("userhead", userheadBitmap);
		intent.putExtra("headPath", headPath);
		intent.putExtra("phone", phontNum);
		intent.putExtra("password", password);
		startActivity(intent);
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
		CommonDialog.headDialog(RegisterOneActivity.this, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent ;
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
					intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
				} else {
					intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				}
				/*Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");*/
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
		croupUri = uri;
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
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, croupUri);
		startActivityForResult(intent, CROUP_PHOTO);
	}
	public String saveHeadImg(Bitmap head) {
		FileOutputStream fos = null;
		String path = "";
		path = Environment.getExternalStorageDirectory()
				+ "/biubiu/"+System.currentTimeMillis()+".png";
		File file = new File(path);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			head.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;

	}
	private Bitmap decodeUriAsBitmap(Uri uri){
	    Bitmap bitmap = null;
	    try {
	        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    }
	    return bitmap;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				if(data != null){
					cropPhoto(data.getData());// 裁剪图片
				}
			}
			break;
		case CROUP_PHOTO:
			try {
				if (croupUri != null) {
					userheadBitmap =  decodeUriAsBitmap(croupUri);
					if(userheadBitmap != null){
						headPath = saveHeadImg(userheadBitmap);
						userHeadImv.setImageBitmap(userheadBitmap);
						addHeadTv.setVisibility(View.GONE);
						verifyTv.setBackgroundResource(R.drawable.register_imageview_photo_bg);
						verifyTv.setText("待审核");
						verifyTv.setVisibility(View.VISIBLE);
					}else{
						userHeadImv.setImageResource(R.drawable.regist_imageview_intophoto);
						addHeadTv.setVisibility(View.VISIBLE);
						verifyTv.setVisibility(View.GONE);
					}
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onChanged(WheelView2 wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if(wheel == mViewYear){
			int index = mViewYear.getCurrentItem();
			currentYear = years[index];
			updateMonthData();
			setBirthData(currentYear, currentMonth, currentDay);
		}else if(wheel == mViewMonth){
			int index = mViewMonth.getCurrentItem();
			currentMonth = months[index];
			updateDayData();
			setBirthData(currentYear, currentMonth, currentDay);
		}else if(wheel == mViewDay){
			int index = mViewDay.getCurrentItem();
			currentDay = days[index];
			setBirthData(currentYear, currentMonth, currentDay);
		}
	}
}
