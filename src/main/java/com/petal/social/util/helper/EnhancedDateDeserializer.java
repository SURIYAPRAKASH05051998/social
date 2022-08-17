package com.petal.social.util.helper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;


public class EnhancedDateDeserializer extends StdDeserializer<Date> {
	private SimpleDateFormat withMillisconds = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private SimpleDateFormat withoutMilliseconds = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private DateDeserializers.DateDeserializer defaultDeserializer = new DateDeserializers.DateDeserializer();

	public EnhancedDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		try {
			return defaultDeserializer.deserialize(p, ctxt);
		} catch (InvalidFormatException ife) {
			try {
				return withMillisconds.parse(p.getText().trim());
			} catch (ParseException e) {
				try {
					return withoutMilliseconds.parse(p.getText().trim());
				} catch (ParseException e1) {
					throw ife;
				}
			}
		}
	}
}

