package com.android.biubiu.adapter;

import java.util.List;

import cc.imeetu.iu.R;

import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.Schools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("InflateParams") 
public class GridViewLableAdapter extends BaseAdapter{
	private Context mContext;
	private List<InterestTagBean> mDates;
	private Boolean isSelector;
	private String typeName;
	public GridViewLableAdapter(Context context,List<InterestTagBean> mDates,String typeName){
		this.mContext=context;
		this.mDates=mDates;
		this.typeName=typeName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.e("getCount()", "=="+mDates.size());
		return mDates.size();
		
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
		final ViewHolder holder ;
		InterestTagBean item = mDates.get(position);
		isSelector=false;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_interest_lable_a, null);
			holder.lable=(TextView) convertView.findViewById(R.id.tag_item_personality_tag);
			holder.mLayout=(RelativeLayout) convertView.findViewById(R.id.layout_interest_lable_rl);
			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.lable.setText(item.getName());
		if(item.getIsChoice()){
			holder.lable.setTextColor(mContext.getResources().getColor(R.color.white));
			
			if(typeName.equals("音乐")){
				holder.lable.setBackgroundResource(R.drawable.main_tab_imageview_oragne_clk);
				
			}else if(typeName.equals("电影")){
				holder.lable.setBackgroundResource(R.drawable.main_tab_imageview_pink_clk);
				
			}else if(typeName.equals("书籍")){
				holder.lable.setBackgroundResource(R.drawable.main_tab_imageview_blue_clk);
				
			}else{
				holder.lable.setBackgroundResource(R.drawable.main_imageview_tabhobby_clk);	
			}
			
		}else{
			holder.lable.setTextColor(mContext.getResources().getColor(R.color.black));
			
			
			if(typeName.equals("音乐")){
				holder.lable.setBackgroundResource(R.drawable.main_tab_imageview_orange);
				
			}else if(typeName.equals("电影")){
				holder.lable.setBackgroundResource(R.drawable.main_tab_imageview_pink);
				
			}else if(typeName.equals("书籍")){
				holder.lable.setBackgroundResource(R.drawable.main_tab_imageview_blue);
				
			}else{
				holder.lable.setBackgroundResource(R.drawable.main_imageview_tabhobby);	
			}
			
			
		}


		

		return convertView;
	}

	private class ViewHolder {
		private TextView lable;
		private RelativeLayout mLayout;

	}

}
