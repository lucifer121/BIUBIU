package com.android.biubiu.activity.biu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.imeetu.iu.R;
import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.chat.Constant;
import com.android.biubiu.utils.Constants;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;

public class BiuChargeActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout backRl;
	private TextView myUmTv;
	private LinearLayout fitityUmLayout;
	private TextView fitityUmTv;
	private LinearLayout oneHdUmLayout;
	private TextView oneHdUmTv;
	private LinearLayout fiveHdUmLayout;
	private TextView fiveHdUmTv;
	private LinearLayout oneThUmLayout;
	private TextView oneThUntv;
	private TextView selectUmTv;
	private TextView umPriceTv;
	private LinearLayout payLayout;
	private Button zfbPayBtn;
	private Button wxPayBtn;
	private View payBtnBgView;
	//是否是支付宝支付
	boolean isZfbPay = true;
	//当前选中的U米数
	int umCount = 50;
	//当前选中的U米数对应价格
	int umCountPrice = 5;
	String orderCode = "";
	//支付结果返回入口
	BCCallback bcCallback = new BCCallback() {
		@Override
		public void done(final BCResult bcResult) {
			handler.sendEmptyMessage(1);
			final BCPayResult bcPayResult = (BCPayResult)bcResult;
			//根据你自己的需求处理支付结果
			//需要注意的是，此处如果涉及到UI的更新，请在UI主进程或者Handler操作
			BiuChargeActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					handler.sendEmptyMessage(1);
					String result = bcPayResult.getResult();
					/*
		                      注意！
		                      所有支付渠道建议以服务端的状态金额为准，此处返回的RESULT_SUCCESS仅仅代表手机端支付成功
					 */
					if(result == null){
						return;
					}
					if (result.equals(BCPayResult.RESULT_SUCCESS)) {
						//成功,校验是否支付成功
						isPaySuc();
						
					} else if (result.equals(BCPayResult.RESULT_CANCEL)){
						//取消
					}else if (result.equals(BCPayResult.RESULT_FAIL)) {
						String toastMsg = "支付失败, 原因: " + bcPayResult.getErrCode() +
								" # " + bcPayResult.getErrMsg() +
								" # " + bcPayResult.getDetailInfo();

						/**
						 * 你发布的项目中不应该出现如下错误，此处由于支付宝政策原因，
						 * 不再提供支付宝支付的测试功能，所以给出提示说明
						 */
						if (bcPayResult.getErrMsg().equals("PAY_FACTOR_NOT_SET") &&
								bcPayResult.getDetailInfo().startsWith("支付宝参数")) {
							toastMsg = "支付失败：由于支付宝政策原因，故不再提供支付宝支付的测试功能，给您带来的不便，敬请谅解";
						}

						/**
						 * 以下是正常流程，请按需处理失败信息
						 */

						//Toast.makeText(BiuChargeActivity.this, toastMsg, Toast.LENGTH_LONG).show();
						Log.d("mytest", toastMsg);
					} else if (result.equals(BCPayResult.RESULT_UNKNOWN)) {
						//可能出现在支付宝8000返回状态
					} else {
						//失败
					}
				}
			});
		}
	};
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1){
				dismissLoadingLayout();
			}
		};
	};
	int myUmCount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biu_charge_layout);
		myUmCount = getIntent().getIntExtra("coin", 0);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		zfbPayBtn = (Button) findViewById(R.id.zfb_pay_btn);
		zfbPayBtn.setOnClickListener(this);
		wxPayBtn = (Button) findViewById(R.id.wx_pay_btn);
		wxPayBtn.setOnClickListener(this);
		myUmTv = (TextView) findViewById(R.id.my_um_tv);
		myUmTv.setText(myUmCount+"粒");
		payLayout = (LinearLayout) findViewById(R.id.pay_layout);
		payBtnBgView = findViewById(R.id.btn_bg_view);
		fitityUmLayout = (LinearLayout) findViewById(R.id.fitity_um_layout);
		fitityUmLayout.setOnClickListener(this);
		fitityUmTv = (TextView) findViewById(R.id.fitity_um_tv);
		oneHdUmLayout = (LinearLayout) findViewById(R.id.one_hdum_layout);
		oneHdUmLayout.setOnClickListener(this);
		oneHdUmTv = (TextView) findViewById(R.id.one_hdum_tv);
		fiveHdUmLayout = (LinearLayout) findViewById(R.id.five_hdum_layout);
		fiveHdUmLayout.setOnClickListener(this);
		fiveHdUmTv = (TextView) findViewById(R.id.five_hdum_tv);
		oneThUmLayout = (LinearLayout) findViewById(R.id.one_thum_layout);
		oneThUmLayout.setOnClickListener(this);
		oneThUntv = (TextView) findViewById(R.id.one_thum_tv);
		selectUmTv = (TextView) findViewById(R.id.select_um_tv);
		umPriceTv = (TextView) findViewById(R.id.um_price_tv);
		backRl = (RelativeLayout) findViewById(R.id.back_rl);
		backRl.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zfb_pay_btn:
