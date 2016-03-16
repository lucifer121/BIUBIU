package com.android.biubiu.bean;

import java.io.Serializable;
import java.util.List;

public class SettingBean implements Serializable{
	private  int sex;
	private String city;
	private int age_down;
	private int age_up;
	private List<PersonalTagBean> personalized_tags;
	private int message;
	private int sound;
	private int vibration;
	private String token;
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getAge_down() {
		return age_down;
	}
	public void setAge_down(int age_down) {
		this.age_down = age_down;
	}
	public int getAge_up() {
		return age_up;
	}
	public void setAge_up(int age_up) {
		this.age_up = age_up;
	}
	public List<PersonalTagBean> getPersonalized_tags() {
		return personalized_tags;
	}
	public void setPersonalized_tags(List<PersonalTagBean> personalized_tags) {
		this.personalized_tags = personalized_tags;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
 

}
