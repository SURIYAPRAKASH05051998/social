package com.petal.social.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationResponseDto {

    private String countryCode;
    private String mobileNumber;
    private String otp;

	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Override
	public String toString() {
		return "ValidationResponseDto [countryCode=" + countryCode + ", mobileNumber=" + mobileNumber + ", otp=" + otp
				+ "]";
	}
	public ValidationResponseDto(String countryCode, String mobileNumber, String otp) {
		super();
		this.countryCode = countryCode;
		this.mobileNumber = mobileNumber;
		this.otp = otp;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
    
    
}
