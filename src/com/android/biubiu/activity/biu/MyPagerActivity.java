package com.android.biubiu.activity.biu;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.activity.mine.ChangeBrithdayActivity;
import com.android.biubiu.activity.mine.ChangeCityActivity;
import com.android.biubiu.activity.mine.ChangeConstellationActivity;
import com.android.biubiu.activity.mine.ChangeHeightWeightActivity;
import com.android.biubiu.activity.mine.ChangeHomeTwonActivity;
import com.android.biubiu.activity.mine.ChangeIdentityProfessionActivity;
import com.android.biubiu.activity.mine.ChangeNameActivity;
import com.android.biubiu.activity.mine.ChangeSchoolActivity;
import com.android.biubiu.activity.mine.InterestLabelActivity;
import com.android.biubiu.activity.mine.PersonalityTagActivity;
import com.android.biubiu.activity.mine.ScanUserHeadActivity;
import com.android.biubiu.adapter.UserPagerPhotoAdapter;
import com.android.biubiu.adapter.UserPagerTagAdapter;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.view.MyGridView;

public class MyPagerActivity extends BaseActivity implements OnClickListener{
	private ImageView userheadImv;
	private TextView usernameTv;
	private ImageView addPhotoImv;
	private ViewPager photoPager;
	private TextView userInfoTv;
	private TextView userInfoBigTv;
	private TextView userOpenTv;
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
	private MyGridView personalTagGv;
	private LinearLayout interestTagLinear;
	private MyGridView interestTagGv;
	private RelativeLayout backRl;

