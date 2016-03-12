package com.android.biubiu.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TagBean implements Serializable{
	@SerializedName("")
	private String tagType;
	@SerializedName("")
	private String tagName;
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
