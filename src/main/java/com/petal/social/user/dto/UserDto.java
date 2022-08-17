package com.petal.social.user.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	
	private UUID userCode;
	private String userName;
	private String countryCode;
	private String userMobile;
	private String userEmail;
	private String deviceId;
	private String userPassword;
	private String creationDate;
	private String modificationDate;
	
	public String getUserName() {
		return userName;
	}
	@Override
	public String toString() {
		return "UserDto [userCode=" + userCode + ", userName=" + userName + ", countryCode=" + countryCode
				+ ", userMobile=" + userMobile + ", userEmail=" + userEmail + ", userEmail=" + userEmail + ",userPassword=" + userPassword + ", creationDate="
				+ creationDate + ", modificationDate=" + modificationDate + "]";
	}
	public UUID getUserCode() {
		return userCode;
	}
	public void setUserCode(UUID userCode) {
		this.userCode = userCode;
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
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
