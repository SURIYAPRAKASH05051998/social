package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateKeyConflictException extends RuntimeException {

    private static final long serialVersionUID = -985303818733443462L;

    public DuplicateKeyConflictException() {
        super();
    }

    public DuplicateKeyConflictException(String message) {
        super(message);
    }

    public DuplicateKeyConflictException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
