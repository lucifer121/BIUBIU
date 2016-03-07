package com.android.biubiu.common;

import java.io.File;



import com.android.biubiu.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @author lucifer
 * @date 2015-12-7
 * @return
 */
public class PerfectInformation {

	/**
	 * 启动完善信息dialog
	 * @param context
	 * @param contentString
	 * @author lucifer
	 * @date 2015-12-7
	 */
	public static void showDiolagPerfertInformation(final Context mContext
			) {

		final AlertDialog portraidlg = new AlertDialog.Builder(mContext)
				.create();
		portraidlg.show();
		Window win = portraidlg.getWindow();
		win.setContentView(R.layout.up_userhead_hint_view);
		
//		
//		back.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				portraidlg.dismiss();
//			}
//		});
//		goPerfect.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent(mContext,SetPersonalInformation2Activity.class);
//				
//				mContext.startActivity(intent);
//			}
//		});
//		TextView contentView=(TextView) win.findViewById(R.id.content_item_perfect_information_tv);
//		contentView.setText(""+contentString);
////		RadioButton portrait_native = (RadioButton) win
////				.findViewById(R.id.Portrait_native);
////		portrait_native.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View arg0) {
////				// TODO Auto-generated method stub
////				Intent intent1 = new Intent(Intent.ACTION_PICK, null);
////				intent1.setDataAndType(
////						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
////				startActivityForResult(intent1, 11);
////				portraidlg.dismiss();
////			}
////		});
////		RadioButton portrait_take = (RadioButton) win
////				.findViewById(R.id.Portrait_take);
////		portrait_take.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View arg0) {
////				// TODO Auto-generated method stub
////				// 调用摄像头
////				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////				intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri
////						.fromFile(new File(Environment
////								.getExternalStorageDirectory(),
////								"/user_header.png")));
////				startActivityForResult(intent2, 22);
////				portraidlg.dismiss();
//			}
//		});
//		View viewTop = win.findViewById(R.id.view_top_dialog_sethead);
//		View viewBottom = win.findViewById(R.id.view_bottom_dialog_sethead);
//		// 点击dialog外部，关闭dialog
//		viewTop.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				portraidlg.dismiss();
//			}
//		});
//		viewBottom.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				portraidlg.dismiss();
//			}
//		});
//
	}

}
