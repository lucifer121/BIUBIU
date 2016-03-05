package com.android.biubiu.adapter;

import com.android.biubiu.fragment.GuildFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GuildAdapter extends FragmentPagerAdapter{
	int count = 0;
	public GuildAdapter(FragmentManager fm,int count) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.count = count;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return GuildFragment.newInstance(arg0%count);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

}
