package com.android.biubiu.bean;

import java.io.Serializable;

public class UserPhotoBean implements Serializable{
	String photoCode;
	String photoThumbnail;
	String photoOrigin;
	String photoName;
	public String getPhotoCode() {
		return photoCode;
	}
	public void setPhotoCode(String photoCode) {
		this.photoCode = photoCode;
	}
	public String getPhotoThumbnail() {
		return photoThumbnail;
	}
	public void setPhotoThumbnail(String photoThumbnail) {
		this.photoThumbnail = photoThumbnail;
	}
	public String getPhotoOrigin() {
		return photoOrigin;
	}
	public void setPhotoOrigin(String photoOrigin) {
		this.photoOrigin = photoOrigin;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

}
