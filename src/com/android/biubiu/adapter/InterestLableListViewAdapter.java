package com.android.biubiu.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cc.imeetu.iu.R;

import com.android.biubiu.MainActivity;
import com.android.biubiu.activity.mine.InterestLabelActivity;
import com.android.biubiu.bean.InterestByCateBean;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.Schools;
import com.android.biubiu.utils.DensityUtil;
import com.avos.avoscloud.LogUtil.log;

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


public class InterestLableListViewAdapter extends BaseAdapter {
	private Context mContext;
	public List<InterestByCateBean> mListDate;
	
	private GridViewLableAdapter mAdapter;
//	private List<LableBean> mDates=new ArrayList<LableBean>();
	
	
	
	public InterestLableListViewAdapter (Context context,List<InterestByCateBean> mDate){
		this.mContext=context;
		this.mListDate=mDate;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.e("getCount()====", ""+mListDate.size());
		
		return mListDate.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		final List<InterestTagBean> mDateLables;
		InterestByCateBean item=mListDate.get(position);
		mDateLables=item.getmInterestList();

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_interest_, null);
			holder.mGridView=(GridView) convertView.findViewById(R.id.id_gridView_interest);
			holder.mView=convertView.findViewById(R.id.id_view_interest);
			holder.interest=(TextView) convertView.findViewById(R.id.interest_item_tv);
			holder.bottomLayout=(RelativeLayout) convertView.findViewById(R.id.bottom_item_interest);
			mAdapter=new GridViewLableAdapter(mContext, mDateLables,item.getTypename());
			holder.mGridView.setAdapter(mAdapter);
			holder.mGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int positionId, long arg3) {
					
					
					Toast.makeText(mContext, ((InterestLabelActivity) mContext).mDates.get(position).getmInterestList().get(positionId).getName(), Toast.LENGTH_SHORT).show();
					
					if(((InterestLabelActivity) mContext).mDates.get(position).getmInterestList().get(positionId).getIsChoice()==false){
						((InterestLabelActivity) mContext).mDates.get(position).getmInterestList().get(positionId).setIsChoice(true);
						
					}else{
						((InterestLabelActivity) mContext).mDates.get(position).getmInterestList().get(positionId).setIsChoice(true);
					}	
//					if(mDateLables.get(positionId).getIsChoice()==false){
//						mListDate.get(position).getmInterestList();
//						mDateLables.get(positionId).setIsChoice(true);
//						
//					}else{
//						mDateLables.get(positionId).setIsChoice(false);
//					}
//					boolean a=mListDate.get(position).getmInterestList().get(positionId).getIsChoice();
//					Toast.makeText(mContext, ""+a, 1000).show();
				//	((InterestLabelActivity) mContext).mDates.get(position).getmInterestList().get(positionId).setName("你点我了");
				//	mAdapter.notifyDataSetChanged();
//					((InterestLabelActivity) mContext).mDates.clear();
//					((InterestLabelActivity) mContext).getDateByAdapter();
//					((InterestLabelActivity) mContext).mDates.addAll(mListDate);
					((InterestLabelActivity) mContext).initAdapter();
				}
			});
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		setGridviewHight(mDateLables,holder);
		holder.interest.setText(item.getTypename());
		if(position==(mListDate.size()-1)){
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
	public void setGridviewHight(List<InterestTagBean> mList,ViewHolder holder) {
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

	public List<InterestByCateBean>  getdate(){
		log.e("fanhuishuju", ""+this.mListDate.size());
		return  this.mListDate;
	}


}