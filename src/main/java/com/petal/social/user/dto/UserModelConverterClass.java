package com.petal.social.user.dto;

import java.util.UUID;

import javax.validation.Valid;

import com.petal.social.user.database.dao.UserRecord;

public class UserModelConverterClass {

	public static UserRecord convertUserRequestModelToUserRecord(UserRequestDto userRequestModel) {
		UserRecord userRecord = null;
		if (userRequestModel != null) {
			userRecord = new UserRecord();
			userRecord.setUserName(userRequestModel.getUserName());
			userRecord.setUserEmail(userRequestModel.getUserEmail());
			userRecord.setCountryCode(userRequestModel.getCountryCode());
			userRecord.setUserMobile(userRequestModel.getMobileNumber());
			userRecord.setUserPassword(userRequestModel.getUserPassword());
			//userRecord.setDeviceId(userRequestModel.getDeviceUUID());
		}
		return userRecord;
	}
	
	public static UserRecord convertUserUpdateRequestModelToUserRecord(UserUpdateRequestDto userRequestModel) {
		UserRecord userRecord = null;
		if (userRequestModel != null) {
			userRecord = new UserRecord();
			userRecord.setUserCode(UUID.fromString(userRequestModel.getUserCode()));
			userRecord.setUserName(userRequestModel.getUserName());
			userRecord.setUserEmail(userRequestModel.getUserEmail());
			userRecord.setCountryCode(userRequestModel.getCountryCode());
			userRecord.setUserMobile(userRequestModel.getMobileNumber());
			userRecord.setDeviceId(userRequestModel.getDeviceUUID());
		}
		return userRecord;
	}
	
	public static UserDto convertUserRecordToUserDto(UserRecord userRecord) {
		UserDto userDto = null;
		if (userRecord != null) {
			userDto = new UserDto();
			userDto.setUserName(userRecord.getUserName());
			userDto.setUserEmail(userRecord.getUserEmail());
			userDto.setUserPassword(userRecord.getUserPassword());
			userDto.setCountryCode(userRecord.getCountryCode());
			userDto.setUserMobile(userRecord.getUserMobile());
			userDto.setDeviceId(userRecord.getDeviceId());
			userDto.setUserCode(userRecord.getUserCode());
			userDto.setCreationDate(userRecord.getCreationDate());
			userDto.setModificationDate(userRecord.getModificationDate());
		}
		return userDto;
	}

	public static UserRecord convertUserRequestModelToUserLogin(@Valid UserLoginDto userLogin) {
		UserRecord userRecord = null;
		if (userLogin != null) {
			userRecord = new UserRecord();
			userRecord.setUserEmail(userLogin.getUserEmail());
			userRecord.setUserPassword(userLogin.getUserPassword());
			
		}
		return userRecord;
	}
	
	
	}
