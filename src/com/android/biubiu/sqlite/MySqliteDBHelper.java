package com.android.biubiu.sqlite;

import com.avos.avoscloud.DeleteCallback;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.StaticLayout;

public class MySqliteDBHelper extends SQLiteOpenHelper {

	private static final int VERSION = 2;

//	private static final String TBL_CHANNEL = "channel";
//	private static final String TBL_CHANNEL_COLUMN_ID = "_id";
//	private static final String TBL_CHANNEL_COLUMN_CODE = "_code";
//	private static final String TBL_CHANNEL_COLUMN_NAME = "_name";
//	private static final String TBL_CHANNEL_COLUMN_URL = "_url";
//	private static final String TBL_CHANNEL_COLUMN_STATE = "_state";
//
//	private static final String TBL_USERS = "users";
//	private static final String TBL_USERS_COLUMN_ID = "_id";
//	private static final String TBL_USERS_COLUMN_NAME = "_name";
//	private static final String TBL_USERS_COLUMN_PWD = "_pwd";





	public MySqliteDBHelper(Context context) {
		super(context, DbConstents.DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer user = new StringBuffer();
		user.append("create table if not exists ");
		user.append(DbConstents.USER_FRIEND + "(");
		user.append(DbConstents.USER_FRIEND_COLUMN_ID + " integer primary key autoincrement ,");
		user.append(DbConstents.USER_FRIEND_STAR_SIGN + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_USER_CODE + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_NICK_NAME + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_SCHOOL + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_IS_GRADUATED + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_SEX + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_CARRER + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_ICON_URL + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_COMPANY + " varchar(100) ,");
		user.append(DbConstents.USER_FRIEND_AGE + " Integer ");
		user.append(")");
		db.execSQL(user.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		switch (db.getVersion()) {
		case 1:
			
		case 2:
			StringBuffer push = new StringBuffer();
			push.append("create table if not exists ");
			push.append(DbConstents.PUSH_MATCH + "(");
			push.append(DbConstents.PUSH_USER_CODE + " varchar(100) primary key,");
			push.append(DbConstents.PUSH_TIME + " Integer ");
			push.append(")");
			db.execSQL(push.toString());
		default:
			break;
		}
	}

}
