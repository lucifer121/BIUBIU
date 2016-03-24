package com.android.biubiu.activity.biu;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.utils.LogUtil;

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
	//是否是支付宝支付
	boolean isZfbPay = true;
	//当前选中的U米数
	int umCount = 50;
	//当前选中的U米数对应价格
	int umCountPrice = 10;
	//支付结果返回入口
	BCCallback bcCallback = new BCCallback() {
		@Override
		public void done(final BCResult bcResult) {
			final BCPayResult bcPayResult = (BCPayResult)bcResult;
			//根据你自己的需求处理支付结果
			//需要注意的是，此处如果涉及到UI的更新，请在UI主进程或者Handler操作
			BiuChargeActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					String result = bcPayResult.getResult();
					/*
		                      注意！
		                      所有支付渠道建议以服务端的状态金额为准，此处返回的RESULT_SUCCESS仅仅代表手机端支付成功
					 */
					if (result.equals(BCPayResult.RESULT_SUCCESS)) {
						//成功
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

						Toast.makeText(BiuChargeActivity.this, toastMsg, Toast.LENGTH_LONG).show();
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biu_charge_layout);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		zfbPayBtn = (Button) findViewById(R.id.zfb_pay_btn);
		wxPayBtn = (Button) findViewById(R.id.wx_pay_btn);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zfb_pay_btn:
			if(isZfbPay){
				payZfb("1001001001", 0.01f);
			}else{
				isZfbPay = true;
				payLayout.setBackgroundResource(R.drawable.pay_btn_normal_blue);
				zfbPayBtn.setBackgroundResource(R.drawable.pay_afb_selector);
				zfbPayBtn.setTextColor(getResources().getColor(R.color.white));
				wxPayBtn.setBackgroundColor(getResources().getColor(R.color.white));
				wxPayBtn.setTextColor(getResources().getColor(R.color.pay_blue_txt));
			}
			break;
		case R.id.wx_pay_btn:
			if(!isZfbPay){
				payWeiXin("1002001001", 0.01f);
			}else{
				isZfbPay = false;
				payLayout.setBackgroundResource(R.drawable.pay_btn_normal_green);
				zfbPayBtn.setBackgroundColor(getResources().getColor(R.color.white));
				zfbPayBtn.setTextColor(getResources().getColor(R.color.pay_green_txt));
				wxPayBtn.setBackgroundResource(R.drawable.pay_wx_selector);
				wxPayBtn.setTextColor(getResources().getColor(R.color.white));
			}
			break;
		case R.id.fitity_um_layout:
			if(umCount != 50){
				umCount = 50;
				umCountPrice = 10;
				fitityUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_light);
				oneHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fiveHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneThUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fitityUmTv.setTextColor(getResources().getColor(R.color.white));
				oneHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				fiveHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneThUntv.setTextColor(getResources().getColor(R.color.text_huise2));
				selectUmTv.setText("50U米");
				umPriceTv.setText("¥ 10");
			}
			break;
		case R.id.one_hdum_layout:
			if(umCount != 100){
				umCount = 100;
				umCountPrice = 20;
				fitityUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_light);
				fiveHdUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				oneThUmLayout.setBackgroundResource(R.drawable.pay_btn_buybiubi_normal);
				fitityUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneHdUmTv.setTextColor(getResources().getColor(R.color.white));
				fiveHdUmTv.setTextColor(getResources().getColor(R.color.text_huise2));
				oneThUntv.setTextColor(getResources().getColor(R.color.text_huise2));
				selectUmTv.setText("100U米");
				umPriceTv.setText("¥ 20");
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
		default:
			break;
		}

	}
	//支付宝支付
	protected void payZfb(String orderId,float price) {
		// TODO Auto-generated method stub
		Map<String, String> mapOptional = new HashMap<String, String>();
		mapOptional.put("type", "acty");
		int payPrice = (int) (price*100);
		BCPay.getInstance(BiuChargeActivity.this).reqAliPaymentAsync(
				"报名",
				payPrice,
				orderId,
				mapOptional,
				bcCallback);
	}
	//微信支付
	protected void payWeiXin(String orderId,float price) {
		// TODO Auto-generated method stub
		//对于微信支付, 手机内存太小会有OutOfResourcesException造成的卡顿, 以致无法完成支付
		//这个是微信自身存在的问题
		Map<String, String> mapOptional = new HashMap<String, String>();

		mapOptional.put("type", "acty");
		int payPrice = (int) (price*100);
		if (BCPay.isWXAppInstalledAndSupported() &&
				BCPay.isWXPaySupported()) {

			BCPay.getInstance(BiuChargeActivity.this).reqWXPaymentAsync(
					"报名",               //订单标题
					payPrice,                           //订单金额(分)
					orderId,  //订单流水号
					mapOptional,            //扩展参数(可以null)
					bcCallback);            //支付完成后回调入口

		} else {
			Toast.makeText(BiuChargeActivity.this,
					"您尚未安装微信或者安装的微信版本不支持", Toast.LENGTH_LONG).show();
		}
	}
}
