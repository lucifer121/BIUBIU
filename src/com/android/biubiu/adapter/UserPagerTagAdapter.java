package com.android.biubiu.adapter;

import java.util.ArrayList;

import cc.imeetu.iu.R;

import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.utils.LogUtil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class UserPagerTagAdapter extends BaseAdapter{

	ArrayList<PersonalTagBean> tags;
	Context context;
	public UserPagerTagAdapter(Context context,ArrayList<PersonalTagBean> tags) {
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
		PersonalTagBean tag = tags.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.userpager_tag_item, null);
			vh = new ViewHolder();
			vh.tagTv = (TextView) convertView.findViewById(R.id.tag_tv);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tagTv.setText(tag.getName());
		return convertView;
	}
	class ViewHolder{
		TextView tagTv;
	}
}
