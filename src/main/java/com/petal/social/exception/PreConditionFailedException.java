package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when request is invalid.
 */
@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PreConditionFailedException extends RuntimeException {
    private static final long serialVersionUID = -7341587619203219634L;

    public PreConditionFailedException() {
        super();
    }

    public PreConditionFailedException(String message) {
        super(message);
    }
}
