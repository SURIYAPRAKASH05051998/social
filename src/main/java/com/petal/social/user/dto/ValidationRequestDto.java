package com.petal.social.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ValidationRequestDto {
	

    @NotBlank(message = "Enter the valid country code")
    private String countryCode;
    
    @Size(min=10)
    @NotBlank(message = "Enter the valid phoneNo")
    private String mobileNumber;

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
    

}
