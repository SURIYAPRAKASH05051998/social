package com.petal.social.user.database.dao;

import java.util.UUID;

public class UserRecord {

	private UUID   userCode;
	private String userName;
	private String deviceId;
	private String userEmail;
	private String countryCode;
	private String userMobile;
	private String creationDate;
	private String userPassword;
	private String modificationDate;
	public UUID getUserCode() {
		return userCode;
	}
	public void setUserCode(UUID userCode) {
		this.userCode = userCode;
	}
	@Override
	public String toString() {
		return "UserRecord [userCode=" + userCode + ", userName=" + userName + ", deviceId=" + deviceId + ", userEmail="
				+ userEmail + ", countryCode=" + countryCode + ", userMobile=" + userMobile + ", creationDate="
				+ creationDate + ", userPassword=" + userPassword + ", modificationDate=" + modificationDate + "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	
}
