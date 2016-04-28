package com.android.biubiu.fragment;

import java.util.Arrays;
import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import cc.imeetu.iu.R;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.android.biubiu.AboutOurActivity;
import com.android.biubiu.BeginGuiderActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.MatchSettingActivity;
import com.android.biubiu.activity.GuildActivity;
import com.android.biubiu.activity.LoginOrRegisterActivity;
import com.android.biubiu.activity.biu.MyPagerActivity;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.LoginUtils;
import com.android.biubiu.utils.SharePreferanceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuLeftFragment extends Fragment implements OnClickListener {
	private View mView;
	private RelativeLayout biubiuLayout, messageLayout, settingLayout,
	leadLayout, shareLayout;
	private ImageView userHead;
	private RelativeLayout userHeadLayout;
	ImageOptions imageOptions;
	private TextView userName;

	private RelativeLayout aboutLayout;
	/**
	 * 是否已经登录
	 */
	private Boolean isLogin=false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			initView(inflater, container);
		}
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container) {
		mView = inflater.inflate(R.layout.left_menu, container, false);
		biubiuLayout = (RelativeLayout) mView
				.findViewById(R.id.left_menu_item1_rl);
		messageLayout = (RelativeLayout) mView
				.findViewById(R.id.left_menu_item2_rl);
		settingLayout = (RelativeLayout) mView
				.findViewById(R.id.left_menu_item3_rl);
		leadLayout = (RelativeLayout) mView
				.findViewById(R.id.left_menu_item4_rl);
		shareLayout = (RelativeLayout) mView
				.findViewById(R.id.left_menu_item5_rl);
		aboutLayout=(RelativeLayout) mView.findViewById(R.id.aboutOur_left_rl);

		userHead = (ImageView) mView.findViewById(R.id.main_touxiang_img);
		userHeadLayout = (RelativeLayout) mView
				.findViewById(R.id.main_touxiang_rl);
		userName=(TextView) mView.findViewById(R.id.name_main_tv);
		biubiuLayout.setOnClickListener(this);
		messageLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		leadLayout.setOnClickListener(this);
		shareLayout.setOnClickListener(this);
		userHeadLayout.setOnClickListener(this);
		aboutLayout.setOnClickListener(this);

		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.loadingbbbb)
		.setFailureDrawableId(R.drawable.photo_fail)
		.setIgnoreGif(false)
		.build();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(LoginUtils.isLogin(getActivity())){		
			isLogin=true;

			userName.setText(SharePreferanceUtils.getInstance().getUserName(getActivity(), SharePreferanceUtils.USER_NAME, ""));
			x.image().bind(userHead,SharePreferanceUtils.getInstance().getUserHead(getActivity(), SharePreferanceUtils.USER_HEAD, ""),imageOptions);
		}else{
			isLogin = false;
			userName.setText("登录注册");

			userHead.setImageResource(R.drawable.ease_default_avatar);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_menu_item1_rl:
			//	Toast.makeText(getActivity(), "biu", Toast.LENGTH_SHORT).show();
			((MainActivity) getActivity()).closeMenu();
			break;
		case R.id.left_menu_item2_rl:
			//	Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();
			((MainActivity) getActivity()).showSecondaryMenu();
			;
			break;
		case R.id.left_menu_item3_rl:
			((MainActivity) getActivity()).closeMenu();
			if(isLogin){
				Intent intent=new Intent(getActivity(),MatchSettingActivity.class);
				startActivity(intent);
			}else{
				Intent intent = new Intent(getActivity(),
						LoginOrRegisterActivity.class);
				startActivity(intent);
			}	
			break;
		case R.id.left_menu_item4_rl:
			((MainActivity) getActivity()).closeMenu();
			Intent intentGuid=new Intent(getActivity(),BeginGuiderActivity.class);
			startActivity(intentGuid);
			break;
		case R.id.left_menu_item5_rl:
			//Toast.makeText(getActivity(), "share", Toast.LENGTH_SHORT).show();
			showShare();
			break;
		case R.id.main_touxiang_rl:
			((MainActivity) getActivity()).closeMenu();
			if(isLogin){
				Intent intent=new Intent(getActivity(),MyPagerActivity.class);
				startActivity(intent);
			}else{
				Intent intent = new Intent(getActivity(),
						LoginOrRegisterActivity.class);
				startActivity(intent);
			}	
			break;
		case R.id.aboutOur_left_rl:
			((MainActivity) getActivity()).closeMenu();
			Intent intent=new Intent(getActivity(),AboutOurActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	private void showShare() {
		ShareSDK.initSDK(getActivity());
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize(); 

		//		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		//		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		//		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		//		 oks.setTitle("今天是个好日子");
		//		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		//		 oks.setTitleUrl("http://www.imeetu.cc/"); 
		//		 // text是分享文本，所有平台都需要这个字段
		//		 oks.setText("我是分享文本");
		//		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		//		 //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		//		 // url仅在微信（包括好友和朋友圈）中使用
		//		 oks.setUrl("http://www.imeetu.cc/");
		//		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		//		 oks.setComment("我是测试评论文本");
		//		 // site是分享此内容的网站名称，仅在QQ空间使用
		//		 oks.setSite(getString(R.string.app_name));
		//		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		//		 oks.setSiteUrl("http://www.imeetu.cc/");
		oks.setTitle("iU—青春恋爱成长平台");
		oks.setTitleUrl("http://www.imeetu.cc");
		oks.setText("星星发亮，是为了让每一个人都能找到属于自己的星星。而从我到你，只有一个iU的距离。");
		oks.setImageUrl("http://protect-app.oss-cn-beijing.aliyuncs.com/app-resources/img/icon/ShareIconAndroid.png");
		oks.setUrl("http://www.imeetu.cc/");

		// 启动分享GUI
		oks.show(getActivity());
	}
}
