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

}
