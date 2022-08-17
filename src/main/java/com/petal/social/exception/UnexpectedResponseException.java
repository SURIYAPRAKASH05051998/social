package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnexpectedResponseException extends RuntimeException {
    private static final long serialVersionUID = 3086527861311663507L;

    public UnexpectedResponseException() {
        super();
    }

    public UnexpectedResponseException(String message) {
        super(message);
    }
}
