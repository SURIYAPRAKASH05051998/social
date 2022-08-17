package com.petal.social.util.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseModel<T>  {

    private int statusCode;
    private String statusMessage;
    private T responseModel;
   

	public ResponseModel(int statusCode, String statusMesssage,  T responseModel) {
		this.statusCode = statusCode;
		this.statusMessage=statusMesssage;
		this.responseModel = responseModel;
	}
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	@Override
	public String toString() {
		return "ResponseModel [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", responseModel="
				+ responseModel + "]";
	}
	public T getResponseModel() {
		return responseModel;
	}
	public void setResponseModel(T responseModel) {
		this.responseModel = responseModel;
	}
    
}
