package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UpdateConflictException extends RuntimeException {
    private static final long serialVersionUID = 2341867079721111101L;

    public UpdateConflictException() {
        super();
    }

    public UpdateConflictException(String message) {
        super(message);
    }
}
