package com.android.biubiu.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
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
import com.android.biubiu.callback.BiuBooleanCallback;

public class UploadImgUtils {
	public static void uploadPhoto(Context context,String path,BiuBooleanCallback callback){
		getOssToken(context,path,callback);
	}
	//鉴权
	public static void getOssToken(final Context context,final String path,final BiuBooleanCallback callback){
		if(!NetUtils.isNetworkConnected(context)){
			Toast.makeText(context.getApplicationContext(),context.getResources().getString(R.string.net_error),1000).show();;
			return;
		}
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REGISTER_OSS);
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
				callback.callback(false);
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.d("mytest", "ret=="+arg0);
				try {
					JSONObject jsonObjs = new JSONObject(arg0);
					String  state = jsonObjs.getString("state");
					if(!state.equals("200")){
						return;
					}
					JSONObject obj = jsonObjs.getJSONObject("data");
					//JSONObject obj = new JSONObject(jsonObjs.getString("data"));
					String accessKeyId = obj.getString("accessKeyId");
					String accessKeySecret = obj.getString("accessKeySecret");
					String securityToken = obj.getString("securityToken");
					String expiration = obj.getString("expiration");
					asyncPutObjectFromLocalFile(path,context,accessKeyId,accessKeySecret,securityToken,expiration,callback);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	// 从本地文件上传，使用非阻塞的异步接口
	public static void asyncPutObjectFromLocalFile(String headPath,final Context context,final String accessKeyId,final String accessKeySecret,final String securityToken,final String expiration,final BiuBooleanCallback callback){
		String endpoint = HttpContants.A_LI_YUN;
		//OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("XWp6VLND94vZ8WNJ", "DSi9RRCv4bCmJQZOOlnEqCefW4l1eP");
		OSSCredentialProvider credentialProvider = new OSSFederationCredentialProvider() {
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
		OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(context, SharePreferanceUtils.DEVICE_ID, "");
		final String fileName = "profile/"+System.currentTimeMillis()+deviceId+".jpeg";
		// 构造上传请求
		PutObjectRequest put = new PutObjectRequest("protect-app",fileName, headPath);

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
				updateHead(context,fileName,callback);
			}
			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				callback.callback(false);
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
		protected static void updateHead(final Context context,String fileName,final BiuBooleanCallback callback) {
			String token = SharePreferanceUtils.getInstance().getToken(context, SharePreferanceUtils.TOKEN, "");
			String deviceId = SharePreferanceUtils.getInstance().getDeviceId(context, SharePreferanceUtils.DEVICE_ID, "");
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
							callback.callback(false);
							return ;
						}
						JSONObject data = jsons.getJSONObject("data");
//						String token = data.getString("token");
//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
						String photoUrl = data.getString("icon_url");
						String photoUrl_thum = data.getString("icon_thumbnailUrl");
						Intent intent = new Intent();
						intent.putExtra("headUrl", photoUrl);
						intent.putExtra("thumUrl", photoUrl_thum);
						SharePreferanceUtils.getInstance().putShared(context, SharePreferanceUtils.USER_HEAD, photoUrl_thum);
						callback.callback(true);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		}
}
