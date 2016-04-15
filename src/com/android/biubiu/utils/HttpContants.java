package com.android.biubiu.utils;

public class HttpContants {

	public static final String HTTP_ADDRESS = "http://app.iu.imeetu.cc/meetu_maven/";
	//阿里云
	public static final String A_LI_YUN = "http://oss-cn-beijing.aliyuncs.com";
	/**
	 * 注册
	 */
	public static final String REGISTER_METHOD = "app/auth/register";
	/**
	 * 个人主页
	 */
	public static final String MY_PAGER_INFO = "app/info/getUserInfo";
	/**
	 * 登录
	 */
	public static final String LOGIN="app/auth/login";
	/**
	 * 退出登录/注销
	 */
	public static final String EXIT="app/auth/logout";
	/**
	 * 更改密码
	 */
	public static final String UPDATE_PASSWORD="app/auth/updatePassword";
	/**
	 * 获取标签
	 */
	public static final String  GAT_TAGS="app/info/getTags";
	/**
	 * 检测手机号是否已注册
	 */
	public static final String IS_REGISTERED = "app/auth/checkPhoneIsRegistered";
	/**
	 * 注册时获得上传图片鉴权
	 */
	public static final String REGISTER_OSS = "app/auth/getOSSSecurityTokenWithoutT";
	/**
	 * 获得上传图片鉴权,需要token
	 */
	public static final String REGISTER_OSS_TOKEN = "app/info/getOSSSecurityToken";
	/**
	 * 修改头像
	 */
	public static final String UPDATE_HEAD = "app/info/updateIcon";
	/**
	 * 上传用户墙照片
	 */
	public static final String UPLOAD_PHOTO = "app/info/savePhoto";
	/**
	 * 删除图片
	 */
	public static final String DELETE_PHOTO = "app/info/delPhoto";
	/**
	 * 更新用户信息
	 */
	public static final String UPDATE_USETINFO = "app/info/updateUserInfo";
	/**
	 * 获取用户设置
	 */	
	public static final String GET_SETTING="app/info/getSettings";
	/**
	 * 更新用户设置
	 */
	public static final String UPDATE_SETTING="app/info/updateSettings";
	/**
	 * 更新用户位置信息
	 */
	public static final String UPDATE_LACATION = "app/info/updateLocation";
	/**
	 * 更新用户活跃时间
	 */
	public static final String UPDATE_ACTIVITY_TIME = "app/info/updateUserInfo";
	/**

	 * 发送biu
	 */
	public static final String SEND_BIU="app/biubiu/sendBiu";
	
	/**
	 * 抢biu
	 */
	public static final String GRAB_BIU="app/biubiu/grabBiu";
	/**
	 * 更新CHANNELID
	 */
	public static final String UPDATE_CHANNEL = "app/biubiu/updateChannelIdAndDeviceType";
	/**
	 * 获取biubiu列表
	 */
	public static final String GET_BIU_LIST = "app/biubiu/biubiuList";
	/**
	 * 获取头像已读状态
	 */
	public static final String UPDATE_HEAD_STATE = "app/biubiu/updateIconStatus";
	/**
	 * 获取biubiu详情
	 */
	public static final String BIU_DETIAL="app/biubiu/biuDetails";
	/**
	 * 修改抢biubiu人状态
	 */
	public static final String UPDATE_BIU_STATE="app/biubiu/updateChatListState";
	/**
	 * 获取未登录时biubiu列表
	 */
	public static final String GET_UNLOGIN_BIU_LIST="app/auth/biubiuListUnlogin";
	/**
	 * 抢biu时 不想见到他
	 */
	public static  final String NO_LONGER_MATCH="app/biubiu/nolongerMatch";
	
	/**
	 * 获取好友列表
	 */
	public static final String GET_FRIDENS_LIST="app/biubiu/getFriendsList";
	/**
	 * 解除匹配关系
	 */
	public static final String REMOVE_FRIEND="app/biubiu/removeFriend";
	/**
	 * 举报好友
	 */
	public static final String REPORT="app/biubiu/report";
	/**
	 * 获取订单编号
	 */
	public static final String GET_ORDER_CODE="app/biubiu/createBill";
	/**
	 * 查询订单是否成功
	 */
	public static final String QUERY_PAY="app/biubiu/checkBill";
}
