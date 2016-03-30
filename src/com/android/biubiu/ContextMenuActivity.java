package com.android.biubiu;

import cc.imeetu.iu.R;

import com.android.biubiu.chat.Constant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseBaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class ContextMenuActivity extends EaseBaseActivity {
	   public static final int RESULT_CODE_COPY = 1;
	    public static final int RESULT_CODE_DELETE = 2;
	    public static final int RESULT_CODE_FORWARD = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EMMessage message = getIntent().getParcelableExtra("message");
		
		int type = message.getType().ordinal();
		
		if (type == EMMessage.Type.TXT.ordinal()) {
		    if(message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false) ||
		            message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
		    //    setContentView(R.layout.em_context_menu_for_location);
		    }else if(message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
		     //   setContentView(R.layout.em_context_menu_for_image);
		    }else{
		        setContentView(R.layout.activity_context_menu);
		    }
		}
		
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void copy(View view){
		setResult(RESULT_CODE_COPY);
		finish();
	}
	public void delete(View view){
		setResult(RESULT_CODE_DELETE);
		finish();
	}
	public void forward(View view){
		setResult(RESULT_CODE_FORWARD);
		finish();
	}
	

}
