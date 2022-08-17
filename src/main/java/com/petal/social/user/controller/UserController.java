package com.petal.social.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petal.social.user.database.dao.UserRecord;
import com.petal.social.user.database.service.UserService;
import com.petal.social.user.dto.DeviceIdRequestDto;
import com.petal.social.user.dto.UserContactsRequestDto;
import com.petal.social.user.dto.UserContactsResponseDto;
import com.petal.social.user.dto.UserDto;
import com.petal.social.user.dto.UserLoginDto;
import com.petal.social.user.dto.UserRequestDto;
import com.petal.social.user.dto.UserUpdateRequestDto;
import com.petal.social.user.dto.ValidationRequestDto;
import com.petal.social.user.dto.ValidationResponseDto;
import com.petal.social.util.model.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "User Management")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1.0.0/users")
public class UserController {

	@Autowired
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@ApiOperation(value = "Register User")
	@PostMapping("/register")
	public ResponseModel<UserDto> registerUser( @Valid @RequestBody UserRequestDto newUser){
		ResponseModel<UserDto> createUser= userService.createUser(newUser);
		logger.info(createUser.toString());
		return createUser;
		
 }
	@ApiOperation(value = "Update User By User Code")
	@PatchMapping("/update/{user-code}")
	public ResponseModel<UserDto> updateUser(@Valid @RequestBody UserUpdateRequestDto user, @Valid @NonNull @PathVariable(value="user-code") String userCode) {
		user.setUserCode(userCode);
		ResponseModel<UserDto> updateUser = userService.updateUser(user);
		logger.info(updateUser.toString()); 
		return updateUser;
	}
	
	@ApiOperation(value = "Generate Otp")
	@PostMapping("/generate/otp")
	public ResponseModel<ValidationResponseDto> generateOtp(@Valid @RequestBody ValidationRequestDto validationRequestDto) {
		ResponseModel<ValidationResponseDto> generateOtp = userService.generateOtp(validationRequestDto);
		logger.info(generateOtp.toString()); 
		return generateOtp;
	}
	
	@ApiOperation(value = "Get User Detail By Device Id")
	@PostMapping("/get/device-id")
	public ResponseModel<List<UserDto>> getUserByDeviceId(@Valid @NonNull @RequestBody DeviceIdRequestDto deviceIds ) {
		ResponseModel<List<UserDto>> userDto = userService.getUserByDeviceId(deviceIds);
		logger.info(userDto.toString()); 
		return userDto;
	}
	
	@ApiOperation(value = "Get All Users")
	@GetMapping("/get/all-users")
	public ResponseModel<List<UserRecord>> getAllUsers() {
		ResponseModel<List<UserRecord>> userDto = userService.getAllUsers();
		logger.info(userDto.toString()); 
		return userDto;
	}
	

	@ApiOperation(value = "Get User By Mobile Number")
	@GetMapping("/get/mobile/{mobile-no}")
	public ResponseModel<UserDto> getUserByMobileNumber(@Valid @NonNull @PathVariable(value="mobile-no") String mobileNo) {
		ResponseModel<UserDto> userDto = userService.getUserByMobileNumber(mobileNo);
		logger.info(userDto.toString()); 
		return userDto;
	}
	
	@ApiOperation(value = "Get Register and Un Registered User By Contact")
	@PostMapping("/get/contacts")
	public ResponseModel<UserContactsResponseDto> getRegisteredAndUnRegisteredUser(@Valid @RequestBody UserContactsRequestDto contactLists) {
		ResponseModel<UserContactsResponseDto> contactDetail = userService.getContactDetails(contactLists);
		logger.info(contactDetail.toString()); 
		return contactDetail;
	}
	
	@ApiOperation(value = "Delete User By Mobile Number")
	@DeleteMapping("/delete/mobile/{mobile-no}")
	public ResponseModel<UserDto> deleteByMobileNumber(@Valid @NonNull @PathVariable(value="mobile-no") String mobileNo) {
		ResponseModel<UserDto> userDto = userService.deleteUserByMobileNo(mobileNo);
		logger.info(userDto.toString()); 
		return userDto;
	}
	
	@ApiOperation(value = "Register Login")
	@PostMapping("/login")
	public ResponseModel<UserDto> registerLogin( @Valid @RequestBody UserLoginDto userLogin){
		ResponseModel<UserDto> createLogin= userService.createLogin(userLogin);
		logger.info(createLogin.toString());
		return createLogin;
		
 }
	
}
