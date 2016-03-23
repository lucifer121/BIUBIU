package com.android.biubiu.activity.mine;



import java.io.IOException;

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
import com.android.biubiu.common.MyDatePicker;
import com.android.biubiu.utils.DateUtils;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.okhttp.Callback;
import com.avos.avoscloud.okhttp.Request;
import com.avos.avoscloud.okhttp.Response;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeBrithdayActivity extends BaseActivity implements OnClickListener{
	// 控件相关
		private TextView birthday;
		private TextView queding;
		private String birth;
		private ImageView backImageView;
		private RelativeLayout backlLayout, quedingLayout;
		private long birthLong;
		private UserInfoBean infoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_brithday);
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
		birth = infoBean.getBirthday();
		intiView();
	}
	private void intiView() {

		birthday = (TextView) super.findViewById(R.id.name_changbirth_et);
		birthday.setText(birth);
		birthday.setOnClickListener(this);
		queding = (TextView) super
				.findViewById(R.id.mine_changebirth_wancheng_bt);
		// queding.setOnClickListener(this);
		backImageView = (ImageView) super
				.findViewById(R.id.back_changebirth_mine);
		// backImageView.setOnClickListener(this);
		backlLayout = (RelativeLayout) super
				.findViewById(R.id.back_changebirth_mine_rl);
		backlLayout.setOnClickListener(this);
		quedingLayout = (RelativeLayout) super
				.findViewById(R.id.mine_changebirth_wancheng_rl);
		quedingLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.name_changbirth_et:
			Time time = new Time("GMT+8");
			time.setToNow();
			int year = time.year;
			int month = time.month;
			int day = time.monthDay;

			this.showDialog(1);
			new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker arg0, int year, int month,
						int day) {
					// TODO Auto-generated method stub
					birthday.setText(year + "-" + (month + 1) + "-" + day);

					birthLong = DateUtils.getStringToDate(birthday.getText()
							.toString());
					if(birthLong>System.currentTimeMillis()){
						toastShort("请选择有效日期");	
						birthday.setText("");
					}else{
						birthday.setText(year + "-" + (month + 1) + "-" + day);
					}
					
				}
				
			}, 1995, 0, 1).show();
//			new MyDatePicker(ChangeBrithdayActivity.this, new OnDateSetListener() {
//				
//				@Override
//				public void onDateSet(DatePicker arg0, int year, int month, int day
//						) {
//					// TODO Auto-generated method stub
//					birthday.setText(year + "-" + (month + 1) + "-" + day);
//
//					birthLong = DateUtils.getStringToDate(birthday.getText()
//							.toString());
//				}
//			}, year, month, day,1).show();
			break;
		// 点击 完成 上传时间
		case R.id.mine_changebirth_wancheng_rl:
			if(null == birthday.getText() || birthday.getText().toString().equals("")){
				toastShort(getResources().getString(R.string.reg_one_no_birth));
				return;
			}
			infoBean.setBirthday(birthday.getText().toString());
			updateInfo();
			break;
		// 返回
		case R.id.back_changebirth_mine_rl:
			finish();
			break;
		default:
			break;
		}

	}

	private void updateInfo() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_USETINFO);
		String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", token);
			requestObject.put("device_code", deviceId);
			requestObject.put("birth_date",infoBean.getBirthday());
			requestObject.put("parameters", "birth_date");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data", requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "error--"+ex.getMessage());
				LogUtil.d("mytest", "error--"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "birthday=="+result);
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort("修改信息失败");
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
					String token = data.getString("token");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
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
	/**
	 * 设置点击返回键的状态
	 */
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		Intent intent = new Intent();
//		intent.putExtra("birthday", birth);
//		ChangeBirthdayActivity.this.setResult(RESULT_CANCELED, intent);
		finish();
	}

//	/**
//	 * 上传 修改信息
//	 * 
//	 * @param user
//	 * @author lucifer
//	 * @date 2015-11-6
//	 */
//	public void completeInfo(final ObjUser user) {
//		birthLong = DateUtils.getStringToDate(birthday.getText().toString());
//		user.setBirthday(birthLong);
//
//		// 只上传信息
//		ObjUserWrap.completeUserInfo(user, new ObjFunBooleanCallback() {
//
//			@Override
//			public void callback(boolean result, AVException e) {
//				if (result) {
//					Toast.makeText(getApplicationContext(), "已保存", 1000)
//							.show();
//					Intent intent = new Intent();
//					intent.putExtra("birthday", birthday.getText().toString());
//					ChangeBirthdayActivity.this.setResult(RESULT_OK, intent);
//					finish();
//
//				} else {
//					Toast.makeText(getApplicationContext(), "保存失败", 1000)
//							.show();
//				}
//			}
//		});
//	}
//	

}
