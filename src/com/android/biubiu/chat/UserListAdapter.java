package com.android.biubiu.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;
import com.android.biubiu.R;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> mData=new ArrayList<String>();
	

	public UserListAdapter(Context context,List<String> mData){
		this.mContext=context;
		this.mData=mData;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_chat_user_list, null);
			holder.img=(ImageView) convertView.findViewById(R.id.userHead_chat_user_list_img);
			holder.userName=(TextView) convertView.findViewById(R.id.userName_chat_user_List_tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.userName.setText(mData.get(position));
		return convertView;
	}
	
	private class ViewHolder{
		private ImageView img;
		private TextView userName;
		private TextView userInfo;
	}



}
