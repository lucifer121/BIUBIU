package com.android.biubiu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SettingBean implements Serializable{
	private  String sex;
	private  String sex2;
	private String city;
	@SerializedName("age_down")
	private int ageDown;
	@SerializedName("age_up")
	private int ageUp;
	@SerializedName("personalized_tags")
	private ArrayList<PersonalTagBean> personalTags;
	private int message;
	private int sound;
	private int vibration;
	private int personalityTags;
	
	public int getPersonalityTags() {
		return personalityTags;
	}
	public void setPersonalityTags(int personalityTags) {
		this.personalityTags = personalityTags;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public int getAgeDown() {
		return ageDown;
	}
	public void setAgeDown(int ageDown) {
		this.ageDown = ageDown;
	}
	public int getAgeUp() {
		return ageUp;
	}
	public void setAgeUp(int ageUp) {
		this.ageUp = ageUp;
	}
	public ArrayList<PersonalTagBean> getPersonalTags() {
		return personalTags;
	}
	public void setPersonalTags(ArrayList<PersonalTagBean> personalTags) {
		this.personalTags = personalTags;
	}
	public int getMessage() {
		return message;
	}
	public void setMessage(int message) {
		this.message = message;
	}
	public int getSound() {
		return sound;
	}
	public void setSound(int sound) {
		this.sound = sound;
	}
	public int getVibration() {
		return vibration;
	}
	public void setVibration(int vibration) {
		this.vibration = vibration;
	}
	public String getSex2() {
		return sex2;
	}
	public void setSex2(String sex2) {
		this.sex2 = sex2;
	}
	
}
