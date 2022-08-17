package com.petal.social.user.database.impl;

import java.sql.Types;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.petal.social.user.database.dao.UserDao;
import com.petal.social.user.database.dao.UserRecord;
import com.petal.social.user.database.rowmapper.UserDetailsRowMapper;
import com.petal.social.util.constants.DbConstants;
import com.petal.social.util.helper.DaoHelper;
import com.petal.social.util.helper.ObjectToJsonHelper;
import com.petal.social.util.model.DaoParameter;

@Component
public class UserDaoImpl implements UserDao {

	private DaoHelper daoHelper;
	@Autowired
	private ObjectToJsonHelper objectToJsonHelper;

	public UserDaoImpl() {

	}

	@Autowired
	public UserDaoImpl(DaoHelper daoHelper) {
		this.daoHelper = daoHelper;
		}

	@Override
	public int createUser(UserRecord user) {
		return daoHelper.executeCallForInt(DbConstants.CREATE_USER, 
				new DaoParameter(user.getUserName(), Types.VARCHAR),
				new DaoParameter(user.getUserEmail(), Types.VARCHAR),
				new DaoParameter(user.getUserPassword(), Types.VARCHAR),
				new DaoParameter(user.getCountryCode(), Types.VARCHAR),
				new DaoParameter(user.getUserMobile(), Types.VARCHAR));

	}

	@Override
	public boolean isUserRegistered(String countryCode, String mobileNo, String email) {
		return daoHelper.executeCallForBoolean(DbConstants.IS_MOBILE_EMAIL_REGISTERED,
				new DaoParameter(countryCode, Types.VARCHAR),
				new DaoParameter(mobileNo, Types.VARCHAR),
				new DaoParameter(email, Types.VARCHAR));
	}

	@Override
	public UserRecord getUserByMobile(String mobileNo) {
		return daoHelper.executeCallForObject(DbConstants.GET_USER_BY_MOBILE,
				new UserDetailsRowMapper(objectToJsonHelper.getObjectMapper()), new DaoParameter(mobileNo, Types.VARCHAR));
	}

	@Override
	public int updateUser(UserRecord user) {
		return daoHelper.executeCallForInt(DbConstants.UPDATE_USER,
				new DaoParameter(user.getUserCode(), Types.OTHER),
				new DaoParameter(user.getUserName(), Types.VARCHAR),
				new DaoParameter(user.getUserEmail(), Types.VARCHAR),
				new DaoParameter(user.getCountryCode(), Types.VARCHAR),
				new DaoParameter(user.getUserMobile(), Types.VARCHAR)
		);
	}

	@Override
	public UserRecord getUserByDeviceId(String deviceId) {
		return daoHelper.executeCallForObject(DbConstants.GET_USER_BY_DEVICE_ID,
				new UserDetailsRowMapper(objectToJsonHelper.getObjectMapper()), new DaoParameter(deviceId, Types.OTHER));
	}

	@Override
	public List<UserRecord> getAllUsers() {
		return daoHelper.executeCallForObjects(DbConstants.GET_ALL_USERS,
				new UserDetailsRowMapper(objectToJsonHelper.getObjectMapper()));
	}

	@Override
	public boolean isContactRegistered(String mobileNo) {
		return daoHelper.executeCallForBoolean(DbConstants.IS_CONTACT_REGISTERED,
				new DaoParameter(mobileNo, Types.VARCHAR));
	}

	@Override
	public int deleteUserByMobileNo(String mobileNo) {
		return daoHelper.executeCallForInt(DbConstants.DELETE_USER_BY_MOBILE_NO,
				new DaoParameter(mobileNo, Types.VARCHAR));
	}

	@Override
	public boolean isUserRegistered(String countryCode, String mobileNo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLoginEmailRegistered(String emailId, String passWord) {
		return daoHelper.executeCallForBoolean(DbConstants.IS_LOGIN_REGISTERED,
				new DaoParameter(emailId, Types.VARCHAR),
				new DaoParameter(passWord, Types.VARCHAR));
	}
	@Override
	public UserRecord getUserByEmail(String email, String password) {
		return daoHelper.executeCallForObject(DbConstants.GET_USER_BY_EMAIL,
				new UserDetailsRowMapper(objectToJsonHelper.getObjectMapper()), 
				new DaoParameter(email, Types.VARCHAR),
				new DaoParameter(password, Types.VARCHAR));
	}

	public boolean isLoginEmailCheck(String emailId) {
		return daoHelper.executeCallForBoolean(DbConstants.IS_EMAIL_REGISTERED,
				new DaoParameter(emailId, Types.VARCHAR));
	}

}
