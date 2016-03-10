package com.android.biubiu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.biubiu.activity.BaseActivity;
import com.android.biubiu.adapter.InterestLableListViewAdapter;
import com.android.biubiu.bean.LableBean;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class InterestLabelActivity extends BaseActivity {
	private ListView mListView;
	private InterestLableListViewAdapter mAdapter;
	private List<Map<String, List<LableBean>>> mDates=new ArrayList<Map<String,List<LableBean>>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interest_label);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		mListView=(ListView) findViewById(R.id.id_listView_intetest_lable);
		mAdapter=new InterestLableListViewAdapter(this,mDates);
		mListView.setAdapter(mAdapter);
	}

	

}