//			if(isZfbPay){
//				showLoadingLayout("正在提交订单……");
//				getOrderCode();
//			}else{
				showLoadingLayout("正在提交订单……");
				getOrderCode();
				isZfbPay = true;
				payBtnBgView.setBackgroundResource(R.drawable.pay_btn_normal_blue);
				zfbPayBtn.setBackgroundResource(R.drawable.pay_afb_selector);
				zfbPayBtn.setTextColor(getResources().getColor(R.color.white));
				wxPayBtn.setBackgroundColor(getResources().getColor(R.color.white));
				wxPayBtn.setTextColor(getResources().getColor(R.color.pay_blue_txt));
//			}
			break;
		case R.id.wx_pay_btn:
//			if(!isZfbPay){
//				showLoadingLayout("正在提交订单……");
//				getOrderCode();
//			}else{
				showLoadingLayout("正在提交订单……");
				getOrderCode();
				isZfbPay = false;
				payBtnBgView.setBackgroundResource(R.drawable.pay_btn_normal_green);
				zfbPayBtn.setBackgroundColor(getResources().getColor(R.color.white));
				zfbPayBtn.setTextColor(getResources().getColor(R.color.pay_green_txt));
				wxPayBtn.setBackgroundResource(R.drawable.pay_wx_selector);
				wxPayBtn.setTextColor(getResources().getColor(R.color.white));
