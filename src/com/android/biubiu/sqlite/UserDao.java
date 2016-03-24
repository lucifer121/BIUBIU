package com.android.biubiu.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.biubiu.bean.UserBean;
import com.android.biubiu.bean.UserFriends;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.LogUtil.log;


public class UserDao {
	private MySqliteDBHelper helper;
	private int photoWidth=0;

	public UserDao(Context context) {
		helper = new MySqliteDBHelper(context);
		
	}
	// 插入或替换用户
	public void insertOrReplaceUser(UserFriends user) {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL(
				"insert or replace into userfriends values("
						+ "null,?,?,?,?,?,?,?,?,?,?)",
				new Object[] {user.getStarsign(),user.getUserCode(),user.getNickname(),user.getSchool(),user.getIsgraduated(),
								user.getSex(),user.getCarrer(),user.getIcon_thumbnailUrl()!= null ? user.getIcon_thumbnailUrl():"",
								user.getCompany(),user.getAge()						
						});
		db.close();
	}

	/**
	 * 查询某用户
	 * @param userCode 用户id
	 * @return
	 */
	public List<UserFriends> queryUser(String userCode) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from userfriends where "
				+ DbConstents.USER_FRIEND_USER_CODE + "=?", new String[] { userCode });
		List<UserFriends> list = new ArrayList<UserFriends>();
		while (c.moveToNext()) {
			UserFriends user = new UserFriends();
			user.setAge(c.getInt(c.getColumnIndex(DbConstents.USER_FRIEND_AGE)));
			user.setCarrer(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_CARRER)));
			user.setCompany(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_COMPANY)));
			user.setIcon_thumbnailUrl(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_ICON_URL)));
			user.setIsgraduated(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_IS_GRADUATED)));
			user.setNickname(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_NICK_NAME)));
			user.setSchool(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_SCHOOL)));
			user.setSex(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_SEX))); 
			user.setStarsign(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_STAR_SIGN)));
			user.setUserCode(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_USER_CODE)));

			list.add(user);
		}
		c.close();
		db.close();
		return list;
	}
	/**
	 * 获取所有好友
	 * @param userCode
	 * @return
	 */
	public List<UserFriends> queryUserAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from userfriends", null);
		List<UserFriends> list = new ArrayList<UserFriends>();
		while (c.moveToNext()) {
			UserFriends user = new UserFriends();
			user.setAge(c.getInt(c.getColumnIndex(DbConstents.USER_FRIEND_AGE)));
			user.setCarrer(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_CARRER)));
			user.setCompany(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_COMPANY)));
			user.setIcon_thumbnailUrl(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_ICON_URL)));
			user.setIsgraduated(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_IS_GRADUATED)));
			user.setNickname(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_NICK_NAME)));
			user.setSchool(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_SCHOOL)));
			user.setSex(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_SEX))); 
			user.setStarsign(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_STAR_SIGN)));
			user.setUserCode(c.getString(c.getColumnIndex(DbConstents.USER_FRIEND_USER_CODE)));

			list.add(user);
		}
		c.close();
		db.close();
		return list;
	}
	
	/**
	 * 删除所有好友
	 */
	public void deleteAllUser(){
		SQLiteDatabase db=helper.getReadableDatabase();
		db.execSQL("delete from userfriends");
		db.close();
	}
	


	public boolean intToBol(int i) {
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int bolToInt(boolean b) {
		if (b) {
			return 1;
		} else {
			return 0;
		}
	}
}
