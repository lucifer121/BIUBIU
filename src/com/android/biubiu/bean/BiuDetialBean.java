package com.android.biubiu.bean;

import java.util.List;

import android.R.integer;

public class BiuDetialBean {
	private String token;
	/**
	 * 头像url
	 */
	private String icon_thumbnailUrl;
	/**
	 * 用户的code
	 */
	private String user_code;
	/**
	 * 距离 单位m
	 */
	private String distance;
	/**
	 * 匹配度
	 */
	private String matching_score;
	
	private int sex;
	private int age;
	/**
	 * 星座
	 */
	private int starsign;
	/**
	 * 是否毕业
	 */
	private String isgraduated;
	/**
	 * 学校
	 */
	private int school;
	/**
	 * 公司
	 */
	private integer company;
	/**
	 * 命中个性标签个数
	 */
	private int  hit_tags_num;
	
	private List<PersonalTagBean> hit_tags;
	
	/**
	 * 共同兴趣个数
	 */
	private int interested_tags_num;
	
	private List<InterestByCateBean> interested_tags;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIcon_thumbnailUrl() {
		return icon_thumbnailUrl;
	}

	public void setIcon_thumbnailUrl(String icon_thumbnailUrl) {
		this.icon_thumbnailUrl = icon_thumbnailUrl;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getMatching_score() {
		return matching_score;
	}

	public void setMatching_score(String matching_score) {
		this.matching_score = matching_score;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getStarsign() {
		return starsign;
	}

	public void setStarsign(int starsign) {
		this.starsign = starsign;
	}

	public String getIsgraduated() {
		return isgraduated;
	}

	public void setIsgraduated(String isgraduated) {
		this.isgraduated = isgraduated;
	}

	public int getSchool() {
		return school;
	}

	public void setSchool(int school) {
		this.school = school;
	}

	public integer getCompany() {
		return company;
	}

	public void setCompany(integer company) {
		this.company = company;
	}

	public int getHit_tags_num() {
		return hit_tags_num;
	}

	public void setHit_tags_num(int hit_tags_num) {
		this.hit_tags_num = hit_tags_num;
	}

	public List<PersonalTagBean> getHit_tags() {
		return hit_tags;
	}

	public void setHit_tags(List<PersonalTagBean> hit_tags) {
		this.hit_tags = hit_tags;
	}

	public int getInterested_tags_num() {
		return interested_tags_num;
	}

	public void setInterested_tags_num(int interested_tags_num) {
		this.interested_tags_num = interested_tags_num;
	}

	public List<InterestByCateBean> getInterested_tags() {
		return interested_tags;
	}

	public void setInterested_tags(List<InterestByCateBean> interested_tags) {
		this.interested_tags = interested_tags;
	}
	
	


}
