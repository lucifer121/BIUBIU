package com.android.biubiu;

import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.view.MyGridView;
import com.android.biubiu.view.RangeSeekBar;
import com.android.biubiu.view.RangeSeekBar.OnRangeSeekBarChangeListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MatchSettingActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout backRl;
	private ToggleButton boyToggle;
	private ToggleButton girlToggle;
	private ToggleButton cityToggle;
	private ToggleButton unLimitToggle;
	private TextView ageMinTv;
	private TextView ageMaxTv;
	private RelativeLayout personalTagRl;
	private MyGridView tagGv;
	private ToggleButton newMsgToggle;
	private ToggleButton voiceToggle;
	private ToggleButton shockToggle;
	private RelativeLayout logoutRl;
	private LinearLayout seekLinear;

	private boolean isSelBoy = true;
	private boolean isSelGirl = true;
	private boolean isSameCity = true;
	private boolean isUnLimit = true;
	private boolean isRecvMsg = true;
	private boolean isOpenVoice = true;
	private boolean isOpenShck = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_setting);
		initView();
		setRangeAge();
	}
	private void initView() {
		// TODO Auto-generated method stub
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);
		boyToggle = (ToggleButton) findViewById(R.id.boy_toggle);
		girlToggle = (ToggleButton) findViewById(R.id.girl_toggle);
		cityToggle = (ToggleButton) findViewById(R.id.city_toggle);
		unLimitToggle = (ToggleButton) findViewById(R.id.unlimit_toggle);
		ageMinTv = (TextView) findViewById(R.id.age_min_tv);
		ageMaxTv = (TextView) findViewById(R.id.age_max_tv);
		personalTagRl = (RelativeLayout) findViewById(R.id.personal_rl);
		personalTagRl.setOnClickListener(this);
		tagGv = (MyGridView) findViewById(R.id.interest_tag_gv);
		newMsgToggle = (ToggleButton) findViewById(R.id.newmsg_toggle);
		voiceToggle = (ToggleButton) findViewById(R.id.voice_toggle);
		shockToggle = (ToggleButton) findViewById(R.id.shock_toggle);
		logoutRl = (RelativeLayout) findViewById(R.id.logout_rl);
		logoutRl.setOnClickListener(this);
		seekLinear = (LinearLayout) findViewById(R.id.seek_linear);
		initToggle();
	}
	private void setRangeAge() {
		ageMinTv.setText(""+SharePreferanceUtils.getInstance().getMinAge(MatchSettingActivity.this, SharePreferanceUtils.AGE_MIN, 16));
		ageMaxTv.setText(""+SharePreferanceUtils.getInstance().getMinAge(MatchSettingActivity.this, SharePreferanceUtils.AGE_MAX, 40));
		RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(16, 40, this);
		int maxAge = Integer.parseInt(ageMinTv.getText().toString());
		int minAge = Integer.parseInt(ageMaxTv.getText().toString());
		seekBar.setSelectedMaxValue(maxAge);
		seekBar.setSelectedMinValue(minAge);
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Integer minValue, Integer maxValue) {
				Log.d("mytest","min=="+minValue+"  max=="+maxValue);
				ageMinTv.setText(minValue+"");
				ageMaxTv.setText(maxValue+"");
			}
		});       
		seekBar.setNotifyWhileDragging(true);
		seekLinear.addView(seekBar);
	}
	//保存设置年龄范围
	private void saveRangeAge() {
		// TODO Auto-generated method stub
		int minAge = Integer.parseInt(ageMinTv.getText().toString());
		SharePreferanceUtils.getInstance().putShared(MatchSettingActivity.this, SharePreferanceUtils.AGE_MIN, minAge);
		int maxAge = Integer.parseInt(ageMaxTv.getText().toString());
		SharePreferanceUtils.getInstance().putShared(MatchSettingActivity.this, SharePreferanceUtils.AGE_MAX, maxAge);
	}
	private void initToggle() {
		// TODO Auto-generated method stub
		boyToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isSelBoy = false;
					isChecked = false;
					boyToggle.setBackgroundResource(R.drawable.setting_btn_yes);
				}else{
					isSelBoy = true;
					isChecked = true;
					boyToggle.setBackgroundResource(R.drawable.setting_btn_no);
				}
			}
		});
		girlToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isSelGirl = false;
					isChecked = false;
					girlToggle.setBackgroundResource(R.drawable.setting_btn_yes);
				}else{
					isSelGirl = true;
					isChecked = true;
					girlToggle.setBackgroundResource(R.drawable.setting_btn_no);
				}
			}
		});
		cityToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isSameCity = false;
					isChecked = false;
					cityToggle.setBackgroundResource(R.drawable.setting_btn_yes);
				}else{
					isSameCity = true;
					isChecked = true;
					cityToggle.setBackgroundResource(R.drawable.setting_btn_no);
				}
			}
		});
		unLimitToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isUnLimit = false;
					isChecked = false;
					unLimitToggle.setBackgroundResource(R.drawable.setting_btn_yes);
				}else{
					isUnLimit = true;
					isChecked = true;
					unLimitToggle.setBackgroundResource(R.drawable.setting_btn_no);
				}
			}
		});
		newMsgToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isRecvMsg = false;
					isChecked = false;
					newMsgToggle.setBackgroundResource(R.drawable.setting_btn_yes);
				}else{
					isRecvMsg = true;
					isChecked = true;
					newMsgToggle.setBackgroundResource(R.drawable.setting_btn_no);
				}
			}
		});
		voiceToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isOpenVoice = false;
					isChecked = false;
					voiceToggle.setBackgroundResource(R.drawable.setting_btn_yes);
				}else{
					isOpenVoice = true;
					isChecked = true;
					voiceToggle.setBackgroundResource(R.drawable.setting_btn_no);
				}
			}
		});
		shockToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isOpenShck = false;
					isChecked = false;
					shockToggle.setBackgroundResource(R.drawable.setting_btn_yes);
				}else{
					isOpenShck = true;
					isChecked = true;
					shockToggle.setBackgroundResource(R.drawable.setting_btn_no);
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_rl:
			saveRangeAge();
			finish();
			break;
		case R.id.personal_rl:
			break;
		case R.id.logout_rl:
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			saveRangeAge();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
