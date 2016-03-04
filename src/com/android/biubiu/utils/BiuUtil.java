package com.android.biubiu.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import com.android.biubiu.bean.DotBean;
import android.graphics.SumPathEffect;
import android.util.Log;

public class BiuUtil {
	/**
	 * 计算边界预留角度，防止重合
	 * @param userViewR userview边长的一半
	 * @param r
	 * @return
	 */
	public static double getEdgeAngle(double userViewR,double r){
		//一定要记得把弧度转化为角度啊！！！！！！
		double angle = Math.asin(Math.sqrt(2)*userViewR/(r*2));
		return Math.round(angle*180/Math.PI);
	}
	/**
	 * 获取规定范围内随机度数
	 * @param sunN 区域数
	 * @param index 区域index
	 * @param userViewR 放置view的边长
	 * @param r 圆圈的半径
	 */
	public static double  getRandomAngle(int sunN,int index,double userViewR,double r){
		double angle1 = index*360/sunN+BiuUtil.getEdgeAngle(userViewR, r);
		double angle2 = (index+1)*360/sunN-BiuUtil.getEdgeAngle(userViewR, r);
		double  randomAngle = (Math.random()*(angle2-angle1)+angle1);
		return randomAngle;
	}
	/**
	 * 计算放置位置横坐标
	 * @param sunN sunN 圈上放置总数
	 * @param index 区域index
	 * @param userViewR 用户view边长
	 * @param r 圈的半径
	 * @param x0 圆心横坐标
	 * @return
	 */
	public static int getLocationX(double randomAngle,double userViewR,double r,float x0){
		int x = (int)(getCircleXByAngle(randomAngle, r,x0)-userViewR/2);
		return x;
	}
	/**
	 * 计算放置位置纵坐标
	 * @param sunN 圈上放置总数
	 * @param index 区域index
	 * @param userViewR 用户view边长
	 * @param r 圈的半径
	 * @param y0 圆心纵坐标
	 * @return
	 */
	public static int getLocationY(double randomAngle,double userViewR,double r,float y0){
		int y = (int)(getCircleYByAngle(randomAngle, r,y0)-userViewR/2);
		return y;
	}
	/**
	 * 大圈
	 * 获取规定范围内随机度数
	 * @param sunN 区域数
	 * @param index 区域index
	 * @param userViewR 放置view的边长
	 * @param r 圆圈的半径
	 */
	public static double  getRandomAngleBig(ArrayList<Double> angleList,int sunN,int index,double userViewR,double r){
		double angle1 = angleList.get(0);
		double angle2 = angleList.get(1);
		double angle3 = angleList.get(2);
		double angle4 = angleList.get(3);
		double angleLit = 0;
		double angleBig = 0;
		double angleArea = angle3-angle2;
		if(index < sunN/2){
			angleLit = index*angleArea*2/sunN + angle2 + BiuUtil.getEdgeAngle(userViewR, r);
			angleBig = (index+1)*angleArea*2/sunN +angle2 - BiuUtil.getEdgeAngle(userViewR, r);
		}else{
			index = index - sunN/2;
			angleLit = index*angleArea*2/sunN + angle4 + BiuUtil.getEdgeAngle(userViewR, r);
			angleBig = (index+1)*angleArea*2/sunN +angle4 - BiuUtil.getEdgeAngle(userViewR, r);
		}
		double  randomAngle = (Math.random()*(angleBig-angleLit)+angleLit);
		return randomAngle;
	}
	/**
	 * 计算已知角度的坐标x
	 * @param angle 角度
	 * @param r 圆的半径
	 * @return
	 */
	public static double getCircleXByAngle(double angle,double r,float x0){
		double x = r * Math.sin(angle* Math.PI / 180)+x0;
		return x;
	}
	/**
	 * 计算已知角度的坐标y
	 * @param angle 角度
	 * @param r 圆的半径
	 * @return
	 */
	public static double getCircleYByAngle(double angle,double r,float y0){
		double y = r * Math.cos(angle* Math.PI / 180)+y0;
		return y;
	}
	/**
	 * 计算分割点横坐标
	 * @param i 第几个区域
	 * @param n 圆上区域总数
	 * @param r 圆的半径
	 * @return
	 */
	public static double getCircleX(int i,int n,double r,float x0){
		double x = r * Math.sin(i * 2 * Math.PI / n)+x0;
		return x;
	}
	/**
	 * 计算分割点纵坐标
	 * @param i 第几个区域
	 * @param n 圆上区域总数
	 * @param r 圆的半径
	 * @return
	 */
	public static double getCircleY(int i,int n,double r,float y0){
		double y = r * Math.cos(i * 2 * Math.PI / n )+y0;
		return y;
	}
	/**
	 * 计算最外层圆分割点横坐标
	 * @param i 第几个区域
	 * @param n 圆上区域总数
	 * @param r 圆的半径
	 * @param angleArea 可放置用户信息的角度区域的一半
	 * @param offestAngle 与基准线偏移量
	 * @return
	 */
	public static double getBigCircleX(int i,int n,double r,double angleArea,double offestAngle,float x0){
		double x = r * Math.sin(i * 2 * angleArea / n + offestAngle*Math.PI/180)+x0;
		return x;
	}
	/**
	 * 计算最外层圆分割点纵坐标
	 * @param i 第几个区域
	 * @param n 圆上区域总数
	 * @param r 圆的半径
	 * @param angleArea 可放置用户信息的角度区域的一半
	 * @param offestAngle 与基准线偏移量
	 * @return
	 */
	public static double getBigCircleY(int i,int n,double r,double angleArea,double offestAngle,float y0){
		double y = r * Math.cos(i * 2 * angleArea / n + offestAngle*Math.PI/180)+y0;
		return y;
	}

