package com.android.biubiu.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class InterestByCateBean implements Serializable{
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
	@SerializedName("data")
	private List<InterestTagBean> mInterestList;
	
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
		return mInterestList;
	}

	public void setmInterestList(List<InterestTagBean> mInterestList) {
		this.mInterestList = mInterestList;
	}


}