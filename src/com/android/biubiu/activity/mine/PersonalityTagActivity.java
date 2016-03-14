package com.android.biubiu.activity.mine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.adapter.GridRecycleTagAdapter;
import com.android.biubiu.adapter.GridRecycleTagAdapter.OnTagsItemClickCallBack;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PersonalityTagActivity extends BaseActivity implements OnTagsItemClickCallBack{
	private RecyclerView mRecyclerView;
	private GridRecycleTagAdapter mAdapter;
	private List<PersonalTagBean> mList=new ArrayList<PersonalTagBean>();
	
	private int isSelectorTagNumber=0;
	// 计算recycle 高度
	private int mHight;
	private RelativeLayout backLayout,completeLayout;
	//网络相关
	private String TAG="PersonalityTagActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personality_tag);
		initView();
		loadData();
		initAdapter();
		setRecycleviewHight();
	}

	private void loadData() {
//		PersonalTagBean item=new PersonalTagBean();
//		item.setId(1);
//		item.setTag("溫柔");
//		item.setIsChoice(false);
//		mList.add(item);
//		PersonalTagBean item2=new PersonalTagBean();
//		item2.setId(1);
//		item2.setTag("可愛");
//		item2.setIsChoice(false);
//		mList.add(item2);
//		PersonalTagBean item3=new PersonalTagBean();
//		item3.setId(1);
//		item3.setTag("直爽");
//		item3.setIsChoice(false);
//		mList.add(item3);
//		PersonalTagBean item4=new PersonalTagBean();
//		item4.setId(1);
//		item4.setTag("浪漫");
//		item4.setIsChoice(false);
//		mList.add(item4);
//		PersonalTagBean item5=new PersonalTagBean();
//		item5.setId(1);
//		item5.setTag("吃貨");
//		item5.setIsChoice(false);
//		mList.add(item5);
//		PersonalTagBean item6=new PersonalTagBean();
//		item6.setId(1);
//		item6.setTag("软妹子");
//		item6.setIsChoice(false);
//		mList.add(item6);
//		PersonalTagBean item7=new PersonalTagBean();
//		item7.setId(1);
//		item7.setTag("偽腐");
//		item7.setIsChoice(false);
//		mList.add(item7);
//		PersonalTagBean item8=new PersonalTagBean();
//		item8.setId(1);
//		item8.setTag("文藝");
//		item8.setIsChoice(false);
//		mList.add(item8);
//		PersonalTagBean item9=new PersonalTagBean();
//		item9.setId(1);
//		item9.setTag("隨性");
//		item9.setIsChoice(false);
//		mList.add(item9);
//		PersonalTagBean item10=new PersonalTagBean();
//		item10.setId(1);
//		item10.setTag("真誠");
//		item10.setIsChoice(false);
//		mList.add(item10);
//		PersonalTagBean item11=new PersonalTagBean();
//		item11.setId(1);
//		item11.setTag("御姐");
//		item11.setIsChoice(false);
//		mList.add(item11);
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GAT_TAGS);
		JSONObject requestObject = new JSONObject();		
		try {
			requestObject.put("device_code", Utils.getDeviceID(this));
			requestObject.put("type", Constants.PERSONALIED);
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
					System.out.println(obj.get("tags"));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		mRecyclerView=(RecyclerView) findViewById(R.id.id_recyclerView_personality_tag);
		backLayout=(RelativeLayout) findViewById(R.id.back_personality_tag_mine_rl);
		completeLayout=(RelativeLayout) findViewById(R.id.complete_personality_tag_rl);
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		completeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toastShort("完成");
			}
		});
	}
	private void initAdapter() {
		// TODO Auto-generated method stub
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
		mAdapter = new GridRecycleTagAdapter(this, mList);

		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickLitenerBack(this);
	}

	@Override
	public void onItemClick(int id) {
		// TODO Auto-generated method stub
	//	toastShort(mList.get(id).getTag());
		isSelectorTagNumber=0;
		for(int i=0;i<mList.size();i++){
			if(mList.get(i).getIsChoice()==true){
				isSelectorTagNumber++;
			}
		}
		
		if(mList.get(id).getIsChoice()==false){
			if(isSelectorTagNumber<10){
				mList.get(id).setIsChoice(true);
				mAdapter.notifyDataSetChanged();
			}else{
				toastShort("阿哦，已经不能再添加了哦！");
			}
			
		}else{
			mList.get(id).setIsChoice(false);
			mAdapter.notifyDataSetChanged();
		}
		
		
//		mAdapter.setSelectedPosition(id);
//		mAdapter.notifyDataSetChanged();
	}

	

	/**
	 * 设置 recycleview高度
	 */
	public void setRecycleviewHight() {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mRecyclerView
				.getLayoutParams();
		if (mList.size() != 0 && (mList.size()) % 4 == 0) {
			mHight = (((mList.size()) / 4)) * DensityUtil.dip2px(this, 37);
		} else {
			mHight = (((mList.size()) / 4) + 1) * DensityUtil.dip2px(this, 37);
		}
		params.height = mHight;
		mRecyclerView.setLayoutParams(params);
	}

	@Override
	public void onItemLongClick(View view, int position) {
		// TODO Auto-generated method stub
		
	}

	
}