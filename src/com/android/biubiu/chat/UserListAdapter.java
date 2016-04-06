package com.android.biubiu.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import cc.imeetu.iu.R;

import com.android.biubiu.activity.biu.MyPagerActivity;
import com.android.biubiu.bean.UserFriends;
import com.android.biubiu.sqlite.SchoolDao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter {
	private Context mContext;
	private List<UserFriends> mData=new ArrayList<UserFriends>();
	private SchoolDao schoolDao;
	private ImageOptions imageOptions;

	public UserListAdapter(Context context,List<UserFriends> mData){
		this.mContext=context;
		this.mData=mData;
		schoolDao=new SchoolDao();
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		
		.setFailureDrawableId(R.drawable.photo_fail)
		.setIgnoreGif(true)
		.build();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
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
		final UserFriends item=mData.get(position);
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_chat_user_list, null);
			holder.img=(ImageView) convertView.findViewById(R.id.userHead_chat_user_list_img);
			holder.userName=(TextView) convertView.findViewById(R.id.userName_chat_user_List_tv);
			holder.age=(TextView) convertView.findViewById(R.id.userInfo_chat_user_List_tv);
			holder.star=(TextView) convertView.findViewById(R.id.userXingzuo_chat_user_List_tv);
			holder.school=(TextView) convertView.findViewById(R.id.userJob_chat_user_List_tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.userName.setText(item.getNickname());
		holder.age.setText(item.getAge()+"岁");
		holder.star.setText(item.getStarsign()+"");
		if(item.getIsgraduated().equals("1")){
			if(schoolDao.getschoolName(item.getSchool()).get(0).getUnivsNameString()!=null){
				holder.school.setText(schoolDao.getschoolName(item.getSchool()).get(0).getUnivsNameString());
			}
			
		}else{
			holder.school.setText(item.getCarrer());
		}
		x.image().bind(holder.img, item.getIcon_thumbnailUrl(),imageOptions);
		holder.img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(mContext,MyPagerActivity.class);
				intent.putExtra("userCode", item.getUserCode());
				mContext.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	private class ViewHolder{
		private ImageView img;
		private TextView userName;
		private TextView userInfo;
		private TextView age;
		private TextView star;
		private TextView school;
	}



}