//			}
			break;
		case R.id.fitity_um_layout:
			if(umCount != 50){
				umCount = 50;
				umCountPrice = 5;
				fitityUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_light);
				oneHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fiveHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneThUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fitityUmTv.setTextColor(getResources().getColor(R.color.white));
				oneHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				fiveHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneThUntv.setTextColor(getResources().getColor(R.color.text_huise2));
				selectUmTv.setText("50U米");
				umPriceTv.setText("¥ 5");
			}
			break;
		case R.id.one_hdum_layout:
			if(umCount != 100){
				umCount = 100;
				umCountPrice = 10;
				fitityUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_light);
				fiveHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneThUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fitityUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneHdUmTv.setTextColor(getResources().getColor(R.color.white));
				fiveHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneThUntv.setTextColor(getResources().getColor(R.color.text_huise2));
				selectUmTv.setText("100U米");
				umPriceTv.setText("¥ 10");
			}
			break;
		case R.id.five_hdum_layout:
			if(umCount != 500){
				umCount = 500;
				umCountPrice = 50;
				fitityUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fiveHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_light);
				oneThUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fitityUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				fiveHdUmTv.setTextColor(getResources().getColor(R.color.white));
				oneThUntv.setTextColor(getResources().getColor(R.color.text_huise2));
				selectUmTv.setText("500U米");
				umPriceTv.setText("¥ 50");
			}
			break;
		case R.id.one_thum_layout:
			if(umCount != 1000){
				umCount = 1000;
				umCountPrice = 100;
				fitityUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fiveHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneThUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_light);
				fitityUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				fiveHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneThUntv.setTextColor(getResources().getColor(R.color.white));
				selectUmTv.setText("1000U米");
				umPriceTv.setText("¥ 100");
			}
			break;
		case R.id.back_rl:
			finish();
		default:
			break;
		}

	}
	//获取订单号
	private void getOrderCode(){
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GET_ORDER_CODE);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("bill_type","Um");
			if(isZfbPay){
				requestObject.put("channel","ALI");
			}else{
				requestObject.put("channel","WX");
			}
			requestObject.put("title","U米充值");
			requestObject.put("totalfee",umCountPrice*100);
			LogUtil.e("mytest", "price--"+umCountPrice*100);
			requestObject.put("totalnum",umCount);
		} catch (JSONException e) {

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
				LogUtil.d("mytest", "eee"+arg0.getMessage());
				dismissLoadingLayout();
				toastShort("提交订单信息失败");
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.e("mytest", "get order--"+result);
				JSONObject jsons;
					try {
						jsons = new JSONObject(result);
						String code = jsons.getString("state");
						if(!code.equals("200")){
							dismissLoadingLayout();
							toastShort("提交订单信息失败");
							return;
						}
						JSONObject data = jsons.getJSONObject("data");
//						String token = data.getString("token");
//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
						orderCode = data.getString("bill_no");
						if(isZfbPay){
							payZfb(orderCode, umCountPrice);
						}else{
							payWeiXin(orderCode, umCountPrice);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}
	private void isPaySuc() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.QUERY_PAY);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token", SharePreferanceUtils.getInstance().getToken(this, SharePreferanceUtils.TOKEN, ""));
			requestObject.put("device_code", SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			requestObject.put("bill_no",orderCode);
		} catch (JSONException e) {

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
				LogUtil.e("mytest", "pay ee"+arg0.getMessage());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "pay--"+result);
				JSONObject jsons;
				try {
					jsons = new JSONObject(result);
					String code = jsons.getString("state");
					if(!code.equals("200")){
						toastShort("充值失败");
						return;
					}
					JSONObject data = jsons.getJSONObject("data");
//					String token = data.getString("token");
//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					//1--成功，  2--失败
					String payResult= data.getString("result");
					if(payResult.equals(Constants.PAY_SUC)){
						toastShort("充值成功");
						//int umCount = data.getInt("virtual_currency");
						setResult(RESULT_OK);
						finish();
					}else{
						toastShort("充值失败");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	//支付宝支付
	protected void payZfb(String orderId,int price) {
		// TODO Auto-generated method stub
		Map<String, String> mapOptional = new HashMap<String, String>();
		mapOptional.put("type", "umi");
		int payPrice = (int) (price*100);
		LogUtil.d("mytest", "zfbprice--"+payPrice);
		BCPay.getInstance(BiuChargeActivity.this).reqAliPaymentAsync(
				"U米充值",
				payPrice,
				orderId,
				mapOptional,
				bcCallback);
	}
	//微信支付
	protected void payWeiXin(String orderId,int price) {
		// TODO Auto-generated method stub
		//对于微信支付, 手机内存太小会有OutOfResourcesException造成的卡顿, 以致无法完成支付
		//这个是微信自身存在的问题
		Map<String, String> mapOptional = new HashMap<String, String>();
		mapOptional.put("type", "umi");
		int payPrice = (int) (price*100);
		if (BCPay.isWXAppInstalledAndSupported() &&
				BCPay.isWXPaySupported()) {

			BCPay.getInstance(BiuChargeActivity.this).reqWXPaymentAsync(
					"U米充值",               //订单标题
					payPrice,                           //订单金额(分)
					orderId,  //订单流水号
					mapOptional,            //扩展参数(可以null)
					bcCallback);            //支付完成后回调入口

		} else {
			dismissLoadingLayout();
			Toast.makeText(BiuChargeActivity.this,
					"您尚未安装微信或者安装的微信版本不支持", Toast.LENGTH_LONG).show();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		handler.sendEmptyMessage(1);
	}
}
