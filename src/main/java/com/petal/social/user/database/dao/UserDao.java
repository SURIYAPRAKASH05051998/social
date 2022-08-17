package com.petal.social.user.database.dao;

import java.util.List;

public interface UserDao {

	int createUser(UserRecord user);
	
	int updateUser(UserRecord user);
	
	boolean isUserRegistered(String countryCode, String mobileNo);
	
	UserRecord getUserByMobile(String mobileNo);
	
	UserRecord getUserByDeviceId(String deviceId);
	
	List<UserRecord> getAllUsers();
	
	boolean isContactRegistered(String mobileNo);
	
	int deleteUserByMobileNo(String mobileNo);

	boolean isUserRegistered(String countryCode, String mobileNo, String email);
	
	
	public UserRecord getUserByEmail(String email, String password);
}
