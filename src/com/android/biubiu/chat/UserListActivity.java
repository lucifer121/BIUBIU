package com.android.biubiu.chat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.bean.UserFriends;
import com.android.biubiu.chat.MyHintDialog.OnDialogClick;
import com.android.biubiu.common.Constant;
import com.android.biubiu.fragment.FriendsListFragment;
import com.android.biubiu.sqlite.UserDao;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;
import com.avos.avoscloud.LogUtil.log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class UserListActivity extends BaseActivity {
	private RelativeLayout backLayout;
	private ListView mListView;
	private List<UserFriends> mData=new ArrayList<UserFriends>();
	private UserListAdapter mAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	private String TAG="UserListActivity";
	private UserDao userDao;
	private ImageOptions imageOptions;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_list);
		imageOptions = new ImageOptions.Builder()
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		
		.setFailureDrawableId(R.drawable.photo_fail)
		
		.build();

		userDao=new UserDao(this);
		initView();
		initData();
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		backLayout=(RelativeLayout) findViewById(R.id.back_chat_userList_rl);
		mListView=(ListView) findViewById(R.id.chat_user_list_listView);
		
		swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.id_swipeRefreshlayout_userlist);
		swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						
						initData();
						 swipeRefreshLayout.setRefreshing(false);
					}
				}, 600);
				
			}
		});
		
		backLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}



	private void initAdapter() {
		
		log.e(TAG, "initAdapter");
		
		mAdapter=new UserListAdapter(this, mData);
		mListView.setAdapter(mAdapter);
		

		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(UserListActivity.this,ChatActivity.class);
				intent.putExtra(Constant.EXTRA_USER_ID, mData.get(position).getUserCode());
				startActivityForResult(intent, 0);
			}
		});

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {

				MyHintDialog.getDialog(UserListActivity.this, "解除关系", "嗨~确定要解除和他的关系吗", "确定",new OnDialogClick() {				
					@Override
					public void onOK() {
						// TODO Auto-generated method stub
					//	toastShort("ok");
						removeFriend(mData.get(position).getUserCode());
					}				
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
					//	toastShort("no");
					}
				});
				
				return true;
			}
		});
	}


/**
 * 加载好友列表
 */
	private void initData() {
		// TODO Auto-generated method stub
		showLoadingLayout(getResources().getString(R.string.loading));

		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.GET_FRIDENS_LIST);
		JSONObject object=new JSONObject();
		try {
			object.put("token", SharePreferanceUtils.getInstance().
					getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
			object.put("device_code", SharePreferanceUtils.getInstance().
					getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data", object.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub

				dismissLoadingLayout();
			}

			@Override
			public void onFinished() {
				
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, arg0);
				dismissLoadingLayout();
				JSONObject jsons;			
					try {
						jsons = new JSONObject(arg0);					
					String code = jsons.getString("state");
					LogUtil.d(TAG, ""+code);
					if(!code.equals("200")){
						toastShort(""+jsons.getString("error"));	
						return;
					}
					JSONObject obj = jsons.getJSONObject("data");
					
					
//					String token=obj.getString("token");
//					if(token!=null&&!token.equals("")){
//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
//					}
					Gson gson=new Gson();

					if(obj.optJSONArray("users")==null||obj.optJSONArray("users").toString().length()==0){
						LogUtil.e(TAG, "meihaoyou");
						mData.clear();
						initAdapter();
						return;
					}
					List<UserFriends> userFriendsList=gson.fromJson(obj.optJSONArray("users").toString(),
							new TypeToken<List<UserFriends>>() {}.getType());
					mData.clear();
					if(userFriendsList!=null&&userFriendsList.size()>0){
						mData.addAll(userFriendsList);
					}
					
					LogUtil.e(TAG, ""+userFriendsList.size()+" ||"+mData.size());
					int number=mData.size();
			//		Log.e(TAG, ""+number);
					if(number!=0){
						userDao.deleteAllUser();
						for(int i=0;i<mData.size();i++){
							userDao.insertOrReplaceUser(mData.get(i));
						}						

					}else{
						
					}			
					
					
					initAdapter();
							
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		});
		
		
	}



/**
 * 删除好友
 */
	private void removeFriend(String userCode){
		RequestParams params=new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.REMOVE_FRIEND);
		JSONObject object=new JSONObject();
		try {
			object.put("token", SharePreferanceUtils.getInstance().
					getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, ""));
			object.put("device_code", SharePreferanceUtils.getInstance().
					getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, ""));
			object.put("user_code", userCode);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data", object.toString());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				toastShort("解除匹配失败");
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				LogUtil.e(TAG, arg0.toString());
				JSONObject jsonObject;
				
				try {
					jsonObject = new JSONObject(arg0);
					String state=jsonObject.getString("state");
					LogUtil.e(TAG, state);
					if(!state.equals("200")){
						toastShort(jsonObject.getString("error"));					
					}
					toastShort("解除匹配成功");
					JSONObject jsonObject2=new JSONObject();
					jsonObject2=jsonObject.getJSONObject("data");

					initData();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	switch (requestCode) {
	case 0:
		if(resultCode==RESULT_OK){
			initData();
		}
		
		break;

	default:
		break;
	}	
}
	
	

	
	

	

}
