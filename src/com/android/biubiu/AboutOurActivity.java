package com.android.biubiu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import cc.imeetu.iu.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutOurActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout versionLayout;
	private RelativeLayout commentLayout;
	private TextView versionTv;
	private TextView phoneTv;
	private TextView weixinTv;
	private RelativeLayout backRl;
	UpdateResponse updateInfoAll = null;
	String versionName = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_our);
		PackageManager pm = getPackageManager();
		String pName = "cc.imeetu.iu";
		PackageInfo pinfo;
		try {
			pinfo = pm.getPackageInfo(pName, PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		versionLayout = (RelativeLayout) findViewById(R.id.version_layout);
		commentLayout = (RelativeLayout) findViewById(R.id.comment_layout);
		versionTv = (TextView) findViewById(R.id.version_tv);
		phoneTv = (TextView) findViewById(R.id.phone_tv);
		weixinTv = (TextView) findViewById(R.id.weixin_tv);
		backRl = (RelativeLayout) findViewById(R.id.back_abour_our_rl);

		versionLayout.setOnClickListener(this);
		commentLayout.setOnClickListener(this);
		phoneTv.setOnClickListener(this);
		weixinTv.setOnClickListener(this);
		backRl.setOnClickListener(this);
		versionTv.setText(versionName);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.version_layout:
			checkUpdate();
			break;
		case R.id.comment_layout:
			commentApp();
			break;
		case R.id.weixin_tv:
			ClipboardManager copy = (ClipboardManager) AboutOurActivity.this  
			.getSystemService(Context.CLIPBOARD_SERVICE);  
			copy.setText(weixinTv.getText().toString());
			toastShort("已复制");
			break;
		case R.id.phone_tv:
			Intent in2 = new Intent();
			in2.setAction(Intent.ACTION_CALL);//指定意图动作
			in2.setData(Uri.parse("tel:"+phoneTv.getText().toString()));//指定电话号码
			startActivity(in2);
			break;
		case R.id.back_abour_our_rl:
			finish();
			break;
		default:
			break;
		}
	}
	private void checkUpdate() {
		// TODO Auto-generated method stub
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
				updateInfoAll = updateInfo;
				switch (updateStatus) {
				case UpdateStatus.Yes: // has update
					//UmengUpdateAgent.showUpdateDialog(getApplicationContext(), updateInfo);
					showUpdateDialog();
					break;
				case UpdateStatus.No: // has no update
					Toast.makeText(AboutOurActivity.this, "已经是最新版本啦！", Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.NoneWifi: // none wifi
					Toast.makeText(getApplicationContext(), "no wifi ", Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.Timeout: // time out
					Toast.makeText(getApplicationContext(), "time out", Toast.LENGTH_SHORT).show();
					break;
				}
			}

		});
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		//UmengUpdateAgent.forceUpdate(SystemSettingsActivity.this);
		UmengUpdateAgent.update(AboutOurActivity.this);
	}
	private void showUpdateDialog(){
		Dialog dialog = new AlertDialog.Builder(this).setTitle("有新版本").setMessage("\n"+"最新版本："+updateInfoAll.version+"\n\n更新内容：\n"+updateInfoAll.updateLog+"\n")
				.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						UmengUpdateAgent.setUpdateUIStyle(UpdateStatus.STYLE_NOTIFICATION);
						File file = UmengUpdateAgent.downloadedFile(AboutOurActivity.this, updateInfoAll);
						if (file == null) {
							UmengUpdateAgent.startDownload(AboutOurActivity.this, updateInfoAll);
						} else {
							UmengUpdateAgent.startInstall(AboutOurActivity.this, file);
						}
						dialog.dismiss();
					}
				}).setNeutralButton("以后再说", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}
	/**
	 * 进入市场评分
	 * */

	public void commentApp() {
		final PackageManager packageManager = AboutOurActivity.this.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		if(pinfo == null || pinfo.size() ==0){
			Toast.makeText(AboutOurActivity.this, "您还未安装任何应用市场", 1000).show();
			return;
		}
		try {
			Uri uri = Uri.parse("market://details?id="+getPackageName());  
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);  
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			startActivity(intent);  
		} catch (ActivityNotFoundException e) {
			// TODO: handle exception
			Toast.makeText(AboutOurActivity.this, "您还未安装任何应用市场", 1000).show();
		}
	}

	// 判断市场是否存在的方法
	public static boolean isAvilible(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE

	}
}
