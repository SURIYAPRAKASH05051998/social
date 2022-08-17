package com.petal.social.user.dto;

import java.util.List;

public class UserContactsRequestDto {
	
		private List<ContactRequestDto> contactLists;

		public List<ContactRequestDto> getContactLists() {
			return contactLists;
		}

		public void setContactLists(List<ContactRequestDto> contactLists) {
			this.contactLists = contactLists;
		}
		

}