	/**
	 * 根据坐标x求角度
	 * @param x
	 * @return
	 */
	public static double getAngleByX(float x,double r,float x0){
		double a = Math.asin((x-x0)/r);
		return Math.round(a*180/Math.PI);
	}
	/**
	 * 计算圆上分割点
	 * */
	public static ArrayList<DotBean> caculateCircle(int sumN,double r,float x0,float y0) {
		ArrayList<DotBean> c1DotList = new ArrayList<DotBean>();
		for(int i =0;i<sumN;i++){
			DotBean bean  = new DotBean();
			bean.setAdd(false);
			bean.setIndex(i);
			bean.setX(getCircleX(i, sumN, r,x0));
			bean.setY(getCircleY(i, sumN, r,y0));
			c1DotList.add(bean);
		}
		return c1DotList;
	}
	public static ArrayList<DotBean> caculateCircleBig(ArrayList<Double> angleList,float userViewR,double r,float x0,float y0,int sumN) {
		ArrayList<DotBean> c3DotList = new ArrayList<DotBean>();
		double angle1 = angleList.get(0);
		double angle2 = angleList.get(1);
		double angle3 = angleList.get(2);
		double angle4 = angleList.get(3);
		//可放置用户信息的角度区域 上下两个 大小相等 上面和下面分别分为n/2个区域
		double angleArea = angle3 - angle2;
		for(int i = 0;i<sumN/2;i++){
			//上半部分偏移为angle1
			DotBean bean  = new DotBean();
			bean.setAdd(false);
			bean.setIndex(i);
			bean.setX(getBigCircleX(i, sumN, r, angleArea, angle2,x0));
			bean.setY(getBigCircleY(i, sumN, r, angleArea, angle2,y0));
			c3DotList.add(bean);
		}
		for(int i = 0;i<sumN/2;i++){
			//下半部分偏移为angle3
			DotBean bean  = new DotBean();
			bean.setAdd(false);
			bean.setIndex(i);
			bean.setX(getBigCircleX(i, sumN, r, angleArea, angle4,x0));
			bean.setY(getBigCircleY(i, sumN, r, angleArea, angle4,y0));
			c3DotList.add(bean);
		}
		return c3DotList;
	}
	//获取临界角度
	public static ArrayList< Double> getEdgeAngle(int width,float userViewR,double r,float x0,float y0){
		ArrayList< Double> edgeAngleList = new ArrayList<Double>();
		float boundaryX1 = width - userViewR/2;
		float boundaryX2 = userViewR/2;
		//临界点的四个角度
		double angle1 = BiuUtil.getAngleByX(boundaryX1,r,x0);
		double angle2 = 180- angle1;
		double angle3 = 180 + angle1;
		double angle4 = 360 - angle1;
		edgeAngleList.add(angle1);
		edgeAngleList.add(angle2);
		edgeAngleList.add(angle3);
		edgeAngleList.add(angle4);
		return edgeAngleList;
	}
}
