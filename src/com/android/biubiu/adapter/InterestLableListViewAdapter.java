package com.android.biubiu.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.biubiu.R;
import com.android.biubiu.bean.InterestLableBean;
import com.android.biubiu.bean.LableBean;
import com.android.biubiu.bean.Schools;
import com.android.biubiu.utils.DensityUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams") public class InterestLableListViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<InterestLableBean> mDate;
	
	private GridViewLableAdapter mAdapter;
//	private List<LableBean> mDates=new ArrayList<LableBean>();
	
	
	
	public InterestLableListViewAdapter (Context context,List<InterestLableBean> mDate){
		this.mContext=context;
		this.mDate=mDate;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.e("getCount()====", ""+mDate.size());
		return mDate.size();
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
		
		List<LableBean> mDateLables=new ArrayList<LableBean>();
		InterestLableBean item=mDate.get(position);
		mDateLables=item.getmInterestMap().get(item.getInterest());

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_interest_, null);
			holder.mGridView=(GridView) convertView.findViewById(R.id.id_gridView_interest);
			holder.mView=convertView.findViewById(R.id.id_view_interest);
			holder.interest=(TextView) convertView.findViewById(R.id.interest_item_tv);
			holder.bottomLayout=(RelativeLayout) convertView.findViewById(R.id.bottom_item_interest);
			mAdapter=new GridViewLableAdapter(mContext, mDateLables);
			holder.mGridView.setAdapter(mAdapter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		setGridviewHight(mDateLables,holder);
		holder.interest.setText(item.getInterest());
		if(position==(mDate.size()-1)){
			holder.bottomLayout.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	private class ViewHolder {
		private TextView interest;
		private GridView mGridView;
		private View mView;
		private RelativeLayout bottomLayout;

	}
	/**
	 * 设置 Gridview高度
	 */
	public void setGridviewHight(List<LableBean> mList,ViewHolder holder) {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) holder.mGridView
				.getLayoutParams();
		int mHight;
		if (mList.size() != 0 && (mList.size()) % 4 == 0) {
			mHight = (((mList.size()) / 4)) * DensityUtil.dip2px(mContext, 37);
		} else {
			mHight = (((mList.size()) / 4) + 1) * DensityUtil.dip2px(mContext, 37);
		}
		params.height = mHight;
		holder.mGridView.setLayoutParams(params);
		LinearLayout.LayoutParams params2 = (android.widget.LinearLayout.LayoutParams) holder.mView
				.getLayoutParams();
		params2.height=mHight+DensityUtil.dip2px(mContext, 13);
		holder.mView.setLayoutParams(params2);
	}



}