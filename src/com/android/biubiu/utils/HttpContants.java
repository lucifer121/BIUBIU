package com.android.biubiu.utils;

public class HttpContants {

	public static final String HTTP_ADDRESS = "http://123.56.193.210:8080/meetu_maven/";
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
	 * 发送biu
	 */
	public static final String SEND_BIU="app/biubiu/sendBiu";
	
	/**
	 * 抢biu
	 */
	public static final String GRAB_BIU="app/biubiu/grabBiu";
}
