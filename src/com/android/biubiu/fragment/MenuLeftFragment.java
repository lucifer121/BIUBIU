package com.android.biubiu.fragment;

import java.util.Arrays;
import java.util.List;

import com.android.biubiu.R;
import com.android.biubiu.activity.MainActivity;
import com.android.biubiu.biu.activity.BiuSetActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MenuLeftFragment extends Fragment implements OnClickListener
{
	private View mView;
	private RelativeLayout biubiuLayout,messageLayout,settingLayout,leadLayout,shareLayout;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if (mView == null)
		{
			initView(inflater, container);
		}
		
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container)
	{
		mView = inflater.inflate(R.layout.left_menu, container, false);
		biubiuLayout=(RelativeLayout) mView.findViewById(R.id.left_menu_item1_rl);
		messageLayout=(RelativeLayout) mView.findViewById(R.id.left_menu_item2_rl);
		settingLayout=(RelativeLayout) mView.findViewById(R.id.left_menu_item3_rl);
		leadLayout=(RelativeLayout) mView.findViewById(R.id.left_menu_item4_rl);
		shareLayout=(RelativeLayout) mView.findViewById(R.id.left_menu_item5_rl);
		biubiuLayout.setOnClickListener(this);
		messageLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		leadLayout.setOnClickListener(this);
		shareLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_menu_item1_rl:
			Toast.makeText(getActivity(), "biu", Toast.LENGTH_SHORT).show();
			((MainActivity)getActivity()).closeMenu();
			break;
		case R.id.left_menu_item2_rl:
			Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();
			((MainActivity)getActivity()).showSecondaryMenu();;
			break;
		case R.id.left_menu_item3_rl:
			((MainActivity)getActivity()).closeMenu();
			Intent intentSet = new Intent(getActivity(),BiuSetActivity.class);
			startActivity(intentSet);
			getActivity().overridePendingTransition(R.anim.right_in_anim,R.anim.no_anim);
			break;
		case R.id.left_menu_item4_rl:
			Toast.makeText(getActivity(), "lead", Toast.LENGTH_SHORT).show();
			break;
		case R.id.left_menu_item5_rl:
			Toast.makeText(getActivity(), "share", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}
}
