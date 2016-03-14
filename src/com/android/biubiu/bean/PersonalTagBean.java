package com.android.biubiu.bean;

public class PersonalTagBean {
	/**
	 * 标签id
	 */
	private String code;
	/**
	 * 标签內容
	 */
	private String name;
	/**
	 * 是否选择
	 */
	private Boolean isChoice=false;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsChoice() {
		return isChoice;
	}
	public void setIsChoice(Boolean isChoice) {
		this.isChoice = isChoice;
	}

	
	
	


}