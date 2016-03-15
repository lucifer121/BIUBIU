package com.android.biubiu.activity.biu;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;
import com.android.biubiu.view.Flowlayout;









import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BiuBiuSendActivity extends BaseActivity implements OnClickListener{

	private String mNames[] = {
			"我想认识你","披星戴月",
			"哦","啊","今天是个好日子啊","真是个忧伤的故事",
//			"jordan","layout","viewgroup",
//			"margin","padding","text",
//			"name","type","search","logcat"

	};
	private Flowlayout mFlowLayout;
	Button sendBiuBtn;
	private RelativeLayout backLayout;
	private EditText mEditText;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_send);
		initView();
		initChildViews();
	}
	@SuppressLint("CutPasteId") 
	private void initView() {
		// TODO Auto-generated method stub
		sendBiuBtn = (Button) findViewById(R.id.send_biu);
		sendBiuBtn.setOnClickListener(this);
		backLayout=(RelativeLayout) findViewById(R.id.back_send_biu_mine_rl);
		mEditText=(EditText) findViewById(R.id.topic_send_biu_et);
		mEditText.addTextChangedListener(watcher);
		button=(Button) findViewById(R.id.send_biu);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				toastShort("fasong");
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
			// TODO Auto-generated method stub
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
		for(int i = 0; i < mNames.length; i ++){
			final TextView view = new TextView(this);

			view.setText(mNames[i]);
			view.setTextColor(getResources().getColor(R.color.textview_item_send_bg));
			view.setTextSize(11);
			
		//	view.setPadding(24, 24, 24, 24);
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), view.getText(), Toast.LENGTH_SHORT).show();
					mEditText.setText(view.getText());
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