package com.android.biubiu.activity.biu;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.utils.LogUtil;

public class BiuChargeActivity extends BaseActivity{
	Button chargeBtn;
	Button chargeBtn2;
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
		chargeBtn = (Button) findViewById(R.id.zfb_pay_btn);
		chargeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				payZfb("1001001001", 0.01f);
			}
		});
		chargeBtn2 = (Button) findViewById(R.id.wx_pay_btn);
		chargeBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				payWeiXin("1002001001", 0.01f);
			}
		});
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
