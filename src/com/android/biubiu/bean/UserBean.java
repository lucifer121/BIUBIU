package com.android.biubiu.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserBean implements Serializable{
	@SerializedName("user_code")
	String id;
	@SerializedName("nickname")
	String nickname;
	@SerializedName("sex")
	String sex;
	@SerializedName("age")
	String age;
	@SerializedName("isgraduated")
	String isStudent;
	@SerializedName("carrer")
	String career;
	@SerializedName("starsign")
	String star;
	@SerializedName("school")
	String school;
	@SerializedName("icon_thumbnailUrl")
	String userHead;
	@SerializedName("already_seen")
	String alreadSeen;
	@SerializedName("company")
	String company;
	@SerializedName("chat_id")
	String chatId;
	@SerializedName("reference_id")
	String referenceId;
	//bean加入的时间
	@SerializedName("time")
	long time;
	//bean在圆圈上的位置index
	int index;
	//bean的x坐标
	int x;
	//bean的y坐标
	int y;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getIsStudent() {
		return isStudent;
	}
	public void setIsStudent(String isStudent) {
		this.isStudent = isStudent;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getUserHead() {
		return userHead;
	}
	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
	public String getAlreadSeen() {
		return alreadSeen;
	}
	public void setAlreadSeen(String alreadSeen) {
		this.alreadSeen = alreadSeen;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

}
