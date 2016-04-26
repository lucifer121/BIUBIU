package com.android.biubiu.activity.biu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.common.Umutils;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.DensityUtil;
import com.android.biubiu.utils.DisplayUtils;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.NetUtils;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.android.biubiu.utils.Utils;
import com.android.biubiu.view.Flowlayout;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BiuBiuSendActivity extends BaseActivity implements OnClickListener{

	private String mNames[] = {
			"我想认识你","披星戴月",
			"哦","啊","今天是个好日子啊","真是个忧伤的故事",
	};
	private Flowlayout mFlowLayout;
	Button sendBiuBtn;
	private RelativeLayout backLayout;
	private EditText mEditText;
	private Button button;
	private List<PersonalTagBean> mList=new ArrayList<PersonalTagBean>();
	private String TAG="BiuBiuSendActivity";
	private TextView number;
	private ImageView userPhoto;
	private ImageOptions imageOptions;
	//private LinearLayout flowRlLayout;
	private ScrollView flowScroll;
	int viewHight;
	int bottomSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_send);

		//		initChildViews();
		viewHight=(int) (350.0*DisplayUtils.getWindowHeight(this)/640.0);
		bottomSend=(int) (52.0*DisplayUtils.getWindowHeight(this)/640.0);
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.loadingbbbb)
		.setFailureDrawableId(R.drawable.photo_fail)
		.setIgnoreGif(true)
		.build();
		initView();
		initData();


	}

	/**
	 * 网络加载话题标签
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
			requestObject.put("type", Constants.CHAT);
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
				dismissLoadingLayout();
				showErrorLayout(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismissErrorLayout();
						initData();
					}
				});
				toastShort("获取话题标签失败");	
				log.e(TAG, arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				dismissLoadingLayout();
				Log.d(TAG, "sendtag--"+arg0);
				JSONObject jsons;
				try {
					jsons=new JSONObject(arg0);
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
						showErrorLayout(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dismissErrorLayout();
								initData();
							}
						});
						toastShort("获取话题标签失败");	
						return;
					}

					JSONObject obj = jsons.getJSONObject("data");
					//						System.out.println(obj.get("tags"));
					String dataTag=obj.getJSONArray("tags").toString();
					Gson gson=new Gson();

					//					String token=obj.getString("token");
					//					if(token!=null||token.equals("")){
					//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					//					}

					LogUtil.e(TAG, dataTag);
					List<PersonalTagBean> personalTagBeansList = gson.fromJson(dataTag,  
							new TypeToken<List<PersonalTagBean>>() {  
					}.getType()); 
					LogUtil.e(TAG, "personalTagBeansList"+personalTagBeansList.size());
					for (PersonalTagBean tag : personalTagBeansList) {  

						mList.add(tag);
						log.e(TAG, tag.getName());

					}  
					initChildViews();
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		});

		x.image().bind(userPhoto, SharePreferanceUtils.getInstance().getUserHead(getApplicationContext(), SharePreferanceUtils.USER_HEAD,""),imageOptions);

	}
	@SuppressLint("CutPasteId") 
	private void initView() {
		// TODO Auto-generated method stub
		userPhoto=(ImageView) findViewById(R.id.photo_head_senbiu_img);
		sendBiuBtn = (Button) findViewById(R.id.send_biu);
		sendBiuBtn.setOnClickListener(this);
		backLayout=(RelativeLayout) findViewById(R.id.back_send_biu_mine_rl);
		mEditText=(EditText) findViewById(R.id.topic_send_biu_et);
		mEditText.addTextChangedListener(watcher);
		number=(TextView) findViewById(R.id.number_send_biu_tv);
		button=(Button) findViewById(R.id.send_biu);
		//flowRlLayout=(LinearLayout) findViewById(R.id.relativeLayout1_send_biu_rl);
		flowScroll = (ScrollView) findViewById(R.id.flow_scroll);
		flowScroll.setFillViewport(true);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if(mEditText.getText().length()<=0){
					toastShort("请选择话题标签");
				}else{
					String sendTimeStr = SharePreferanceUtils.getInstance().getBiuTime(BiuBiuSendActivity.this, SharePreferanceUtils.SEND_BIU_TIME, "");
					if(!sendTimeStr.equals("")){
						long time = System.currentTimeMillis() - Long.parseLong(sendTimeStr);
						if(time/1000 > 90){
							sendBiu(mEditText.getText().toString());
						}else{
							Toast.makeText(BiuBiuSendActivity.this, "距离上次发biu还不到90秒哦！", 1000).show();
						}	
					}else{
						sendBiu(mEditText.getText().toString());
					}
				}
			}
		});
		backLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		/*RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) flowRlLayout.getLayoutParams();
		params.height=(int) viewHight;
		flowRlLayout.setLayoutParams(params);

		LinearLayout.LayoutParams params2=(android.widget.LinearLayout.LayoutParams) sendBiuBtn.getLayoutParams();
		params2.bottomMargin=bottomSend;
		sendBiuBtn.setLayoutParams(params2);*/

	}
	private TextWatcher watcher=new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			number.setText(""+arg0.length());

			changeBG();
		}
	};
	private void changeBG(){
		if(mEditText.getText().length()>0){
			button.setBackgroundResource(R.drawable.biu_btn_normal);
		}else{
			button.setBackgroundResource(R.drawable.biu_btn_disabled);
		}
	}

	@SuppressWarnings("deprecation")
	private void initChildViews() {
		// TODO Auto-generated method stub
		mFlowLayout = (Flowlayout) findViewById(R.id.flowlayout);
		MarginLayoutParams lp = new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 30;
		//		lp.rightMargin = 5;
		lp.topMargin = 45;
		//		lp.bottomMargin = 5;
		for(int i = 0; i < mList.size(); i ++){

			final TextView view = new TextView(this);
			view.setText(mList.get(i).getName());
			view.setTextColor(getResources().getColor(R.color.textview_item_send_bg));
			view.setTextSize(11);			
			//	view.setPadding(24, 24, 24, 24);
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.item_send_biu_img_bg));
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//	Toast.makeText(getApplicationContext(), view.getText(), Toast.LENGTH_SHORT).show();
					mEditText.setText(view.getText());
					mEditText.setSelection(mEditText.getText().toString().length());
				}
			});
			mFlowLayout.addView(view,lp);
		}
		/*RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) flowRlLayout.getLayoutParams();
		if(mFlowLayout.getmHeight()>viewHight){
			params.height=mFlowLayout.getmHeight();
		}else{
			params.height=viewHight;
		}
		flowRlLayout.setLayoutParams(params);*/
		ViewTreeObserver vto = mFlowLayout.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
		public boolean onPreDraw() {
			mFlowLayout.getViewTreeObserver().removeOnPreDrawListener(this);
		int height = mFlowLayout.getMeasuredHeight();
		LinearLayout.LayoutParams params=(android.widget.LinearLayout.LayoutParams) mFlowLayout.getLayoutParams(); 
		params.height=mFlowLayout.getmHeight();
		mFlowLayout.setLayoutParams(params);
		return true;
		}
		}); 
		LinearLayout.LayoutParams params2=(android.widget.LinearLayout.LayoutParams) sendBiuBtn.getLayoutParams();
		params2.bottomMargin=bottomSend;
		sendBiuBtn.setLayoutParams(params2);
	}
	/**
	 * 发送biu
	 */
	private void sendBiu(String chatTag){
		if(!NetUtils.isNetworkConnected(getApplicationContext())){
			toastShort(getResources().getString(R.string.net_error));
			return;
		}
		showLoadingLayout(getResources().getString(R.string.sending));
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.SEND_BIU);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().
					getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().
					getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("chat_tags", chatTag);
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
				dismissLoadingLayout();
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				dismissLoadingLayout();
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
					JSONObject obj = jsons.getJSONObject("data");
					//					String token=obj.getString("token");
					//					if(token!=null||!token.equals("")){
					//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					//					}

					//					LogUtil.d(TAG, token);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Umutils.count(BiuBiuSendActivity.this, Umutils.SEND_BIU_TOTAL);
				toastShort("发送成功");
				setResult(RESULT_OK);
				finish();	

			}
		});


	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//发biubiu按钮点击
		case R.id.send_biu:
			//实际需要在调用发biubiu接口请求成功后执行以下代码
			setResult(RESULT_OK);
			finish();
			break;

		default:
			break;
		}
	}

}