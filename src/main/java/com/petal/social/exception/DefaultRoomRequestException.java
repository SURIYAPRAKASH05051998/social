package com.petal.social.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when request is invalid.
 */
@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class DefaultRoomRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DefaultRoomRequestException() {
        super();
    }

    public DefaultRoomRequestException(String message) {
        super(message);
    }
}
