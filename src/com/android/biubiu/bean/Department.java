package com.android.biubiu.bean;

import java.io.Serializable;

/**
 * @author lucifer
 * @date 2015-11-3
 * @return
 */
public class Department implements Serializable {
	private String id;// 专业id
	private String schoolId;// 学校id
	private String departmentName;// 学校名字

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
