package com.android.biubiu.sqlite;

import android.text.StaticLayout;

import com.google.gson.annotations.SerializedName;

public class DbConstents {
	public static final String DBNAME="iu.db";
	//好友表
	public static final String USER_FRIEND="userfriends";
	
	public static final String USER_FRIEND_COLUMN_ID="_id";
	public static final String USER_FRIEND_STAR_SIGN="star_sign";

	public static final String USER_FRIEND_USER_CODE="user_code";
	

	public static final String USER_FRIEND_NICK_NAME="nick_name";

	public static final String USER_FRIEND_SCHOOL="school";

	public static final String USER_FRIEND_IS_GRADUATED="is_graduated";

	public static final String USER_FRIEND_SEX="sex";

	public static final String USER_FRIEND_CARRER="carrer";

	public static final String USER_FRIEND_ICON_URL="icon_thumbnailUrl";
	
	public static final String USER_FRIEND_COMPANY="company";

	public static final String USER_FRIEND_AGE="age";
	//推送匹配表
	public static final String PUSH_MATCH="push_match_tb";
	public static final String PUSH_USER_CODE="user_code";
	public static final String PUSH_TIME="push_time";
}
