package com.android.biubiu.adapter;

import java.util.ArrayList;
import java.util.List;

import cc.imeetu.iu.R;

import com.android.biubiu.bean.Schools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author lucifer
 * @date 2015-11-3
 * @return
 */
public class SchoolListAllAdapter extends BaseAdapter {
	private Context mContext;
	private List<Schools> mlist = new ArrayList<Schools>();

	public SchoolListAllAdapter(Context context, List<Schools> list) {
		this.mContext = context;
		this.mlist = list;

	}

	@Override
	public int getCount() {

		return mlist.size();
	}

	@Override
	public Object getItem(int position) {

		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Schools item = mlist.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_choose_school, null);
			holder.schoolName = (TextView) convertView
					.findViewById(R.id.schoolName_school_selector_item_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.schoolName.setText(item.getUnivsNameString());

		return convertView;
	}

	private class ViewHolder {
		private TextView schoolName;

	}

}
