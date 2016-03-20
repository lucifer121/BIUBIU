package com.android.biubiu.bean;

public class UserBean {
	String id;
	String nickname;
	String sex;
	String age;
	String isStudent;
	String career;
	String star;
	String school;
	String userHead;
	//bean加入的时间
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

}
