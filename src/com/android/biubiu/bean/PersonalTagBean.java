package com.android.biubiu.bean;

public class PersonalTagBean {
	/**
	 * 标签id
	 */
	private String typecode;
	/**
	 * 标签內容
	 */
	private String typename;
	/**
	 * 是否选择
	 */
	private Boolean isChoice=false;
	
	public Boolean getIsChoice() {
		return isChoice;
	}
	public void setIsChoice(Boolean isChoice) {
		this.isChoice = isChoice;
	}
	public String getId() {
		return typecode;
	}
	public void setId(String id) {
		this.typecode = id;
	}
	public String getTag() {
		return typename;
	}
	public void setTag(String tag) {
		this.typename = tag;
	}
	

}