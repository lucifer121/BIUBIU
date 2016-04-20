package com.android.biubiu.activity.mine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.adapter.GridViewLableAdapter;
import com.android.biubiu.adapter.InterestLableListViewAdapter;



import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.android.biubiu.bean.InterestByCateBean;
import com.android.biubiu.bean.InterestTagBean;


import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.UserInfoBean;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InterestLabelActivity extends BaseActivity {
	private ListView mListView;
	private MyInterestLableListViewAdapter mAdapter;
	
	
	public List<InterestByCateBean> mDates=new ArrayList<InterestByCateBean>();
	
	private String TAG="InterestLabelActivity";
	private Context mContext;
	
	private GridViewLableAdapter mAdapterGridView;
	public List<InterestByCateBean> mDatesReceive=new ArrayList<InterestByCateBean>();

	private RelativeLayout backLayout,completeLayout;
	private UserInfoBean infoBean ;
	private ArrayList<InterestByCateBean> mDataFanhui=new ArrayList<InterestByCateBean>(); 
	private List<InterestByCateBean> mDataSHow=new ArrayList<InterestByCateBean>(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interest_label);
		mContext=InterestLabelActivity.this;
		mDatesReceive=(List<InterestByCateBean>) getIntent().getSerializableExtra("interestTags");
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
		initView();
		initData();
		initAdapter();
	}
	public void initAdapter() {
		
		mAdapter=new MyInterestLableListViewAdapter();
		mListView.setAdapter(mAdapter);
	}
	
	/**
	 * 网上请求数据
	 */
	private void initData() {
		showLoadingLayout(getResources().getString(R.string.loading));
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			dismissLoadingLayout();
			showErrorLayout(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismissErrorLayout();
					initData();
				}
			});
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GAT_TAGS);
		JSONObject requestObject = new JSONObject();		
		try {
			requestObject.put("device_code", SharePreferanceUtils.getInstance().getDeviceId(this, SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("type", Constants.INTEREST);
			requestObject.put("sex", infoBean.getSex());
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				
				dismissLoadingLayout();
				showErrorLayout(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismissErrorLayout();
						initData();
					}
				});
				toastShort("获取标签信息失败");	
			}

			@Override
			public void onFinished() {
				
				
			}

			@Override
			public void onSuccess(String arg0) {
				dismissLoadingLayout();
				Log.d("mytest", "inter--"+arg0);
				JSONObject jsons;
				try {
					jsons=new JSONObject(arg0);
					String code = jsons.getString("state");
					if(!code.equals("200")){
						showErrorLayout(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dismissErrorLayout();
								initData();
							}
						});
						toastShort("获取标签信息失败");	
						return;
					}
					
					JSONObject obj = jsons.getJSONObject("data");
//					String token = obj.getString("token");
//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					String dataTags=obj.getJSONArray("tags").toString();
					System.out.println(obj.get("tags"));
					Gson gson=new Gson();
					List<InterestByCateBean> interestByCateBeansList=gson.fromJson(dataTags,  
							new TypeToken<List<InterestByCateBean>>() {
						
					}.getType()); 
					LogUtil.d(TAG, ""+interestByCateBeansList.size());
					mDates.addAll(interestByCateBeansList);
				
					setView();
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
			}
		});
	
	}
	private void initView() {
		// TODO Auto-generated method stub
		mListView=(ListView) findViewById(R.id.id_listView_intetest_lable);
		backLayout=(RelativeLayout) findViewById(R.id.back_interest_lable_mine_rl);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		completeLayout=(RelativeLayout) findViewById(R.id.complete_interest_lable_rl);
		completeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				fanhuiDate();
				if(mDataFanhui==null||mDataFanhui.size()<=0){
					toastShort("还没有选择标签哦");
					return;
				}
				updateInfo();
//				Intent intent=getIntent();
//				Bundle bundle=new Bundle();
//				bundle.putSerializable("", (Serializable) mDates);
//				intent.putExtras(bundle);
//				setResult(RESULT_OK, intent);
//				finish();
			}
		});
	}
	/**
	 * 更新界面
	 */
	public Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				
				
				mAdapter.notifyDataSetChanged();
				LogUtil.e(TAG, "刷新");
				break;

			default:
				break;
			}
			
		}

	};
	
	/**
	 * 返回更新后的数据
	 */
	public void  fanhuiDate(){
		//TODO
		for(int i=0;i<mDates.size();i++){
			for(int j=0;j<mDates.get(i).getmInterestList().size();j++){
				List<InterestTagBean> mListTags=new ArrayList<InterestTagBean>();
				
				if(mDates.get(i).getmInterestList().get(j).getIsChoice()==true){

					mListTags.add(mDates.get(i).getmInterestList().get(j));	
				}
				if(mListTags!=null&&mListTags.size()>0){
					InterestByCateBean item=new InterestByCateBean();
					item.setTypename(mDates.get(i).getTypename());
					item.setTypecode(mDates.get(i).getTypecode());
					item.setmInterestList(mListTags);
					mDataFanhui.add(item);
				}
				
				
				
			}
			
		}
		
	}
	
	/**
	 * 选中tag
	 */
	public void setView(){
		if(mDatesReceive.size()==0){
			handler.sendEmptyMessage(1);
			
		}else {
			for(int i=0;i<mDatesReceive.size();i++ ){
				
				for(int j=0;j<mDatesReceive.get(i).getmInterestList().size();j++){
					
					for(int k=0;k<mDates.size();k++ ){
						
						for(int m=0;m<mDates.get(k).getmInterestList().size();m++){
							if(mDatesReceive.get(i).getmInterestList().get(j).getCode().equals(mDates.get(k).getmInterestList().get(m).getCode())){
								mDates.get(k).getmInterestList().get(m).setIsChoice(true);
								LogUtil.e(TAG, mDates.get(k).getmInterestList().get(m).getName());
								
							}
							
						}
					}		
				}
			}
			handler.sendEmptyMessage(1);
			
		}

	}
	
