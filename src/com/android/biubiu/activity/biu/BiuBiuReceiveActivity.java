package com.android.biubiu.activity.biu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.layout;
import com.android.biubiu.R.menu;
import com.android.biubiu.adapter.UserInterestAdapter;
import com.android.biubiu.bean.BiuDetialBean;
import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BiuBiuReceiveActivity extends BaseActivity {
	private RelativeLayout backLayout;
	private GridView mGridViewTag,mGridViewInterestTag;
	private UserInterestAdapter interestAdapter;
	private ArrayList<InterestTagBean> mList=new ArrayList<InterestTagBean>();
	private Button grabBT;
	private RelativeLayout neverGrab;
	private String TAG="BiuBiuReceiveActivity";
	private String referenceId,userCode,chatId;
	
	private BiuDetialBean biuDEtialBean=new BiuDetialBean();
	
	private TextView userName,distance,matchingScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_receive);
		referenceId=getIntent().getStringExtra("referenceId");
		userCode=getIntent().getStringExtra("userCode");
		chatId=getIntent().getStringExtra("chatId");
		LogUtil.e(TAG, "referenceId=="+referenceId+"||userCode=="+userCode+"||chatId=="+chatId);
		
		initView();
		initData();
		initAdapter();
	}

	private void initData() {
//		InterestTagBean item=new InterestTagBean();
//		item.setName("美丽");
//		item.setCode("1001");
//		InterestTagBean item2=new InterestTagBean();
//		item2.setName("漂亮");
//		item2.setCode("1002");
//		for(int i=0;i<5;i++){
//			mList.add(item);
//			mList.add(item2);
//		}
		LogUtil.d(TAG, "diao detial");
		//初始化页面
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.BIU_DETIAL);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().
					getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().
					getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			
			requestObject.put("chat_id", chatId);
			requestObject.put("reference_id", referenceId);
			requestObject.put("user_code", userCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		params.addBodyParameter("data", requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d(TAG, arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, arg0);
				JSONObject jsons;				
				try {
					jsons=new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
						toastShort(""+jsons.getString("error"));	
						return;
					}	
					Gson gson=new Gson();
					biuDEtialBean=gson.fromJson(jsons.getJSONObject("data").toString(), BiuDetialBean.class);
					
					userName.setText(biuDEtialBean.getUser_code());
					distance.setText(biuDEtialBean.getDistance());
					matchingScore.setText(biuDEtialBean.getMatching_score());
					
					System.out.println(biuDEtialBean.toString());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

	
		});
		
	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		interestAdapter = new UserInterestAdapter(getApplicationContext(), mList);
		mGridViewTag.setAdapter(interestAdapter);
		interestAdapter = new UserInterestAdapter(getApplicationContext(), mList);
		mGridViewInterestTag.setAdapter(interestAdapter);
		
		setGridviewHight(mGridViewTag);
		setGridviewHight(mGridViewInterestTag);
		mGridViewTag.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				
				toastShort(mList.get(position).getName());
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		backLayout=(RelativeLayout) findViewById(R.id.back_receive_biu_mine_rl);
		mGridViewTag=(GridView) findViewById(R.id.gridview_receive_biubiu_tag);
		mGridViewInterestTag=(GridView) findViewById(R.id.gridview_receive_biubiu_interest_tag);
		grabBT=(Button)findViewById(R.id.grab_biu_receive_biu_bt);
		neverGrab=(RelativeLayout) findViewById(R.id.never_grag_biu_receive_biu_rl);
		userName=(TextView) findViewById(R.id.name_receive_biu_tv);
		distance=(TextView) findViewById(R.id.matching_score_receive_biu_tv);
		
		neverGrab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toastShort("不见你");
			}
		});
		grabBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			toastShort("抢中了啊");
			grabBiu();
			}
		});
		
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
	
	}	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:

				break;

			default:
				break;
			}
		}
		
		
	};
	/**
	 * 设置 Gridview高度
	 */
	public void setGridviewHight(GridView mGridView) {
		LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mGridView
				.getLayoutParams();
		int mHight;
		if (mList.size() != 0 && (mList.size()) % 4 == 0) {
			mHight = (((mList.size()) / 4)) * DensityUtil.dip2px(this, 37);
		} else {
			mHight = (((mList.size()) / 4) + 1) * DensityUtil.dip2px(this, 37);
		}
		params.height = mHight;
		mGridView.setLayoutParams(params);

	}
	/**
	 * 抢biu
	 */
	public void grabBiu(){
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GRAB_BIU);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().
					getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().
					getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("chat_id", chatId);
			requestObject.put("user_code", userCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
		params.addBodyParameter("data", requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d(TAG, arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				Log.d(TAG, "result--"+arg0);
				JSONObject jsons;
			
					try {
						jsons=new JSONObject(arg0);
						String code = jsons.getString("state");
						LogUtil.d(TAG, ""+code);
						if(!code.equals("200")){
							toastShort(""+jsons.getString("error"));	
							return;
						}	
					} catch (Exception e) {
						// TODO: handle exception
					}

			}
		});
		
	}

	

}
