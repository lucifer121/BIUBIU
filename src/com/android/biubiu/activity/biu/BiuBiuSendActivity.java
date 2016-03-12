package com.android.biubiu.activity.biu;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.view.Flowlayout;




import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BiuBiuSendActivity extends BaseActivity implements OnClickListener{

	private String mNames[] = {
			"welcome","android","TextView",
			"apple","jamy","kobe bryant",
			"jordan","layout","viewgroup",
			"margin","padding","text",
			"name","type","search","logcat"

	};
	private Flowlayout mFlowLayout;
	Button sendBiuBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_send);
		initView();
		initChildViews();
	}
	private void initView() {
		// TODO Auto-generated method stub
		sendBiuBtn = (Button) findViewById(R.id.send_biu);
		sendBiuBtn.setOnClickListener(this);
	}
	@SuppressWarnings("deprecation")
	private void initChildViews() {
		// TODO Auto-generated method stub
		mFlowLayout = (Flowlayout) findViewById(R.id.flowlayout);
		MarginLayoutParams lp = new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 5;
		lp.rightMargin = 5;
		lp.topMargin = 5;
		lp.bottomMargin = 5;
		for(int i = 0; i < mNames.length; i ++){
			final TextView view = new TextView(this);

			view.setText(mNames[i]);
			view.setTextColor(Color.WHITE);
			view.setTextSize(16);
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), view.getText(), Toast.LENGTH_SHORT).show();
				}
			});
			mFlowLayout.addView(view,lp);
		}
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