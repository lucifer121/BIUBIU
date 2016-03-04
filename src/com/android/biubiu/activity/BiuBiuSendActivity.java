package com.android.biubiu.activity;

import com.android.biubiu.view.Flowlayout;
import com.biubiu.biubiu.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class BiuBiuSendActivity extends Activity {
	
	 private String mNames[] = {
	            "welcome","android","TextView",
	            "apple","jamy","kobe bryant",
	            "jordan","layout","viewgroup",
	            "margin","padding","text",
	            "name","type","search","logcat"
	            
	    };
	    private Flowlayout mFlowLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu_biu_send);
		initChildViews();
        
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
 
}