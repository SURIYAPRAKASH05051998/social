package com.petal.social.util.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ErrorResponse {

	public static class Error {

		public static Error of(PetalsStatus e) {
			return new Error(e.statusCode, e.statusMesssage);
		}

		public Error(int purpleErrorCode, String errorMessage) {
			this.code = purpleErrorCode;
			this.message = errorMessage;
		}

		private int code;
		private String message;

		public int getCode() {
			return code;
		}
		
		public String getMessage() {
			return message;
		}
	}
	
	public ErrorResponse(List<Error> el) {
		this.errors = el;
	}

	public static ErrorResponse of (Error e, Error...errors) {
	
		ArrayList<Error> el = new ArrayList<>(errors.length + 1);
		el.add(e);
		if (errors.length > 0) {
			el.addAll(Arrays.asList(errors));
		}
		
		return new ErrorResponse(el);
	}
	
	private List<Error> errors;
}
