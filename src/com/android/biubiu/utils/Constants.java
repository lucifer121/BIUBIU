package com.android.biubiu.utils;

public class Constants {
	//身份是学生
	public static final String IS_STUDENT_FLAG = "1";
	//身份不是学生
	public static final String HAS_GRADUATE = "2";
	//男生
	public static final String SEX_MALE = "1";
	//女生
	public static final String SEX_FAMALE = "2";
	//头像已审核
	public static final String ICON_VERIFY = "1";
	//头像未审核
	public static final String ICON_NO_VERIFY = "2";
	//同城
	public static final String SAME_CITY = "1";
	//不限
	public static final String UN_LIMIT = "2";
	//未查看过匹配
	public static final String UN_SEEN = "0";
	//已查看过匹配
	public static final String ALREADY_SEEN = "1";
	//推送消息类型 0--推送匹配
	public static final String MSG_TYPE_MATCH = "0";
	//推送消息类型 2--你匹配的人biubiu被抢了
	public static final String MSG_TYPE_DEL = "2";
	//推送消息类型 1--你的biubiu被抢了
	public static final String MSG_TYPE_GRAB = "1";
	//U米充值成功
	public static final String PAY_SUC = "1";
	//U米充值失败
	public static final String PAY_FAIL = "0";
	/**
	 * type 个性标签
	 */
	public static final String PERSONALIED="personalied";
	/**
	 * type chat话题标签
	 */
	public static final String CHAT="chat";
	/**
	 * type interest兴趣标签
	 */
	public static final String INTEREST="interest";
	/**
	 * 标签分类
	 * */
	public static final String SPORT="运动";
	public static final String MOVIE="电影";
	public static final String BOOK="书籍";
	public static final String MUSIC="音乐";
	
	/**
	 * 聊天页面刷新广播 标记 频道
	 */
	public static final String FLAG_RECEIVE="receive_refresh";
	/**
	 * 抢到biubiu广播 标记 频道
	 */
	public static final String GRAB_BIU="grab_biu_broad";
	/**
	 * 用户特殊标记
	 * */
	public static final int NORMAL_USER = 0;
	public static final int SUPER_ONE_USER = 1;
	public static final int SUPER_TWO_USER = 2;
	public static final int SUPER_THREE_USER = 3;
}
