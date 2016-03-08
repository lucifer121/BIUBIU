package com.android.biubiu.bean;

import java.io.Serializable;

/**
 * @author lucifer
 * @date 2015-11-3
 * @return
 */
public class Schools implements Serializable {
	private String univsId;// 学校标号 id、
	private String provinceId;// 学校所在地
	private String univsNameString;// 学校名称

	public String getUnivsId() {
		return univsId;
	}

	public void setUnivsId(String univsId) {
		this.univsId = univsId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getUnivsNameString() {
		return univsNameString;
	}

	public void setUnivsNameString(String univsNameString) {
		this.univsNameString = univsNameString;
	}

}
