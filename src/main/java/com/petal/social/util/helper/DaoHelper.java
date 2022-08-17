package com.petal.social.util.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petal.social.exception.DuplicateKeyConflictException;
import com.petal.social.exception.NotFoundException;
import com.petal.social.exception.PreConditionFailedException;
import com.petal.social.exception.UnexpectedResponseException;
import com.petal.social.exception.UnprocessableEntityException;
import com.petal.social.exception.UpdateConflictException;
import com.petal.social.util.model.DaoParameter;


@Component
public class DaoHelper {
	private Logger log = LoggerFactory.getLogger(DaoHelper.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Integer> singleColumnIntegerMapper = (resultSet, i) -> resultSet.getInt(1);

	private RowMapper<String> singleColumnStringMapper = (resultSet, i) -> resultSet.getString(1);

	private RowMapper<UUID> singleColumnUuidMapper = (resultSet, i) -> UUID.fromString(resultSet.getString(1));

	private RowMapper<Date> singleColumnDateMapper = (resultSet, i) -> resultSet.getDate(1);

	private RowMapper<Boolean> singleColumnBooleanMapper = (resultSet, i) -> resultSet.getBoolean(1);

	private static String buildSql(String functionName, DaoParameter... params) {
		int count = params == null ? 0 : params.length;
		return "select * from " + functionName + "(" + StringUtils.repeat("?", ",", count) + ")";
	}

	private static Object[] buildArgs(DaoParameter... params) {
		int count = params == null ? 0 : params.length;
		Object[] result = new Object[count];
		for (int i = 0; i < count; i++) {
			result[i] = params[i].getValue();
		}
		return result;
	}

	private static int[] buildArgsTypes(DaoParameter... params) {
		int count = params == null ? 0 : params.length;
		int[] result = new int[count];
		for (int i = 0; i < count; i++) {
			result[i] = params[i].getType();
		}
		return result;
	}

	public static <T> List<T> fromJsonArray(ObjectMapper objectMapper, Array array, Class<T> type) throws SQLException {
		List<T> result = new LinkedList<>();
		for (String json : (String[]) array.getArray()) {
			try {
				result.add(objectMapper.readValue(json, type));
			} catch (IOException e) {
				throw new SQLException("Unable to process json item. Type: " + type + ", Source: " + json + ", Reason: "
						+ e.getMessage());
			}
		}
		return result;
	}

	public static DaoParameter uuid(UUID uuid) {
		return new DaoParameter(uuid, Types.OTHER);
	}

	public static DaoParameter varchar(String title) {
		return new DaoParameter(title, Types.VARCHAR);
	}

	public static DaoParameter json(String json) {
		return new DaoParameter(json, Types.OTHER);
	}

	public static DaoParameter longvarchar(String desc) {
		return new DaoParameter(desc, Types.LONGVARCHAR);
	}

	public static DaoParameter id(int id) {
		return smallInt(id);
	}

	public static DaoParameter smallInt(Integer smallInt) {
		return new DaoParameter(smallInt, Types.SMALLINT);
	}

	public static DaoParameter integer(Integer integer) {
		return new DaoParameter(integer, Types.INTEGER);
	}

	public static DaoParameter decimal(BigDecimal decimal) {
		return new DaoParameter(decimal, Types.DECIMAL);
	}

	public static DaoParameter booleanType(boolean bool) {
		return new DaoParameter(bool, Types.BOOLEAN);
	}

	public static DaoParameter array(String[] array, DaoHelper helper) {
		return new DaoParameter(helper.createArray(array), Types.ARRAY);
	}

	public static DaoParameter arrayUuid(UUID[] array, DaoHelper helper) {
		return new DaoParameter(helper.createArray(array), Types.ARRAY);
	}

	public int executeInsert(String functionName, DaoParameter... params) {
		try {
			return executeCallForInt(functionName, params);
		} catch (DuplicateKeyException e) {
			String message = e.getMessage();
			log.warn("Unexpected response from {}. {}. {}", functionName, message, params, e);
			throw new DuplicateKeyConflictException("Insert failed because the of a duplicate key.", e);
		} catch (UncategorizedSQLException usqle) {
			String message = usqle.getSQLException().getMessage();
			if (StringUtils.startsWith(message, "ERROR: DuplicateKeyException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new DuplicateKeyConflictException("Insert failed because the of a duplicate key.", usqle);
			} else {
				log.error("Unexpected response from {}. {}", functionName, params, usqle);
				throw new UnexpectedResponseException("Cannot insert record.");
			}
		}
	}
	
	public <T> T executeInsertForObject(String functionName,RowMapper<T>rowMapper, DaoParameter... params) {
		try {
			return executeCallForObject(functionName, rowMapper, params);
		} catch (DuplicateKeyException e) {
			String message = e.getMessage();
			log.warn("Unexpected response from {}. {}. {}", functionName, message, params, e);
			throw new DuplicateKeyConflictException("Insert failed because the of a duplicate key.", e);
		} catch (UncategorizedSQLException usqle) {
			String message = usqle.getSQLException().getMessage();
			if (StringUtils.startsWith(message, "ERROR: DuplicateKeyException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new DuplicateKeyConflictException("Insert failed because the of a duplicate key.", usqle);
			} else {
				log.error("Unexpected response from {}. {}", functionName, params, usqle);
				throw new UnexpectedResponseException("Cannot insert record.");
			}
		}
	}

	public int executeDelete(String functionName, DaoParameter... params) {
		try {
			return executeCallForIntExpectingNonZero(functionName, params);
		} catch (DataIntegrityViolationException e) {
			log.warn("This record cannot be deleted because it is being referenced by records in other tables.", e);
			throw new UnprocessableEntityException(
					"This record cannot be deleted because it is being referenced by records in other tables.");
		}
	}

	public <T> T  executeDeleteForObject(String functionName, RowMapper<T>rowMapper, DaoParameter... params) {
		try {
			return executeCallForObject(functionName, rowMapper, params);
		} catch (DataIntegrityViolationException e) {
			log.warn("This record cannot be deleted because it is being referenced by records in other tables.", e);
			throw new UnprocessableEntityException(
					"This record cannot be deleted because it is being referenced by records in other tables.");
		}
	}
	public int executeUpdate(String functionName, DaoParameter... params) {
		try {
			return executeCallForIntExpectingNonZero(functionName, params);
		} catch (DuplicateKeyException dke) {
			String message = dke.getMessage();
			log.warn("Unexpected response from {}. {}. {}", functionName, message, params, dke);
			throw new DuplicateKeyConflictException("Updated failed because of a duplicate key.", dke);
		} catch (UncategorizedSQLException usqle) {
			String message = usqle.getSQLException().getMessage();
			if (StringUtils.startsWith(message, "ERROR: UpdateConflictException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new UpdateConflictException(
						"Update failed because the record had been recently changed by another person.");
			} else if (StringUtils.startsWith(message, "ERROR: NotFoundException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new NotFoundException("Update failed because the record was not found.");
			} else if (StringUtils.startsWith(message, "ERROR: PreConditionFailedException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new PreConditionFailedException("Update failed because of invalid pre-conditions.");
			} else {
				log.error("Unexpected response from {}. {}", functionName, params, usqle);
				throw new UnexpectedResponseException("Cannot update record.");
			}
		}
	}

	public <T> T  executeUpdateForObject(String functionName,RowMapper<T>rowMapper, DaoParameter... params) {
		try {
			return executeCallForObject(functionName, rowMapper, params);
		} catch (DuplicateKeyException dke) {
			String message = dke.getMessage();
			log.warn("Unexpected response from {}. {}. {}", functionName, message, params, dke);
			throw new DuplicateKeyConflictException("Updated failed because of a duplicate key.", dke);
		} catch (UncategorizedSQLException usqle) {
			String message = usqle.getSQLException().getMessage();
			if (StringUtils.startsWith(message, "ERROR: UpdateConflictException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new UpdateConflictException(
						"Update failed because the record had been recently changed by another person.");
			} else if (StringUtils.startsWith(message, "ERROR: NotFoundException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new NotFoundException("Update failed because the record was not found.");
			} else if (StringUtils.startsWith(message, "ERROR: PreConditionFailedException")) {
				log.warn("Unexpected response from {}. {}. {}", functionName, message, params, usqle);
				throw new PreConditionFailedException("Update failed because of invalid pre-conditions.");
			} else {
				log.error("Unexpected response from {}. {}", functionName, params, usqle);
				throw new UnexpectedResponseException("Cannot update record.");
			}
		}
	}
	public void executeCall(String functionName, RowCallbackHandler callback, DaoParameter... params) {
		log.debug("Calling: {}", functionName);

		Object[] args = DaoHelper.buildArgs(params);
		int[] argTypes = DaoHelper.buildArgsTypes(params);
		String sql = DaoHelper.buildSql(functionName, params);
		jdbcTemplate.query(sql, args, argTypes, callback);
	}

	public int executeCallForInt(String functionName, DaoParameter... params) {
		log.debug("Calling: {}", functionName);

		Object[] args = DaoHelper.buildArgs(params);
		int[] argTypes = DaoHelper.buildArgsTypes(params);
		String sql = DaoHelper.buildSql(functionName, params);
		List<Integer> results = jdbcTemplate.query(sql, args, argTypes, singleColumnIntegerMapper);
		if (results.isEmpty()) {
			throw new PreConditionFailedException("The function " + functionName + " returned no rows.");
		}
		return results.get(0);
	}

	private int executeCallForIntExpectingNonZero(String functionName, DaoParameter... params) {
		log.debug("Calling: {}", functionName);

		Object[] args = DaoHelper.buildArgs(params);
		int[] argTypes = DaoHelper.buildArgsTypes(params);
		String sql = DaoHelper.buildSql(functionName, params);
		List<Integer> results = jdbcTemplate.query(sql, args, argTypes, singleColumnIntegerMapper);
		int rowsAffected = results.get(0);
		if (rowsAffected == 0) {
			log.error("Unexpected response from {}. Zero records were updated. {}", functionName, params);
			throw new NotFoundException();
		}
		return rowsAffected;
	}

	public boolean executeCallForBoolean(String functionName, DaoParameter... params) {
		log.debug("Calling: {}", functionName);

		Object[] args = DaoHelper.buildArgs(params);
		int[] argTypes = DaoHelper.buildArgsTypes(params);
		String sql = DaoHelper.buildSql(functionName, params);
		List<Boolean> results = jdbcTemplate.query(sql, args, argTypes, singleColumnBooleanMapper);
		if (results.isEmpty()) {
			throw new PreConditionFailedException("The function " + functionName + " returned no rows.");
		}
		return results.get(0);
	}

	public String executeCallForString(String functionName, DaoParameter... params) {
		List<String> results = executeCallForStrings(functionName, params);
		if (results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public Date executeCallForDate(String functionName, DaoParameter... params) {
		return executeCallForObject(functionName, singleColumnDateMapper, params);
	}
	public List<UUID> executeCallForUuids(String functionName, DaoParameter... params) {
		return executeCallForObjects(functionName, singleColumnUuidMapper, params);
	}

	public List<String> executeCallForStrings(String functionName, DaoParameter... params) {
		return executeCallForObjects(functionName, singleColumnStringMapper, params);
	}

	public List<Integer> executeCallForIntegers(String functionName, DaoParameter... params) {
		return executeCallForObjects(functionName, singleColumnIntegerMapper, params);
	}

	public <T> T executeCallForObject(String functionName, RowMapper<T> rowMapper, DaoParameter... params) {
		log.debug("Calling: {}", functionName);

		Object[] args = DaoHelper.buildArgs(params);
		int[] argTypes = DaoHelper.buildArgsTypes(params);
		String sql = DaoHelper.buildSql(functionName, params);
		List<T> results = jdbcTemplate.query(sql, args, argTypes, rowMapper);
		if (results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public <T> List<T> executeCallForObjects(String functionName, RowMapper<T> rowMapper, DaoParameter... params) {
		log.debug("Calling: {}", functionName);

		Object[] args = DaoHelper.buildArgs(params);
		int[] argTypes = DaoHelper.buildArgsTypes(params);
		String sql = DaoHelper.buildSql(functionName, params);
		return jdbcTemplate.query(sql, args, argTypes, rowMapper);
	}

	public <K, T> Map<K, List<T>> executeCallForMap(String functionName, RowMapper<Pair<K, T>> rowMapper,
			DaoParameter... params) {
		log.debug("Calling: {}", functionName);

		Map<K, List<T>> result = new HashMap<>();
		Object[] args = DaoHelper.buildArgs(params);
		int[] argTypes = DaoHelper.buildArgsTypes(params);
		String sql = DaoHelper.buildSql(functionName, params);
		List<Pair<K, T>> pairs = jdbcTemplate.query(sql, args, argTypes, rowMapper);
		for (Pair<K, T> pair : pairs) {
			List<T> itemList = result.computeIfAbsent(pair.getKey(), k -> new LinkedList<>());
			itemList.add(pair.getValue());
		}
		return result;
	}



	public Array createArray(short[] elements) {
		if (elements == null) {
			return createArray("smallint", new Short[0]);
		}
		return createArray("smallint", ArrayUtils.toObject(elements));
	}

	public Array createArray(Short[] elements) {
		if (elements == null) {
			return createArray("smallint", new Short[0]);
		}
		return createArray("smallint", elements);
	}

	public Array createArray(int[] elements) {
		if (elements == null) {
			return createArray("int", new Integer[0]);
		}
		return createArray("int", ArrayUtils.toObject(elements));
	}

	public Array createArray(Integer[] elements) {
		if (elements == null) {
			return createArray("int", new Integer[0]);
		}
		return createArray("int", elements);
	}

	public Array createArray(Boolean[] elements) {
		if (elements == null) {
			return createArray("boolean", new Boolean[0]);
		}
		return createArray("boolean", elements);
	}

	public Array createArray(UUID[] elements) {
		if (elements == null) {
			return createArray("uuid", new UUID[0]);
		}
		return createArray("uuid", elements);
	}

	public Array createArray(Collection<UUID> elements) {
		if (elements == null) {
			return createArray("uuid", new UUID[0]);
		}

		return createArray("uuid", elements.toArray(new UUID[0]));
	}

	public Array createArray(String[] elements) {
		if (elements == null) {
			return createArray("varchar", new String[0]);
		}
		return createArray("varchar", elements);
	}

	private Array createArray(String type, Object[] elements) {
		Connection connection = null;
		DataSource dataSource = jdbcTemplate.getDataSource();
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			return connection.createArrayOf(type, elements);
		} catch (SQLException e) {
			log.error("Unable to create sql array {} using type {}.", elements, type, e);
			throw new RuntimeException("Unable to create SQL array,", e);
		} finally {
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
	}

	public <T> T safeReturn(T record, UUID uuid, String typeName) throws NotFoundException {
		if (record != null) {
			return record;
		}

		log.error("{} not found: {}", typeName, uuid.toString());
		throw new NotFoundException(String.format("%s not found", typeName));
	}

	public <T> T safeReturn(T record, String code, String typeName) throws NotFoundException {
		if (record != null) {
			return record;
		}

		log.error("{} not found: {}", typeName, code);
		throw new NotFoundException(String.format("%s not found", typeName));
	}

	public <T> T safeReturn(T record, int id, String typeName) throws NotFoundException {
		if (record != null) {
			return record;
		}

		log.error("{} not found: {}", typeName, id);
		throw new NotFoundException(String.format("%s not found", typeName));
	}

	public <T> T safeReturn(T record, String typeName) throws NotFoundException {
		if (record != null) {
			return record;
		}

		log.error("{} not found: {}", typeName);
		throw new NotFoundException(String.format("%s not found", typeName));
	}
}
