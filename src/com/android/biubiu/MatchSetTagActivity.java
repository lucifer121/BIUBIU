package com.android.biubiu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.adapter.GridRecycleTagAdapter;
import com.android.biubiu.adapter.GridRecycleTagAdapter.OnTagsItemClickCallBack;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.SettingBean;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MatchSetTagActivity extends BaseActivity implements OnTagsItemClickCallBack{
	private RecyclerView mRecyclerView;
	private GridRecycleTagAdapter mAdapter;
	private List<PersonalTagBean> mList=new ArrayList<PersonalTagBean>();

	private List<PersonalTagBean> mCheckList=new ArrayList<PersonalTagBean>();

	private int isSelectorTagNumber=0;
	// 计算recycle 高度
	private int mHight;
	private RelativeLayout backLayout,completeLayout;
	//网络相关
	private String TAG="PersonalityTagActivity";
	private ArrayList<PersonalTagBean> mDataReceive=new ArrayList<PersonalTagBean>(); 
	private ArrayList<PersonalTagBean> mDataFanhui=new ArrayList<PersonalTagBean>(); 
	boolean isSelBoy = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personality_tag);
		mDataReceive=(ArrayList<PersonalTagBean>) getIntent().getSerializableExtra("personalTags");
		isSelBoy = getIntent().getBooleanExtra("sex", false);
		initView();
		loadData();
		initAdapter();
		setRecycleviewHight();
	}
	/**
	 * 加载tag 数据
	 */
	private void loadData() {
		showLoadingLayout(getResources().getString(R.string.loading));
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			dismissLoadingLayout();
			showErrorLayout(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismissErrorLayout();
					loadData();
				}
			});
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GAT_TAGS);
		JSONObject requestObject = new JSONObject();		
		try {
			requestObject.put("device_code", SharePreferanceUtils.getInstance().getDeviceId(this, SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("type", Constants.PERSONALIED);
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
			if(isSelBoy){
				requestObject.put("sex", Constants.SEX_MALE);
			}else{
				requestObject.put("sex", Constants.SEX_FAMALE);
			}
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
				Log.d("mytest", "setTag--"+arg0.getMessage());
				dismissLoadingLayout();
				showErrorLayout(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismissErrorLayout();
						loadData();
					}
				});
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				dismissLoadingLayout();
				Log.d("mytest", "setTag--"+arg0);
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
								loadData();
							}
						});
						toastShort("获取标签信息失败");	
						return;
					}
					JSONObject obj = jsons.getJSONObject("data");
//					String token = (jsons.getJSONObject("data").getString("token"));
//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					String dataTag=obj.getJSONArray("tags").toString();
					Gson gson=new Gson();
					List<PersonalTagBean> personalTagBeansList = gson.fromJson(dataTag,  
							new TypeToken<List<PersonalTagBean>>() {  
					}.getType()); 
					for (PersonalTagBean tag : personalTagBeansList) {  
						mList.add(tag);
					} 
					setRecycleviewHight();
					setView();
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
				updata();
			}
		});
	}
	/**
	 * 保存后   返回更新数据
	 */
	public void updata(){
		for(PersonalTagBean bean: mList){
			if(bean.getIsChoice()==true){
				mDataFanhui.add(bean);
			}

		}
		Intent intent = new Intent();
		intent.putExtra("personalTags", mDataFanhui);
		setResult(RESULT_OK, intent);
		finish();
	}
	private void initAdapter() {
		// TODO Auto-generated method stub
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
		mAdapter = new GridRecycleTagAdapter(this, mCheckList);

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
	}
	/**
	 * 设置 recycleview高度
	 */
	public void setRecycleviewHight() {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mRecyclerView
				.getLayoutParams();
		if (mList.size() != 0 && (mList.size()) % 4 == 0) {
			mHight = (((mList.size()) / 4)) * DensityUtil.dip2px(this, 44);
		} else {
			mHight = (((mList.size()) / 4) + 1) * DensityUtil.dip2px(this, 44);
		}
		params.height = mHight;
		mRecyclerView.setLayoutParams(params);
	}
	@Override
	public void onItemLongClick(View view, int position) {
		// TODO Auto-generated method stub
	}
	/**
	 * 选中tag
	 */
	public void setView(){
		if(mDataReceive.size()==0){
		}else {
			for(int i=0;i<mDataReceive.size();i++){

				for(int j=0;j<mList.size();j++){
					if(mDataReceive.get(i).getCode().equals(mList.get(j).getCode())){
						mList.get(j).setIsChoice(true);
						LogUtil.e(TAG, "1");
					}

				}
			}
		}
		mCheckList.clear();
		mCheckList.addAll(mList);
		initAdapter();
	}
}
