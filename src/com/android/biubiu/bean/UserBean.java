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

}
