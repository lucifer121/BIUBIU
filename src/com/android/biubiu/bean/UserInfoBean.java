package com.android.biubiu.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.android.biubiu.utils.Constants;
import com.google.gson.annotations.SerializedName;

public class UserInfoBean implements Serializable{
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
	private ArrayList<InterestByCateBean> interestCates;
	private ArrayList<InterestTagBean> interestTags;
	@SerializedName("matching_score")
	private int matchScore;
	@SerializedName("distance")
	private long distance;
	@SerializedName("time")
	private int time;
	@SerializedName("activity_time")
	private long activityTime;
	@SerializedName("superman")
	private int superMan;
	
	public int getSuperMan() {
		return superMan;
	}
	public void setSuperMan(int superMan) {
		this.superMan = superMan;
	}
	public long getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(long activityTime) {
		this.activityTime = activityTime;
	}
	public int getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(int matchScore) {
		this.matchScore = matchScore;
	}
	public long getDistance() {
		return distance;
	}
	public void setDistance(long distance) {
		this.distance = distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getIconOrigin() {
		return iconOrigin;
	}
	public void setIconOrigin(String iconOrigin) {
		this.iconOrigin = iconOrigin;
	}
	public ArrayList<InterestByCateBean> getInterestCates() {
		return interestCates;
	}
	public void setInterestCates(ArrayList<InterestByCateBean> interestCates) {
		this.interestCates = interestCates;
	}
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
	public ArrayList<InterestTagBean> getInterestTags() {
		return interestTags;
	}
	public void setInterestTags(ArrayList<InterestTagBean> interestTags) {
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
	public String getSexFlag(String sexStr){
		if(sexStr.equals("男")){
			return Constants.SEX_MALE;
		}else{
			return Constants.SEX_FAMALE;
		}
	}
	public String getSexStr(String sexFlag){
		if(sexFlag.equals(Constants.SEX_MALE)){
			return "男生";
		}else{
			return "女生";
		}
	}
}
