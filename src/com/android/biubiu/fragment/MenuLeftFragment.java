package com.android.biubiu.fragment;

import java.util.Arrays;
import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import com.android.biubiu.BeginGuiderActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.MatchSettingActivity;
import com.android.biubiu.R;
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
	private TextView  hintPHoto;
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
		hintPHoto=(TextView) mView.findViewById(R.id.hint_your_photo_tv);
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
		
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.loadingbbbb)
		.setFailureDrawableId(R.drawable.ic_launcher)
		.build();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
			if(LoginUtils.isLogin(getActivity())){		
				isLogin=true;
				hintPHoto.setVisibility(View.GONE);
				userName.setText(SharePreferanceUtils.getInstance().getUserName(getActivity(), SharePreferanceUtils.USER_NAME, ""));
				x.image().bind(userHead,SharePreferanceUtils.getInstance().getUserHead(getActivity(), SharePreferanceUtils.USER_HEAD, ""),imageOptions);
			}else{
				isLogin = false;
				userName.setText("登录注册");
				hintPHoto.setVisibility(View.VISIBLE);
				userHead.setImageResource(R.drawable.main_touxiang);
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
			if(isLogin){
				Intent intent=new Intent(getActivity(),BeginGuiderActivity.class);
				startActivity(intent);
			}else{
				Intent intent = new Intent(getActivity(),
						LoginOrRegisterActivity.class);
				startActivity(intent);
			}	
			break;
		case R.id.left_menu_item5_rl:
			Toast.makeText(getActivity(), "share", Toast.LENGTH_SHORT).show();
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

		default:
			break;
		}

	}
}
