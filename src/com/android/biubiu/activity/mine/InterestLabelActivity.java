package com.android.biubiu.activity.mine;

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
import com.android.biubiu.R;
import com.android.biubiu.R.color;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.adapter.InterestLableListViewAdapter;


import com.android.biubiu.utils.Constants;
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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class InterestLabelActivity extends BaseActivity {
	private ListView mListView;
	private InterestLableListViewAdapter mAdapter;
	private List<InterestByCateBean> mDates=new ArrayList<InterestByCateBean>();
	private RelativeLayout backLayout;
	private String TAG="InterestLabelActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interest_label);
		initView();
		initData();
		initAdapter();
	}
	private void initAdapter() {
		// TODO Auto-generated method stub
		mAdapter=new InterestLableListViewAdapter(this,mDates);
		mListView.setAdapter(mAdapter);
	}
	
	private void initData() {
//		List<InterestTagBean> lableBeans=new ArrayList<InterestTagBean>();
//		InterestTagBean item=new InterestTagBean();
//		item.setIsChoice(false);
//		item.setName("跑步");
//	//	item.setBgColor(R.color.gray);
//		InterestTagBean item2=new InterestTagBean();
//		item2.setIsChoice(false);
//		item2.setName("游泳");
//	//	item2.setBgColor(R.color.gray);
//		for(int i=0;i<7;i++){
//			lableBeans.add(item);
//			lableBeans.add(item2);
//		}
//		for(int i=0;i<4;i++){
////			InterestByCateBean interestLableBeanList=new InterestByCateBean();
////			Map<String, List<InterestTagBean>> mMap=new HashMap<String, List<InterestTagBean>>();
////			interestLableBeanList.setColorBg(R.color.gray);
////			interestLableBeanList.setId(1);
////			interestLableBeanList.setInterest("运动");
////			mMap.put("运动",lableBeans);
////			interestLableBeanList.setmInterestMap(mMap);
////			mDates.add(interestLableBeanList);
//		}
		
		
		
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
	}
	/**
	 * 更新界面
	 */
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				mAdapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
			
		}
		
		
	};

	

}
