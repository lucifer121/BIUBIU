package com.android.biubiu.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfoBean implements Serializable{
	private String id;
	private String nickname;
	private String sex;
	private String birthday;
	private String userHead;
	private String star;
	private String city;
	private String homeTown;
	private String heightWeight;
	private String identity;
	private String school;
	private ArrayList<String> personalTags;
	private ArrayList<String> interestTags;
	private ArrayList<String> userPhotos;
	
	public ArrayList<String> getUserPhotos() {
		return userPhotos;
	}
	public void setUserPhotos(ArrayList<String> userPhotos) {
		this.userPhotos = userPhotos;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getUserHead() {
		return userHead;
	}
	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHomeTown() {
		return homeTown;
	}
	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}
	public String getHeightWeight() {
		return heightWeight;
	}
	public void setHeightWeight(String heightWeight) {
		this.heightWeight = heightWeight;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public ArrayList<String> getPersonalTags() {
		return personalTags;
	}
	public void setPersonalTags(ArrayList<String> personalTags) {
		this.personalTags = personalTags;
	}
	public ArrayList<String> getInterestTags() {
		return interestTags;
	}
	public void setInterestTags(ArrayList<String> interestTags) {
		this.interestTags = interestTags;
	}

}
