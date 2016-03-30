package com.android.biubiu;

import cc.imeetu.iu.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class BeginGuiderActivity extends BaseActivity{
	private ImageView guidImv;
	private Button guidBtn;
	private int guidIndex = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.begin_guide_layout);
		initView();
	}
	private void setGuidView() {
		// TODO Auto-generated method stub
		switch (guidIndex) {
		case 2:
			guidImv.setImageResource(R.drawable.help_imageview_02biubi);
			guidIndex = guidIndex+1;
			break;
		case 3:
			guidBtn.setBackgroundResource(R.drawable.guide_begin2_btn);
			guidImv.setImageResource(R.drawable.help_imageview_03biubi);
			guidIndex = guidIndex+1;
			break;
		case 4:
			finish();
			break;
		default:
			break;
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		guidImv = (ImageView) findViewById(R.id.guid_imv);
		guidImv.setImageResource(R.drawable.help_imageview_01);
		guidBtn = (Button) findViewById(R.id.guid_btn);
		guidBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setGuidView();
			}
		});
	}

}
