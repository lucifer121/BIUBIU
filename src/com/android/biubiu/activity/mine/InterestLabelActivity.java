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

import com.android.biubiu.BaseActivity;
import com.android.biubiu.MainActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.color;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.adapter.GridViewLableAdapter;
import com.android.biubiu.adapter.InterestLableListViewAdapter;



import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.android.biubiu.bean.InterestByCateBean;
import com.android.biubiu.bean.InterestTagBean;


import com.android.biubiu.bean.PersonalTagBean;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interest_label);
		mContext=InterestLabelActivity.this;
		mDatesReceive=(List<InterestByCateBean>) getIntent().getSerializableExtra("interestTags");
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

		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GAT_TAGS);
		JSONObject requestObject = new JSONObject();		
		try {
			requestObject.put("device_code", Utils.getDeviceID(this));
			requestObject.put("type", Constants.INTEREST);
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				toastShort(arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				Log.d("mytest", "result--"+arg0);
				JSONObject jsons;
				try {
					jsons=new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
						toastShort(""+jsons.getString("error"));	
						return;
					}
					
					JSONObject obj = jsons.getJSONObject("data");
					String dataTags=obj.getString("tags").toString();
					System.out.println(obj.get("tags"));
					Gson gson=new Gson();
					List<InterestByCateBean> interestByCateBeansList=gson.fromJson(dataTags,  
							new TypeToken<List<InterestByCateBean>>() {
						
					}.getType()); 
					LogUtil.d(TAG, ""+interestByCateBeansList.size());
					mDates.addAll(interestByCateBeansList);
					handler.sendEmptyMessage(1);
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
				Intent intent=getIntent();
				Bundle bundle=new Bundle();
				bundle.putSerializable("", (Serializable) mDates);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
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
							if(mDatesReceive.get(i).getmInterestList().get(j).getCode()==mDates.get(k).getmInterestList().get(m).getCode()){
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

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_interest_, null);
				holder.mGridView=(GridView) convertView.findViewById(R.id.id_gridView_interest);
				holder.mView=convertView.findViewById(R.id.id_view_interest);
				holder.interest=(TextView) convertView.findViewById(R.id.interest_item_tv);
				holder.bottomLayout=(RelativeLayout) convertView.findViewById(R.id.bottom_item_interest);
				mAdapterGridView=new GridViewLableAdapter(mContext, mDateLables);
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
					
						initAdapter();
	
					}
				});
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			setGridviewHight(mDateLables,holder);
			holder.interest.setText(item.getTypename());
			if(position==(mDates.size()-1)){
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
		
	}
	

	

}
