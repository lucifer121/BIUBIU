package com.android.biubiu.common;

import java.io.File;

import cc.imeetu.iu.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author miracle
 * @date 2015-3-7
 * @return
 */
public class CommonDialog {

	/**
	 * 头像上传说明dialog
	 * @param context
	 * @param contentString
	 * @author miracle
	 * @date 2015-3-7
	 */
	public static AlertDialog headDialog(final Context mContext,final DialogInterface.OnClickListener click1,final DialogInterface.OnClickListener click2) {

		final AlertDialog portraidlg = new AlertDialog.Builder(mContext)
		.create();
		portraidlg.show();
		Window win = portraidlg.getWindow();
		win.setContentView(R.layout.up_userhead_hint_view);
		RelativeLayout bottomRl = (RelativeLayout) win.findViewById(R.id.knew_bottom_up_userhead_rl);
		bottomRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click1.onClick(portraidlg, R.id.knew_bottom_up_userhead_rl);
			}
		});
		View outView = win.findViewById(R.id.out_view);
		outView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click2.onClick(portraidlg, R.id.out_view);
			}
		});
		return portraidlg;
	}

	/**
	 * dialog singleBtn
	 * @param context
	 * @param contentString
	 * @author miracle
	 * @date 2015-3-7
	 */
	public static AlertDialog singleBtnDialog(final Context mContext,String title,String msg,String strBtn,final DialogInterface.OnClickListener click1) {

		final AlertDialog portraidlg = new AlertDialog.Builder(mContext)
		.create();
		portraidlg.show();
		Window win = portraidlg.getWindow();
		win.setContentView(R.layout.dialog_my);
		TextView titleTv = (TextView) win.findViewById(R.id.title_my_hint_dialog_tv);
		TextView messageTv = (TextView) win.findViewById(R.id.message_dralog_tv);
		if(null != title && !"".equals(title)){
			titleTv.setText(title);
			titleTv.setVisibility(View.VISIBLE);
		}else{
			titleTv.setVisibility(View.GONE);
		}
		messageTv.setText(msg);
		RelativeLayout bottomRl = (RelativeLayout) win.findViewById(R.id.no_dialog_my_rl);
		RelativeLayout bottomRl2 = (RelativeLayout) win.findViewById(R.id.ok_dialog_my_rl);
		bottomRl2.setVisibility(View.GONE);
		TextView bottomTv = (TextView) win.findViewById(R.id.no_dialog_my_tv);
		bottomTv.setText(strBtn);
		bottomRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click1.onClick(portraidlg, R.id.no_dialog_my_rl);
			}
		});
		return portraidlg;
	}
	/**
	 * dialog doubleBtn
	 * @param context
	 * @param contentString
	 * @author miracle
	 * @date 2015-3-7
	 */
	public static AlertDialog doubleBtnDialog(final Context mContext,String title,String msg,String strBtn1,String strBtn2,final DialogInterface.OnClickListener click1,final DialogInterface.OnClickListener click2) {

		final AlertDialog portraidlg = new AlertDialog.Builder(mContext)
		.create();
		portraidlg.show();
		Window win = portraidlg.getWindow();
		win.setContentView(R.layout.dialog_my);
		TextView titleTv = (TextView) win.findViewById(R.id.title_my_hint_dialog_tv);
		TextView messageTv = (TextView) win.findViewById(R.id.message_dralog_tv);
		if(null != title && !"".equals(title)){
			titleTv.setText(title);
			titleTv.setVisibility(View.VISIBLE);
		}else{
			titleTv.setVisibility(View.GONE);
		}
		messageTv.setText(msg);
		RelativeLayout bottomRl = (RelativeLayout) win.findViewById(R.id.no_dialog_my_rl);
		RelativeLayout bottomRl2 = (RelativeLayout) win.findViewById(R.id.ok_dialog_my_rl);
		TextView bottomTv = (TextView) win.findViewById(R.id.no_dialog_my_tv);
		bottomTv.setText(strBtn1);
		TextView bottomTv2 = (TextView) win.findViewById(R.id.ok_dialog_my_tv);
		bottomTv2.setText(strBtn2);
		bottomRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click1.onClick(portraidlg, R.id.no_dialog_my_rl);
			}
		});
		bottomRl2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click2.onClick(portraidlg, R.id.ok_dialog_my_rl);
			}
		});
		return portraidlg;
	}
}