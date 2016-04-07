package com.android.biubiu.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.android.biubiu.bean.UserBean;
import com.android.biubiu.bean.UserFriends;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PushMatchDao {
	private MySqliteDBHelper helper;
	public PushMatchDao(Context context) {
		// TODO Auto-generated constructor stub
		helper = new MySqliteDBHelper(context);
	}
	// 插入或替换用户
	public void insertOrReplacePush(UserBean user) {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL(
				"insert or replace into "+DbConstents.PUSH_MATCH+" values("
						+ "?,?)",
						new Object[] {user.getChatId(),System.currentTimeMillis()});
		db.close();
	}
	/**
	 * 查询某用户
	 * @param userCode 用户id
	 * @return
	 */
	public boolean queryIsSameTime(String userCode,long moveTime) {
		long pushTime = 0;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from "+DbConstents.PUSH_MATCH+" where "
				+ DbConstents.PUSH_USER_CODE + "=?", new String[] { userCode });
		List<UserFriends> list = new ArrayList<UserFriends>();
		if(c.moveToNext()) {
			pushTime = c.getInt(c.getColumnIndex(DbConstents.PUSH_TIME));
		}
		c.close();
		db.close();
		if((moveTime - pushTime) <= 3*1000){
			return true;
		}else{
			return false;
		}
	}
	// 删除指定推送
	public void deleteByType(String userCode) {
		SQLiteDatabase sdb = helper.getWritableDatabase();
		sdb.delete(DbConstents.PUSH_MATCH, DbConstents.PUSH_USER_CODE
				+ "=?", new String[] {userCode });
		sdb.close();
	}
	/**
	 * 删除所有推送缓存
	 */
	public void deleteAllPush(){
		SQLiteDatabase db=helper.getReadableDatabase();
		db.execSQL("delete from "+DbConstents.PUSH_MATCH);
		db.close();
	}
}
