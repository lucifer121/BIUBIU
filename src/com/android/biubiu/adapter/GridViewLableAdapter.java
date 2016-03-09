package com.android.biubiu.adapter;

import java.util.List;
import com.android.biubiu.R;

import com.android.biubiu.bean.LableBean;
import com.android.biubiu.bean.Schools;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams") public class GridViewLableAdapter extends BaseAdapter{
	private Context mContext;
	private List<LableBean> mDates;
	public GridViewLableAdapter(Context context,List<LableBean> mDates){
		this.mContext=context;
		this.mDates=mDates;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDates.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
	//	Schools item = mDates.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_personality_tag, null);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		

		return convertView;
	}

	private class ViewHolder {
		private TextView schoolName;

	}

}
