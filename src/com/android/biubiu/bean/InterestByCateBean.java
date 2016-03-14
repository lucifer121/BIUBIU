package com.android.biubiu.bean;

import java.util.List;
import java.util.Map;

public class InterestByCateBean {
	/**
	 * 兴趣 分组id
	 */
	private String typecode;
	/**
	 * 兴趣 分组 名字
	 */
	private String typename;
	
	/**
	 * 
	 */
	private List<InterestTagBean> data;

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public List<InterestTagBean> getmInterestList() {
		return data;
	}

	public void setmInterestList(List<InterestTagBean> mInterestList) {
		this.data = mInterestList;
	}


	
	
	

}