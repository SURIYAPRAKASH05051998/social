package com.petal.social.user.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petal.social.user.database.dao.UserRecord;

public class UserDetailsRowMapper implements RowMapper<UserRecord> {
	private static final RowMapper<UserRecord> mostOfIt = new BeanPropertyRowMapper<>(UserRecord.class);
	private ObjectMapper objectMapper;

	public UserDetailsRowMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public UserRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserRecord record = mostOfIt.mapRow(rs, rowNum);
		return record;

	}
}
