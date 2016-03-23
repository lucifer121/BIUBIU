package com.android.biubiu.bean;

import com.google.gson.annotations.SerializedName;

public class UserFriends {
	/**
	 * 星座
	 */
	@SerializedName("starsign")
	private String starsign;
	/**
	 * 用户code
	 */
	@SerializedName("user_code")
	private String userCode;
	/**
	 * 用户名字
	 */
	@SerializedName("nickname")
	private String nickname;
	/**
	 * 学校
	 */
	@SerializedName("school")
	private String school;
	/**
	 * 是否毕业  1表示 未毕业   2表示 已毕业
	 */
	@SerializedName("isgraduated")
	private String isgraduated;
	/**
	 * 性别
	 */
	@SerializedName("sex")
	private String sex;
	/**
	 * 职业
	 */
	@SerializedName("carrer")
	private String carrer;
	/**
	 * 头像地址 url
	 */
	@SerializedName("icon_thumbnailUrl")
	private String icon_thumbnailUrl;
	
	/**
	 * 公司
	 */
	@SerializedName("company")
	private String company;
	/**
	 * 年龄
	 */
	@SerializedName("age")
	private int age;
	public String getStarsign() {
		return starsign;
	}
	public void setStarsign(String starsign) {
		this.starsign = starsign;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getIsgraduated() {
		return isgraduated;
	}
	public void setIsgraduated(String isgraduated) {
		this.isgraduated = isgraduated;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCarrer() {
		return carrer;
	}
	public void setCarrer(String carrer) {
		this.carrer = carrer;
	}
	public String getIcon_thumbnailUrl() {
		return icon_thumbnailUrl;
	}
	public void setIcon_thumbnailUrl(String icon_thumbnailUrl) {
		this.icon_thumbnailUrl = icon_thumbnailUrl;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	  

}
