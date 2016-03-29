package com.android.biubiu.adapter;


import java.util.List;

import cc.imeetu.iu.R;

import com.android.biubiu.bean.PersonalTagBean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GridRecycleTagAdapter extends
RecyclerView.Adapter<GridRecycleTagAdapter.MyViewHolder>{
	
	private List<PersonalTagBean> list;
	private LayoutInflater mInflater;
	private Context mContext;
	private int selectedPosition = -1;

	public interface OnTagsItemClickCallBack {
		void onItemClick(int id);
		void onItemLongClick(View view, int position);
	}

	private OnTagsItemClickCallBack mOnItemClickLitener;

	public void setOnItemClickLitenerBack(
			OnTagsItemClickCallBack mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	public GridRecycleTagAdapter(Context context, List<PersonalTagBean> list) {

		mInflater = LayoutInflater.from(context);
		this.list = list;
		this.mContext = context;

	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		if (list != null && list.size() > 0) {

			final PersonalTagBean item = list.get(position);
			

			if (mOnItemClickLitener != null) {
				holder.rlAll.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mOnItemClickLitener.onItemClick(position);
					}
				});

				holder.rlAll.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {

						return false;
					}
				});
			}
			holder.mTextView.setText(item.getName());
			if(item.getIsChoice()){
				holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.white));
				holder.mTextView.setBackgroundResource(R.drawable.main_imageview_tabhobby_clk);
			}else{
				holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.black));
				holder.mTextView.setBackgroundResource(R.drawable.main_imageview_tabhobby);
			}

		}
		
		
	
	}
	/**
	 * 为选中的item设置背景 控件设置选中
	 * 
	 * @param i
	 */
	public void setSelectedPosition(int i) {
		selectedPosition = i;
	}
	public int getSelectPosition(){
		return selectedPosition;
	}


	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		MyViewHolder holder = new MyViewHolder(mInflater.inflate(
				R.layout.item_personality_tag, parent, false));

		return holder;
	}
	
	class MyViewHolder extends ViewHolder {
		private TextView mTextView;
		private RelativeLayout rlAll;
		

			int id;

			public MyViewHolder(View view) {
				super(view);

				rlAll = (RelativeLayout) view
						.findViewById(R.id.all_item_personality_tag);
				mTextView=(TextView) view.findViewById(R.id.tag_item_personality_tag);

			}

		}

	
}
