package com.android.biubiu.bean;

public class PersonalTagBean {
	/**
	 * 标签id
	 */
	private int id;
	/**
	 * 标签內容
	 */
	private String tag;
	/**
	 * 是否选择
	 */
	private Boolean isChoice;
	
	public Boolean getIsChoice() {
		return isChoice;
	}
	public void setIsChoice(Boolean isChoice) {
		this.isChoice = isChoice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	

}