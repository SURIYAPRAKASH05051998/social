package com.petal.social.user.database.service;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.petal.social.user.database.dao.UserRecord;
import com.petal.social.user.database.impl.UserDaoImpl;
import com.petal.social.user.dto.ContactRequestDto;
import com.petal.social.user.dto.ContactResponseDto;
import com.petal.social.user.dto.DeviceIdRequestDto;
import com.petal.social.user.dto.UserContactsRequestDto;
import com.petal.social.user.dto.UserContactsResponseDto;
import com.petal.social.user.dto.UserDto;
import com.petal.social.user.dto.UserLoginDto;
import com.petal.social.user.dto.UserModelConverterClass;
import com.petal.social.user.dto.UserRequestDto;
import com.petal.social.user.dto.UserUpdateRequestDto;
import com.petal.social.user.dto.ValidationRequestDto;
import com.petal.social.user.dto.ValidationResponseDto;
import com.petal.social.util.model.PetalsStatus;
import com.petal.social.util.model.ResponseModel;

@Component
public class UserService {

	@Autowired
	private UserDaoImpl userImpl;

	public ResponseModel<UserDto> createUser(UserRequestDto newUser) {
		try {
			UserRecord userRecord = UserModelConverterClass.convertUserRequestModelToUserRecord(newUser);
			if (isUserRegistered(userRecord.getCountryCode(), userRecord.getUserMobile(), userRecord.getUserEmail())) {
				return new ResponseModel<UserDto>(PetalsStatus.USER_ALREADY_REGISTERED.getStatusCode(),
						PetalsStatus.USER_ALREADY_REGISTERED.getStatusMessage(), new UserDto());
			} else {
				int statusCode = userImpl.createUser(userRecord);
				if (statusCode == PetalsStatus.VALID_RESPONSE.getStatusCode()) {
					return new ResponseModel<UserDto>(PetalsStatus.USER_REGISTERED.getStatusCode(),
							PetalsStatus.USER_REGISTERED.getStatusMessage(), UserModelConverterClass
									.convertUserRecordToUserDto(userImpl.getUserByMobile(userRecord.getUserMobile())));
				} else if (statusCode == PetalsStatus.USER_ALREADY_REGISTERED.getStatusCode()) {
					return new ResponseModel<UserDto>(PetalsStatus.USER_REGISTERED.getStatusCode(),
							PetalsStatus.USER_REGISTERED.getStatusMessage(), new UserDto());
				}
				else {
					return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(),
							PetalsStatus.ERROR.getStatusMessage(), null);
				}
			}
		} catch (Exception ex) {
			return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(),
					new UserDto());
		}
	}

	public boolean isUserRegistered(String countryCode, String phoneNo, String email) {
		return userImpl.isUserRegistered(countryCode, phoneNo, email);
	}

	public ResponseModel<UserDto> updateUser(UserUpdateRequestDto user) {
		try {
			UserRecord userRecord = UserModelConverterClass.convertUserUpdateRequestModelToUserRecord(user);
			int statusCode = userImpl.updateUser(userRecord);
			if (statusCode == PetalsStatus.UPDATED_SUCESS.getStatusCode()) {
				return new ResponseModel<UserDto>(PetalsStatus.UPDATED_SUCESS.getStatusCode(),
						PetalsStatus.UPDATED_SUCESS.getStatusMessage(), UserModelConverterClass
								.convertUserRecordToUserDto(userImpl.getUserByMobile(userRecord.getUserMobile())));

			} else if (statusCode == PetalsStatus.NO_DATA.getStatusCode()) {
				return new ResponseModel<UserDto>(PetalsStatus.NO_DATA.getStatusCode(),
						PetalsStatus.NO_DATA.getStatusMessage(), null);
			} else {
				return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(),
						PetalsStatus.ERROR.getStatusMessage(), null);
			}
		} catch (Exception ex) {
			return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(), null);
		}
	}

	public ResponseModel<ValidationResponseDto> generateOtp(@Valid ValidationRequestDto validationRequestDto) {
		try {
			return new ResponseModel<ValidationResponseDto>(PetalsStatus.OTP_SENT_SUCCESS.getStatusCode(),
					PetalsStatus.OTP_SENT_SUCCESS.getStatusMessage(),
					new ValidationResponseDto(validationRequestDto.getCountryCode(),
							validationRequestDto.getMobileNumber(),
							new DecimalFormat("000000").format(new Random().nextInt(999999))));
		} catch (Exception ex) {
			return new ResponseModel<ValidationResponseDto>(PetalsStatus.ERROR.getStatusCode(),
					PetalsStatus.ERROR.getStatusMessage(), null);
		}
	}

	public ResponseModel<List<UserDto>> getUserByDeviceId(DeviceIdRequestDto deviceIds) {
		try {
			List<UserDto> userDetails = new ArrayList<>();
			for (String deviceId : deviceIds.getDeviceIds()) {
				UserRecord userRecord = userImpl.getUserByDeviceId(deviceId);
				if (userRecord != null) {
					userDetails.add(UserModelConverterClass.convertUserRecordToUserDto(userRecord));
				}
			}
			if (!CollectionUtils.isEmpty(userDetails)) {
				return new ResponseModel<List<UserDto>>(PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusCode(),
						PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusMessage(), userDetails);
			} else {
				return new ResponseModel<List<UserDto>>(PetalsStatus.NO_DATA.getStatusCode(),
						PetalsStatus.NO_DATA.getStatusMessage(), userDetails);
			}
		} catch (Exception ex) {
			return new ResponseModel<List<UserDto>>(PetalsStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(), null);
		}
	}

	public ResponseModel<List<UserRecord>> getAllUsers() {
		try {
			List<UserRecord> userRecords = userImpl.getAllUsers();
			if (!CollectionUtils.isEmpty(userRecords)) {
				return new ResponseModel<List<UserRecord>>(PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusCode(),
						PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusMessage(), userRecords);
			} else {
				return new ResponseModel<List<UserRecord>>(PetalsStatus.NO_DATA.getStatusCode(),
						PetalsStatus.NO_DATA.getStatusMessage(), null);
			}
		} catch (Exception ex) {
			return new ResponseModel<List<UserRecord>>(PetalsStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(),
					null);
		}
	}

	public ResponseModel<UserDto> getUserByMobileNumber(@Valid String mobileNo) {
		try {
			UserRecord userRecord = userImpl.getUserByMobile(mobileNo);
			if (userRecord != null) {
				return new ResponseModel<UserDto>(PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusCode(),
						PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusMessage(),
						UserModelConverterClass.convertUserRecordToUserDto(userRecord));
			} else {
				return new ResponseModel<UserDto>(PetalsStatus.NO_DATA.getStatusCode(),
						PetalsStatus.NO_DATA.getStatusMessage(), new UserDto());
			}
		} catch (Exception ex) {
			return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(),
					new UserDto());
		}
	}

	public ResponseModel<UserContactsResponseDto> getContactDetails(@Valid UserContactsRequestDto contactList) {
		List<ContactResponseDto> contactLists = new ArrayList<>();
		UserContactsResponseDto registerUserDto = new UserContactsResponseDto();
		if (!CollectionUtils.isEmpty(contactList.getContactLists())) {
			for (ContactRequestDto contactDto : contactList.getContactLists()) {
				boolean isRegistered = userImpl.isContactRegistered(contactDto.getContactNumber());
				ContactResponseDto contactResponseDto = new ContactResponseDto();
				contactResponseDto.setContactName(contactDto.getContactName());
				contactResponseDto.setContactNumber(contactDto.getContactNumber());
				contactResponseDto.setRegistered(isRegistered);
				contactLists.add(contactResponseDto);
			}
			registerUserDto.setContactLists(contactLists);
		}
		return new ResponseModel<UserContactsResponseDto>(PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusCode(),
				PetalsStatus.DATA_RETRIEVED_SUCCESS.getStatusMessage(), registerUserDto);
	}

	public ResponseModel<UserDto> deleteUserByMobileNo(@Valid String mobileNo) {
		try {
			int status = userImpl.deleteUserByMobileNo(mobileNo);
			if (PetalsStatus.DELETED_SUCCESS.getStatusCode() == status) {
				return new ResponseModel<UserDto>(PetalsStatus.DELETED_SUCCESS.getStatusCode(),
						PetalsStatus.DELETED_SUCCESS.getStatusMessage(), null);
			} else if (PetalsStatus.NO_DATA.getStatusCode() == status) {
				return new ResponseModel<UserDto>(PetalsStatus.NO_DATA.getStatusCode(),
						PetalsStatus.NO_DATA.getStatusMessage(), null);
			} else {
				return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(),
						PetalsStatus.ERROR.getStatusMessage(), null);
			}
		} catch (Exception ex) {
			return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(),
					new UserDto());
		}

	}

	
	 public ResponseModel<UserDto> createLogin(@Valid UserLoginDto userLogin)
	  {
		 try 
		 { 
			 UserRecord userRecord =UserModelConverterClass.convertUserRequestModelToUserLogin(userLogin); 
			 if(isLoginEmailCheck(userRecord.getUserEmail()))
			 {
				 return new ResponseModel<UserDto>(PetalsStatus.EMAIL_NOT_REGISTERED.getStatusCode(),
						  PetalsStatus.EMAIL_NOT_REGISTERED.getStatusMessage(), null); 
			 }
			 else if(isLoginEmailRegistered(userRecord.getUserEmail(),userRecord.getUserPassword())) {
		return new ResponseModel<UserDto>(PetalsStatus.VALID_RESPONSE.getStatusCode(),
			  PetalsStatus.VALID_RESPONSE.getStatusMessage(),UserModelConverterClass
				.convertUserRecordToUserDto(userImpl.getUserByEmail(userRecord.getUserEmail(),userRecord.getUserPassword())));
	  } else{ 
			 return new ResponseModel<UserDto>(PetalsStatus.UN_AUTHORIZED.getStatusCode(),
	  PetalsStatus.UN_AUTHORIZED.getStatusMessage(), null); 
			 }
		 } 
		 catch (Exception ex) { 
			 return new ResponseModel<UserDto>(PetalsStatus.ERROR.getStatusCode(),
					 ex.getLocalizedMessage(),new UserDto()); } 
		 } 
	 public boolean isLoginEmailRegistered(String emailId, String passWord) { 
		 return userImpl.isLoginEmailRegistered(emailId, passWord); }
	 
	 public boolean isLoginEmailCheck(String emailId) { 
		 return userImpl.isLoginEmailCheck(emailId); }
	
}
