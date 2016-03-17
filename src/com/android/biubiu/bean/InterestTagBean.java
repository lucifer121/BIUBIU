package com.android.biubiu.bean;

import java.io.Serializable;

public class InterestTagBean implements Serializable{
	/**
	 * 标签内容
	 */
	private String name;
	/**
	 * 标签code
	 */
	private String code;
	/**
	 * 是否选择
	 */
	private Boolean isChoice=false;
	
	private String tagType;
	
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getIsChoice() {
		return isChoice;
	}
	public void setIsChoice(Boolean isChoice) {
		this.isChoice = isChoice;
	}

	
}
