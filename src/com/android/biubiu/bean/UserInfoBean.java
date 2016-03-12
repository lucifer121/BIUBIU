package com.android.biubiu.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.android.biubiu.utils.Constants;
import com.google.gson.annotations.SerializedName;

public class UserInfoBean implements Serializable{
	@SerializedName("code")
	private String userCode;
	@SerializedName("icon_is_validate")
	private String iconVerify;
	@SerializedName("nickname")
	private String nickname;
	@SerializedName("sex")
	private String sex;
	@SerializedName("birth_date")
	private String birthday;
	@SerializedName("icon_url")
	private String userHead;
	@SerializedName("city")
	private String city;
	@SerializedName("hometown")
	private String homeTown;
	@SerializedName("height")
	private String height;
	@SerializedName("weight")
	private String weight;
	@SerializedName("school")
	private String school;
	@SerializedName("career")
	private String job;
	@SerializedName("isgraduated")
	private String isStudent;
	@SerializedName("description")
	private String aboutMe;
	@SerializedName("personality_tags")
	private ArrayList<TagBean> personalTags;
	@SerializedName("interested_tags")
	private ArrayList<TagBean> interestTags;
	@SerializedName("")
	private String star;
	@SerializedName("")
	private String company;
	@SerializedName("")
	private ArrayList<String> userPhotos;
	
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getIconVerify() {
		return iconVerify;
	}
	public void setIconVerify(String iconVerify) {
		this.iconVerify = iconVerify;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getIsStudent() {
		return isStudent;
	}
	public void setIsStudent(String isStudent) {
		this.isStudent = isStudent;
	}
	public ArrayList<String> getUserPhotos() {
		return userPhotos;
	}
	public void setUserPhotos(ArrayList<String> userPhotos) {
		this.userPhotos = userPhotos;
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
		if(sex.equals(Constants.SEX_MALE)){
			this.sex = "男";
		}else{
			this.sex = "女";
		}
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
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public ArrayList<TagBean> getPersonalTags() {
		return personalTags;
	}
	public void setPersonalTags(ArrayList<TagBean> personalTags) {
		this.personalTags = personalTags;
	}
	public ArrayList<TagBean> getInterestTags() {
		return interestTags;
	}
	public void setInterestTags(ArrayList<TagBean> interestTags) {
		this.interestTags = interestTags;
	}
	public String getSexFlag(){
		if(sex.equals("男")){
			return Constants.SEX_MALE;
		}else{
			return Constants.SEX_FAMALE;
		}
	}
}