	private UserInfoBean infoBean ;
	ImageOptions imageOptions;
	private ArrayList<View> photoPageViews = new ArrayList<View>();
	private UserPagerPhotoAdapter photoAdapter;
	//标记个人描述是否已经展开
	private boolean isOpen = false;
	private UserPagerTagAdapter personalAdapter;
	private UserPagerTagAdapter interestAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pager_layout);
		initView();
		TestUserBean();
		setUserInfoView();
	}

	private void TestUserBean() {
		// TODO Auto-generated method stub
		infoBean = new UserInfoBean();
		String photoStr = "http://ac-tcd4rj3s.clouddn.com/EE4DqTAx7ZFWaMUq1bQqfXDWFW0n1TehKAW5bCQk.jpeg";
		infoBean.setNickname("小强");
		infoBean.setSex("男");
		infoBean.setUserHead(photoStr);
		infoBean.setBirthday("2006-01-01");
		infoBean.setCity("北京");
		infoBean.setHeightWeight("1cm");
		infoBean.setHomeTown("北京");
		infoBean.setId("123456789");
		infoBean.setIdentity("吃货");
		infoBean.setSchool("幼儿园");
		infoBean.setStar("小强座");
		ArrayList<String> photos = new ArrayList<String>();
		ArrayList<String> tags = new ArrayList<String>();
		for (int i = 0; i < 6; i++) {
			photos.add(photoStr);
			tags.add("标签"+i);
		}
		infoBean.setUserPhotos(photos);
		infoBean.setPersonalTags(tags);
		infoBean.setInterestTags(tags);
	}

	private void initView() {
		// TODO Auto-generated method stub
		userheadImv = (ImageView) findViewById(R.id.userhead_imv);
		userheadImv.setOnClickListener(this);
		usernameTv = (TextView) findViewById(R.id.username_tv);
		addPhotoImv = (ImageView) findViewById(R.id.add_userphoto_imv);
		addPhotoImv.setOnClickListener(this);
		photoPager = (ViewPager) findViewById(R.id.userphoto_pager);
		userInfoTv = (TextView) findViewById(R.id.userinfo_tv);
		userInfoBigTv = (TextView) findViewById(R.id.userinfo_big_tv);
		userOpenTv = (TextView) findViewById(R.id.open_tv);
		userOpenTv.setOnClickListener(this);
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
		personalTagGv = (MyGridView) findViewById(R.id.personal_tag_gv);
		interestTagLinear = (LinearLayout) findViewById(R.id.interest_tag_linear);
		interestTagLinear.setOnClickListener(this);
		interestTagGv = (MyGridView) findViewById(R.id.interest_tag_gv);
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);

		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.ic_launcher)
		.setFailureDrawableId(R.drawable.ic_launcher)
		.build();

		photoPager.setOffscreenPageLimit(3);
		photoPager.setPageMargin(DensityUtil.dip2px(getApplicationContext(), 10));
	}
	private void setUserInfoView() {
		// TODO Auto-generated method stub
		x.image().bind(userheadImv, infoBean.getUserHead(), imageOptions);
		usernameTv.setText(infoBean.getNickname());
		nicknameTv.setText(infoBean.getNickname());
		sexTv.setText(infoBean.getSex());
		birthdayTv.setText(infoBean.getBirthday());
		starSignTv.setText(infoBean.getStar());
		cityTv.setText(infoBean.getCity());
		hometownTv.setText(infoBean.getHomeTown());
		heightWeightTv.setText(infoBean.getHeightWeight());
		identityTv.setText(infoBean.getIdentity());
		schoolTv.setText(infoBean.getSchool());

		userInfoTv.setText(R.string.page_test);
		userInfoBigTv.setText(R.string.page_test);

		setUserPhotos();
		setPersonalTags();
		setInterestTags();
	}
	private void setUserPhotos() {
		// TODO Auto-generated method stub
		photoPageViews.clear();
		for(int i=0;i<infoBean.getUserPhotos().size();i++){
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.userpager_photo_item, null);
			photoPageViews.add(view);
		}
		photoAdapter = new UserPagerPhotoAdapter(getApplicationContext(), infoBean.getUserPhotos(), imageOptions, photoPageViews);
		photoPager.setAdapter(photoAdapter);
	}
	private void setInterestTags() {
		// TODO Auto-generated method stub
		interestAdapter = new UserPagerTagAdapter(getApplicationContext(), infoBean.getInterestTags());
		interestTagGv.setAdapter(interestAdapter);
	}
	private void setPersonalTags() {
		// TODO Auto-generated method stub
		personalAdapter = new UserPagerTagAdapter(getApplicationContext(), infoBean.getPersonalTags());
		personalTagGv.setAdapter(personalAdapter);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userhead_imv:
			Intent headIntent = new Intent(MyPagerActivity.this,ScanUserHeadActivity.class);
			headIntent.putExtra("userhead", infoBean.getUserHead());
			startActivity(headIntent);
			break;
		case R.id.open_tv:
			if(isOpen){
				userInfoBigTv.setVisibility(View.GONE);
				userInfoTv.setVisibility(View.VISIBLE);
				userOpenTv.setText("展开");
				isOpen = false;
			}else{
				userInfoTv.setVisibility(View.GONE);
				userInfoBigTv.setVisibility(View.VISIBLE);
				userOpenTv.setText("收起");
				isOpen = true;
			}
			break;
		case R.id.add_userphoto_imv:

			break;
		case R.id.nickname_linear:
			Intent nicknameIntent = new Intent(MyPagerActivity.this,ChangeNameActivity.class);
			startActivity(nicknameIntent);
			break;
		case R.id.birthday_linear:
			Intent birthIntent = new Intent(MyPagerActivity.this,ChangeBrithdayActivity.class);
			startActivity(birthIntent);
			break;
		case R.id.starsign_linear:
			Intent starIntent = new Intent(MyPagerActivity.this,ChangeConstellationActivity.class);
			startActivity(starIntent);
			break;
		case R.id.city_linear:
			Intent cityIntent = new Intent(MyPagerActivity.this,ChangeCityActivity.class);
			startActivity(cityIntent);
			break;
		case R.id.hometown_linear:
			Intent homeIntent = new Intent(MyPagerActivity.this,ChangeHomeTwonActivity.class);
			startActivity(homeIntent);

			break;
		case R.id.height_weight_linear:
			Intent tallIntent = new Intent(MyPagerActivity.this,ChangeHeightWeightActivity.class);
			startActivity(tallIntent);
			break;
		case R.id.identity_linear:
			Intent identityIntent = new Intent(MyPagerActivity.this,ChangeIdentityProfessionActivity.class);
			startActivity(identityIntent);
			break;
		case R.id.school_linear:
			Intent schoolIntent = new Intent(MyPagerActivity.this,ChangeSchoolActivity.class);
			startActivity(schoolIntent);
			break;
		case R.id.personal_tag_linear:
			Intent personalTagIntent=new Intent(MyPagerActivity.this,PersonalityTagActivity.class);
			startActivity(personalTagIntent);
			break;
		case R.id.interest_tag_linear:
			Intent interestLableIntent=new Intent(MyPagerActivity.this,InterestLabelActivity.class);
			startActivity(interestLableIntent);
			break;
		case R.id.back_rl:
			finish();
			break;
		default:
			break;
		}
	}

}
