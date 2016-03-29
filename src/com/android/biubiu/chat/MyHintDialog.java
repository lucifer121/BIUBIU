package com.android.biubiu.chat;

import cc.imeetu.iu.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyHintDialog {
	public static Context mContext;

	/**
	 * 
	 * @author lucifer
	 *
	 */
	public interface OnDialogClick{
		void onDismiss();
		void onOK();
		
	}
//	private static OnDialogClick onDialogClick;
//	
//	public void setOnDialogClick(OnDialogClick onDialogClick){
//		this.onDialogClick=onDialogClick;
//		
//	}
	
	/**
	 * 
	 * @param context
	 * @param mTitle 标题
	 * @param mMessage 提示消息
	 * @param mOk  确定 内容
	 */
	public static void getDialog(Context context,String mTitle,String mMessage,String mOk,final OnDialogClick mDialogClick){
		mContext=context;
		final AlertDialog alertDialog=new AlertDialog.Builder(context).create();
		alertDialog.show();
		Window win=alertDialog.getWindow();
		win.setContentView(R.layout.dialog_my);
		TextView title,message,ok;
		title=(TextView) win.findViewById(R.id.title_my_hint_dialog_tv);
		message=(TextView) win.findViewById(R.id.message_dralog_tv);
		ok=(TextView) win.findViewById(R.id.ok_dialog_my_tv);
		title.setText(mTitle);
		message.setText(mMessage);
		ok.setText(mOk);
		
		RelativeLayout dismissLayout=(RelativeLayout) win.findViewById(R.id.no_dialog_my_rl);
		RelativeLayout okLayout=(RelativeLayout) win.findViewById(R.id.ok_dialog_my_rl);

			
			dismissLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					alertDialog.dismiss();
					mDialogClick.onDismiss();
				}
			});
			okLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					alertDialog.dismiss();
					mDialogClick.onOK();
				}
			});
			
		

		
	}
	


}
