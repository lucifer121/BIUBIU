package com.android.biubiu.activity.mine;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.R.id;
import com.android.biubiu.R.layout;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.common.city.ArrayWheelAdapter;
import com.android.biubiu.common.city.OnWheelChangedListener;
import com.android.biubiu.common.city.WheelView;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChangeHeightWeightActivity extends BaseActivity implements OnWheelChangedListener{
	private WheelView heightWheelView,weightWheelView;

	private String [] heights=new String[70],weights=new String[60];
	//	private String heights[]={"170","171","172"},weights[]={"40","50","60"};
	private TextView nheightWeight;
	private int pHeightCurrent=0,pWeightCurrent=0;

	private RelativeLayout backLayout,completeLayout;
	UserInfoBean infoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_height_weight);
		infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
		loadDate();
		initView();

		setUpData();
		setUpListener();
	}
	/**
	 * 加载数据
	 */
	private void loadDate() {

		for(int i=0;i<70;i++){
			heights[i]=""+(i+150)+"cm";

		}
		for(int j=0;j<60;j++){
			weights[j]=""+(j+40)+"kg";

		}

	}
	private void setUpData() {
		// initProvinceDatas();
		//	constellation.setText(mProvinceDatas[0]);
		heightWheelView.setViewAdapter(new ArrayWheelAdapter<String>(
				ChangeHeightWeightActivity.this, heights));
		weightWheelView.setViewAdapter(new ArrayWheelAdapter<String>(ChangeHeightWeightActivity.this, weights));

		//	Log.e("lucifer", "mProvinceDatas.length==" + mProvinceDatas.length);
		// 设置可见条目数量
		heightWheelView.setVisibleItems(7);
		weightWheelView.setVisibleItems(7);

	}

	private void initView() {
		// TODO Auto-generated method stub
		heightWheelView=(WheelView) findViewById(R.id.id_hight_myview);
		weightWheelView=(WheelView) findViewById(R.id.id_weight_myview);
		nheightWeight=(TextView) findViewById(R.id.height_weight_change_city_tv);

		backLayout=(RelativeLayout) findViewById(R.id.back_changeheight_mine_rl);
		completeLayout=(RelativeLayout) findViewById(R.id.mine_changeHeight_wanchengrl);
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
				if(null == nheightWeight.getText() ||nheightWeight.getText().toString().equals("")){
					toastShort("请选择身高体重");
					return;
				}
				infoBean.setHeight(Integer.parseInt(heights[pHeightCurrent].substring(0, 2)));
				infoBean.setWeight(Integer.parseInt(weights[pWeightCurrent].substring(0, 0)));
				updateInfo();
			}
		});
	}
	protected void updateInfo() {
		// TODO Auto-generated method stub
		RequestParams params = HttpUtils.getUpdateInfoParams(getApplicationContext(), infoBean,"height,weight");
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
				LogUtil.d("mytest", "height=="+result);
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort(jsons.getString("error"));
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
					String token = data.getString("token");
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					Intent intent = new Intent();
					intent.putExtra("userInfoBean", infoBean);
					setResult(RESULT_OK, intent);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	private void setUpListener() {
		// 添加change事件
		heightWheelView.addChangingListener(this);
		weightWheelView.addChangingListener(this);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == heightWheelView) {

			pHeightCurrent = heightWheelView.getCurrentItem();

			nheightWeight.setText(""+heights[pHeightCurrent]+"  "+weights[pWeightCurrent]);
		} 
		if (wheel == weightWheelView) {

			pWeightCurrent = weightWheelView.getCurrentItem();
			nheightWeight.setText(""+heights[pHeightCurrent]+"  "+weights[pWeightCurrent]);

		} 


	}



}
