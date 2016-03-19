package com.android.biubiu.bean;

public class DotBean {
	//分割点横坐标
	double x;
	//分割点纵坐标
	double y;
	//此区域是否被填充
	boolean isAdd;
	//此区域为划分的第几个
	int index;

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public boolean isAdd() {
		return isAdd;
	}
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

}
