package com.android.biubiu.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.android.biubiu.utils.Constants;
import com.google.gson.annotations.SerializedName;

public class UserInfoBean implements Serializable{
	@SerializedName("code")
	private String userCode;
	@SerializedName("photoCircle")
	private String iconCircle;
	@SerializedName("photoOrigin")
	private String iconOrigin;
	@SerializedName("photoStatus")
	private String iconVerify;
	@SerializedName("photos")
	private ArrayList<UserPhotoBean> userPhotos;
	@SerializedName("description")
	private String aboutMe;
	@SerializedName("nickname")
	private String nickname;
	@SerializedName("sex")
	private String sex;
	@SerializedName("birth_date")
	private String birthday;
	@SerializedName("starsign")
	private String star;
	@SerializedName("city")
	private String city;
	@SerializedName("hometown")
	private String homeTown;
	@SerializedName("height")
	private int height;
	@SerializedName("weight")
	private int weight;
	@SerializedName("isgraduated")
	private String isStudent;
	@SerializedName("school")
	private String school;
	@SerializedName("career")
	private String career;
	@SerializedName("company")
	private String company;
	@SerializedName("personality_tags")
	private ArrayList<PersonalTagBean> personalTags;
	@SerializedName("interested_tags")
	private ArrayList<InterestLableBean> interestTags;
	
	public String getIconCircle() {
		return iconCircle;
	}
	public void setIconCircle(String iconCircle) {
		this.iconCircle = iconCircle;
	}
	public String getIconOrign() {
		return iconOrigin;
	}
	public void setIconOrign(String iconOrign) {
		this.iconOrigin = iconOrign;
	}
	public ArrayList<UserPhotoBean> getUserPhotos() {
		return userPhotos;
	}
	public void setUserPhotos(ArrayList<UserPhotoBean> userPhotos) {
		this.userPhotos = userPhotos;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public ArrayList<PersonalTagBean> getPersonalTags() {
		return personalTags;
	}
	public void setPersonalTags(ArrayList<PersonalTagBean> personalTags) {
		this.personalTags = personalTags;
	}
	public ArrayList<InterestLableBean> getInterestTags() {
		return interestTags;
	}
	public void setInterestTags(ArrayList<InterestLableBean> interestTags) {
		this.interestTags = interestTags;
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
	public String getIsStudent() {
		return isStudent;
	}
	public void setIsStudent(String isStudent) {
		this.isStudent = isStudent;
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
	public String getSexFlag(){
		if(sex.equals("男")){
			return Constants.SEX_MALE;
		}else{
			return Constants.SEX_FAMALE;
		}
	}
}
