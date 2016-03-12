package com.android.biubiu.activity.biu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.android.biubiu.activity.mine.AboutMeActivity;
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
import com.android.biubiu.bean.TagBean;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.view.MyGridView;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;

public class MyPagerActivity extends BaseActivity implements OnClickListener{
	private static final int UPDATE_INFO = 1001;
	private static final int UPDATE_PHOTOS = 1002;
	private static final int UPDATE_PERSONAL_TAG = 1003;
	private static final int UPDATE_INTEREST_TAG = 1004;
	private static final int UPDATE_HEAD = 1005;
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
	private RelativeLayout userInfoLinear;
	private UserInfoBean infoBean ;
	ImageOptions imageOptions;
	private ArrayList<View> photoPageViews = new ArrayList<View>();
	private UserPagerPhotoAdapter photoAdapter;
	//标记个人描述是否已经展开
	private boolean isOpen = false;
	private UserPagerTagAdapter personalAdapter;
	private UserPagerTagAdapter interestAdapter;
	ArrayList<String> photoList = new ArrayList<String>();
	ArrayList<TagBean> personalTagList = new ArrayList<TagBean>();
	ArrayList<TagBean> interestTagList = new ArrayList<TagBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_pager_layout);
		initView();
		TestUserBean();
		setUserInfoView(infoBean);
		setUserPhotos(photoList);
		setPersonalTags(personalTagList);
		setInterestTags(interestTagList);
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
		infoBean.setHeight("1cm");
		infoBean.setHomeTown("北京");
		infoBean.setSchool("幼儿园");
		infoBean.setStar("小强座");
		ArrayList<String> photos = new ArrayList<String>();
		ArrayList<TagBean> tags = new ArrayList<TagBean>();
		for (int i = 0; i < 6; i++) {
			photos.add(photoStr);
			TagBean bean = new TagBean();
			bean.setTagName("标签"+i);
			bean.setTagType("1");
			tags.add(bean);
		}
		//log.d("mytest", beanList.toString());
		//Gson gson = new Gson();
		//log.d("mytest", ""+gson.fromJson(beanList.toString(), TagBean[].class));
		infoBean.setUserPhotos(photos);
		infoBean.setPersonalTags(tags);
		infoBean.setInterestTags(tags);
		infoBean.setJob("lala");
		infoBean.setCompany("lala");
		infoBean.setAboutMe(getResources().getString(R.string.page_test));
		infoBean.setIsStudent(Constants.IS_STUDENT_FLAG);

		photoList.addAll(infoBean.getUserPhotos());
		interestTagList.addAll(infoBean.getInterestTags());
		personalTagList.addAll(infoBean.getPersonalTags());
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
		userInfoLinear = (RelativeLayout) findViewById(R.id.userinfo_linear);
		userInfoLinear.setOnClickListener(this);
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
	private void setUserInfoView(UserInfoBean bean) {
		// TODO Auto-generated method stub
		x.image().bind(userheadImv, bean.getUserHead(), imageOptions);
		usernameTv.setText(bean.getNickname());
		nicknameTv.setText(bean.getNickname());
		sexTv.setText(bean.getSex());
		birthdayTv.setText(bean.getBirthday());
		starSignTv.setText(bean.getStar());
		cityTv.setText(bean.getCity());
		hometownTv.setText(bean.getHomeTown());
		heightWeightTv.setText(bean.getHeight()+","+bean.getWeight());
		if(bean.getIsStudent().equals(Constants.IS_STUDENT_FLAG)){
			identityTv.setText("学生");
			schoolTv.setText(bean.getSchool());
		}else{
			identityTv.setText(bean.getJob());
			schoolTv.setText(bean.getCompany());
		}

		userInfoTv.setText(R.string.page_test);
		userInfoBigTv.setText(R.string.page_test);
	}
	private void setUserPhotos(ArrayList<String> photos) {
		// TODO Auto-generated method stub
		photoPageViews.clear();
		for(int i=0;i<photos.size();i++){
			LayoutInflater inflater = getLayoutInflater();
			View view = inflater.inflate(R.layout.userpager_photo_item, null);
			photoPageViews.add(view);
		}
		photoAdapter = new UserPagerPhotoAdapter(getApplicationContext(), photos, imageOptions, photoPageViews);
		photoPager.setAdapter(photoAdapter);
	}
	private void setInterestTags(ArrayList<TagBean> tags) {
		// TODO Auto-generated method stub
		interestAdapter = new UserPagerTagAdapter(getApplicationContext(), tags);
		interestTagGv.setAdapter(interestAdapter);
	}
	private void setPersonalTags(ArrayList<TagBean> tags) {
		// TODO Auto-generated method stub
		personalAdapter = new UserPagerTagAdapter(getApplicationContext(), tags);
		personalTagGv.setAdapter(personalAdapter);
	}
	private void getUserInfo(){
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userhead_imv:
			Intent headIntent = new Intent(MyPagerActivity.this,ScanUserHeadActivity.class);
			headIntent.putExtra("userhead", infoBean.getUserHead());
			headIntent.putExtra("isMyself", true);
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
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, UPDATE_PHOTOS);
			break;
		case R.id.userinfo_linear:
			Intent aboutIntent = new Intent(MyPagerActivity.this,AboutMeActivity.class);
			aboutIntent.putExtra("userInfoBean", infoBean);
			startActivityForResult(aboutIntent, UPDATE_INFO);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case UPDATE_INFO:
			if(resultCode != RESULT_OK){
				return;
			}
			if(data ==null ){
				return;
			}
			UserInfoBean bean = (UserInfoBean) data.getSerializableExtra("userInfoBean");
			setUserInfoView(bean);
			break;
		case UPDATE_PHOTOS:
			if(null == data){
				return;
			}
			Bitmap bm = null;
			//外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
			ContentResolver resolver = getContentResolver();
			//获得图片的uri 
			Uri originalUri = data.getData();      
			//显得到bitmap图片这里开始的第二部分，获取图片的路径：
			try {
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				String[] proj = {MediaStore.Images.Media.DATA};
				//好像是android多媒体数据库的封装接口，具体的看Android文档
				Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
				//按我个人理解 这个是获得用户选择的图片的索引值
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				//将光标移至开头 ，这个很重要，不小心很容易引起越界
				cursor.moveToFirst();
				//最后根据索引值获取图片路径
				String path = cursor.getString(column_index);
				//上传图片，成功后更新界面
				//uploadImg(path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		case UPDATE_HEAD:
			if(null == data){
				return;
			}
			break;
		case UPDATE_INTEREST_TAG:
			if(null == data){
				return;
			}
			break;
		case UPDATE_PERSONAL_TAG:
			if(null == data){
				return;
			}
			break;
		default:
			break;
		}
	}
}
