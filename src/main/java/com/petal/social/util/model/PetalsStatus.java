package com.petal.social.util.model;

import java.util.HashMap;
import java.util.Map;

public enum PetalsStatus {

	VALID_RESPONSE(200, "Success"), 
	DELETED_SUCCESS(200, "Deleted Successfully"),
	UPDATED_SUCESS(204, "Updated Sucessfully"), 
	INVALID_RESPONSE(201, "Error"),
	NO_DATA(402, "No record"), 
	ERROR(500, "Something Went Wrong"), 
	INVALID_REQUEST(202, "Invalid Request"), 
	INVALID_EMAIL(100, "Not an Valid Email Id"),
	USER_REGISTERED(201, "User  registered Successfully"), 
	DATA_RETRIEVED_SUCCESS(200, "Data Retrieved Successfully"), 
	USER_ALREADY_REGISTERED(403, "User already registered"), 
	LOGIN_FAILED(103, "Login Failed"),
	OTP_SENT_SUCCESS(200, "Otp Sent Successfully"), 
	UN_AUTHORIZED(401,"Un authorized User"),
	TOKEN_INVALID(104, "Invalid Token"),
	EMAIL_NOT_REGISTERED(404, "User Not Registered");

	private static final Map<Integer, PetalsStatus> map = new HashMap<>(values().length, 1);
	
	private PetalsStatus(int statusCode, String statusMesssage) {
		this.statusMesssage = statusMesssage;
		this.statusCode = statusCode;
	}

	public int statusCode;
	public String statusMesssage;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMesssage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMesssage = statusMessage;
	}
	
	public static PetalsStatus of(int statusId) {
		PetalsStatus result = map.get(statusId);
		if (result == null) {
			throw new IllegalArgumentException("Invalid error code: " + statusId);
		}
		return result;
	}
}
