package com.petal.social.user.dto;

import java.util.List;

public class UserContactsResponseDto {

	private List<ContactResponseDto> contactLists;

	public List<ContactResponseDto> getContactLists() {
		return contactLists;
	}

	@Override
	public String toString() {
		return "UserContactsResponseDto [contactLists=" + contactLists + "]";
	}

	public void setContactLists(List<ContactResponseDto> contactLists) {
		this.contactLists = contactLists;
	}
	
	
}
