package com.android.biubiu.bean;

import java.util.List;
import java.util.Map;

public class InterestLableBean {
	/**
	 * 兴趣 分组id
	 */
	private int id;
	/**
	 * 兴趣 分组 名字
	 */
	private String interest;
	private int colorBg;
	
	private Map<String, List<LableBean>> mInterestMap;

	public int getId() {
		return id;
	}

	public int getColorBg() {
		return colorBg;
	}

	public void setColorBg(int colorBg) {
		this.colorBg = colorBg;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Map<String, List<LableBean>> getmInterestMap() {
		return mInterestMap;
	}

	public void setmInterestMap(Map<String, List<LableBean>> mInterestMap) {
		this.mInterestMap = mInterestMap;
	}

	
	

}