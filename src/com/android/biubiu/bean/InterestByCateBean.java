package com.android.biubiu.bean;

import java.util.List;
import java.util.Map;

public class InterestByCateBean {
	/**
	 * 兴趣 分组id
	 */
	private int id;
	/**
	 * 兴趣 分组 名字
	 */
	private String interest;
	private int colorBg;
	
	private Map<String, List<InterestTagBean>> mInterestMap;

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

	public Map<String, List<InterestTagBean>> getmInterestMap() {
		return mInterestMap;
	}

	public void setmInterestMap(Map<String, List<InterestTagBean>> mInterestMap) {
		this.mInterestMap = mInterestMap;
	}

	
	

}