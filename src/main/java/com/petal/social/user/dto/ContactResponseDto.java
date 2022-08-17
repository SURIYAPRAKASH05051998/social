package com.petal.social.user.dto;

public class ContactResponseDto {
	
	private String contactName;
	private String contactNumber;
	private boolean isRegistered;
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public boolean isRegistered() {
		return isRegistered;
	}
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	@Override
	public String toString() {
		return "ContactResponseDto [contactName=" + contactName + ", contactNumber=" + contactNumber + ", isRegistered="
				+ isRegistered + "]";
	}
	
	

}
