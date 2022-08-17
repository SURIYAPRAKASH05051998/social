package com.petal.social.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestDto {

	@NotBlank(message = "Enter the user name")
    private String userName;

    @NotBlank
    @Email(message = "Enter the valid email address")
    private String userEmail;

    @NotBlank(message = "Enter the valid country code")
    private String countryCode;
    
    @Size(min=10)
    @NotBlank(message = "Enter the valid phoneNo")
    private String mobileNumber;

 //   @NotNull(message = "Enter the deviceUUID")
    private String userPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


     
 
}
