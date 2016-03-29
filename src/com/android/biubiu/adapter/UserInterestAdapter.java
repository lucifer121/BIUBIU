package com.android.biubiu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cc.imeetu.iu.R;

import com.android.biubiu.adapter.UserPagerTagAdapter.ViewHolder;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.LogUtil;

public class UserInterestAdapter extends BaseAdapter{

	ArrayList<InterestTagBean> tags;
	Context context;
	public UserInterestAdapter(Context context,ArrayList<InterestTagBean> tags) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.tags = tags;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tags.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tags.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		InterestTagBean tag = tags.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.userpager_tag_item, null);
			vh = new ViewHolder();
			vh.tagTv = (TextView) convertView.findViewById(R.id.tag_tv);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		if(tag.getTagType().equals(Constants.BOOK)){
			vh.tagTv.setBackgroundResource(R.drawable.main_tab_imageview_blue_clk);
		}else if(tag.getTagType().equals(Constants.MUSIC)){
			vh.tagTv.setBackgroundResource(R.drawable.main_tab_imageview_oragne_clk);
		}else if(tag.getTagType().equals(Constants.MOVIE)){
			vh.tagTv.setBackgroundResource(R.drawable.main_tab_imageview_pink_clk);
		}else{
			vh.tagTv.setBackgroundResource(R.drawable.main_tab_imageview_green_clk);
		}
		vh.tagTv.setText(tag.getName());
		return convertView;
	}
	class ViewHolder{
		TextView tagTv;
	}
}
