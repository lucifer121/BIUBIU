package com.android.biubiu;

import com.android.biubiu.activity.BaseActivity;
import com.android.biubiu.view.MyGridView;
import com.android.biubiu.view.RangeSeekBar;
import com.android.biubiu.view.RangeSeekBar.OnRangeSeekBarChangeListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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
	private TextView ageRangeTv;
	private RelativeLayout personalTagRl;
	private MyGridView tagGv;
	private ToggleButton newMsgToggle;
	private ToggleButton voiceToggle;
	private ToggleButton shockToggle;
	private RelativeLayout logoutRl;
	private LinearLayout seekLinear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_setting);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);
		boyToggle = (ToggleButton) findViewById(R.id.boy_toggle);
		girlToggle = (ToggleButton) findViewById(R.id.girl_toggle);
		cityToggle = (ToggleButton) findViewById(R.id.city_toggle);
		unLimitToggle = (ToggleButton) findViewById(R.id.unlimit_toggle);
		ageRangeTv = (TextView) findViewById(R.id.age_range_tv);
		personalTagRl = (RelativeLayout) findViewById(R.id.personal_rl);
		personalTagRl.setOnClickListener(this);
		tagGv = (MyGridView) findViewById(R.id.personal_tag_gv);
		newMsgToggle = (ToggleButton) findViewById(R.id.newmsg_toggle);
		voiceToggle = (ToggleButton) findViewById(R.id.voice_toggle);
		shockToggle = (ToggleButton) findViewById(R.id.shock_toggle);
		logoutRl = (RelativeLayout) findViewById(R.id.logout_rl);
		logoutRl.setOnClickListener(this);
		seekLinear = (LinearLayout) findViewById(R.id.seek_linear);
		
		RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(18, 30, this);
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Integer minValue, Integer maxValue) {
				Log.d("mytest","min=="+minValue+"  max=="+maxValue);
				ageRangeTv.setText(minValue+"-"+maxValue);
			}
		});       
		seekBar.setNotifyWhileDragging(true);
		seekLinear.addView(seekBar);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_rl:
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
}
