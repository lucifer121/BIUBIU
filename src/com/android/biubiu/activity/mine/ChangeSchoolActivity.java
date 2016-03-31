package com.android.biubiu.activity.mine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import cc.imeetu.iu.R;

import com.android.biubiu.BaseActivity;
import com.android.biubiu.adapter.SchoolListAllAdapter;
import com.android.biubiu.bean.Schools;
import com.android.biubiu.bean.UserInfoBean;
import com.android.biubiu.sqlite.SchoolDao;
import com.android.biubiu.utils.HttpContants;
import com.android.biubiu.utils.HttpUtils;
import com.android.biubiu.utils.LogUtil;
import com.android.biubiu.utils.SharePreferanceUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ChangeSchoolActivity extends BaseActivity implements OnClickListener{

	
	// 控件相关
		private ImageView back;
		private String school;
		private RelativeLayout backLayout;
		private EditText mEditText;

		// listView 相关
		private ListView mListView, mListViewFind;
		private List<Schools> schoolsAlllList = new ArrayList<Schools>();
		private SchoolDao schoolDao = new SchoolDao();
		private SchoolListAllAdapter mListAllAdapter;
		private List<Schools> schoolsFindList = new ArrayList<Schools>();
		private SchoolListAllAdapter mListFindAdapter;
		
		private  List<Schools> allSchoolsList=new ArrayList<Schools>();
		private TextView topTextView;
		UserInfoBean infoBean;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// 去除title
			super.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// 全屏
			super.getWindow();
			setContentView(R.layout.activity_change_school);
			if(null != getIntent().getSerializableExtra("userInfoBean")){
				infoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
			}
			school = super.getIntent().getStringExtra("school");
			initView();
			loadData();
			initialization();
			
		}

		private void initialization() {
			
			mListAllAdapter = new SchoolListAllAdapter(this, schoolsAlllList);
			// 给listview 加头
		/*	LinearLayout hearder = (LinearLayout) LayoutInflater.from(this)
					.inflate(R.layout.listview_schooll_all_head, null);
			mListView.addHeaderView(hearder);*/
			
			mListView.setAdapter(mListAllAdapter);

			// 点击item 进行的操作
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					// TODO Auto-generated method stub
					if(null != infoBean){
						infoBean.setSchool(schoolsAlllList.get(position).getUnivsId());
						updateInfo();
					}else{
						Intent intent = new Intent();
						intent.putExtra("school", (Serializable)schoolsAlllList.get(position));
						setResult(RESULT_OK, intent);
						finish();
					}
				}
			});

			mEditText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO chaxun
					if (arg0.length() != 0) {
						topTextView.setText("匹配学校");
						mListViewFind.setVisibility(View.VISIBLE);
						mListView.setVisibility(View.GONE);

						try {
							schoolsFindList= schoolDao.getSchoolsFind(arg0.toString());
							addListview(schoolsFindList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							mListViewFind.setVisibility(View.GONE);
						}

					} else {
						mListViewFind.setVisibility(View.GONE);
						topTextView.setText("所有学校");
						
						mListView.setVisibility(View.VISIBLE);
					}

				}
			});
		}

		protected void updateInfo() {
			// TODO Auto-generated method stub
			RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_USETINFO);
			String token = SharePreferanceUtils.getInstance().getToken(getApplicationContext(), SharePreferanceUtils.TOKEN, "");
			String deviceId = SharePreferanceUtils.getInstance().getDeviceId(getApplicationContext(), SharePreferanceUtils.DEVICE_ID, "");
			JSONObject requestObject = new JSONObject();
			try {
				requestObject.put("token", token);
				requestObject.put("device_code", deviceId);
				requestObject.put("school",infoBean.getSchool());
				requestObject.put("parameters", "school");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					LogUtil.d("mytest", "school=="+result);
					try {
						JSONObject jsons = new JSONObject(result);
						String state = jsons.getString("state");
						if(!state.equals("200")){
							toastShort("修改信息失败");
							return ;
						}
						JSONObject data = jsons.getJSONObject("data");
//						String token = data.getString("token");
//						SharePreferanceUtils.getInstance().putShared(getApplicationContext(), SharePreferanceUtils.TOKEN, token);
						Intent intent = new Intent();
						intent.putExtra("userInfoBean", infoBean);
						setResult(RESULT_OK, intent);
						finish();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		private void loadData() {
			// TODO Auto-generated method stub

			schoolsAlllList = schoolDao.getschoolAll();
			LogUtil.e("lucifer", "" + schoolsAlllList.size());
			
			

		}

		private void initView() {
			back = (ImageView) super.findViewById(R.id.back_changeschool_mine);
			back.setOnClickListener(this);
			
			topTextView=(TextView) findViewById(R.id.top_text_school_change_tv);
			backLayout = (RelativeLayout) super
					.findViewById(R.id.back_changeschool_mine_rl);
			backLayout.setOnClickListener(this);

			mListView = (ListView) super
					.findViewById(R.id.schoolList_changeschool_mine);
			
			mListViewFind = (ListView) super
					.findViewById(R.id.schoolList_find_changeschool_mine);

			mEditText = (EditText) super.findViewById(R.id.content_change_grade_et);
		}

		public void addListview(final List<Schools> list) {
			if (list.size() != 0) {
				mListFindAdapter = new SchoolListAllAdapter(this, list);

				mListViewFind.setAdapter(mListFindAdapter);
				if(schoolsFindList!=null&&schoolsFindList.size()>0){
				mListViewFind.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					if(null != infoBean){
						infoBean.setSchool(list.get(position).getUnivsId());
						updateInfo();
					}else{
						Intent intent = new Intent();
						intent.putExtra("school", (Serializable)list.get(position));
						setResult(RESULT_OK, intent);
						finish();
					}
					
				}
			});
		}
				
				

			}
			mListFindAdapter.notifyDataSetChanged();
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_changeschool_mine_rl:
				Intent intent2 = new Intent();
				intent2.putExtra("school", school);
				ChangeSchoolActivity.this.setResult(RESULT_CANCELED, intent2);
				finish();

				break;

			default:
				break;
			}

		}

		// 从专业传过来的值 再传到设置 专业的页面
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			switch (requestCode) {
			case 20:
				if (resultCode == RESULT_OK) {
					String school = data.getStringExtra("school");
					String major = data.getStringExtra("department");

					Intent intent = new Intent();
					intent.putExtra("schools", school);
					intent.putExtra("departments", major);
					ChangeSchoolActivity.this.setResult(RESULT_OK, intent);
					finish();
				}

				break;

			default:
				break;
			}

			super.onActivityResult(requestCode, resultCode, data);

		}

		/**
		 * 设置点击返回键的状态
		 */
		public void onBackPressed() {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("school", school);
			ChangeSchoolActivity.this.setResult(RESULT_CANCELED, intent);
			finish();
		}

	

}
