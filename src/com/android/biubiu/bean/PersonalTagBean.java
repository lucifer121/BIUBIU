package com.android.biubiu.bean;

import java.io.Serializable;

public class PersonalTagBean implements Serializable{
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