//	public void getDateByAdapter(){
//		mDates.clear();
//		LogUtil.e("返回数据", ""+mAdapter.getdate());
//		mDates.addAll(mAdapter.getdate());
//		LogUtil.e("新的数据了啊", ""+mDates.size());
//		
//	}
	
	public class MyInterestLableListViewAdapter extends BaseAdapter{
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDates.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mDates.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			
			final List<InterestTagBean> mDateLables;
			InterestByCateBean item=mDates.get(position);
			mDateLables=item.getmInterestList();

			
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_interest_, null);
				holder.mGridView=(GridView) convertView.findViewById(R.id.id_gridView_interest);
				holder.mView=convertView.findViewById(R.id.id_view_interest);
				holder.interest=(TextView) convertView.findViewById(R.id.interest_item_tv);
				holder.img=(ImageView) convertView.findViewById(R.id.img_item_interest_);
				holder.bottomLayout=(RelativeLayout) convertView.findViewById(R.id.bottom_item_interest);
				mAdapterGridView=new GridViewLableAdapter(mContext, mDateLables,item.getTypename());
				holder.mGridView.setAdapter(mAdapterGridView);
				holder.mGridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int positionId, long arg3) {
						if(mDateLables.get(positionId).getIsChoice()==false){
							mDates.get(position).getmInterestList();
							mDateLables.get(positionId).setIsChoice(true);					
						}else{
							mDateLables.get(positionId).setIsChoice(false);
						}
						boolean a=mDates.get(position).getmInterestList().get(positionId).getIsChoice();
					

					
						handler.sendEmptyMessage(1);
	
					}
				});
				
			
			setGridviewHight(mDateLables,holder);
			holder.interest.setText(item.getTypename());
			if(position==(mDates.size()-1)){
				holder.bottomLayout.setVisibility(View.VISIBLE);
			}
			if(item.getTypename().equals("音乐")){
				holder.img.setImageResource(R.drawable.main_tab_imageview_left_orange);
				
			}else if(item.getTypename().equals("电影")){
				holder.img.setImageResource(R.drawable.main_tab_imageview_left_pink);
				
			}else if(item.getTypename().equals("书籍")){
				holder.img.setImageResource(R.drawable.main_tab_imageview_left_blue);
				
			}else{
				holder.img.setImageResource(R.drawable.main_tab_imageview_left_green);	
			}

			return convertView;
		}
		
		private class ViewHolder {
			private TextView interest;
			private GridView mGridView;
			private View mView;
			private RelativeLayout bottomLayout;
			private ImageView img;

		}
		/**
		 * 设置 Gridview高度
		 */
		public void setGridviewHight(List<InterestTagBean> mList,ViewHolder holder) {
			LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) holder.mGridView
					.getLayoutParams();
			int mHight;
			if (mList.size() != 0 && (mList.size()) % 4 == 0) {
				mHight = (((mList.size()) / 4)) * DensityUtil.dip2px(mContext, 43);
			} else {
				mHight = (((mList.size()) / 4) + 1) * DensityUtil.dip2px(mContext, 43);
			}
			params.height = mHight;
			holder.mGridView.setLayoutParams(params);
			LinearLayout.LayoutParams params2 = (android.widget.LinearLayout.LayoutParams) holder.mView
					.getLayoutParams();
			params2.height=mHight+DensityUtil.dip2px(mContext, 13);
			holder.mView.setLayoutParams(params2);
		}
		
	}
	
	
	/**
	 * 更新上传信息
	 */
	protected void updateInfo() {
		// TODO Auto-generated method stub
		infoBean.setInterestCates(mDataFanhui);
//		if(mDataFanhui.size()==0){
//			return;
//		}
		ArrayList<InterestTagBean> list = new ArrayList<InterestTagBean>();
		for(int i=0;i<mDataFanhui.size();i++){
			list.addAll(mDataFanhui.get(i).getmInterestList());
		}
		infoBean.setInterestTags(list);
		showLoadingLayout("正在保存");
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_USETINFO);
		String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", token);
			requestObject.put("device_code", deviceId);
			StringBuffer interTags = new StringBuffer();
			if(infoBean.getInterestTags().size()>0){
				ArrayList<InterestTagBean> beans = infoBean.getInterestTags();
				for(int i=0;i<beans.size();i++){
					InterestTagBean bean = beans.get(i);
					if(i == beans.size()-1){
						interTags.append(bean.getCode());
						break;
					}
					interTags.append(bean.getCode()+",");
				}
			}
			requestObject.put("interested_tags",interTags.toString());
			requestObject.put("parameters", "interested_tags");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data", requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "error--"+ex.getMessage());
				LogUtil.d("mytest", "error--"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
				LogUtil.d("mytest", "interes=="+result);
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					LogUtil.d(TAG, state);
					if(!state.equals("200")){
						toastShort("获取标签信息失败");
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
//					String token = data.getString("token");
//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					Intent intent=getIntent();
					Bundle bundle = new Bundle();
					bundle.putSerializable("interestTags", (Serializable) mDataFanhui);
					if (mDataFanhui!=null) {
						System.out.println(mDataFanhui);						
					} 
					intent.putExtras(bundle);			
					setResult(RESULT_OK, intent);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	

	

}
