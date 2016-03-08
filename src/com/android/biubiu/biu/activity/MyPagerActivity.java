package com.android.biubiu.biu.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.biubiu.R;
import com.android.biubiu.activity.BaseActivity;

public class MyPagerActivity extends BaseActivity implements OnClickListener{
	private ImageView userheadImv;
	private TextView usernameTv;
	private LinearLayout userPhotoLinear;
	private ImageView addPhotoImv;
	private ViewPager photoPager;
	private LinearLayout userInfoLinear;
	private TextView userInfoTv;
	private LinearLayout nicknameLinear;
	private TextView nicknameTv;
	private LinearLayout sexLinear;
	private TextView sexTv;
	private LinearLayout birthdayLinear;
	private TextView birthdayTv;
	private LinearLayout starSignLinear;
	private TextView starSignTv;
	private LinearLayout cityLinear;
	private TextView cityTv;
	private LinearLayout hometownLinear;
	private TextView hometownTv;
	private LinearLayout heightWeightLinear;
	private TextView heightWeightTv;
	private LinearLayout identityLinear;
	private TextView identityTv;
	private LinearLayout schoolLinear;
	private TextView schoolTv;
	private LinearLayout personalTagLinear;
	private GridView personalTagGv;
	private LinearLayout interestTagLinear;
	private GridView interestTagGv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pager_layout);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		userheadImv = (ImageView) findViewById(R.id.userhead_imv);
		userheadImv.setOnClickListener(this);
		usernameTv = (TextView) findViewById(R.id.username_tv);
		userPhotoLinear = (LinearLayout) findViewById(R.id.userphoto_linear);
		userPhotoLinear.setOnClickListener(this);
		addPhotoImv = (ImageView) findViewById(R.id.add_userphoto_imv);
		addPhotoImv.setOnClickListener(this);
		photoPager = (ViewPager) findViewById(R.id.userphoto_pager);
		userInfoLinear = (LinearLayout) findViewById(R.id.userinfo_linear);
		userInfoLinear.setOnClickListener(this);
		userInfoTv = (TextView) findViewById(R.id.userinfo_tv);
		nicknameLinear = (LinearLayout) findViewById(R.id.nickname_linear);
		nicknameLinear.setOnClickListener(this);
		nicknameTv = (TextView) findViewById(R.id.nickname_tv);
		sexLinear = (LinearLayout) findViewById(R.id.sex_linear);
		sexTv = (TextView) findViewById(R.id.sex_tv);
		birthdayLinear = (LinearLayout) findViewById(R.id.birthday_linear);
		birthdayLinear.setOnClickListener(this);
		birthdayTv = (TextView) findViewById(R.id.birthday_tv);
		starSignLinear = (LinearLayout) findViewById(R.id.starsign_linear);
		starSignLinear.setOnClickListener(this);
		starSignTv = (TextView) findViewById(R.id.starsign_tv);
		cityLinear = (LinearLayout) findViewById(R.id.city_linear);
		cityLinear.setOnClickListener(this);
		cityTv = (TextView) findViewById(R.id.city_tv);
		hometownLinear = (LinearLayout) findViewById(R.id.hometown_linear);
		hometownLinear.setOnClickListener(this);
		hometownTv = (TextView) findViewById(R.id.hometown_tv);
		heightWeightLinear = (LinearLayout) findViewById(R.id.height_weight_linear);
		heightWeightLinear.setOnClickListener(this);
		heightWeightTv = (TextView) findViewById(R.id.height_weight_tv);
		identityLinear = (LinearLayout) findViewById(R.id.identity_linear);
		identityLinear.setOnClickListener(this);
		identityTv = (TextView) findViewById(R.id.identity_tv);
		schoolLinear = (LinearLayout) findViewById(R.id.school_linear);
		schoolLinear.setOnClickListener(this);
		schoolTv = (TextView) findViewById(R.id.school_tv);
		personalTagLinear = (LinearLayout) findViewById(R.id.personal_tag_linear);
		personalTagLinear.setOnClickListener(this);
		personalTagGv = (GridView) findViewById(R.id.personal_tag_gv);
		interestTagLinear = (LinearLayout) findViewById(R.id.interest_tag_linear);
		interestTagLinear.setOnClickListener(this);
		interestTagGv = (GridView) findViewById(R.id.interest_tag_gv);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userhead_imv:

			break;
		case R.id.userinfo_linear:

			break;
		case R.id.add_userphoto_imv:

			break;
		case R.id.userphoto_linear:

			break;
		case R.id.nickname_linear:

			break;
		case R.id.birthday_linear:

			break;
		case R.id.starsign_linear:

			break;
		case R.id.city_linear:

			break;
		case R.id.hometown_linear:

			break;
		case R.id.height_weight_linear:

			break;
		case R.id.identity_linear:

			break;
		case R.id.school_linear:

			break;
		case R.id.personal_tag_linear:

			break;
		case R.id.interest_tag_linear:

			break;

		default:
			break;
		}
	}

}
