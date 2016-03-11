package com.android.biubiu.activity.mine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.R;

public class ScanUserHeadActivity extends BaseActivity implements OnClickListener{
	private static final int EDIT_HEAD = 1001;
	private static final int CROP_PHOTO = 1002;
	boolean isMyself = true;
	RelativeLayout scanLayout;
	RelativeLayout editRl;
	ImageView headImv;
	String userHeadStr = "";
	ImageOptions imageOptions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_userhead_layout);
		getIntentInfo();
		initView();
	}
	private void getIntentInfo() {
		// TODO Auto-generated method stub
		userHeadStr = getIntent().getStringExtra("userhead");
		isMyself = getIntent().getBooleanExtra("isMyself", false);
	}
	private void initView() {
		// TODO Auto-generated method stub
		scanLayout = (RelativeLayout) findViewById(R.id.scan_linear);
		scanLayout.setOnClickListener(this);
		editRl = (RelativeLayout) findViewById(R.id.edit_rl);
		editRl.setOnClickListener(this);
		headImv = (ImageView) findViewById(R.id.head_imv);
		
		if(isMyself){
			editRl.setVisibility(View.VISIBLE);
		}else{
			editRl.setVisibility(View.GONE);
		}
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		.setLoadingDrawableId(R.drawable.ic_launcher)
		.setFailureDrawableId(R.drawable.ic_launcher)
		.build();
		x.image().bind(headImv, userHeadStr, imageOptions);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scan_linear:
			finish();
			break;
		case R.id.edit_rl:
			if(isMyself){
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");
				startActivityForResult(intent, EDIT_HEAD);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 调用系统的裁剪功能
	 * 
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		// 调用拍照的裁剪功能
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽和搞的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// // outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_PHOTO);
	}
	public String saveHeadImg(Bitmap head) {
		FileOutputStream fos = null;
		String path = "";
		path = Environment.getExternalStorageDirectory()
				+ "/biubiu/"+System.currentTimeMillis()+".png";
		File file = new File(path);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			head.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case EDIT_HEAD:
			if (resultCode == RESULT_OK) {
				cropPhoto(data.getData());// 裁剪图片
			}
			break;
		case CROP_PHOTO:
			if (data != null) {
				Bundle extras = data.getExtras();
				Bitmap userheadBitmap = extras.getParcelable("data");
				String headPath = saveHeadImg(userheadBitmap);
				//上传照片，成功后将路径返回上一级页面
				//uploadImg(path);
			}
			break;
		default:
			break;
		}
	}
}
