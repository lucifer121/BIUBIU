package com.android.biubiu.sqlite;

import java.util.ArrayList;
import java.util.List;






import com.android.biubiu.bean.Department;
import com.android.biubiu.bean.Schools;

import android.R.string;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author lucifer
 * @date 2015-11-3
 * @return
 */
public class SchoolDao {
	private SQLiteDatabase database;

	public List<Schools> getschoolAll() {
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
		String sql = "select * from schools order by school_id";
		List<Schools> list = new ArrayList<Schools>();

		// Cursor cur =
		// database.rawQuery("SELECT city.id_city, city.name FROM taxi, city WHERE city.id_city = taxi.id_city GROUP BY city.id_city",
		// null);

		Cursor c = database.rawQuery(sql, null);
		while (c.moveToNext()) {
			Schools item = new Schools();
			item.setUnivsId(c.getString(c.getColumnIndex("school_id")));
			item.setProvinceId(c.getString(c.getColumnIndex("province_id")));
			item.setUnivsNameString(c.getString(c.getColumnIndex("school_name")));

			System.out
					.println("id=" + item.getUnivsId() + " 省= "
							+ item.getProvinceId() + " 学校="
							+ item.getUnivsNameString());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		return list;

	}

	@SuppressWarnings("null")
	public List<Schools> getSchoolsFind(String schoolName) {
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
		String sql = "select * from schools where school_name like ? order by school_id";
		List<Schools> list = new ArrayList<Schools>();
		StringBuffer sBuffer = new StringBuffer();

		for (int i = 0; i < schoolName.length(); i++) {
			sBuffer.append("%");
			sBuffer.append(schoolName.substring(i, i + 1));
			sBuffer.append("%");
		}
		Log.e("lucifer",
				"schoolName==" + schoolName + "sBuffer==" + sBuffer.toString());
		// Cursor cur =
		// database.rawQuery("SELECT city.id_city, city.name FROM taxi, city WHERE city.id_city = taxi.id_city GROUP BY city.id_city",
		// null);

		Cursor c = database.rawQuery(sql, new String[] { sBuffer.toString() });
		while (c.moveToNext()) {
			Schools item = new Schools();
			item.setUnivsId(c.getString(c.getColumnIndex("school_id")));
			item.setProvinceId(c.getString(c.getColumnIndex("province_id")));
			item.setUnivsNameString(c.getString(c.getColumnIndex("school_name")));

			System.out
					.println("id=" + item.getUnivsId() + " 省= "
							+ item.getProvinceId() + " 学校="
							+ item.getUnivsNameString());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		return list;

	}
/**
 * 根据学校id 查专业
 * @param schoolId
 * @return
 */
	public List<Department> getDepartments(String schoolId) {
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
		String sql = "select * from department  where school_id=? order by id";
		List<Department> list = new ArrayList<Department>();

		// Cursor cur =
		// database.rawQuery("SELECT city.id_city, city.name FROM taxi, city WHERE city.id_city = taxi.id_city GROUP BY city.id_city",
		// null);

		Cursor c = database.rawQuery(sql, new String[] { schoolId });
		while (c.moveToNext()) {
			Department item = new Department();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setSchoolId(c.getString(c.getColumnIndex("school_id")));
			item.setDepartmentName(c.getString(c
					.getColumnIndex("department_name")));

			System.out.println("id=" + item.getId() + " 学校= "
					+ item.getSchoolId() + " 专业=" + item.getDepartmentName());

			list.add(item);
		}
		 if(c != null)  
	            c.close();
		return list;

	}

	/**
	 * 根据学校id 查学校名字
	 * 
	 * @param schoolId
	 * @return
	 */
	public List<Schools> getschoolName(String schoolId) {
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
		String sql = "select * from schools  where school_id=? ";
		List<Schools> list = new ArrayList<Schools>();

		// Cursor cur =
		// database.rawQuery("SELECT city.id_city, city.name FROM taxi, city WHERE city.id_city = taxi.id_city GROUP BY city.id_city",
		// null);

		Cursor c = database.rawQuery(sql, new String[] { schoolId });
		while (c.moveToNext()) {
			Schools item = new Schools();
			item.setUnivsId(c.getString(c.getColumnIndex("school_id")));
			item.setProvinceId(c.getString(c.getColumnIndex("province_id")));
			item.setUnivsNameString(c.getString(c.getColumnIndex("school_name")));

			System.out
					.println("id=" + item.getUnivsId() + " 省= "
							+ item.getProvinceId() + " 学校="
							+ item.getUnivsNameString());

			list.add(item);
		}
		 if(c != null)  
	            c.close();  
		return list;
	}

	/**
	 * 查 专业名称
	 * 
	 * @param schoolId
	 * @param departmentId
	 * @return
	 */
	public List<Department> getDepartmentsName(String schoolId,
			String departmentId) {
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
		String sql = "select * from department  where school_id=? and id=? order by id";
		List<Department> list = new ArrayList<Department>();

		// Cursor cur =
		// database.rawQuery("SELECT city.id_city, city.name FROM taxi, city WHERE city.id_city = taxi.id_city GROUP BY city.id_city",
		// null);

		Cursor c = database.rawQuery(sql,
				new String[] { schoolId, departmentId });
		while (c.moveToNext()) {
			Department item = new Department();
			item.setId(c.getString(c.getColumnIndex("id")));
			item.setSchoolId(c.getString(c.getColumnIndex("school_id")));
			item.setDepartmentName(c.getString(c
					.getColumnIndex("department_name")));

			System.out.println("id=" + item.getId() + " 学校= "
					+ item.getSchoolId() + " 专业=" + item.getDepartmentName());

			list.add(item);
		} if(c != null)  
            c.close();
		return list;

	}

}
