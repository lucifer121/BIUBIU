package com.android.biubiu.bean;

public class LableBean {
	/**
	 * 兴趣标签 id
	 */
	private int id;
	/**
	 * 兴趣标签 名字
	 */
	private String name;
	/**
	 * 是否点击选择了
	 */
	private Boolean isSelector;
	private int bgColor;
	public int getId() {
		return id;
	}
	
	public Boolean getIsSelector() {
		return isSelector;
	}

	public void setIsSelector(Boolean isSelector) {
		this.isSelector = isSelector;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBgColor() {
		return bgColor;
	}
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
	

}