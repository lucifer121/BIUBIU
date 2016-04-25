package com.android.biubiu.activity.mine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cc.imeetu.iu.R;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.biubiu.BaseActivity;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;

public class ScanUserHeadActivity extends BaseActivity implements OnClickListener{
	private static final int EDIT_HEAD = 1001;
	private static final int CROP_PHOTO = 1002;
	boolean isMyself = true;
	RelativeLayout scanLayout;
	RelativeLayout editRl;
	ImageView headImv;
	String userHeadStr = "";
	ImageOptions imageOptions;
	//上传文件相关
	String accessKeyId = "";
	String accessKeySecret = "";
	String securityToken = "";
	String expiration = "";
	Uri croupUri;
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
		.setLoadingDrawableId(R.drawable.loadingbbbb)
		.setFailureDrawableId(R.drawable.photo_imageview_fail)
		.setIgnoreGif(true)
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
				dialogHint();
			}
			break;
		default:
			break;
		}
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
	//鉴权
	public void getOssToken(final String path){
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REGISTER_OSS_TOKEN);
		String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token",token);
			requestObject.put("device_code", deviceId);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		params.addBodyParameter("data", requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "error--"+ex.getMessage());
				LogUtil.d("mytest", "error--"+ex.getCause());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "edithead=="+arg0);
				try {
					JSONObject jsonObjs = new JSONObject(arg0);
					String state = jsonObjs.getString("state");
					if(!state.equals("200")){
						toastShort("修改头像失败");
						return;
					}
					JSONObject obj = jsonObjs.getJSONObject("data");
					accessKeyId = obj.getString("accessKeyId");
					accessKeySecret = obj.getString("accessKeySecret");
					securityToken = obj.getString("securityToken");
					expiration = obj.getString("expiration");
					//					String token = obj.getString("token");
					//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					//上传到阿里云
					asyncPutObjectFromLocalFile(path);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	// 从本地文件上传，使用非阻塞的异步接口
	public void asyncPutObjectFromLocalFile(String path) {
		String endpoint = HttpContants.A_LI_YUN;
		//OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("XWp6VLND94vZ8WNJ", "DSi9RRCv4bCmJQZOOlnEqCefW4l1eP");
		OSSCredentialProvider credetialProvider = new OSSFederationCredentialProvider() {
			@Override
			public OSSFederationToken getFederationToken() {

				return new OSSFederationToken(accessKeyId, accessKeySecret, securityToken, expiration);
			}
		};
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		OSSLog.enableLog();
		OSS oss = new OSSClient(getApplicationContext(), endpoint, credetialProvider, conf);
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		final String fileName = "profile/"+System.currentTimeMillis()+deviceId+".jpg";
		// 构造上传请求
		PutObjectRequest put = new PutObjectRequest("protect-app",fileName, path);

		// 异步上传时可以设置进度回调
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
			}
		});
		OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				Log.d("PutObject", "UploadSuccess");
				Log.d("ETag", result.getETag());
				Log.d("RequestId", result.getRequestId());
				//上传照片成功，调用修改头像接口
				updateHead(fileName);
			}
			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				toastShort("修改头像失败");
				// 请求异常
				if (clientExcepion != null) {
					// 本地异常如网络异常等
					clientExcepion.printStackTrace();
				}
				if (serviceException != null) {
					// 服务异常
					Log.e("ErrorCode", serviceException.getErrorCode());
					Log.e("RequestId", serviceException.getRequestId());
					Log.e("HostId", serviceException.getHostId());
					Log.e("RawMessage", serviceException.getRawMessage());
				}
			}
		});
	}
	//修改头像接口，成功后将路径返回上一级页面
	protected void updateHead(String fileName) {
		String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_HEAD);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token",token);
			requestObject.put("device_code", deviceId);
			requestObject.put("icon_url", fileName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "updatehead=="+result);
				try {
					JSONObject jsons = new JSONObject(result);
					String state = jsons.getString("state");
					if(!state.equals("200")){
						toastShort("修改头像失败");
						return ;
					}
					JSONObject data = jsons.getJSONObject("data");
					//					String token = data.getString("token");
					//					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
					String photoUrl = data.getString("icon_url");
					String photoUrl_thum = data.getString("icon_thumbnailUrl");
					Intent intent = new Intent();
					intent.putExtra("headUrl", photoUrl);
					intent.putExtra("thumUrl", photoUrl_thum);
					SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.USER_HEAD, photoUrl_thum);
					setResult(RESULT_OK, intent);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
	/**
	 * 调用系统的裁剪功能
	 * 
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		croupUri = uri;
		LogUtil.d("mytest", "1uri--"+croupUri);
		LogUtil.e("mytest", "1uri--"+croupUri);
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
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, croupUri);
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, CROP_PHOTO);
	}
	private Bitmap decodeUriAsBitmap(Uri uri){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
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
			LogUtil.d("mytest", "uri--"+croupUri);
			LogUtil.e("mytest", "uri--"+croupUri);
			if (croupUri != null) {
				try {
					Bitmap userheadBitmap = decodeUriAsBitmap(croupUri);
					String headPath = saveHeadImg(userheadBitmap);
					//上传图片鉴权
					getOssToken(headPath);
				} catch (NullPointerException e) {
					// TODO: handle exception
				}
			}
			break;
		default:
			break;
		}
	}

	public void dialogHint(){
		final AlertDialog alertDialog=new AlertDialog.Builder(this).create();
		alertDialog.show();
		Window win=alertDialog.getWindow();
		win.setContentView(R.layout.up_userhead_hint_view);
		RelativeLayout knowLayout=(RelativeLayout) win.findViewById(R.id.knew_bottom_up_userhead_rl);
		knowLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
				Intent intent ;
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
					intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
				} else {
					intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				}
				/*Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");*/
				startActivityForResult(intent, EDIT_HEAD);

			}
		});
	}

}
