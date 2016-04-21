package com.android.biubiu.chat;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.adapter.ScanChatImgAdapter;
import com.android.biubiu.utils.LogUtil;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;

public class ScanChatPhoto extends BaseActivity{
	RelativeLayout allLayout;
	ViewPager chatImgPager;
	Button saveBtn;
	ArrayList<EMMessage> msgList = new ArrayList<EMMessage>();
	ScanChatImgAdapter imgAdapter;
	int index = 0;
	ImageOptions imageOptions;
	boolean isLoading = false;
	private static String BIU_PATH = Environment.getExternalStorageDirectory()
			+ "/biubiu/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_chatimg_layout);
		getIntentInfo();
		initView();
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.loadingbbbb)
		.setIgnoreGif(true)
		.setFailureDrawableId(R.drawable.photo_imageview_fail)
		.build();
	}
	private void getIntentInfo() {
		// TODO Auto-generated method stub
		ArrayList<EMMessage> list = (ArrayList<EMMessage>) getIntent().getSerializableExtra("msgList");
		if(list != null && list.size()>0){
			msgList.clear();
			msgList.addAll(list);
		}
		index = getIntent().getIntExtra("index", 0);
	}
	private void initView() {
		// TODO Auto-generated method stub
		allLayout = (RelativeLayout) findViewById(R.id.all_layout);
		chatImgPager = (ViewPager) findViewById(R.id.chatphoto_scan_pager);
		
		chatImgPager.setOffscreenPageLimit(3);
		imgAdapter = new ScanChatImgAdapter(ScanChatPhoto.this, msgList, imageOptions);
		chatImgPager.setAdapter(imgAdapter);
		chatImgPager.setCurrentItem(index);
		chatImgPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				index = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		saveBtn = (Button) findViewById(R.id.save_photo_btn);
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isLoading){
					saveImg();
				}
			}
		});
	}
	protected void saveImg() {
		// TODO Auto-generated method stub
		isLoading = true;
		toastShort("开始下载");
	    EMImageMessageBody imgBody = (EMImageMessageBody) msgList.get(index).getBody();
		x.image().loadFile(imgBody.getRemoteUrl(),null, new CommonCallback<File>() {
			
			@Override
			public void onSuccess(File arg0) {
				// TODO Auto-generated method stub
				isLoading = false;
				toastShort("下载完成");
				saveFile(arg0);
			}
			
			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				isLoading = false;
			}
			
			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				isLoading = false;
				toastShort("下载失败");
			}
			
			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				isLoading = false;
			}
		});
	}
	protected void saveFile(File file) {
		// TODO Auto-generated method stub
		FileOutputStream fos = null;
		String path = "";
		path = BIU_PATH+System.currentTimeMillis()+".jpg";
		LogUtil.d("mytest", "path---"+path);
		File saveFile = new File(path);
		if (!saveFile.getParentFile().exists()) {
			saveFile.getParentFile().mkdirs();
		}
		copyFile(file, saveFile);
	}
	/**
	 * 复制文件
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean copyFile(File from, File to) {
		boolean done = false;
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(from);
			os = new FileOutputStream(to);
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = is.read(buffer)) > 0) {
				os.write(buffer, 0, read);
			}
			done = true;
		} catch(IOException e) {
			Log.d("复制文件出错","from=" + from + " to=" + to, e);
		} finally {
			closeSilently(is);
			closeSilently(os);
		}
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + BIU_PATH)));
		return done;
	}
	
	/**
	 * 关闭
	 * @param c
	 */
	public static void closeSilently(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			c = null;
		}
	}
}
