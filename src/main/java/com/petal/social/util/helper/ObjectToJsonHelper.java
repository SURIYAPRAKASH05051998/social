package com.petal.social.util.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

	@Component
	public class ObjectToJsonHelper {
	    private ObjectMapper databaseFieldObjectMapper;

	    public ObjectToJsonHelper() {
	        this.databaseFieldObjectMapper = new ObjectMapper();
	        this.databaseFieldObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	        this.databaseFieldObjectMapper.registerModule(new JavaTimeModule());

	        SimpleModule module = new SimpleModule();
	        module.addDeserializer(Date.class, new EnhancedDateDeserializer(Date.class));
	        this.databaseFieldObjectMapper.registerModule(module);
	        this.databaseFieldObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    }

	    public String toJSON(Object value) throws JsonProcessingException {
	        return this.databaseFieldObjectMapper.writeValueAsString(value);
	    }

	    public ObjectMapper getObjectMapper() {
	        return this.databaseFieldObjectMapper;
	    }
}
