package com.android.biubiu.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.android.biubiu.bean.Citybean;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CityDao {
	private SQLiteDatabase database;

	public List<Citybean> getPrivance() {
		database = SQLiteDatabase.openOrCreateDatabase(DBManagerCity.DB_PATH
				+ "/" + DBManagerCity.DB_NAME, null);
		String sql = "select *from city";
		List<Citybean> list = new ArrayList<Citybean>();
		Cursor c = database.rawQuery(sql, null);
		while (c.moveToNext()) {
			Citybean item = new Citybean();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setPrivance(c.getString(c.getColumnIndex("province")));
			item.setPrivance_num(c.getString(c.getColumnIndex("province_num")));
			item.setCity(c.getString(c.getColumnIndex("city")));
			item.setCity_num(c.getString(c.getColumnIndex("city_num")));
			item.setTown(c.getString(c.getColumnIndex("town")));
			item.setTown_num(c.getString(c.getColumnIndex("town_num")));

			System.out.println("id=" + item.getId() + " 省= "
					+ item.getPrivance() + " 市=" + item.getCity() + "区="
					+ item.getTown());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		database.close();
		return list;

	}

	/**
	 * 查询所有的省信息
	 * 
	 * @return
	 * @author lucifer
	 * @date 2015-11-4
	 */
	public List<Citybean> getAllPrivance() {
		database = SQLiteDatabase.openOrCreateDatabase(DBManagerCity.DB_PATH
				+ "/" + DBManagerCity.DB_NAME, null);
		String sql = "select *from city group by province_num";
		List<Citybean> list = new ArrayList<Citybean>();
		Cursor c = database.rawQuery(sql, null);
		while (c.moveToNext()) {
			Citybean item = new Citybean();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setPrivance(c.getString(c.getColumnIndex("province")));
			item.setPrivance_num(c.getString(c.getColumnIndex("province_num")));
			item.setCity(c.getString(c.getColumnIndex("city")));
			item.setCity_num(c.getString(c.getColumnIndex("city_num")));
			item.setTown(c.getString(c.getColumnIndex("town")));
			item.setTown_num(c.getString(c.getColumnIndex("town_num")));

			System.out.println("id=" + item.getId() + " 省= "
					+ item.getPrivance() + " 市=" + item.getCity() + "区="
					+ item.getTown());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		database.close();
		return list;

	}

	/**
	 * 根据传进来的省 查 该省的所有市
	 * 
	 * @param provinceNum
	 * @return
	 * @author lucifer
	 * @date 2015-11-4
	 */
	public List<Citybean> getAllCity(String provinceNum) {

		database = SQLiteDatabase.openOrCreateDatabase(DBManagerCity.DB_PATH
				+ "/" + DBManagerCity.DB_NAME, null);
		String sql = "select *from city where province= ? group by city";
		List<Citybean> list = new ArrayList<Citybean>();
		Cursor c = database.rawQuery(sql, new String[] { provinceNum });
		while (c.moveToNext()) {
			Citybean item = new Citybean();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setPrivance(c.getString(c.getColumnIndex("province")));
			item.setPrivance_num(c.getString(c.getColumnIndex("province_num")));
			item.setCity(c.getString(c.getColumnIndex("city")));
			item.setCity_num(c.getString(c.getColumnIndex("city_num")));
			item.setTown(c.getString(c.getColumnIndex("town")));
			item.setTown_num(c.getString(c.getColumnIndex("town_num")));

			System.out.println("id=" + item.getId() + " 省= "
					+ item.getPrivance() + " 市=" + item.getCity() + "区="
					+ item.getTown());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		database.close();
		return list;

	}

	/**
	 * 根据传捡来的省和市 查询所有的区
	 * 
	 * @param provinceNum
	 * @param cityNum
	 * @return
	 * @author lucifer
	 * @date 2015-11-4
	 */

	public List<Citybean> getAllTown(String provinceNum, String cityNum) {
		database = SQLiteDatabase.openOrCreateDatabase(DBManagerCity.DB_PATH
				+ "/" + DBManagerCity.DB_NAME, null);
		String sql = "select *from city where province=? and city=?";
		List<Citybean> list = new ArrayList<Citybean>();
		Cursor c = database
				.rawQuery(sql, new String[] { provinceNum, cityNum });
		while (c.moveToNext()) {
			Citybean item = new Citybean();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setPrivance(c.getString(c.getColumnIndex("province")));
			item.setPrivance_num(c.getString(c.getColumnIndex("province_num")));
			item.setCity(c.getString(c.getColumnIndex("city")));
			item.setCity_num(c.getString(c.getColumnIndex("city_num")));
			item.setTown(c.getString(c.getColumnIndex("town")));
			item.setTown_num(c.getString(c.getColumnIndex("town_num")));

			System.out.println("id=" + item.getId() + " 省= "
					+ item.getPrivance() + " 市=" + item.getCity() + "区="
					+ item.getTown());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		database.close();
		return list;
	}

	/**
	 * 根据省市区的名称 查到 唯一的id信息
	 * 
	 * @param provinceNum
	 * @param cityNum
	 * @param twonNum
	 * @return
	 * @author lucifer
	 * @date 2015-11-4
	 */
	public List<Citybean> getID(String province, String city) {

		database = SQLiteDatabase.openOrCreateDatabase(DBManagerCity.DB_PATH
				+ "/" + DBManagerCity.DB_NAME, null);
		String sql = "select *from city where province=? and city=?";
		List<Citybean> list = new ArrayList<Citybean>();
		Cursor c = database.rawQuery(sql, new String[] { province, city});
		while (c.moveToNext()) {
			Citybean item = new Citybean();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setPrivance(c.getString(c.getColumnIndex("province")));
			item.setPrivance_num(c.getString(c.getColumnIndex("province_num")));
			item.setCity(c.getString(c.getColumnIndex("city")));
			item.setCity_num(c.getString(c.getColumnIndex("city_num")));
			item.setTown(c.getString(c.getColumnIndex("town")));
			item.setTown_num(c.getString(c.getColumnIndex("town_num")));

//			System.out.println("id=" + item.getId() + " 省= "
//					+ item.getPrivance() + " 市=" + item.getCity() + "区="
//					+ item.getTown());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		database.close();
		return list;

	}

	/**
	 * 根据城市id 获取城市
	 * 
	 * @param cityID
	 * @return
	 * @author lucifer
	 * @date 2015-11-6
	 */
	public List<Citybean> getCity(String cityID) {

		database = SQLiteDatabase.openOrCreateDatabase(DBManagerCity.DB_PATH
				+ "/" + DBManagerCity.DB_NAME, null);
		String sql = "select *from city where id=?";
		List<Citybean> list = new ArrayList<Citybean>();
		Cursor c = database.rawQuery(sql, new String[] { cityID });
		while (c.moveToNext()) {
			Citybean item = new Citybean();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setPrivance(c.getString(c.getColumnIndex("province")));
			item.setPrivance_num(c.getString(c.getColumnIndex("province_num")));
			item.setCity(c.getString(c.getColumnIndex("city")));
			item.setCity_num(c.getString(c.getColumnIndex("city_num")));
			item.setTown(c.getString(c.getColumnIndex("town")));
			item.setTown_num(c.getString(c.getColumnIndex("town_num")));

			System.out.println("id=" + item.getId() + " 省= "
					+ item.getPrivance() + " 市=" + item.getCity() + "区="
					+ item.getTown());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		database.close();
		return list;

	}

}
