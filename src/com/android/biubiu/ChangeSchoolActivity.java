package com.android.biubiu;

import java.util.ArrayList;
import java.util.List;















import com.android.biubiu.activity.BaseActivity;


import com.android.biubiu.adapter.SchoolListAllAdapter;
import com.android.biubiu.bean.Schools;
import com.android.biubiu.sqlite.SchoolDao;
import com.android.biubiu.utils.LogUtil;

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

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// 去除title
			super.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// 全屏
			super.getWindow();
			setContentView(R.layout.activity_change_school);

			school = super.getIntent().getStringExtra("school");
			
			initView();
			loadData();
			loadData2();
			
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

//					log.e("lucifer", ""
//							+ schoolsAlllList.get(position).getUnivsId()
//							+ " "
//							+ schoolsAlllList.get(position)
//									.getUnivsNameString());
//
//					Intent intent = new Intent(ChangeSchoolActivity.this,
//							ChangeMajorActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("schools",
//							schoolsAlllList.get(position));
//					intent.putExtras(bundle);
//
//					startActivityForResult(intent, 20);
					toastShort(""+schoolsAlllList.get(position).getUnivsNameString());
				}
			});

		
			// listView 加头
		/*	LinearLayout hearderViewLayout = (LinearLayout) LayoutInflater.from(
					this).inflate(R.layout.listview_schooll_find_head, null);
			mListViewFind.addHeaderView(hearderViewLayout);*/
			// mListFindAdapter=new SchoolListAllAdapter(this, list);

		

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

		private void loadData2() {
			// TODO Auto-generated method stub
			
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

		public void addListview(List<Schools> list) {
			if (list.size() != 0) {
				mListFindAdapter = new SchoolListAllAdapter(this, list);

				mListViewFind.setAdapter(mListFindAdapter);
				if(schoolsFindList!=null&&schoolsFindList.size()>0){
				mListViewFind.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO

//					Intent intent = new Intent(ChangeSchoolActivity.this,
//							ChangeMajorActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("schools",
//							schoolsFindList.get(position));
//					intent.putExtras(bundle);
//					startActivityForResult(intent, 20);
					toastShort(""+schoolsAlllList.get(position).getUnivsNameString());
					
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
