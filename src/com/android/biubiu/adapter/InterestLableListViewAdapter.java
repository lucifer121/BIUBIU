package com.android.biubiu.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.biubiu.R;
import com.android.biubiu.bean.LableBean;
import com.android.biubiu.bean.Schools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

@SuppressLint("InflateParams") public class InterestLableListViewAdapter extends BaseAdapter{
	private Context mContext;
	private List<Map<String, List<LableBean>>> mDate;
	
	private GridViewLableAdapter mAdapter;
	private List<LableBean> mDates=new ArrayList<LableBean>();
	
	
	
	public InterestLableListViewAdapter (Context context,List<Map<String, List<LableBean>>> mDate){
		this.mContext=context;
		this.mDate=mDate;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_interest_, null);
			holder.mGridView=(GridView) convertView.findViewById(R.id.id_gridView_interest);
			mAdapter=new GridViewLableAdapter(mContext, mDates);
			holder.mGridView.setAdapter(mAdapter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

	

		return convertView;
	}

	private class ViewHolder {
		private TextView schoolName;
		private GridView mGridView;

	}

}